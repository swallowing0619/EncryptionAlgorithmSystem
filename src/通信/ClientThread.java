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



public class ClientThread extends Thread{
	private OutputStream  output;
	private DataOutputStream dos;
	private ArrayList<MsgListener> listener = new ArrayList<MsgListener>();
	private MsgListener l;
	
	public void addListener(MsgListener l){
		this.l=l;
	}
	public void run (){
		try{
			System.out.println("���ӷ�����......");
			Socket socket = new Socket("127.0.0.1",6666);
			System.out.println("�ɹ���");
			//��ȡ�Է����͵���Ϣ
			InputStream input = socket.getInputStream();
			DataInputStream dis = new DataInputStream(input);
			//��Է����͵���Ϣ
			output = socket.getOutputStream();
			dos = new DataOutputStream(output);			
			while(true){
				//������ݰ������
				int type=dis.readInt();
				if(type==1){
					//������ݰ�ĳ���
					int len = dis.readInt();
					byte[] bytes= new byte[len];
					dis.readFully(bytes);
					//��ȡ�ͻ��˵��������ַ�
					String line = new String(bytes,"GBK");
//					System.out.println(line);
					//�ķ��������յ�����Ϣ��ʾ��������
					l.onRecvMsg(line);

				}else if(type == 2){
					int len = dis.readInt();
					byte[] bytes= new byte[len];
					dis.readFully(bytes);
					//��ȡ�ͻ��˵��������ַ�
					String line = new String(bytes,"GBK");
					//������
					if(line.equals("������")){
						//�ÿͻ�����Ӽ�����Կ�����
						l.addComponent();
					}
				}
				
				
			}
		}catch (Exception e){
			System.out.println("ʧ�ܣ�");
			e.printStackTrace();
		}
	}
	
	/*
	 * �������������Ϣ�ķ���
	 */
	public void sendMessage(String msg){
		try {
			//�����������д���ֽ�
			byte[] bytes=msg.getBytes();
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
	 * ����Э����Կ����ķ���
	 */
	public void sendKEYDeal(String msg){
		try {
			//�����������д���ֽ�
			byte[] bytes=msg.getBytes();
			int len = bytes.length;
			dos.writeInt(2);//��Ϣ����Ϊ2
			dos.writeInt(len);
			dos.write(bytes);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * ���������ж�ȡ�ַ�
	 */
	public String readLine(InputStream input) throws IOException{
		//����һ���ֽڶ���
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//����������ѭ����ȡ�ֽ�
		while(true){
			//��ȡһ���ֽ�
			int n = input.read();
			//�س���
			if(n == '\r')
				continue;
			//���з������
			if(n == '\n'){
				break;
			}
			//����
			bos.write(n);
		}
		//���ֽڶ����е����ȡ��
		byte[] bytes = bos.toByteArray();
		//ת���ַ�
		String content = new String(bytes,"GBK");
		return content;
	
	}

}
