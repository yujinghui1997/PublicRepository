package com.store.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.store.Entity.Ad;
import com.store.Entity.Cache;
import com.store.Entity.Car;
import com.store.Entity.Shop;
import com.store.Entity.Task;
import com.store.Entity.User;
import com.store.Service.AdService;
import com.store.Service.CacheService;
import com.store.Service.CarService;
import com.store.Service.ShopService;
import com.store.Service.TaskService;
import com.store.Service.UserService;
import com.store.Util.LocalLock;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.javassist.expr.NewArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("shop")
public class ShopController {

	@Value("${resources_path}")
	private String uploadAddress;
	
	@Value("${spring.admin.qq}")
	private String admin;
	
	private @Resource UserService UserService;
	private @Resource ShopService ShopService;
	private @Resource CacheService CacheService;
	private @Resource TaskService TaskService;
	private @Resource AdService AdService;
	private @Resource CarService CarService;
	
	
	/**
	 * 上传图片
	 * @param session
	 * @param request
	 * @param response
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping("upload")
	@Transactional(propagation = Propagation.REQUIRED)
	public void uploadPicture(HttpSession session,HttpServletRequest request, HttpServletResponse response, PrintWriter writer)
			throws Exception {
		// 获取从前台传过来得图片
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = req.getFile("file");
		String houzhu = multipartFile.getContentType();// 获取图片的文件类型
		int one = houzhu.lastIndexOf("/");
		String type = houzhu.substring((one + 1), houzhu.length());// 根据获取到的文件类型截取出图片后缀
		String realPath = uploadAddress;//文件保存路径
		try {
			File dir = new File(realPath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File shop = new File(realPath+"shop");
			if (!shop.exists()) {
				shop.mkdir();
			}
			File help = new File(realPath+"help.txt");
			FileWriter fileWriter=null;
			if (!help.exists()) {
				help.createNewFile();//创建文件
				// 三、向目标文件中写入内容
				// FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
				fileWriter = new FileWriter(help, true);
				fileWriter.append(realPath+" 该文件夹是用来存放微信商城小程序商品、任务、广告及用户头像，删除可能导致程序异常！！！！！");
				fileWriter.flush();
			}
			if (fileWriter!=null) {
				fileWriter.close();
			}
			String filedata = this.dates();// 获取到当前的日期时间用户生成文件名防止文件名重复
			File file = new File(realPath+"shop", filedata + "." + type);// 将文件的地址和生成的文件名拼在一起
			Files.write(Paths.get(uploadAddress+"shop/"+filedata+"."+type), multipartFile.getBytes());
			//图片传到session里
			String uidStr=request.getParameter("uid");
			String typeStr=request.getParameter("type");
			Integer uid=Integer.parseInt(uidStr);
			List<Cache> cache = CacheService.queryOneCache(uid, typeStr);
			if (cache.size()==0) {//创建缓存
				Cache cache2=new Cache();
				cache2.setType(typeStr);
				cache2.setName(filedata + "." + type);
				cache2.setUid(uid);
				CacheService.addCache(cache2);
			}else {//修改缓存
				File file2 =new File(uploadAddress+"shop/"+cache.get(0).getName());//删除旧的文件
				file2.delete();
				CacheService.updateCache(typeStr, filedata + "." + type, uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	// 获取当前日期时间的string类型用于文件名防重复
	public String dates() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	
	/**
	 * 添加商品
	 * @param id
	 * @param title
	 * @param txt
	 * @param price
	 * @param session
	 * @return
	 */
	@LocalLock(key = "addshop:arg[1]")
	@RequestMapping(value="addshop/{id}/{title}/{txt}/{price}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addshop(
			@PathVariable("id") Integer id,
			@PathVariable("title") String title,
			@PathVariable("txt") String txt,
			@PathVariable("price") BigDecimal price){
		List<Cache> photo = CacheService.queryOneCache(id, "photo");
		List<Cache> content = CacheService.queryOneCache(id, "content");
		Map<String, Object> map=new HashMap<>();
		Shop shop=new Shop();
		shop.setPhoto(photo.get(0).getName());
		shop.setTitle(title);
		shop.setTxt(txt);
		shop.setPrice(price);
		shop.setContent(content.get(0).getName());
		shop.setState("unfinish");
		shop.setUid(id);
		Integer result = ShopService.addShop(shop);
		if (result==1) {
			List<Shop> allShop = ShopService.queryAllShop(id);
			map.put("mes", 1);
			map.put("list", allShop);
			CacheService.deleteCache(id);
		}else {
			map.put("mes", 0);
		}
		return map;
	} 
	
	
	/**
	 * 查询用户所有的商品
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "queryshopbyuid/{uid}")
	public Map<String, Object> queryShopByUId(@PathVariable("uid") Integer uid){
		Map<String, Object> map=new HashMap<>();
		List<Shop> queryAllShop = ShopService.queryAllShop(uid);
		map.put("list", queryAllShop);
		return map;
	}
	
	/**
	 *查询商品的详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryshopbyid/{id}")
	public Map<String, Object> queryShopById(@PathVariable("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		Shop shop = ShopService.queryOneShop(id);
		map.put("shop", shop);
		return map;
	}
	

	/**
	 * 查询所有的商品
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "queryall")
	public Map<String, Object> queryAll(){
		Map<String, Object> map=new HashMap<>();
		List<Shop> shops = ShopService.queryAll();
		List<Task> tasks=TaskService.queryAll();
		User adminUser = UserService.queryUserByEmail(admin);
		if (adminUser!=null) {
			List<Ad> ads = AdService.queryAllAd(adminUser.getId());
			if (ads.size()==0) {
				map.put("ad", null);
			}else {
				map.put("ad", ads);
			}
		}else {
			map.put("ad", null);
		}
		map.put("shop", shops);
		map.put("task", tasks);
		map.put("admin", admin);
		return map;
	}
	
	
		
	
	
}
