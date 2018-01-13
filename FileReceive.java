
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceive {
	private static final int SERVER_PORT = 35000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			while(true) {
				socket = serverSocket.accept();
				new FileReceiveThread(socket);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				serverSocket.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		} 
	}
}

class FileReceiveThread extends Thread{
	Socket socket;
	public FileReceiveThread(Socket socket) {
		this.socket = socket;
		start();
	}
	
	@Override
	public void run() {
		InputStream is = null;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		
		byte[] buf = new byte[1024];
		int len;
		
		String fileName = null;
		
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(new BufferedInputStream(is));
			
			fileName = dis.readUTF();
			//System.out.println(fileName);
			fos = new FileOutputStream("layout/" + fileName);
			
			while((len=dis.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}
			
			fos.flush();
			fos.close();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
				is.close();
				socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
 

