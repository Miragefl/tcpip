package com.viscum;

import org.apache.commons.lang.StringUtils;

public class DesTicket {

	public static void main(String[] args) throws Exception {
		String msg = "^^^^^19710008|bus|194|10008|bus|84|20180511091348|03-9100110000093-20180511091348-6-T03170113000007426-6-12345-200-180-起始站01-1-117.138,34.257-25-40201005-20180511091348-终点站03-3-117.140,34.260-25-40201006-20180511091348";
		String[] ts=msg.split("\\|");
		System.out.println(StringUtilJoin(ts,"",3,ts.length));
		System.out.println(sign(StringUtilJoin(ts,"",3,ts.length).getBytes()));
	}

	private static void desTicketNo(String str) throws Exception {
		if (!str.startsWith(":")) {
			throw new Exception("报文被篡改！");
		}
		String len = str.substring(1, 5);
		System.out.println("报文长度" + len);
		String n = str.substring(5, 6);
		System.out.println("n:" + n);
//		System.out.println("未解密的时间戳：" + str.substring(6, 22));
		long desTimestamp = Long.valueOf(str.substring(6, 21))
				/ Long.valueOf(n);
		System.out.println("解密的时间戳：" + desTimestamp);
		System.out.println("校验位：" + str.substring(21, 24));
		String bizContent = new String(desStr(str.substring(24).getBytes()));
		System.out.println(sign(bizContent.getBytes()));
		System.out.println("票号业务字段：" + bizContent);
		System.out.println(bizContent.substring(0, 13));
		System.out.println(bizContent.substring(13, 23));
		System.out.println(bizContent.substring(23, 34));
		System.out.println(bizContent.substring(34, 35));
		System.out.println(bizContent.substring(35, 53));
		System.out.println(bizContent.substring(53, 68));
		System.out.println(bizContent.substring(68, 82));
		System.out.println(bizContent.substring(82, 96));
	}

	/**
	 * 解密报文
	 * 
	 * @param bs
	 *            待加密的字节数组
	 * @return
	 */
	private static byte[] desStr(byte[] bs) {

		byte[] res = new byte[bs.length];
		for (int i = 0; i < bs.length; i++) {
			res[i] = (byte) (bs[i] - 1);
		}
		return res;
	}

	
	
	private static int sign(byte[] bs){
		int rs=0;
		for(byte b:bs){
			rs+=b;
		}
		return rs%512;
	}
	
	public static String StringUtilJoin(String[] args,String split,int startIndex,int endIndex){
		String join = StringUtils.join(args, split, startIndex, endIndex);
		return join;
	}
}
