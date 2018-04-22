package com.viscum.tcpipTest;

public class Logger {
	
	public static void info(Object o){
		System.out.println(o.toString());
	}
	public static void error(Object o,Throwable e){
		System.out.println(o.toString());
		e.printStackTrace();
	}

}
