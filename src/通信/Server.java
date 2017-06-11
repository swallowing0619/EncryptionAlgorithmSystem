package 通信;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {//�����
	

	public void setup(int port){
		
	    try {
	    	//�󶨷������˿ں�
			ServerSocket sers = new ServerSocket(port);
			System.out.println("����������˿�"+port+"�ɹ���");
			
			while(true){
				//�ȴ�ͻ��˷���
				Socket socket = sers.accept();
				System.out.println("���˷��ʣ�");
				
				//�ѿͻ��˽����̴߳���
				SocketThread st = new SocketThread(socket);
				st.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public static void main(String[] args) {
		new Server().setup(6666);
		
	}
	

}
