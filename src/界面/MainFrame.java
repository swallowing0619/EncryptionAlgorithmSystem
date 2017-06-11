package 界面;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import 通信.ChatThread;
import 通信.Server;
import 通信.SocketThread;

public class MainFrame {
	private JFrame jf;
	private JLabel closepanel;
	private JLabel minimizepanel;
//	private Graphics g;
	private ImageIcon close = new ImageIcon("images/close2.jpg");
	private ImageIcon mini = new ImageIcon("images/mini2.jpg");
	private int oldX,oldY;
	private boolean firstpress=true;


	public static void main(String args[]) {
		new MainFrame().unit();
	}

	// ��ʼ������
	private void unit() {

		jf = new JFrame();
		jf.setTitle("�����㷨��ʦ");
		jf.setDefaultCloseOperation(3);
		jf.setSize(new Dimension(500, 400));
		jf.setUndecorated(true);
		jf.setLocationRelativeTo(null);
		jf.setIconImage(new ImageIcon("images/encrypt.png").getImage());
		// �����������
		westPanel();
		// �����������
		eastPanel();
		// �м����
		JPanel centerPanel = new JPanel(){
			//��дpaint����(�ػ�)
			public void paint(Graphics g){
				super.paint(g);		
				// ��ʾ����
				String str = "�����㷨��ʦ";
				Font f = new Font("����", Font.BOLD + Font.ITALIC, 28);
				g.setFont(f);
				g.setColor(Color.gray);
				g.drawString(str, 85, 200);
				this.setBackground(Color.white);
			}
		};
		
		jf.add(centerPanel);
		jf.setVisible(true);
		jf.addMouseMotionListener(ma);
		jf.addMouseListener(ma);

	}


	/**
	 * �������
	 */
	public void eastPanel() {
		// ����
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		eastPanel.setPreferredSize(new Dimension(100, 40));
		eastPanel.setBackground(Color.white);
		// ���ڵĹرռ������
		closepanel = new JLabel(close);
		closepanel.setBackground(Color.RED);
		closepanel.setPreferredSize(new Dimension(30, 30));
		closepanel.addMouseListener(ma);//����
		
		// ���ڵ���С���������
		minimizepanel = new JLabel(mini);
		minimizepanel.setBackground(Color.RED);
		minimizepanel.setPreferredSize(new Dimension(30, 30));
		minimizepanel.addMouseListener(ma);

		eastPanel.add(minimizepanel);
		eastPanel.add(closepanel);
		jf.add(eastPanel, BorderLayout.EAST);

	}

	/**
	 * �������
	 */
	public void westPanel() {
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(90, 0));
		jp.setBackground(Color.red);
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		// ��ť����
		String types[] = { "�������", "RC4����", "LFSR����", "DES����","RSA����",
				"�Ự����" };
		// һ��ʵ��ť
		for (int i = 0; i < types.length; i++) {
			Button bt = new Button(types[i]);
			bt.addActionListener(l);//����
			bt.setActionCommand(types[i]);
			bt.setBackground(Color.WHITE);
			bt.setPreferredSize(new Dimension(80,35));
			jp.add(bt);
		}
		jf.add(jp, BorderLayout.WEST);
	}

	ActionListener l = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("�Ự����")) {
				//��ͨ�Ų��ֽ����̴߳���
				ChatThread chat = new ChatThread();
				chat.start();
			
			}
			else {
				String cryptName = e.getActionCommand();
				//ʵ��һ�����ܴ���
				CryptFrame cf = new CryptFrame(cryptName);
				cf.unit();
			}
		}

	};

	MouseAdapter ma = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			// �رհ�ť�ļ���
			if (e.getSource() == closepanel)
				System.exit(0);

			// ��С����ť �ļ���
			if (e.getSource() == minimizepanel)
				jf.setExtendedState(1);
		}

		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == closepanel) {
				Graphics2D g = (Graphics2D) closepanel.getGraphics();
				g.setComposite(AlphaComposite.SrcOver.derive(0.4f));
				g.setColor(Color.white);
				g.fillRect(0, 1, 30, 30);
				g.setComposite(AlphaComposite.SrcOver.derive(1f));
			}
			if (e.getSource() == minimizepanel) {
				Graphics2D g = (Graphics2D) minimizepanel.getGraphics();
				g.setComposite(AlphaComposite.SrcOver.derive(0.4f));
				g.setColor(Color.white);
				g.fillRect(0, 1, 30, 30);
				g.setComposite(AlphaComposite.SrcOver.derive(1f));
			}
		}

		public void mouseExited(MouseEvent e) {
			if (e.getSource() == closepanel) {
				closepanel.repaint();
			}
			if (e.getSource() == minimizepanel) {
				minimizepanel.repaint();

			}

		}
		public void mouseDragged(MouseEvent e) {
			//�����Ǵ��?���϶��Ĵ���
				Point point=jf.getLocation();
				int tempx,tempy;
				tempx=e.getX()+point.x;
				tempy=e.getY()+point.y;
				jf.setLocation(point.x+tempx-oldX, point.y+tempy-oldY);
				oldX=tempx;
				oldY=tempy;
			
		}
		public void mousePressed(MouseEvent e) {
			
			//�����Ǵ��?���϶��Ĵ���
			if(firstpress){
					Point point=jf.getLocation();
					oldX=e.getX()+point.x;
					oldY=e.getY()+point.y;
					firstpress=false;
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			firstpress=true;
			
		}

	};

}
