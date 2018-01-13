import java.net.*;
import java.io.*;
import java.util.*;

public class RecvControlCmd extends Thread{
	
	private static final int CLOUD_SERVER_PORT = 20000;	
	private static final int BUF_SIZE = 32;
	private static final int CLIENT_PORT = 2000;
	private static final int GW_SERVER_PORT = 10000;
	private static final String gw = "202.117.15.152";
	
	private Map<RoomLocation, String> map;
	
	public RecvControlCmd(Map<RoomLocation, String> map){
		this.map = map;
		this.start();
	}
	
	public void run(){
		DatagramSocket server_socket, socket;
		DatagramPacket packet;
		DatagramPacket data_send;
		byte[] buf = new byte[BUF_SIZE];
		try{
			server_socket = new DatagramSocket(CLOUD_SERVER_PORT);
			packet = new DatagramPacket(buf, BUF_SIZE);
			
			socket = new DatagramSocket(CLIENT_PORT);
			InetAddress inetAddress = InetAddress.getByName(gw);			
			
			while(true){
				server_socket.receive(packet);
				byte[] data = packet.getData();
				String str = new String(data);				
				
				String[] array = str.split(" ");
				
				int building_number = Integer.parseInt(array[0]);
				int building_level = Integer.parseInt(array[1]);
				int room = Integer.parseInt(array[2]);			
				
				String roomIp = map.get(new RoomLocation(building_number, building_level, room));
				if(roomIp == null){
					System.out.println("Don't know the ip_BBB of this room! please wait sensor data coming...");
					continue;
				}
				
				String cmd = null;
				cmd = roomIp + " " + array[3] + " " + array[4] + " " + array[5] + " " + array[6] + " " + array[7] + " " + array[8] + " " + array[9] + " " + array[10];	
				System.out.println(cmd);
							
				data_send = new DatagramPacket(cmd.getBytes(), cmd.length(), inetAddress, GW_SERVER_PORT);
				socket.send(data_send);						
			}
		} catch(IOException e){
			e.printStackTrace();
		}		
	}
}

