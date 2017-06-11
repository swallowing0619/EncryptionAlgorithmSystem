package 通信;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ChatThread extends Thread implements ActionListener {
	public static Server server;
	private Object[] options = { "��(Y)", "��(N)" }; 
	  
	public void run() {
		try {
			// ʵ��ͨ�ŷ���˺Ϳͻ��˴���
			if (server == null) {
				server = new Server();
				server.setup(6666);
			}
			
			ClientFrame client1 = new ClientFrame();
			client1.unit();
			
		} catch (Exception e) {
			System.out.println("ͨ��ʧ��");
			e.printStackTrace();
		}
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}
