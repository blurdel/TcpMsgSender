package com.blurdel.tester;


public class Main {

	public static void main(String[] args) {
		
		if (args == null || args.length == 0) {
			System.out.println("usage: appname [port=9000] [count=3]");
		}
		
		int port = 9000;
		int count = 3;
		
		try {
			port = Integer.parseInt(args[0]);
			count = Integer.parseInt(args[1]);
		}
		catch (Exception e) {
//			e.printStackTrace();
		}
		
		System.out.println("using port=" + port + ", count=" + count);
		
		new TcpClient(port, count).run();

	}

}
