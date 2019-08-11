package com.test.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;

@RestController
@RequestMapping("user")
public class GridfsController {

	private @Resource GridFsTemplate gridFsTemplate;
	private @Resource MongoDbFactory mongoDbFactory;

	/**
	 * 文件上传
	 * @param mfile
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void upload(@RequestParam("file") MultipartFile mfile) {
		String fname = mfile.getOriginalFilename();// 文件名称
		String prefix = fname.substring(fname.lastIndexOf("."));// 截取后缀
		try {
			final File file = File.createTempFile(fname, prefix);
			mfile.transferTo(file);
			ObjectId id = gridFsTemplate.store(new FileInputStream(file), file.getName(),
					new BasicDBObject("user_id", 101));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取文件对象
		GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5cc6aaeb03f97116ec37318f")));
		// 获取文件元数据
		GridFsResource gridFsResource = new GridFsResource(file,
				GridFSBuckets.create(mongoDbFactory.getDb()).openDownloadStream(file.getObjectId()));
		String fileName = file.getFilename().replace(",", "");
		// 处理中文文件名乱码
		if (request.getHeader("User-Agent").toUpperCase().contains("MSIE")
				|| request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
				|| request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		// 通知浏览器进行文件下载
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		IOUtils.copy(gridFsResource.getInputStream(), response.getOutputStream());

	}

}
