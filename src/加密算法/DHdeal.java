package 加密算法;

import java.util.Scanner;

public class DHdeal {
	public int q=251,a=3;
	
	public int chengfang(int x,int y)
	{
		int temp=1;
		for(int i=1;i<=x;i++)
			{
				temp=temp*y%q;
			}
			return temp;
	}

	public static void main(String[] args)
	{  
		DHdeal b = new DHdeal();
	    int x1,x2;      //x1 x2 �ֱ�ΪA��B�������ѡ�����
		int y1,y2;      //y1 y2 �ֱ�ΪA��B���˸�� yA �� a^xA modq��yB �� a^xB modq ���������
		int k1,k2,k;    //k1 k2 �ֱ�ΪA��B���˸�� k1 �� (yB)xA modq��k2 �� (yA)xB modq ���������
        System.out.println("���㷨q="+ b.q +"\n���㷨a="+ b.a );	
		//A��y1 yA �� a^xA modq
        System.out.println("������С��q��һ��������ΪA���ѡ���x1:");
        Scanner sc1=new Scanner(System.in);
        x1=sc1.nextInt();
		y1=b.chengfang(x1, b.a)%b.q;
		System.out.println("A�����ѡ���x1�����y1Ϊ:");
		System.out.println(y1);
		//B��y2 yB �� a^xB modq
		System.out.println("������С��q��һ��������ΪB���ѡ���x2:");
		Scanner sc2=new Scanner(System.in);
        x2=sc2.nextInt();
		y2=b.chengfang(x2, b.a)%b.q;
		System.out.println("B�����ѡ���x2�����y2Ϊ:");
		System.out.println(y2);
		//A��k1 k1 �� (yB)xA modq
	    k1=b.chengfang(x1, y2)%b.q;
	    System.out.println("A���x1��y2����������Կk1Ϊ:");
		System.out.println(k1);
		//B��k2 k2 �� (yA)xB modq 
		k2=b.chengfang(x2, y1)%b.q;
		System.out.println("B���x2��y1����������Կk2Ϊ:");
		System.out.println(k2);
		if(k1==k2) //��Կ��ͬ
		{
			k=k1;k=k2;
		    System.out.println("������ԿΪ��"+k);
		}
		
	 
	}
}
