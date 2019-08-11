package com.gz.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gz.entity.TextMessage;
import com.gz.service.TextMessageService;
import com.gz.util.PrintUtil;

@RestController
@RequestMapping("export")
public class ExportController {

	@Autowired
	private TextMessageService textMessageService;

	@RequestMapping("/excel")
	public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
		String accounttime = request.getParameter("accounttime");
		List<TextMessage> list = textMessageService.getAllTextMessage();// 查出来的数据
		SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();// 创建poi导出数据对象
		SXSSFSheet sheet = sxssfWorkbook.createSheet("sheet页名字");// 创建sheet页
		SXSSFRow headRow = sheet.createRow(0);// 创建表头
		// 设置表头信息
		headRow.createCell(0).setCellValue("序号");
		headRow.createCell(1).setCellValue("开发者微信");
		headRow.createCell(2).setCellValue("openID");
		headRow.createCell(3).setCellValue("发送消息时间");
		headRow.createCell(4).setCellValue("消息类型");
		headRow.createCell(5).setCellValue("消息内容");
		headRow.createCell(6).setCellValue("消息id");
		int x = 1;// 序号
		for (TextMessage text : list) {// 遍历上面数据库查到的数据
			SXSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);// 填充数据
			dataRow.createCell(0).setCellValue(x); // 看你实体类在进行填充
			dataRow.createCell(1).setCellValue(text.getToUserName());
			dataRow.createCell(2).setCellValue(text.getFromUserName());
			dataRow.createCell(3).setCellValue(text.getCreateTime());
			dataRow.createCell(4).setCellValue(text.getMsgType());
			dataRow.createCell(5).setCellValue(text.getContent());
			dataRow.createCell(6).setCellValue(text.getMsgId());
			x++;
		}
		String filename = "导出excel表格名字";// 下载导出
		response.setCharacterEncoding("UTF-8");// 设置头信息
		response.setContentType("application/vnd.ms-excel");
		// 一定要设置成xlsx格式
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(filename + ".xlsx", "UTF-8"));
		ServletOutputStream outputStream = response.getOutputStream();// 创建一个输出流
		sxssfWorkbook.write(outputStream);// 写入数据
		outputStream.close();// 关闭
		sxssfWorkbook.close();
	}

	@RequestMapping("print")
	public void print() {
		PrintUtil.print("E:\\Git\\repo\\MyRepository/README.md","1");
		//PrintUtil.print();
		
		
	}

}
