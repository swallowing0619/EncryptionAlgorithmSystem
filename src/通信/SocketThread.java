package 通信;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class SocketThread extends Thread {
	public static ArrayList<SocketThread> list = new ArrayList<SocketThread>();
	public static SocketThread drawst;
	public static boolean KEY_DEAL = false;
	private Socket socket;
	private InputStream input;
	private OutputStream output;
	private DataOutputStream dos;
	private String name;
	
	public SocketThread(Socket socket) {
		this.socket = socket;
		// ��ӵ�����
		list.add(this);
	}

	public void run() {
		try {
			// ��ȡ�ͻ��˷��͵���Ϣ

			input = socket.getInputStream();

			DataInputStream dis = new DataInputStream(input);
			// ��ͻ��˷��͵���Ϣ
			output = socket.getOutputStream();
			dos = new DataOutputStream(output);
			// ��������
			String str = "������������֣�\r\n";
			// ��������ͻ��˷�����Ϣ
			sendMessage(str);
			// ��ȡ�ͻ������������
			name = readLine(input);
			String name2 = name + "(" + socket.getInetAddress() + ")";
			System.out.println("name:" + name2);
			if(list.size()>1){
				// Ⱥ����Ϣ
				for (int i = 0; i < list.size(); i++) {
					SocketThread st = list.get(i);
					if (st == this) {
						continue;
					}
					// ������ͻ��˷�����Ϣ
					String msg = st.name + "����!\n" ;
					this.sendMessage(msg);
					st.sendMessage(name + "����!\n");
				}
				
			}else{
				this.sendMessage("�Է������ߣ�\n");
			}
			// �ӿͻ��˶�ȡ�ַ���Ϣ
			while (true) {
				// ������ݰ������
				int type = dis.readInt();
				if (type == 1) {
					// ������ݰ�ĳ���
					int len = dis.readInt();
					byte[] bytes = new byte[len];
					dis.readFully(bytes);
					// ��ȡ�ͻ��˵��������ַ�
					String line = new String(bytes, "GBK");

					if ("bye".equals(line)) {
						System.out.println("�������յ� " + name + "�����ߣ�");
						break;
					}
					
					// ��ӡ��ǰ�ͻ���˵�Ļ�
					System.out.println("�������յ� " + name + ":" + line + "======");
					

					// Ⱥ����Ϣ
					for (int i = 0; i < list.size(); i++) {
						SocketThread st = list.get(i);
						if (st == this) {
							continue;
						}
						// ������ͻ��˷�����Ϣ
						String msg = name + ":" + line;
						st.sendMessage(msg);

					}
				} else if (type == 2) {
					// ������ݰ�ĳ���
					int len = dis.readInt();
					byte[] bytes = new byte[len];
					dis.readFully(bytes);
					// ��ȡ�ͻ��˵��������ַ�
					String line = new String(bytes, "GBK");
					if ("Э����Կ".equals(line)) {
						System.out.println("�������յ�:Э����Կ���");
						//����Э�̵�״̬�����ʼ������ԿЭ��
						KEY_DEAL = true;
						
						// Ⱥ��Э������
						for (int i = 0; i < list.size(); i++) {
							SocketThread st = list.get(i);
							// ������ͻ��˷�����Ϣ,Э������
							String msg = "\n���ڿ�ʼ���лỰ��ԿЭ�̣�����\n";
							st.sendMessage(msg);
							msg = "��֪����Q=251,����A=3\n";
							st.sendMessage(msg);
							msg= "�������ѡ��һ��X<Q,�����Y=A^X mod Q,\n��Y�Լ�ɢ��ֵ������ǩ���͸�Է�\n";
							st.sendMessage(msg);
							msg= "�յ��Է�����Ϣ����֤��Ϣ�������Ժ���Դ��\n��Yֵ������Կ����K=Y^X mod Q\n\n";
							st.sendMessage(msg);
							st.sendAddComponent("������");

						}
						
					}

				}
			}
			// �ͻ����߹رյ�ǰ�˿�
			socket.close();
			// ɾ������еĶ���
			list.remove(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	/*
	 * ��ȡ�������ķ���
	 */

	private String readLine(InputStream input) throws IOException {
		// �½�һ���ֽڶ���
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataInputStream dis = new DataInputStream(input);
		dis.readInt();
		dis.readInt();
		while (true) {
			int n = input.read();
			// System.out.println(n);
			// �س���
			if (n == '\r') {
				continue;
			}
			// ���з�
			if (n == '\n') {
				break;
			}
			// �Ѷ�ȡ���ֽ������ȱ���
			bos.write(n);
		}
		// ���ֽڶ����е����ȡ����
		byte[] bytes = bos.toByteArray();
		String content = new String(bytes, "GBK");
		return content;
	}

	/*
	 * ��ͻ��˷�����Ϣ�ķ���
	 */
	
	public void sendMessage(String msg) {
		try {
			// �����������д���ֽ�
			byte[] bytes = msg.getBytes();
			int len = bytes.length;
			dos.writeInt(1);
			dos.writeInt(len);
			dos.write(bytes);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * ��ͻ��˷����������ķ���
	 */
	public void sendAddComponent(String msg) {
		try {
			// �����������д���ֽ�
			byte[] bytes = msg.getBytes();
			int len = bytes.length;
			dos.writeInt(2);
			dos.writeInt(len);
			dos.write(bytes);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	

}
