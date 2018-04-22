package com.viscum.httpTest;

import com.viscum.utils.HttpCilentUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

public class HttpClient {

	public static void main(String[] args) throws Exception {
		String filePath = "C:/Users/sh/Desktop/subSectionHTTP.txt";
		File file = new File(filePath);
		List<String> r = FileUtils.readLines(file, "UTF-8");
		int len = r.size();
		String url = "http://192.168.1.104:8090/manyibaoServer/bus/subsection.do";
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			String postStr = r.get(i);
			JSONObject postData = JSONObject.fromObject(postStr);
			JSONObject json = HttpCilentUtil.callPost(url, null, postData);
			System.out.println(json.toString());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("时长："+(endTime - startTime));
	}

}
