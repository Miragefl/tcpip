package com.viscum.tcpipTest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TcpIpClientTest {
	Logger logger = LoggerFactory.getLogger(TcpIpClientTest.class);

	public static void main(String[] strs) throws IOException {
		String filePath = "C:/Users/sh/Desktop/subSectionTCP.txt";
		File file = new File(filePath);
		List<String> r = FileUtils.readLines(file, "UTF-8");
		int len = r.size();
		long startTime = System.currentTimeMillis();
		for (int j = 0; j < len; j++) {
			String host = "192.168.1.61";
			int port = 7079;
			String message = r.get(j);
			if (message.startsWith("#")) {
				continue;
			}
			Integer timeOut = 30000;
			String head = "";
			try {
				head = StringUtils.leftPad(String.valueOf(message.getBytes("UTF-8").length), 8, "^");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				SocketClientUtil client = new SocketClientUtil();
				Socket socket = new Socket(host, port);
				socket.setSoTimeout(timeOut);
				for (int i = 0; i < 1; i++) {
					byte[] res = client.sendAndRec(socket, head + message);
					System.out.println("============>Server端报文：" + new String(res));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("时长：" + (endTime - startTime));
		System.out.println("平均单笔时长：" + (endTime - startTime) / len);

	}

	private byte[] desStr(byte[] bs) {

		logger.info("解密前长度:{}", bs.length);
		logger.info("解密前:{}", new String(bs));
		byte[] res = new byte[bs.length];
		for (int i = 0; i < bs.length; i++) {
			res[i] = (byte) (bs[i] - 1);
		}
		logger.info("解密后:{}", new String(res));
		return res;
	}

	private void replaceLeft(String src) {

		Pattern p = Pattern.compile("^(0+).+$");
		Matcher m = p.matcher(src);
		StringBuilder builder = new StringBuilder();
		while (m.find()) {
			builder.append(m.group(1));
		}
		System.out.println(src.substring(builder.toString().length()));
	}

	private void format(String src) {

		System.out.println(String.format("%1$.3f", Float.valueOf(src)));

		// System.out.printf("Tab键的效果是：%08d%n", 7);

	}
}
