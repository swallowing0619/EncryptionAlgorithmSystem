package 通信;

import java.awt.Color;
/**
 * 
 *����˵��øýӿڿ��ڿͻ����ڽ��в���
 *
 */
public interface MsgListener {
	//�ڿͻ�����־����ʾ��Ϣ
	public void onRecvMsg(String str);
	//�ڿͻ�������������
	public void addComponent();
	
	
}
