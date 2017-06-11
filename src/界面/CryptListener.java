package 界面;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import 加密算法.DESCrypt;
import 加密算法.FangSheCrypt;
import 加密算法.LFSRDemo;
import 加密算法.LSFRCrypt;
import 加密算法.RC4Crypt;
import 加密算法.RSACrypt;

public class CryptListener implements ActionListener {
	public static String cryptName;// �����㷨���
	public int nameFlag;
	public CryptFrame cframe;

	private FangSheCrypt fscrypt = new FangSheCrypt();
	private RC4Crypt rc4Crypt = new RC4Crypt();
	private DESCrypt desCrypt = new DESCrypt();
	private LFSRDemo lfsrCrypt =  new LFSRDemo();
	private RSACrypt rsaCrypt = new RSACrypt();
	private RSACrypt rsaCrypt2 = new RSACrypt();

	private String mingwen, miwen, key1, key2;
	public byte[] rsaMiwenByte,desMiwenByte;
	private int n;
	private byte[] desKeyByte;
	private int[] lsfrMi;

	// ���췽��
	public CryptListener(String cryptName, CryptFrame cframe) {
		this.cryptName = cryptName;
		this.cframe = cframe;

		// �趨���
		if (cryptName.equals("�������")) {
			nameFlag = 1;
		} else if (cryptName.equals("RC4����")) {
			nameFlag = 2;
		} else if (cryptName.equals("LFSR����")) {
			nameFlag = 3;
		} else if (cryptName.equals("DES����")) {
			nameFlag = 4;
		} else if (cryptName.equals("RSA����")) {
			nameFlag = 5;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("����")) {

			if (cframe.mingwInput.getText().equals("")) {
				System.out.println("�������ı���");
				return; // �������Ϊ���򷵻�
			} else {
				switch (nameFlag) {
				case 1: // �������
					fangsheEncrypt();
					break;
				case 2: // RC4����
					RC4Encrypt();
					break;
				case 3: // LFSR����
					LFSREncrypt();
					break;
				case 4: // DES����
					try {
						DESEncrypt();
					} catch (InvalidKeyException | NoSuchAlgorithmException
							| IllegalBlockSizeException | BadPaddingException
							| NoSuchPaddingException | InvalidKeySpecException e1) {
						e1.printStackTrace();
					}
					break;
				case 5: // RSA����
					RSAEncrypt();
					break;
				default:
					break;
				}
			}

		} else if (e.getActionCommand().equals("����")) {
			if (cframe.miwInput.getText().equals("")) {
				return;
			} else {
				switch (nameFlag) {
				case 1: // �������
					fangsheDecrypt();
					break;
				case 2: // RC4����
					RC4Decrypt();
					break;
				case 3: // LFSR����
					LFSRDecrypt();
					break;
				case 4: // DES����
					try {
						DESDecrypt();
					} catch (InvalidKeyException | IllegalBlockSizeException
							| BadPaddingException | NoSuchAlgorithmException
							| NoSuchPaddingException | InvalidKeySpecException e1) {
						e1.printStackTrace();
					}
					break;
				case 5: // RSA����
					RSADecrypt();
					break;
				default:
					break;
				}
			}

		}

	}
	
	public void RSAEncrypt(){
		rsaCrypt = new RSACrypt();
		rsaCrypt2 = new RSACrypt();
		mingwen = cframe.mingwInput.getText();// ����
		// rsaCrypt2 = new RSACrypt();
		int publicKey = rsaCrypt2.publicKey;
		int privateKey = rsaCrypt2.privateKey;
		n = rsaCrypt2.n;
		rsaCrypt.getPublicKey(publicKey);
		rsaCrypt.getn(n);
		byte[] rsamiwen = rsaCrypt.encrypt(mingwen);
		// ��ʾ����
		rsaMiwenByte = rsamiwen;
		cframe.keyInput1.setText("" + publicKey);
		cframe.keyInput3.setText("" + privateKey);
		cframe.miwInput.setText(rsaMiwenByte + "");
		
		cframe.showmiw.append("���ģ�" + rsamiwen + "\n");
		cframe.showmiw.append("�ҵ�˽Կ��" + publicKey+"  ģ��n="+n+"\n");
		System.out.println("RSA������ɣ�");
	}
	
	public void RSADecrypt(){
		miwen = cframe.miwInput.getText();// ����
		String privateKey = cframe.keyInput3.getText();
		byte[] miw = miwen.getBytes();
		System.out.println("rsa���ģ�" + rsaMiwenByte);
		// int privatekey =
		// Integer.parseInt(cframe.keyInput3.getText());
		// rsaCrypt2.privateKey = privatekey;
		String rsamingwen = rsaCrypt2.decrypt(rsaMiwenByte);
		// ��ʾ����
		cframe.showmingw.append("���ģ�" + rsamingwen + "\n");
		cframe.showmingw.append("�Է���Կ��" + privateKey+"  ģ��n="+n+"\n");
		System.out.println("RSA������ɣ�");
	}
	
	public void DESEncrypt() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException{
		mingwen = cframe.mingwInput.getText();// ����
		//��������Կ
		desCrypt = new DESCrypt();
		desKeyByte = desCrypt.miyao();//��Կ
		System.out.println("�����Կ��"+desKeyByte);
		cframe.keyInput1.setText(desKeyByte+"");
		cframe.keyInput3.setText(desKeyByte+"");
		//����
		byte[] encryptedData = desCrypt.encrypt(desKeyByte, mingwen);
		desMiwenByte = encryptedData;
		cframe.showmiw.append(""+encryptedData);
		cframe.miwInput.setText(""+encryptedData);
		System.out.println("DES������ɣ�");
	}
	public void DESDecrypt() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException{
		miwen = cframe.miwInput.getText();// ����
		//����
		System.out.println("des���ģ�"+desMiwenByte);
		String decryptedData = desCrypt.decrypt(desKeyByte, desMiwenByte);
		cframe.showmingw.append("���ģ�"+decryptedData);
		System.out.println("DES������ɣ�");
	}
	public void LFSREncrypt(){
		mingwen = cframe.mingwInput.getText();//����
		String fiveKey = cframe.keyInput1.getText();
		String fourKey = cframe.keyInput2.getText();
		//����
		String miwen = lfsrCrypt.jiami(mingwen, "11111", fiveKey, "1111", fourKey);
//		lsfrMi = miwen;
		cframe.miwInput.setText(miwen+"");
		cframe.showmiw.append("���ģ�"+miwen+"");
		cframe.showmiw.append("\n��ʼ���зֱ�Ϊ11111,1111\n");
		
	}
	
	public void LFSRDecrypt(){
		miwen = cframe.miwInput.getText();//����
		String fiveKey = cframe.keyInput3.getText();
		String fourKey = cframe.keyInput4.getText();
		//����
		char[] mingwen = lfsrCrypt.jiemi(miwen, "11111", fiveKey, "1111", fourKey);
		String mingwen2 = String.valueOf(mingwen);
		cframe.showmingw.append("���ģ�"+mingwen2);
		cframe.showmingw.append("\n��ʼ���зֱ�Ϊ11111,1111\n");
	}

	public void RC4Encrypt() {
		mingwen = cframe.mingwInput.getText();// ����
		key1 = cframe.keyInput1.getText();// ��Կ1
		RC4Crypt.L = key1.length();
		RC4Crypt.length = mingwen.length();
		rc4Crypt.seedKey(key1);
		String rc4miwen = rc4Crypt.encode(mingwen);
		// ��ʾ����
		cframe.showmiw.append("���ģ�" + rc4miwen + "\n");
		cframe.miwInput.setText(rc4miwen);
		System.out.println("RC4������ɣ�");
	}

	public void RC4Decrypt() {
		miwen = cframe.miwInput.getText();// ����
		key2 = cframe.keyInput3.getText();
		RC4Crypt.L = key2.length();
		RC4Crypt.length = mingwen.length();
		rc4Crypt.seedKey(key2);
		System.out.println("RC4Ҫ���ܵ����ģ�" + miwen);
		// ��ʾ����
		String rc4mingwen = rc4Crypt.decode(miwen);
		cframe.showmingw.append("���ģ�" + rc4mingwen + "\n");
		System.out.println("RC4������ɣ�");
	}

	public void fangsheEncrypt() {
		mingwen = cframe.mingwInput.getText();// ����
		key1 = cframe.keyInput1.getText();// ��Կ1
		key2 = cframe.keyInput2.getText();// ��Կ2
		// �������
		FangSheCrypt.k1 = Integer.parseInt(key1);
		FangSheCrypt.k2 = Integer.parseInt(key2);
		char[] miw = fscrypt.encode(mingwen, mingwen.length());
		String miw2 = String.valueOf(miw);
		// ��ʾ����
		cframe.showmiw.append("���ģ�" + miw2 + "\n");
		System.out.println("���������ɣ�");
	}

	public void fangsheDecrypt() {
		miwen = cframe.miwInput.getText();// ����
		char[] miwen2 = miwen.toCharArray();
		key1 = cframe.keyInput3.getText();// ��Կ1
		key2 = cframe.keyInput4.getText();// ��Կ2
		// �������
		FangSheCrypt.k1 = Integer.parseInt(key1);
		FangSheCrypt.k2 = Integer.parseInt(key2);
		char[] mingw = fscrypt.decode(miwen2, miwen.length());
		String mingw2 = String.valueOf(mingw);
		// ��ʾ����
		cframe.showmingw.append("���ģ�" + mingw2 + "\n");
//		cframe.mingwInput.setText(mingw2);
		System.out.println("���������ɣ�");
	}

}
