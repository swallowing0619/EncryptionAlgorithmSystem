package 加密算法;

import java.util.Scanner;

public class FangSheCrypt {
	public static int k1;
	public static int k2;
	
	public int gcd(int a, int b)               //��������㷨����֤�Ƿ���
	{
		int temp;
		int t=0;
		if(a<b)
		{ temp=a;a=b;b=temp;}
			while(t!=0) 
			{
				t=a%b;
				a=b;
				b=t;
			}
			return a;
	}
		
	public int gcdex(int a,int n)             //����Ԫ�㷨
	{
		int Q;
		int x1,x2,x3,t1,t2,t3,y1,y2,y3;
		
		x1=1;x2=0;x3=n;
		y1=0;y2=1;y3=a;	
		while(true)
		{
			if(y3==0)
				return 0;
			if(y3==1)
				return y2;	
			Q=x3/y3;
			t1=x1-Q*y1;t2=x2-Q*y2;t3=x3-Q*y3;
			x1=y1;x2=y2;x3=y3;
			y1=t1;y2=t2;y3=t3;
			
		}
	}
	//����
	public char[] encode(String str,int length)
	{
		System.out.println("���ܣ�����="+str+" ���ȣ�"+length);
		int[] fx = new int[length];
		int[] x = new int[length];
		char[] miwen = new char[length];		
		for(int i=0;i<str.length();i++)
		{
			x[i]=str.charAt(i)-'a';
		}
		for(int i=0;i<length;i++)
		{
		    fx[i] = (k1*x[i]+k2) % 26;
		}
		for(int i=0;i<length;i++)
		{
		    miwen[i] = (char) (fx[i] + 'a');  //
		    System.out.println(miwen[i]);
		}
		return miwen;
	}
	//����
	public char[] decode(char miwen[],int length)
	{
		int[] x = new int[length];
		int[] fx = new int[length];
		char[] mingwen = new char[length];	
		for(int i=0;i<length;i++)
		{
		    fx[i] = miwen[i]-'a';
		}
		for(int i=0;i<length;i++)
		{
		    x[i] = (((gcdex(k1,26)+26)%26*(fx[i]-k2))%26+26)%26;
		}
		for(int i=0;i<length;i++)
		{
		    mingwen[i] = (char) (x[i] + 'a');
		}
		
		return mingwen;
	}
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in); 
		FangSheCrypt fs = new FangSheCrypt();
		System.out.println("�����������ַ��ȣ�");
		int length = scan.nextInt();  
		
		Scanner scan1=new Scanner(System.in);
		System.out.println("�����������ַ�");
		String str = scan1.nextLine();
		
		int[] x = new int[length];

		System.out.println("��������Կk1,k2��");
		k1= scan.nextInt();
		k2= scan.nextInt();
		
		char[] temp = new char[length];
		temp = fs.encode(str,length);	
		
		for(int i=0;i<length;i++)
		{
			System.out.print(temp[i]+" ");
		}
		
		System.out.println("\n");
		
	    char[] temp2 = new char[length];
		temp2 = fs.decode(temp,length);
		
		for(int i=0;i<length;i++)
		{
			System.out.print(temp2[i]+" ");
		}

	}

}
