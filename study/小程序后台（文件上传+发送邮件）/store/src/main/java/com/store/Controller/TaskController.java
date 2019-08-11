package com.store.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.store.Entity.Cache;
import com.store.Entity.Task;
import com.store.Entity.User;
import com.store.Service.CacheService;
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
@RequestMapping("task")
public class TaskController {

	@Value("${resources_path}")
	private String uploadAddress;
	
	private @Resource TaskService TaskService;
	private @Resource CacheService CacheService;
	private @Resource UserService UserService;
	
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
			File Task = new File(realPath+"task");
			if (!Task.exists()) {
				Task.mkdir();
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
			File file = new File(realPath+"task", filedata + "." + type);// 将文件的地址和生成的文件名拼在一起
			Files.write(Paths.get(uploadAddress+"task/"+filedata+"."+type), multipartFile.getBytes());
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
				File file2 =new File(uploadAddress+"task/"+cache.get(0).getName());//删除旧的文件
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
	 * 添加任务
	 * @param id
	 * @param title
	 * @param txt
	 * @param price
	 * @param session
	 * @return
	 */
	@LocalLock(key = "addtask:arg[1]")
	@RequestMapping(value="addtask/{id}/{title}/{txt}/{price}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addTask(
			@PathVariable("id") Integer id,
			@PathVariable("title") String title,
			@PathVariable("txt") String txt,
			@PathVariable("price") BigDecimal price){
		Map<String, Object> map=new HashMap<>();
		User wallet = UserService.queryById(id);//用户钱包
		BigDecimal decimal = wallet.getMoney();
		if (price.compareTo(decimal)==1||decimal.compareTo(BigDecimal.ZERO)<1) {
			map.put("mes","钱包余额不足！");
			return map;
		}
		List<Cache> photo = CacheService.queryOneCache(id, "photo");
		List<Cache> content = CacheService.queryOneCache(id, "content");
		Task Task=new Task();
		Task.setPhoto(photo.get(0).getName());
		Task.setTitle(title);
		Task.setTxt(txt);
		Task.setPrice(price);
		Task.setContent(content.get(0).getName());
		Task.setState("unfinish");
		Task.setUid(id);
		Integer result = TaskService.addTask(Task);
		UserService.updateMoney(id, new BigDecimal("-"+price));//扣除发布任务人的钱
		if (result==1) {
			List<Task> allTask = TaskService.queryAllTask(id);
			map.put("mes", "发布成功！");
			map.put("list", allTask);
			CacheService.deleteCache(id);
		}else {
			map.put("mes", "发布失败！");
		}
		return map;
	} 
	
	/**
	 * 查询用户所有的任务
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "querytaskbyuid/{uid}")
	public Map<String, Object> queryTaskByUId(@PathVariable("uid") Integer uid){
		Map<String, Object> map=new HashMap<>();
		List<Task> queryAllTask = TaskService.queryAllTask(uid);
		map.put("list", queryAllTask);
		return map;
	}
	
	/**
	 *查询任务的详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="querytaskbyid/{id}")
	public Map<String, Object> queryTaskById(@PathVariable("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		Task Task = TaskService.queryOneTask(id);
		map.put("task", Task);
		return map;
	}
	

	/**
	 * 领取任务
	 * @param id
	 * @return
	 */
	@LocalLock(key = "lingqu:arg[0]")
	@RequestMapping(value = "lingqu/{id}/{uid}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> lingqu(@PathVariable("id") Integer id,@PathVariable("uid") Integer uid){
		Map<String, Object> map=new HashMap<>();
		Task task = TaskService.queryOneTask(id);
		BigDecimal money=new BigDecimal(task.getPrice()+"");//任务佣金
		if (uid.equals(task.getUid())) {
			map.put("mes","不能领取自己发布的任务");
			return map;
		}
		UserService.updateMoney(uid, money);//增加自己的钱
		TaskService.updateTask(id);
		map.put("mes", "领取成功！");
		return map;
	} 
	
	
	
	
}
