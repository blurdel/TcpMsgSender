package com.blurdel.tester;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TcpClient implements Runnable {

	static final int PREFIX = 1431061028; // $BLURDEL
	
	private int port;
	private int count;
	
	public TcpClient(int port, int count) {
		super();
		this.port = port;
		this.count = count;
	}
	
	private ByteBuffer getData() {
		
		int prefix = PREFIX;
		int type = 67890;
		
		byte[] payload = new byte[(1 * 8)];
				
		ByteBuffer bb = ByteBuffer.allocate(12 + payload.length).order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(prefix);
		bb.putInt(type);
		bb.putInt(payload.length);
		
		// payload
		long ms = System.currentTimeMillis();
		bb.putLong(ms);
		
		return bb;		
	}

	@Override
	public void run() {
		Socket sock = null;
		ByteBuffer bb = null;
		
		try {
			
			sock = new Socket("127.0.0.1", port);
			OutputStream out = sock.getOutputStream();
			
			long start = System.currentTimeMillis();
			
			for (int i = 0; i < count || count == 0; i++) {
				bb = getData();
				out.write(bb.array());
				Thread.sleep(0, 500);
			}
			
			long stop = System.currentTimeMillis();
			long total = stop - start;
			System.out.println("time: " + String.format("%.2f", (total > 60000 ? total/60000.0 : 0)) + " minutes, " 
					+ (total/1000.0) + " seconds, " + total + " ms");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (sock != null)
					sock.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
}
