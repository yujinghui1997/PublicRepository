package com.anchor.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anchor.Entity.Admin;
import com.anchor.Entity.AnchorMes;
import com.anchor.Entity.Log;
import com.anchor.Service.AdminService;
import com.anchor.Service.LogService;
import com.anchor.Util.PreventRepeat;
import com.anchor.Util.DefaultUtil;

@Controller
@RequestMapping("admin")
public class AdminController {

	
	private @Resource AdminService adminService;
	private @Resource LogService LogService;
	//
	@RequestMapping("delSession")
	@ResponseBody
	public Map<String, Object> delSession(HttpSession session){
		session.removeAttribute("oldUrlParams");
		Map<String, Object> map=new HashMap<>();
		String sessionId=session.getId();
		Integer id=(Integer)session.getAttribute("id");
		if (id==null) {
			map.put("err", 0);
			session.removeAttribute("id");
			return map;
		}
		Admin admin = adminService.queryById(id);
		if (!sessionId.equals(admin.getSessionid())) {
			map.put("err", 0);
			map.put("mes", "多方登录");
			session.removeAttribute("id");
		}else {
			map.put("err", 1);
		}
		return map;
	}
	
	/**
	 * 管理员登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	@PreventRepeat
	public Map<String, Object> login(HttpSession session,@RequestParam("username") String username,@RequestParam("password") String password){
		Map< String, Object> map=new HashMap<String, Object>();
		Admin login = adminService.login(username, password);
		if (login!=null) {
			session.setAttribute("id", login.getId());
			adminService.updataSessionId(session.getId(), username, password);
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
 	}
	
	

	/**
	 * 查询所有数据
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("queryAll")
	public String queryAll(ModelMap modelMap,
			@RequestParam("page") Integer page,
			@RequestParam(value = "id",required = false) Integer id,@RequestParam(value="data",required = false) String data){
		if (page<0) {
			page=0;
		}
		if (page>0) {
			page=page-1;
		}
		List<AnchorMes> anchors=null;
		Integer anchorMaxPage=0;
		Integer logMaxPage=0;
		if (data==null||data.equals("")) {
			anchors= adminService.queryAll(page*DefaultUtil.defaultPage,DefaultUtil.defaultPage);
			Integer anchorCount= adminService.queryMaxPage();
			if (anchorCount%DefaultUtil.defaultPage==0) {
				anchorMaxPage=anchorCount/DefaultUtil.defaultPage;
			}else {
				anchorMaxPage=anchorCount/DefaultUtil.defaultPage+1;
			}
		}else {
			if (data.matches(DefaultUtil.name)) {
				anchors = adminService.queryAllSouName("%"+data+"%",page*DefaultUtil.defaultPage,DefaultUtil.defaultPage);
				Integer result=adminService.queryMaxPageName("%"+data+"%");
				if (result%DefaultUtil.defaultPage==0) {
					anchorMaxPage=result/DefaultUtil.defaultPage;
				}else {
					anchorMaxPage=result/DefaultUtil.defaultPage+1;
				}
			}else {
				anchors = adminService.queryAllSouPhone("%"+data+"%",page*DefaultUtil.defaultPage,DefaultUtil.defaultPage);
				Integer result=adminService.queryMaxPagePhone("%"+data+"%");
				if (result%DefaultUtil.defaultPage==0) {
					anchorMaxPage=result/DefaultUtil.defaultPage;
				}else {
					anchorMaxPage=result/DefaultUtil.defaultPage+1;
				}
			}
		}
		List<Log> logs = LogService.queryAll(page*DefaultUtil.defaultPage,DefaultUtil.defaultPage);
		Integer logCount= LogService.queryMaxPage();
		if (logCount%DefaultUtil.defaultPage==0) {
			logMaxPage=logCount/DefaultUtil.defaultPage;
		}else {
			logMaxPage=logCount/DefaultUtil.defaultPage+1;
		}
		List<AnchorMes> newAnchors=new ArrayList<AnchorMes>();
		List<Log> moneys = adminService.querySumMoney();
		for (int i = 0; i < anchors.size(); i++) {
			AnchorMes anchorMes=new AnchorMes();
			anchorMes.setHsNickname(anchors.get(i).getHsNickname());
			anchorMes.setHsNumber(anchors.get(i).getHsNumber());
			Integer id2 = anchors.get(i).getId();
			anchorMes.setId(id2);
			anchorMes.setMoney(anchors.get(i).getMoney());
			anchorMes.setPhone(anchors.get(i).getPhone());
			anchorMes.setRealName(anchors.get(i).getRealName());
			anchorMes.setState(anchors.get(i).getState());
			anchorMes.setVxNumber(anchors.get(i).getVxNumber());
			anchorMes.setTiXian(new BigDecimal("0.00"));
			for (int j = 0; j < moneys.size(); j++) {
				Integer uid = moneys.get(j).getUid();
				if (uid.equals(id2)) {
					anchorMes.setTiXian(moneys.get(j).getTiXian());
				}
			}
			newAnchors.add(i, anchorMes);
		}
		modelMap.addAttribute("anchorMes", newAnchors);
		modelMap.addAttribute("logs", logs);
		modelMap.addAttribute("anchorMaxPage", anchorMaxPage);
		modelMap.addAttribute("logMaxPage", logMaxPage);
		modelMap.addAttribute("data", data);
		if (page==0) {
			modelMap.addAttribute("Page", 1);
		}else {
			modelMap.addAttribute("Page", page+1);
		}
		return "index";
 	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@PreventRepeat
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> delete(@RequestParam("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		Integer a= adminService.deleteAnchor(id);
		Integer b= adminService.deleteUser(id);
		Integer c=adminService.deleteWallet(id);
		Integer d=adminService.deleteLog(id);
		if (a==1&&b==1&&c==1) {
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
	}
	
	/**
	 * 解封
	 * @param id
	 * @return
	 */
	@RequestMapping("jie")
	@ResponseBody
	@PreventRepeat
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> jie(@RequestParam("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		Integer result = adminService.updateWalletState(id, 0);
		if (result==1) {
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
	}
	
	/**
	 * 冻结
	 * @param id
	 * @return
	 */
	@RequestMapping("feng")
	@ResponseBody
	@PreventRepeat
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> feng(@RequestParam("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		Integer result = adminService.updateWalletState(id, 1);
		if (result==1) {
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
	}
	
	
	/**|
	 * 加载信息
	 * @param data
	 * @return
	 */
	@RequestMapping("xinX")
	@ResponseBody
	public Map<String, Object> xinX(@RequestParam("data") String data){
		Map<String, Object> map= new HashMap<>();
		List<AnchorMes>  anchors=null;
		if (data.matches(DefaultUtil.name)) {
			anchors = adminService.queryAllSouName(data+"%",0,DefaultUtil.defaultPage);
			List<String> list=new ArrayList<>();
			for (int i = 0; i < anchors.size(); i++) {
				list.add(i, anchors.get(i).getRealName());
			}
			map.put("datas",list);
		}else {
			anchors = adminService.queryAllSouPhone(data+"%",0,DefaultUtil.defaultPage);
			List<String> list=new ArrayList<>();
			for (int i = 0; i < anchors.size(); i++) {
				list.add(i, anchors.get(i).getPhone());
			}
			map.put("datas",list);
		}
		
		return map;
	}
	
	
	
}
