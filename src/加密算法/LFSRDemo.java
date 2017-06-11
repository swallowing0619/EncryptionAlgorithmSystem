package 加密算法;

public class LFSRDemo {
	
	public static String jiami(String m,String st5,String se5,String st4,String se4)
	{
		String s=m;
		LSFRCrypt lfsr1=new LSFRCrypt(5,s);
		LSFRCrypt lfsr2=new LSFRCrypt(4,s);
		
		
		lfsr1.state(5,st5);
		lfsr1.seed(5,se5);
		lfsr1.liu(5);
		
		lfsr2.state(4,st4);
		lfsr2.seed(4,se4);
		lfsr2.liu(4);
		int c[]=new int[lfsr1.list.size()];
		c[0]=lfsr1.a[0];
		Object []o1=lfsr1.list.toArray();
		Object []o2=lfsr2.list.toArray();
		
		int []g1=new int[o1.length];
		int []g2=new int[o2.length];
		
		for(int i=1;i<lfsr1.list.size();i++)
		{
			
			
			g1[i]=Integer.parseInt(String.valueOf(o1[i]));
			g2[i]=Integer.parseInt(String.valueOf(o2[i]));
			c[i]=(g1[i]^g2[i]^1)*c[i-1]^g1[i];
			
			
		}
		
		int lg=0;
		String sj="";
		for(int i=0;i<c.length;i++)
		{
			sj=sj+c[i];
		}
		System.out.println("��Կ������Ϊ��"+sj);
		int[] sb=new int[lfsr1.len];
		while(lg<sj.length())
		{
			String sd=sj.substring(lg, lg+8);
			
			
			int ch=Integer.parseInt(sd, 2);
			sb[lg/8]=ch;
			lg=lg+8;
		}
		
		 byte []b1=s.getBytes();
		
		 for(int i=0;i<s.length();i++)
		 {
			sb[i]=(int)(b1[i]^sb[i]);
		 }
		 System.out.println("���ܺ������");
		 for(int i=0;i<sb.length;i++)
		 {
			System.out.println(sb[i]);
		 }
		 
//		 return sb;
		 
		 StringBuilder sb2=new StringBuilder();
			for(int i=0;i<sb.length;i++)
			{
				String mm=String.valueOf(sb[i]);
				sb2.append(mm);
				sb2.append(' ');
			}
		return sb2.toString();
		 
	}
	public static char[] jiemi(String m,String st5,String se5,String st4,String se4)
	{
		    System.out.println(m);
//			lfsr1.miwen=lfsr2.miwen=m;
			String[] st=m.split(" ");
			int[] miwen=new int[st.length];
			for(int i=0;i<st.length;i++)
			{
				miwen[i]=Integer.parseInt(st[i]);
			}
					
			LSFRCrypt lfsr1=new LSFRCrypt(5,st.length);
			LSFRCrypt lfsr2=new LSFRCrypt(4,st.length);
			lfsr1.miwen=lfsr2.miwen=miwen;
		lfsr1.state(5,st5);
		lfsr1.seed(5,se5);
		lfsr1.liu2(5);
		
		lfsr2.state(4,st4);
		lfsr2.seed(4,se4);
		lfsr2.liu2(4);
		System.out.println(lfsr1.list.size());
		int c[]=new int[lfsr1.list.size()];
		c[0]=lfsr1.a[0];
		Object []o1=lfsr1.list.toArray();
		Object []o2=lfsr2.list.toArray();
		
		int []g1=new int[o1.length];
		int []g2=new int[o2.length];
		
		for(int i=1;i<lfsr1.list.size();i++)
		{
			
			
			g1[i]=Integer.parseInt(String.valueOf(o1[i]));
			g2[i]=Integer.parseInt(String.valueOf(o2[i]));
			c[i]=(g1[i]^g2[i]^1)*c[i-1]^g1[i];
			
			
		}
		
		int lg=0;
		String sj="";
		for(int i=0;i<c.length;i++)
		{
			sj=sj+c[i];
		}
		System.out.println("��Կ������Ϊ��"+sj);
		int[] sb=new int[lfsr1.miwen.length];
		while(lg<sj.length())
		{
			String sd=sj.substring(lg, lg+8);
			
			
			int ch=Integer.parseInt(sd, 2);
			sb[lg/8]=ch;
			lg=lg+8;
		}
		for(int i=0;i<lfsr1.miwen.length;i++)
		 {
			sb[i]=(int)(lfsr1.miwen[i]^sb[i]);
		 }
		 System.out.println("���ܺ������");
		 
		 char[] mingwen2 = new char[sb.length];
		 for(int i=0;i<sb.length;i++)
		 {
			 mingwen2[i]=(char)sb[i];
//			System.out.println(mingwen2[i]);
		 }
		 
		 return mingwen2;
	}
	public static void main(String []args)
	
	{
		
              jiami("abcd", "11111", "11111", "1111","1111");//���ܵ������ʽ
//              jiemi("22 ,21,33,70","11111","11111","1111","1111");//���ܵ������ʽ
		
		
	}

}
