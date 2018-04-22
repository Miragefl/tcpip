package com.viscum.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.viscum.constant.Constant;
import com.viscum.exception.WxException;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 此类提供�?��基本的帮助功能，如想要更加便捷的、更加丰富的帮助功能可以在SystempHelper类中扩充�?
 *
 * @author zhaojianzhong
 *
 */
public class Helper {
	/**
	 * 按照知道格式得到服务器的当前时间
	 *
	 * @param format
	 *            -format 的取值参�?jdk帮助文档 SimpleDateFormat类的说明；常见的格式：yyyy-MM-dd
	 *            HH:mm:ss SSS
	 * @return
	 */
	public static String getCurrentTime(String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		Date now = new Date();
		return formatter.format(now);
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyyMMddHHmmss");
	}

	public static String getPatientNo() {
		return getCurrentTime("yyMMddHHmmss");
	}

	public static String getTime(Timestamp timeStamp) {
		return getTime(timeStamp, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getTime(Timestamp timeStamp, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		return formatter.format(timeStamp);
	}

	public static Map<String, Object> timestampToString(Map<String, Object> map, String format) {
		if (format == null) {
			format = "yyyyMMddHHmmss";
		}
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof Timestamp) {
				entry.setValue(getTime((Timestamp) entry.getValue(), format));
			}
		}
		return map;
	}

	public static List<Map<String, Object>> timestampToString(List<Map<String, Object>> list, String format) {
		for (Map<String, Object> map : list) {
			timestampToString(map, format);
		}
		return list;
	}

	public static Timestamp str2Timestamp(String timeStr, String pattern) throws ParseException {
		Timestamp result = null;
		if (timeStr == null) {
			throw new IllegalArgumentException("Timestamp is null");
		}
		if (pattern != null && !"".equals(pattern)) {
			if (!"yyyyMMddHHmmss".equals(pattern) && !"yyyy-MM-dd HH:mm:ss".equals(pattern) && !"MM/dd/yyyy HH:mm:ss".equals(pattern) && !"yyyy-MM-dd".equals(pattern) && !"MM/dd/yyyy".equals(pattern)) {
				throw new IllegalArgumentException("Date format [" + pattern + "] is invalid");
			}
		} else {
			pattern = "yyyyMMddHHmmss";
		}
		result = new Timestamp(parse(timeStr, pattern).getTime());
		return result;
	}

	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 计算两个给定的日期的时间差，差�?以毫秒为单位�?
	 *
	 * @param startTimeStr
	 * @param endTimeStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long getBetweenValue(String startTimeStr, String endTimeStr, String format) throws ParseException {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		Date startTime = formatter.parse(startTimeStr);
		Date endTime = formatter.parse(endTimeStr);
		return endTime.getTime() - startTime.getTime();
	}

	/**
	 * 取指定日期的前�?后的日期（如 半年前的日期�?auth zhaojz
	 *
	 * @param type
	 *            的取值为�?Calendar.YEAR , Calendar.MONTH , Calendar.DATE
	 *            ,Calendar.HOUR ,Calendar.MINUTE ,Calendar.SECOND
	 * @count 在type的基�?��加一个�?，正数为加，负数为减
	 * @formatStr 输出日期的格�?如：yyyy-MM-dd HH:mm:ss SSS
	 * @param count
	 * @return
	 */
	public static String getSpecialTime(int type, int count, String formatStr) {
		Calendar calendar = new GregorianCalendar();
		calendar.add(type, count);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		return simpleDateFormat.format(calendar.getTime());
	}

	public static String getSpecialTime(long timeMillis, String formatStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		return simpleDateFormat.format(timeMillis);
	}

	public static String getSpecialDate(Date src, int count, String formatStr) {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, count);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		return simpleDateFormat.format(src);
	}

	public static Date parse(String strDate, String pattern) throws ParseException {
		return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
	}

	/**
	 * 修正路径的末尾字�?如果path不以文件分隔符结尾，自动添加文件分隔�?
	 *
	 * @param path
	 * @return
	 */
	public static String correctPathEndChar(String path) {
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		return path;
	}

	/**
	 * 读取文本文件
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readCharFile(String fileName) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
			}
			return sb.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null)
				in.close();
		}
	}

	/**
	 * 写文本文�?
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static void writeCharFile(String fileName, String fileContent) throws IOException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(fileName)));
			bw.write(fileContent);
		} catch (IOException e) {
			throw e;
		} finally {
			if (bw != null)
				bw.close();
		}
	}

	/**
	 * 以字节流的形式读取文�?
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] readByteFile(String fileName) throws IOException {
		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(fileName));
			out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			byte[] content = out.toByteArray();
			return content;
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}

	/**
	 * 以字节流的形式写文件
	 *
	 * @param fileName
	 *            文件�?
	 * @param content
	 *            文件内容
	 * @return
	 * @throws IOException
	 */
	public static void writeByteFile(String fileName, byte[] content) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName, true);
			fos.write(content);
			fos.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	public static void writeByteFile(String fileName, InputStream in, int length) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);

			BufferedInputStream bin = new BufferedInputStream(in);
			byte[] temp = new byte[1024];
			int size = 0;
			int total = 0;
			while ((size = bin.read(temp)) != -1) {
				fos.write(temp, 0, size);
				total += size;
				if (total >= length) {
					break;
				}
			}

			// for (int i = 0; i < length; i++) {
			// fos.write(in.read());
			// }
			fos.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	public static void writeByteFile(String fileName, byte[] content, boolean append) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName, append);
			fos.write(content);
			fos.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * 清空文件�?
	 *
	 * @param filePath
	 * @throws IOException
	 */
	public static void clearDir(String filePath) throws IOException {
		File f = new File(filePath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目�?
			if (f.listFiles().length == 0) {// 若目录下没有文件则直接删�?
				// f.delete();
			} else {// 若有则把文件放进数组，并判断是否有下级目�?
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						clearDir(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
			}
		}
	}

	/**
	 * 删除文件�?
	 *
	 * @param filePath
	 * @throws IOException
	 */
	public static void deleteDir(String filePath) throws IOException {
		clearDir(filePath);
		File f = new File(filePath);// 定义文件路径
		f.delete();
	}

	/**
	 * 创建目录，如果目录以存在直接返回，否则创建目�?
	 *
	 * @param filePath
	 * @throws IOException
	 */
	public static void mkdirs(String filePath) throws Exception {
		// String[] dirArray = filePath.split("/");
		File file = new File(filePath);
		if (file.exists())
			return;
		boolean result = file.mkdirs();
		// result = file.mkdir();

		if (!result)
			throw new Exception("创建目录失败");
	}

	/**
	 *
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 * @throws Exception
	 */
	public static boolean move(String srcFileName, String destFileName) throws Exception {
		File srcFile = new File(srcFileName);
		File destFile = new File(destFileName);
		mkdirs(destFile.getParent());
		// File dir = new File(destPath);
		boolean success = srcFile.renameTo(destFile);
		// FileUtils.copyFileToDirectory(file,dir);
		// file.deleteOnExit();
		return success;
	}

	/**
	 *
	 * @param str
	 * @param length
	 *            格式化后的字符串长度
	 * @return
	 */
	public static String rightFullSpace(String str, int length) {
		int srcLength = str.length();
		String spaceStr = "          ";// 长度�?0 的空�?
		if (length > srcLength) {
			while ((str + spaceStr).length() < length) {
				spaceStr += spaceStr;
			}
			str = str + spaceStr;
			str = str.substring(0, length);
		}
		return str;
	}

	/**
	 *
	 * @param appendContent
	 * @param appendBeginIndex
	 * @param prevContent
	 * @param prevBeginIndex
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public byte[] arraycopy(byte[] prevContent, int prevBeginIndex, int length, byte[] appendContent, int appendBeginIndex, int appendLength) throws IOException {
		String tempPath = correctPathEndChar(Helper.class.getResource("/").getPath());
		String tempName = tempPath + "temp_" + String.valueOf(Math.round(Math.random() * 10000));
		// writeByteFile(tempName,prevContent);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tempName, true);
			for (int i = 0; i < length; i++) {
				fos.write(prevContent[prevBeginIndex + i]);
			}
			for (int i = 0; i < appendLength; i++) {
				fos.write(prevContent[appendBeginIndex + i]);
			}
			fos.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fos != null)
				fos.close();
		}

		return null;
	}

	public static String nvl(Object value) {
		if (value == null || "null".equals(value)) {
			return "";
		} else {
			return String.valueOf(value);
		}
	}

	/**
	 * @param value
	 * @param formatStr
	 *            "##,###.00"
	 * @return
	 */
	public static String formatDecimal(Object value, String formatStr) {
		/*
		 * String valueStr = String.valueOf(value); if(valueStr == null ||
		 * "null".equals(value)){ valueStr = "0"; }
		 */
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern(formatStr);
		return myformat.format(value);
	}

	public static String getCurrentPageNum(String totalNum, String pageSize, String beginPos) {
		int i = Integer.parseInt(beginPos) / Integer.parseInt(pageSize);
		int mod = Integer.parseInt(beginPos) % Integer.parseInt(pageSize);
		if (mod > 0) {
			i++;
		}
		return String.valueOf(i);
	}

	public static String getTotalPageNum(String totalNum, String pageSize) {
		int i = Integer.parseInt(totalNum) / Integer.parseInt(pageSize);
		int mod = Integer.parseInt(totalNum) % Integer.parseInt(pageSize);
		if (mod > 0) {
			i++;
		}
		return String.valueOf(i);
	}

	public static void main(String[] args) {
		// System.out.println(getCurrentPageNum("223","20","21"));
		// System.out.println(getTotalPageNum("130", "50"));
		String[] divide = divide("10.11", "3.11");
		System.out.println(divide[0]);
		System.out.println(divide[1]);
	}

	// 流转化成字符�?
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	// 流转化成文件
	public static void inputStream2File(InputStream is, String savePath) throws Exception {
		System.out.println("文件保存路径�?" + savePath);
		File file = new File(savePath);
		InputStream inputSteam = is;
		BufferedInputStream fis = new BufferedInputStream(inputSteam);
		FileOutputStream fos = new FileOutputStream(file);
		int f;
		while ((f = fis.read()) != -1) {
			fos.write(f);
		}
		fos.flush();
		fos.close();
		fis.close();
		inputSteam.close();
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map<?, ?> properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<?> entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			/*
			 * if("_token".equals(name)){ continue; }
			 */
			returnMap.put(name, value);
		}
		JSONObject retJson = new JSONObject();
		retJson.putAll(returnMap);
		// Logger.getLogger("Helper").debug("recieve message:"+retJson.toString());
		return returnMap;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParameterMap2(HttpServletRequest request) {
		String token=request.getHeader("token");
		// 参数Map
		Map<?, ?> properties = request.getParameterMap();
		// 返回值Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<?> entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			/*
			 * if("_token".equals(name)){ continue; }
			 */
			returnMap.put(name, value);
		}
		JSONObject retJson = new JSONObject();
		retJson.putAll(returnMap);
		// Logger.getLogger("Helper").debug("recieve message:"+retJson.toString());
		if(token != null){
			returnMap.put("token", token);
		}
		return returnMap;
	}

	public static Map<String, String> appendInsertFieldToMap(Map<String, String> inputMap) {
		Map<String, String> returnMap = inputMap;
		if (!inputMap.containsKey("id")) {
			returnMap.put("id", UUID.randomUUID().toString());
		}
		String currentTime = getCurrentTime();
		returnMap.put("add_time", currentTime);
		// returnMap.put("add_user",Helper.nvl((String)inputMap.get("user_id")));
		returnMap.put("update_time", "");
		returnMap.put("update_user", "");
		return returnMap;
	}



	public static Map<String, String> cloneMap(Map<String, String> param) {
		Map<String, String> newMap = new HashMap<String, String>();
		Iterator<String> it = param.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			newMap.put(key, param.get(key));
		}
		return newMap;
	}

	public static Map<String, Object> cloneMap2(Map<String, Object> param) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		Iterator<String> it = param.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			newMap.put(key, param.get(key));
		}
		return newMap;
	}

	/**
	 * 产生Min-Max之间的数�?
	 *
	 * @return
	 */
	public static String getRandom(int min, int max) {
		double d = Math.round(Math.random() * (max - min) + min);
		return String.valueOf((int) d);
	}

	public static JSONObject getJSON(String status, String desc) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("desc", "");
		map.put("status", "0");
		return JSONObject.fromObject(map);
	}

	public static JSONObject getSuccJSON(List<Map<String, Object>> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (list != null) {
			for (Map<String, Object> tmpMap : list) {
				replaceNull(tmpMap);
			}
			map.put("body", list);
		}
		map.put("desc", "");
		map.put("status", "0");
		return JSONObject.fromObject(map);
	}

	public static JSONObject getFailJSON(String code, String msg) {
		return getJSON(code, msg, null);
	}

	public static JSONObject getSuccJSON(Map<String, Object> data) {
		return getJSON("0", "", data);
	}

	public static JSONObject getJSON(String code, String msg, Map<String, Object> data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", code);
		map.put("desc", msg);
		if (data != null) {
			replaceNull(data);
			map.put("body", data);
		}
		return JSONObject.fromObject(map);
	}

	public static JSONObject getJSON(Map<String, Object> map) {
		replaceNull(map);
		return JSONObject.fromObject(map);
	}

	public static Map<String, Object> getSubMap(Map<String, Object> param, String[] keys) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		for (int i = 0; i < keys.length; i++) {
			newMap.put(keys[i], param.get(keys[i]));
		}
		return newMap;
	}

	public static void replaceNull(Map<String, Object> map) {
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (map.get(key) == null) {
				map.put(key, "");
			}
		}

	}

	public static List<Map<String, Object>> filterColumnList(List<Map<String, Object>> list, String[] keys) {
		List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			newlist.add(getSubMap(list.get(i), keys));
		}
		return newlist;
	}

	public static int toInt(Object obj) {
		try {
			String tmp = String.valueOf(obj);
			if (tmp.indexOf(".") != -1) {
				tmp = tmp.substring(0, tmp.indexOf("."));
			}
			return Integer.parseInt(tmp);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int toInt(Object obj, int defaultValue) {
		try {
			return Integer.parseInt(String.valueOf(obj));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static float toFloat(Object obj) {
		try {
			return Float.parseFloat(String.valueOf(obj));
		} catch (Exception e) {
			return 0;
		}
	}

	public static long toLong(Object obj) {
		try {
			return Long.parseLong(String.valueOf(obj));
		} catch (Exception e) {
			return 0;
		}
	}


	/**
	 * 生成指定长度的随机码
	 *
	 * @param len
	 * @return
	 */
	public static String getRandomChar(int len) {
		Random random = new Random();
		char ch = '0';
		LinkedList<String> ls = new LinkedList<String>();
		for (int i = 0; i < 10; i++) {// 0-9
			ls.add(String.valueOf(48 + i));
		}
		for (int i = 0; i < 26; i++) {// A-Z
			ls.add(String.valueOf(65 + i));
		}
		for (int i = 0; i < 26; i++) {// a-z
			ls.add(String.valueOf(97 + i));
		}
		StringBuilder sb = new StringBuilder();
		int index;
		for (int i = 0; i < len; i++) {
			index = random.nextInt(ls.size());
			if (index > (ls.size() - 1)) {
				index = ls.size() - 1;
			}
			ch = (char) Integer.parseInt(String.valueOf(ls.get(index)));
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 生成指定长度的劵码
	 *
	 * @param len
	 * @return
	 */
	public static String getRandomNum(int len) {
		Random random = new Random();
		String ch = "0";
		LinkedList<String> ls = new LinkedList<String>();
		for (int i = 0; i < 10; i++) {// 0-9
			ls.add(String.valueOf(0 + i));
		}

		StringBuilder sb = new StringBuilder();
		int index;
		for (int i = 0; i < len; i++) {
			index = random.nextInt(ls.size());
			if (index > (ls.size() - 1)) {
				index = ls.size() - 1;
			}
			ch = String.valueOf(ls.get(index));
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 获取时间戳
	 *
	 * @return
	 */
	public static String getTimestampStr() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 校验校验码
	 *
	 * @param request
	 * @param verifyCode
	 * @throws WxException
	 */
	public static void checkVerifyCode(HttpServletRequest request, String verifyCode) throws WxException {
		try {
			HttpSession session = request.getSession(false);

			if (session == null) {
				throw new WxException(Constant.RET_FAIL, "访问非法！");
			}
			String session_verifyObj = (String) session.getAttribute("verifyObj");
			Long session_verifyObjTime = (Long) session.getAttribute("session_verifyObjTime");

			if (!StringUtils.equals(session_verifyObj, verifyCode)) {
				throw new WxException(Constant.RET_FAIL, "验证码错误！");
			}
			long currentTime = System.currentTimeMillis();

			if (currentTime - session_verifyObjTime > 60000) {
				throw new WxException(Constant.RET_FAIL, "验证码超时");
			}
		} catch (WxException e) {
			throw e;
		} catch (Exception e) {
			throw new WxException(Constant.RET_FAIL, "校验验证码出错");
		}

	}

	public static Map<String, Object> convertMap(Map<String, String> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Set<String> sset = param.keySet();
		for (String os : sset) {
			returnMap.put(os, param.get(os));
		}
		return returnMap;
	}

	// 计算距离
	public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
		// 维度
		double lat1 = (Math.PI / 180) * latitude1;
		double lat2 = (Math.PI / 180) * latitude2;

		// 经度
		double lon1 = (Math.PI / 180) * longitude1;
		double lon2 = (Math.PI / 180) * longitude2;

		// 地球半径
		double R = 6371;

		// 两点间距离 km，如果想要米的话，结果*1000就可以了
		double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

		DecimalFormat df = new DecimalFormat("#.00");

		return Double.valueOf(df.format(d));
	}

	// 验证正整数
	public static boolean IsIntNumber(String str) {
		String regex = "^\\+?[1-9][0-9]*$";
		return str.matches(regex);
	}

	// 金额验证
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 除法计算 此方法用于金额计算 将除数放大100倍 计算后 再缩小100倍
	 *
	 * @param divisor
	 *            除数
	 * @param dividend
	 *            被除数
	 * @return String[商,余数]
	 */
	public static String[] divide(String divisor, String dividend) {

		BigDecimal divisorBig = new BigDecimal(divisor).multiply(new BigDecimal("100"));
		BigDecimal dividendBig = new BigDecimal(dividend);
		BigDecimal[] divideAndRemainder = divisorBig.divideAndRemainder(dividendBig);
		// 商
		String divide = divideAndRemainder[0].divide((new BigDecimal("100"))).toString();
		// 余数
		String remainder = divideAndRemainder[1].divide((new BigDecimal("100"))).toString();
		return new String[] { divide, remainder };
	}

	/**
	 * 加法
	 *
	 * @param addend
	 *            加数
	 * @param augend
	 *            被加数
	 * @return
	 */
	public static String add(String addend, String augend) {
		BigDecimal addendBig = new BigDecimal(addend);
		BigDecimal augendBig = new BigDecimal(augend);
		BigDecimal add = addendBig.add(augendBig);
		return add.toString();
	}

	/**
	 * 校验入参是否为null||""
	 *
	 * @param params
	 * @param args
	 * @return
	 * @throws WxException
	 */
	public static JSONObject checkNull(Map<String, Object> params, String[] args) throws WxException {
		for (String t : args) {
			if (!params.containsKey(t)) {
				throw new WxException(Constant.RET_FAIL, "参数[" + t + "]为必须字段!");
			} else {
				if (StringUtils.isBlank(String.valueOf(params.get(t))) || "null".equals(String.valueOf(params.get(t)))) {
					throw new WxException(Constant.RET_FAIL, "参数[" + t + "]不能为空!");
				}
			}
		}
		return null;
	}
	/**
	 * 功能描述:从httpServletRequest中获取用户的ip <br/>
	 *
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request)
	{
		String ip="127.0.0.1";
		try
		{
			ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("Proxy-Client-IP");
			}

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getRemoteAddr();
			}
			if(ip.indexOf(",")>0){
				ip=ip.split(",")[0];
			}
			return ip;
		}
		catch (Exception e)
		{
			return ip;
		}

	}


}
