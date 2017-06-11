package 加密算法;

import java.util.Scanner;

public class RC4Crypt {
	public static int L=8 ;                   //������Կ���鳤��
	public static int length=5;                  //���ļ����ĳ���
	public static int[] keys;
	
	public int[] generateKey(byte Key[])        //������Կ��
	{
	    int[] S = new int[256];
	    int[] K = new int[256];
	    for(int i=0;i<256;i++)
	    {
	    	S[i] = i;
	    	K[i] = Key[i % L];
	    }
	    for(int temp,j=0,i=0;i<256;i++)
	    {
	    	j=(j+S[i]+K[i])%256;
	    	temp=S[i];S[i]=S[j];S[j]=temp;
	    }
	    int tmp,t;
	    int k[] = new int[length];
	    for(int j=0,i=0,count=0;i<length;count++)
	    {
	    	i=(i+1)%256;
	    	j=(j+S[i])%256;
	    	tmp=S[i];S[i]=S[j];S[j]=tmp;
	    	t=(S[i]+S[j])%256;
	    	k[count]=S[t];
	    }
	    return k;
	}
	
	public void seedKey(String strr)                
	{
		byte[] key=new byte[L];
		for(int i=0;i<L;i++)
		{
			key[i] = (byte) (strr.charAt(i)-0);
		}
		keys = generateKey(key);         //������Կ��
	}
	
	public int[] encryption(int[] keys,int[] mingwen)    //���ܺ���(�����������)
	{		
		int[] temp = new int[length];
		for(int i=0;i<length;i++)
		{
			temp[i] = keys[i]^mingwen[i];
		}
		return temp;
	}
	
	public String encode(String strr)
	{
		int[] mingwen=new int[length];
		for(int i=0;i<length;i++)
		{
			mingwen[i] = strr.charAt(i)-'a';
		}
		
		int[] miwen = encryption(keys, mingwen);     //����
		
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<length;i++)
		{
			String s=String.valueOf(miwen[i]);
			sb.append(s);
			sb.append(' ');
		}
		System.out.println("������������£�");
		//System.out.print(sb);
		return sb.toString();
	}
	
	public String decode(String strr)
	{
		String[] st=strr.split(" ");
		int[] miwen=new int[length];
		for(int i=0;i<length;i++)
		{
			miwen[i]=Integer.parseInt(st[i]);
		}
		
		int[] mingwen = new int[length];
		char[] tmp = new char[length];
		
		mingwen = encryption(keys,miwen);
		for(int i=0;i<length;i++)
		{
			tmp[i] = (char)(mingwen[i]+'a');
		}
		String mw=String.valueOf(tmp);
		return mw;
	}

	public static void main(String[] args) {
		RC4Crypt rc4 = new RC4Crypt();
//		Scanner scan=new Scanner(System.in);
//		System.out.println("���������ĳ��ȣ�");
//		length = scan.nextInt();
		
		String s="china";
		String seed="������ϧ������ϧ";
		System.out.println(seed.length());
		rc4.seedKey(seed);
		System.out.println(rc4.decode(rc4.encode(s)));
	 

	}
}
