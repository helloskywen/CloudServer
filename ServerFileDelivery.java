
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFileDelivery {
	
	private static final int SERVER_PORT = 45000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			while(true) {
				socket = serverSocket.accept();
				new ServerFileDeliveryThread(socket);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}			
		} 
	}
}

class ServerFileDeliveryThread extends Thread{
	
	Socket socket;
	
	public ServerFileDeliveryThread(Socket socket) {
		this.socket = socket;
		start();
	}
	
	public void run() {
		FileInputStream fis = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		
		InputStream is = null;
		DataInputStream dis = null;
		
		byte[] buf = new byte[1024];
		
		try {
			dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			
			String fileName = dis.readUTF();
			
			File file = new File("layout/" + fileName);
			
			if(file.exists()) {
				dos.writeUTF("yes");
				
				fis = new FileInputStream(file);
				int len;
				
				while((len = fis.read(buf)) > 0) {
					dos.write(buf, 0, len);
				}
				
				dos.flush();
			} else {
				dos.writeUTF("no");
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}

