package com.viscum;

import java.util.HashMap;
import java.util.Map;

public class test {

	public static void main(String[] args) {
		Map<String,Object> params = new HashMap<String, Object>();
		String key = String.valueOf(params.get("key"));
		System.out.println(key.equals(null));
	}
}
