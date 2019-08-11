package com.store.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.store.Entity.Cache;
import com.store.Entity.User;
import com.store.Service.SettingService;
import com.store.Service.UserService;
import com.store.Util.LocalLock;

@RestController
@RequestMapping("set")
@Transactional(propagation = Propagation.REQUIRED)
public class SettingController {

	
	private @Resource SettingService SettingService;
	private @Resource UserService  UserService;
	
	@Value("${resources_path}")
	private String uploadAddress;
	
	
	/**
	 * 修改密码
	 * @param id
	 * @param oldpass
	 * @param newpass
	 * @return
	 */
	@RequestMapping(value="updatepassword/{id}/{oldpass}/{newpass}")
	public Map<String, Object> updatePassword(
			@PathVariable("id") Integer id,
			@PathVariable("oldpass") String oldpass,
			@PathVariable("newpass") String newpass){
		Map<String, Object> map=new HashMap<String, Object>();
		Integer result = SettingService.updatePassword(id, oldpass, newpass);
		if (result==1) {
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
	}
	
	
	/**
	 * 修改用户昵称
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "updatenickname/{id}/{name}")
	public Map<String, Object> updatenickname(@PathVariable("id") Integer id,@PathVariable("name") String name){
		Map<String, Object> map=new HashMap<>();
		Integer result = SettingService.updateName(id, name);
		if (result==1) {
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
	}
	
	
	
	/**
	 * 上传头像图片
	 * @param session
	 * @param request
	 * @param response
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping("updatephoto")
	public void uploadPicture(HttpSession session,HttpServletRequest request, HttpServletResponse response, PrintWriter writer)
			throws Exception {
		String uidStr=request.getParameter("uid");
		Integer uid=Integer.parseInt(uidStr);
		User user = UserService.queryById(uid);
		// 获取从前台传过来得图片
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = req.getFile("file");
		String realPath = uploadAddress;//文件保存路径
		try {
			File dir = new File(realPath);
			if (!dir.exists()) {
				dir.mkdir();//创建目录
			}
			File photo = new File(realPath+"head");
			if (!photo.exists()) {
				photo.mkdir();//创建目录
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
			File dir2 = new File(uploadAddress+"head/"+user.getEmail()+".jpeg");
			if (dir2.exists()) {
				File file2 =new File(uploadAddress+"head/"+user.getEmail()+".jpeg");//删除旧的文件
				file2.delete();
			}
			File file = new File(realPath+"head", user.getEmail() + ".jpeg");// 将文件的地址和生成的文件名拼在一起
			Files.write(Paths.get(uploadAddress+"head/"+user.getEmail()+".jpeg"), multipartFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 删除照片
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deletephoto/{id}")
	public Map<String, Object> deletephoto(@PathVariable("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		User user = UserService.queryById(id);
		File file2 =new File(uploadAddress+"head/"+user.getEmail()+".jpeg");//删除旧的文件
		file2.delete();
		return map;
	}
	
}
