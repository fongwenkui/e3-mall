package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictureController {

	@Value("${image.server.url}")
	private String imageServerUrl;
	
	/**
	 * 文件上传
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile) throws Exception {
		Map map=new HashMap<>();
		try {
			// 1、取文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			// 2、创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			// 3、执行上传处理
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//4、拼接返回的url和ip地址，拼装成完整的url
			String url=imageServerUrl+path;
			map.put("error", 0);
			map.put("url", url);
		} catch (Exception e) {
			map.put("error", 1);
			map.put("message", "文件上传失败");
		}
		String json = JsonUtils.objectToJson(map);
		return json;
	}

}
