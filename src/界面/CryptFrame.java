package 界面;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CryptFrame extends JFrame {
	public static String cryptName;// �����㷨���
	private ImageIcon icon;// ͼ��

	public JTextField mingwInput, miwInput;// ����,���������
	public JTextField keyInput1, keyInput2, keyInput3, keyInput4;// ��Կ�����
	public JTextArea showmingw, showmiw;// ��ʾ���ܺͽ��ܺ���ı�

	private JButton encode, decode;// �ӽ���ȷ����ť
	private JLabel closepanel;
	private JLabel minimizepanel;
	private ImageIcon close;
	private ImageIcon mini;
	private JFrame jf;
	private int oldX, oldY;
	private boolean firstpress = true;
	public CryptListener cryptlistener;// ��ť�����¼�

	// ���췽��
	public CryptFrame(String cryptName) {
		this.cryptName = cryptName;
		this.icon = new ImageIcon("images/icon.jpg");
		close = new ImageIcon("images/close2.jpg");
		mini = new ImageIcon("images/mini2.jpg");
		jf = this;
		cryptlistener = new CryptListener(cryptName, this);
	}

	// ��ʼ������
	public void unit() {
		jf.setTitle(cryptName);
		jf.setSize(500, 410);
		// ��ֹ�ر��������
		jf.setLocationRelativeTo(null);
		jf.setUndecorated(true);
		// ����ͼ��
		jf.setIconImage(icon.getImage());
		// �������
		northPanel();

		// �ϱ����
		sorthPanel();
		jf.setVisible(true);
		jf.addMouseMotionListener(ma);
		jf.addMouseListener(ma);
	}

	/**
	 * �������
	 */
	private void northPanel() {
		JPanel jp = new JPanel() {
			// ��дpaint����(�ػ�)
			public void paint(Graphics g) {
				super.paint(g);
				// ��ʾ����
				String str = CryptFrame.cryptName;
				Font f = new Font("����", Font.BOLD, 20);
				g.setFont(f);
				g.setColor(Color.WHITE);
				g.drawString(str, 200, 35);

			}
		};
		jp.setBackground(Color.PINK);
		jp.setPreferredSize(new Dimension(0, 60));
		jp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// ���ڵĹرռ������
		closepanel = new JLabel(close);
		closepanel.setBackground(Color.RED);
		closepanel.setPreferredSize(new Dimension(30, 30));
		closepanel.addMouseListener(ma);

		// ���ڵ���С���������
		minimizepanel = new JLabel(mini);
		minimizepanel.setBackground(Color.RED);
		minimizepanel.setPreferredSize(new Dimension(30, 30));
		minimizepanel.addMouseListener(ma);

		jp.add(minimizepanel);
		jp.add(closepanel);
		this.add(jp, BorderLayout.NORTH);

	}

	MouseAdapter ma = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			// �رհ�ť�ļ���
			if (e.getSource() == closepanel)
				jf.dispose();// !!!�رմ���

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
			// �����Ǵ��?���϶��Ĵ���
			Point point = jf.getLocation();
			int tempx, tempy;
			tempx = e.getX() + point.x;
			tempy = e.getY() + point.y;
			jf.setLocation(point.x + tempx - oldX, point.y + tempy - oldY);
			oldX = tempx;
			oldY = tempy;

		}

		public void mousePressed(MouseEvent e) {

			// �����Ǵ��?���϶��Ĵ���
			if (firstpress) {
				Point point = jf.getLocation();
				oldX = e.getX() + point.x;
				oldY = e.getY() + point.y;
				firstpress = false;
			}
		}

		public void mouseReleased(MouseEvent e) {
			firstpress = true;

		}

	};

	// private JTextField fskeyInput;

	/**
	 * �ϱ����
	 */
	private void sorthPanel() {
		JPanel jp = new JPanel(new BorderLayout());
		jp.setPreferredSize(new Dimension(0, 350));

		leftPanel(jp);

		// �н���
		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(4, 0));
		center.setBackground(Color.yellow);
		jp.add(center);

		rightPanel(jp);

	}

	public void leftPanel(JPanel jp) {
		// ��߼������
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(248, 0));
		left.setBackground(Color.LIGHT_GRAY);
		jp.add(left, BorderLayout.WEST);
		showmiw = new JTextArea(14, 20);
		// showmiw.setFocusable(false);
		left.add(showmiw);

		// ��ǩ
		JLabel mingwjl = new JLabel("����������");
		left.add(mingwjl);
		// �����
		mingwInput = new JTextField(13);
		left.add(mingwInput);
		if (cryptName == "RC4����") {
			JLabel keyjl = new JLabel("��������Կ");
			left.add(keyjl);
			keyInput1 = new JTextField(13);
			left.add(keyInput1);
		} else if (cryptName == "�������" ) {
			JLabel key1 = new JLabel("KEY1");
			left.add(key1);
			keyInput1 = new JTextField(5);
			left.add(keyInput1);
			JLabel key2 = new JLabel("KEY2");
			left.add(key2);
			keyInput2 = new JTextField(5);
			left.add(keyInput2);
		}else if (cryptName == "RSA����") {
			JLabel keyjl = new JLabel("�ҵ�˽Կ");
			left.add(keyjl);
			keyInput1 = new JTextField(13);
			keyInput1.setText(" �����");
			left.add(keyInput1);
		} else if(cryptName == "DES����"){
			JLabel keyjl = new JLabel("�����Կ");
			left.add(keyjl);
			keyInput1 = new JTextField(13);
			keyInput1.setText("�����");
			left.add(keyInput1);
		}else if(cryptName == "LFSR����"){
			JLabel keyjl = new JLabel("�����Կ");
			left.add(keyjl);
			keyInput1 = new JTextField(5);
			left.add(keyInput1);
			JLabel keyj3 = new JLabel("�Ľ���Կ");
			left.add(keyj3);
			keyInput2 = new JTextField(5);
			left.add(keyInput2);
			
		}
		// ��ť
		encode = new JButton("����");
		encode.setBackground(Color.WHITE);
		encode.setPreferredSize(new Dimension(80, 30));
		encode.addActionListener(cryptlistener);
		left.add(encode);
	}

	public void rightPanel(JPanel jp) {
		// �ұ߼������
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(248, 0));
		jp.add(right, BorderLayout.EAST);
		right.setBackground(Color.LIGHT_GRAY);
		showmingw = new JTextArea(14, 20);
		right.add(showmingw);
//		showmingw.setFocusable(false);
		// ��ǩ
		JLabel miwjl = new JLabel("����������");
		right.add(miwjl);
		// �����
		miwInput = new JTextField(13);
		right.add(miwInput);
		if (cryptName == "RC4����" ) {
			JLabel keyjl2 = new JLabel("��������Կ");
			right.add(keyjl2);
			keyInput3 = new JTextField(13);
			right.add(keyInput3);
		} else if (cryptName == "�������") {
			JLabel key1 = new JLabel("KEY1");
			right.add(key1);
			keyInput3 = new JTextField(5);
			right.add(keyInput3);
			JLabel key2 = new JLabel("KEY2");
			right.add(key2);
			keyInput4 = new JTextField(5);
			right.add(keyInput4);
		}else if(cryptName == "RSA����"){
			JLabel keyjl2 = new JLabel("��Կ");
			right.add(keyjl2);
			keyInput3 = new JTextField(13);
			right.add(keyInput3);
		} else if(cryptName == "DES����"){
			JLabel keyjl = new JLabel("��Կ");
			right.add(keyjl);
			keyInput3 = new JTextField(13);
			right.add(keyInput3);
		}else if(cryptName == "LFSR����"){
			
			JLabel keyjl = new JLabel("�����Կ");
			right.add(keyjl);
			keyInput3 = new JTextField(5);
			right.add(keyInput3);
			JLabel keyj3 = new JLabel("�Ľ���Կ");
			right.add(keyj3);
			keyInput4 = new JTextField(5);
			right.add(keyInput4);
		}

		// ��ť
		decode = new JButton("����");
		decode.setBackground(Color.WHITE);
		decode.setPreferredSize(new Dimension(80, 30));
		decode.addActionListener(cryptlistener);//����
		right.add(decode);

		jf.add(jp, BorderLayout.SOUTH);
	}

}
