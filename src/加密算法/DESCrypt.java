package 加密算法;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * ͨ��DES���ܽ���ʵ��һ��String�ַ�ļ��ܺͽ���.
 * 
 * @author badpeas
 * 
 */
public class DESCrypt {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException,
			BadPaddingException {
		// 1.1 >>> ����Ҫ����һ���ܳ�
		// DES�㷨Ҫ����һ�������ε������Դ
		Scanner in = new Scanner(System.in);
		System.out.println("��������");
		String str = in.nextLine();
		byte[] rawKeyData = miyao();

		// 2.1 >>> ���ü��ܷ���
		byte[] encryptedData = encrypt(rawKeyData, str);
		// 3.1 >>> ���ý��ܷ���
		decrypt(rawKeyData, encryptedData);
	}

	public static byte[] miyao() throws NoSuchAlgorithmException  {
		SecureRandom sr = new SecureRandom();
		// Ϊ����ѡ���DES�㷨���һ��KeyGenerator����
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(sr);
		// ����ܳ�
		SecretKey key = kg.generateKey();
		// ��ȡ�ܳ����
		byte rawKeyData[] = key.getEncoded();
		System.out.println("�ܳ�====>" + rawKeyData);
		return rawKeyData;

	}

	/**
	 * ���ܷ���
	 * 
	 * @param rawKeyData
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 */
	public static byte[] encrypt(byte rawKeyData[], String str)
			throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalBlockSizeException, BadPaddingException,
			NoSuchPaddingException, InvalidKeySpecException {
		// DES�㷨Ҫ����һ�������ε������Դ

		SecureRandom sr = new SecureRandom();
		// ��ԭʼ�ܳ���ݴ���һ��DESKeySpec����
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		// ����һ���ܳ׹�����Ȼ�������DESKeySpecת����һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɼ��ܲ���
		Cipher cipher = Cipher.getInstance("DES");
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		// ���ڣ���ȡ��ݲ�����
		byte data[] = str.getBytes();
		// ��ʽִ�м��ܲ���
		byte[] encryptedData = cipher.doFinal(data);

		System.out.println("���ܺ�===>" + encryptedData);
		return encryptedData;
	}

	/**
	 * ���ܷ���
	 * 
	 * @param rawKeyData
	 * @param encryptedData
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 */
	public static String decrypt(byte rawKeyData[], byte[] encryptedData)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException {
		// DES�㷨Ҫ����һ�������ε������Դ
		SecureRandom sr = new SecureRandom();
		// ��ԭʼ�ܳ���ݴ���һ��DESKeySpec����
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		// ����һ���ܳ׹�����Ȼ�������DESKeySpec����ת����һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɽ��ܲ���
		Cipher cipher = Cipher.getInstance("DES");
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		// ��ʽִ�н��ܲ���
		byte decryptedData[] = cipher.doFinal(encryptedData);
		System.out.println("���ܺ�===>" + new String(decryptedData));
		return new String(decryptedData);
	}
}