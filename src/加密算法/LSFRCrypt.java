package 加密算法;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LSFRCrypt {

	public int[] a;       //������żĴ�����ʼ״̬
	public int[] b;       //���������Կ
	public int len;       //�������ĵĳ���
	public int num;       //����a[i]*b[i]���м����
	public String s;      //����
	public List list;     //��Կ������
	public LinkedList curr1; //��¼�Ĵ����ĵ�ǰ״̬
	public LinkedList curr2; //��¼a[i]*b[i]�ĵ�ǰ״̬
	public byte[] by;
	public int[] miwen;
	Scanner in=new Scanner(System.in);
	public LSFRCrypt(int l,String m)
	{
		a=new int[l];
		b=new int[l];
		list=new ArrayList();
		curr1=new LinkedList();
		curr2=new LinkedList();
		num=0;
		s=m;
		len=m.length();
		
	}
	public LSFRCrypt(int l,int i)
	{
		a=new int[l];
		b=new int[l];
		list=new ArrayList();
		curr1=new LinkedList();
		curr2=new LinkedList();
		num=0;
		miwen=new int[i];
	}
	
	public void state(int l,String st)//�����ʼ״̬����
	{
		char[] aab=st.toCharArray();
		for(int i=0;i<l;i++)
		{
			a[i]=(int)aab[i]-48;
			curr1.add(a[i]);
		}
	}
	public void seed(int l,String stt)//����������Կ����
	{
		char[] ab=stt.toCharArray();
		
		Object []obj=curr1.toArray();
		for(int i=0;i<l;i++)
		{
			b[i]=(int)ab[i]-48;
			curr2.add(Integer.parseInt(String.valueOf(obj[i]))*b[i]);
		}
	}
	public void mm(int l)
	{
		Object []obj=curr2.toArray();
    	list.add(curr1.getFirst());//��curr1�������Կ������Կ��
    	for(int j=0;j<l;j++)
    	{
    	 {
    		num+=Integer.parseInt(String.valueOf(obj[j]));
    	
    	 }
    	 if(num%2==1)          //����f����Ľ��
    	 {
    		curr1.add(1);
    	 }
    	 else
    		curr1.add(0);
    
    	 curr1.remove(0);
    	 Object []obj2=curr1.toArray();
    	 curr2.clear();
		 for(int m=0;m<l;m++)
		 {
			curr2.add(Integer.parseInt(String.valueOf(obj2[m]))*b[m]);
		 }
    	}
    	
	}
	public void liu(int l)//������Ĳ�����Կ��
	{
		
		for(int i=0;i<8*len;i++)
        {
			mm(l);
        }
	}
	public void liu2(int l)//������Ĳ�����Կ��
	{
		for(int i=0;i<8*miwen.length;i++)
		{
			mm(l);
		}
	}

}
