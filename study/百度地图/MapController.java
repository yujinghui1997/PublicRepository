package com.test.Controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.Util.MapUtil;

@Controller
@RequestMapping("map")
public class MapController {

	/**
	 * 根据城市名获取地图
	 * 
	 * @param address
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("address/{address}")
	public String address(@PathVariable("address") String address, ModelMap modelMap) throws IOException {
		Object[] coordinate = MapUtil.getCoordinate(address);
		modelMap.addAttribute("jing", coordinate[0]);
		modelMap.addAttribute("wei", coordinate[1]);
		modelMap.addAttribute("num", 6);
		return "index";
	}

	/**
	 * 根据IP地址获取地图
	 * 
	 * @param address
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("ip/{ip}")
	public String ip(@PathVariable("ip") String ip, ModelMap modelMap) throws IOException {
		// String ip = "121.22.116.130";
		Map<String, Object> map = MapUtil.getAddresses("ip=" + ip, "utf-8");
		String address = (String) map.get("country");
		address += map.get("region");
		address += map.get("city");
		address += map.get("county");
		Object[] coordinate = MapUtil.getCoordinate(address);
		modelMap.addAttribute("jing", coordinate[0]);
		modelMap.addAttribute("wei", coordinate[1]);
		return "index";
	}

	/**
	 * 根据经纬度获取当前位置
	 * 
	 * @param address
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("jingwei/{jing}/{wei}")
	public String jingwei(@PathVariable("jing") String jing, @PathVariable("wei") String wei, ModelMap modelMap){
		modelMap.addAttribute("wei", wei);
		modelMap.addAttribute("jing", jing);
		return "index";
	}

}
