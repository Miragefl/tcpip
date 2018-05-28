package com.viscum.tcpipTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class SocketClientUtil {

	Logger logger = LoggerFactory.getLogger(SocketClientUtil.class);

	/**
	 * socket发送请求接收返回报文
	 *
	 * @param host
	 * @param port
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public byte[] sendAndRec(String host, Integer port, String message)
			throws Exception {

		try {
			logger.info("============>Client端报文：" + message);
			logger.info("============>Client端主机地址：" + host);
			logger.info("============>Client端端口：" + port);

			OutputStream output = null;
			InputStream input = null;
			Socket socket = new Socket(host, port);
			socket.setSoTimeout(300000);
			output = socket.getOutputStream();

			BufferedOutputStream bufOut = new BufferedOutputStream(output);

			// 写数据发送报文
			logger.info(message);
			bufOut.write(message.getBytes("UTF-8"));
			bufOut.flush();
			logger.info("============>已经发送报文...");

			input = socket.getInputStream();
			BufferedInputStream bufInput = new BufferedInputStream(input);

			byte[] buf = new byte[1024];

			bufInput.read(buf, 0, 8);
			String strSize = new String(buf, 0, 8);
			Integer size = Integer.valueOf(strSize);

			buf = new byte[size];
			bufInput.read(buf, 0, size);

			logger.info(strSize + new String(buf));
			return buf;

		} catch (IOException e) {
			logger.error("发送报文异常", e);
			throw e;
		}

	}

	public byte[] sendAndRec(Socket socket, String message)
			throws Exception {

		try {
			logger.info("============>Client端报文：" + message);

			OutputStream output = null;
			InputStream input = null;
			output = socket.getOutputStream();
			BufferedOutputStream bufOut = new BufferedOutputStream(output);
			// 写数据发送报文
//            logger.info(message);
			bufOut.write(message.getBytes("UTF-8"));
			bufOut.flush();
			logger.info("============>已经发送报文...");

			input = socket.getInputStream();

			BufferedInputStream bufInput = new BufferedInputStream(input);

			byte[] buf = new byte[1024];

			bufInput.read(buf, 0, 8);
			String strSize = new String(buf, 0, 8);
			logger.info("================"+strSize);
			Integer size = Integer.valueOf(strSize.trim().replace("^", ""));

			buf = new byte[size];
			bufInput.read(buf, 0, size);

//            logger.info(strSize + new String(buf));
			return buf;

		} catch (IOException e) {
			logger.error("发送报文异常", e);
			throw e;
		}

	}

	public byte[] streamToBytes(InputStream inputStream, int len) {
		/**
		 * inputStream.read(要复制到得字节数组,起始位置下标,要复制的长度)
		 * 该方法读取后input的下标会自动的后移，下次读取的时候还是从上次读取后移动到的下标开始读取 所以每次读取后就不需要在制定起始的下标了
		 */
		byte[] bytes = new byte[len];
		try {
			inputStream.read(bytes, 0, len);
		} catch (IOException e) {
			logger.error("读取返回报文错误", e);
		}
		return bytes;
	}

}
