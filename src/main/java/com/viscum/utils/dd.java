package com.viscum.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class dd {
	public static void main(String[] args) {
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			//读取pem证书
			BufferedReader br = new BufferedReader(new FileReader("xxx.pem"));
			String s = br.readLine();
			StringBuffer publickey = new StringBuffer();
			s = br.readLine();
			while (s.charAt(0) != '-') {
				publickey.append(s + "\r");
				s = br.readLine();
			}
			byte[] keybyte = base64decoder.decodeBuffer(publickey.toString());
			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
			PublicKey publicKey = kf.generatePublic(keySpec);
			BASE64Encoder bse=new BASE64Encoder();
			System.out.println("pk:"+bse.encode(publicKey.getEncoded()));

			//被签的原文
			String toSign="sxxxsasdsss";
			//生成的签名
			String sign="xxxxx";

			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(publicKey);
			signature.update(toSign.getBytes());
			boolean verify = signature.verify(base64decoder
					.decodeBuffer(
							sign));
			System.out.println(verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
