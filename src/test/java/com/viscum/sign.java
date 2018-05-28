package com.viscum;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class sign {
	private static Logger logger = LoggerFactory.getLogger(sign.class);

	public static void main(String[] args) {
		//生成的校验码：438
		//传过来的校验码：376
		String msg = "10008|bus|451|20180511102427|03-9100110000093-20180511102427-6-T03170113000007426-6-12345-200-180-起始站01-1-117.138,34.257-25-40201005-20180511102427-终点站03-3-117.140,34.260-25-40201006-20180511102427";
		signTicket(msg, "\\|", 3);

//		String msg = "X";
//		StringToAscii(msg);
	}

	public static void signTicket(String message, String split, int startIndex) {
		logger.info("原始报文:{}", message);
		String[] msgs = message.split(split);
		String signBody = StringUtils.join(msgs, "", startIndex, msgs.length);
		logger.info("待签名报文：{}", signBody);
		int sign = sign(signBody.getBytes());
		logger.info("生成的签名：{}", sign);
	}

	public static int sign(byte[] bs) {
		int rs = 0;
		for (byte b : bs) {
			rs += b;
		}
		return rs % 512;
	}

	public static int StringToAscii(String msg) {
		byte[] bytes = msg.getBytes();
		int rs = 0;
		for (byte b : bytes) {
			logger.info("ascii码：{}",b);
			rs += b;
		}
		return rs;
	}


}
