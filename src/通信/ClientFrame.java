package 通信;

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

import 加密算法.DHdeal;
import 加密算法.Md5Hash;
import 界面.CryptFrame;

public class ClientFrame extends JFrame implements MsgListener {
	private ImageIcon icon;// ͼ��
	private ImageIcon close;
	private ImageIcon mini;
	private JLabel closepanel;
	private JLabel minimizepanel;
	public JFrame jf = this;
	private int oldX, oldY;
	private boolean firstpress = true;
	private JTextArea log;
	private JTextField inputTxt;//��Ϣ�����
	private ClientThread ct;
	public JPanel rightpanel;
	private JLabel inputName;//ԭ���
	private JLabel jianTou;
	private JButton key_deal;
	//
	private int X;
	private JTextField X_jt;
	private DHdeal dh = new DHdeal();
	private int Y;
	private JTextField Y_jt,Y2_jt;
	private JTextField Md5_jt;
	private JTextField K_jt;
	private int KEY;
	
	public byte[] rsamiwen;
	

	public static void main(String[] args) {
		new ClientFrame().unit();
	}

	public ClientFrame() {

		this.icon = new ImageIcon("images/icon.jpg");
		close = new ImageIcon("images/close2.jpg");
		mini = new ImageIcon("images/mini2.jpg");
	}

	// �ӿڵ�ʵ��
	public void onRecvMsg(String str) {
		log.append(str);
	}

	// �ӿڵ�ʵ��
	public void addComponent() {
		// �Ƴ�ԭ���
//		System.out.println("yichu");
		rightpanel.remove(inputName);
		rightpanel.remove(jianTou);
		rightpanel.remove(key_deal);
		// �����Կ�����
		JLabel jl = new JLabel("X=");
		rightpanel.add(jl);
		 X_jt = new JTextField(5);
		rightpanel.add(X_jt);
		jianTou = new JLabel("��");
		jianTou.setPreferredSize(new Dimension(30, 18));
		rightpanel.add(jianTou);
		JButton jb = new JButton("����Yֵ");
		jb.setBackground(Color.white);
		jb.addActionListener(l);
		rightpanel.add(jb);
		jl = new JLabel("Y=");
		rightpanel.add(jl);
		Y_jt = new JTextField(5);
		rightpanel.add(Y_jt);
		jianTou = new JLabel("��");
		jianTou.setPreferredSize(new Dimension(30, 18));
		rightpanel.add(jianTou);
		jb = new JButton("����ɢ��");
		jb.addActionListener(l);
		jb.setBackground(Color.white);
		rightpanel.add(jb);
		Md5_jt = new JTextField(5);
		rightpanel.add(Md5_jt);
		jl = new JLabel("�Է�Y=");
		rightpanel.add(jl);
		Y2_jt = new JTextField(5);
		rightpanel.add(Y2_jt);
		jb = new JButton("����Kֵ");
		jb.addActionListener(l);
		jb.setBackground(Color.white);
		rightpanel.add(jb);
		jl = new JLabel("K=");
		rightpanel.add(jl);
		K_jt = new JTextField(5);
		rightpanel.add(K_jt);

		jb = new JButton("RSA����");
		jb.addActionListener(l);
		jb.setBackground(Color.PINK);
		rightpanel.add(jb);

		rightpanel.repaint();
	}

	public void unit() {
		this.setTitle("�Ự����");
		this.setSize(new Dimension(500, 400));
		// ����ͼ��
		this.setIconImage(icon.getImage());
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		// �������
		northPanel();

		// �ϱ�
		JPanel southpanel = new JPanel(new BorderLayout());
		southpanel.setPreferredSize(new Dimension(0, 340));
		this.add(southpanel, BorderLayout.SOUTH);

		// ���
		JPanel leftpanel = new JPanel();
		leftpanel.setPreferredSize(new Dimension(410, 0));
		leftpanel.setBackground(Color.WHITE);
		southpanel.add(leftpanel, BorderLayout.WEST);

		log = new JTextArea(16, 35);
		log.setFocusable(false);
		leftpanel.add(log);
		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(400, 2));
		center.setBackground(Color.GRAY);
		leftpanel.add(center);
		inputTxt = new JTextField(25);
		leftpanel.add(inputTxt);
		JButton jb = new JButton("����");
		jb.setBackground(Color.LIGHT_GRAY);
		leftpanel.add(jb);
		jb.addActionListener(l);

		// �ұ�
		rightpanel = new JPanel();
		rightpanel.setPreferredSize(new Dimension(90, 0));
		rightpanel.setBackground(Color.LIGHT_GRAY);
		southpanel.add(rightpanel, BorderLayout.EAST);

		inputName = new JLabel("��������");
		rightpanel.add(inputName);
		jianTou = new JLabel("��");
		jianTou.setPreferredSize(new Dimension(30, 20));
		rightpanel.add(jianTou);
		key_deal = new JButton("Э����Կ");
		key_deal.setBackground(Color.WHITE);
		key_deal.addActionListener(l);// ��Ӽ���
		rightpanel.add(key_deal);

		this.setVisible(true);
		this.addMouseMotionListener(ma);
		this.addMouseListener(ma);

		// ���ÿͻ����߳�
		ct = new ClientThread();
		ct.addListener(this);
		ct.start();

	}

	// ��ť������
	ActionListener l = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("����")) {
				String msg = inputTxt.getText();
				inputTxt.setText("");
				msg += "\n";
				log.append("��:" + msg);
				// ���͵�������
				ct.sendMessage(msg);
			}

			else if (e.getActionCommand().equals("Э����Կ")) {
				System.out.println("Э����Կ!!!");
				ct.sendKEYDeal("Э����Կ");

			}

			else if (e.getActionCommand().equals("����Yֵ")) {
				X = Integer.parseInt(X_jt.getText()); 
				//����
				Y = dh.chengfang(X, dh.a) ;
				System.out.println(Y+"");
				Y_jt.setText(""+Y);
				

			} else if (e.getActionCommand().equals("����ɢ��")) {
				System.out.println(""+Y);
				Md5_jt.setText( Md5Hash.MD5(Y_jt.getText()));
				inputTxt.setText(Md5Hash.MD5(Y_jt.getText()));

			} else if (e.getActionCommand().equals("����Kֵ")) {
				int Y2 = Integer.parseInt(Y2_jt.getText());
				KEY = dh.chengfang(X, Y2) ;
				K_jt.setText(KEY+"");

			} else if (e.getActionCommand().equals("RSA����")) {
				//ʵ��һ�����ܴ���
				CryptFrame cf = new CryptFrame("RSA����");
				cf.unit();
				rsamiwen = cf.cryptlistener.rsaMiwenByte;
			}
		}

	};

	/**
	 * �������
	 */
	private void northPanel() {
		JPanel jp = new JPanel() {
			// ��дpaint����(�ػ�)
			public void paint(Graphics g) {
				super.paint(g);
				// ��ʾ����
				String str = "����������";
				Font f = new Font("����", Font.BOLD, 20);
				g.setFont(f);
				g.setColor(Color.WHITE);
				g.drawString(str, 200, 35);

			}
		};
		jp.setBackground(new Color(32, 178, 170));
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

}
