import java.io.IOException;


public class HowToSync {

	private int x;
	
	public void puzzle() throws IOException
	{
		//get one
		System.in.read();
		{
			int current =x;
			x = current +1;
			System.out.print(x+ "-");
		}
	}
	
	public void go()
	{
		for (int i=0;i<5;i++)
		{
			new Thread()
			{
				public void run()
				{
					//
					try {
						puzzle();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	public static void main(String[] args) {
		new HowToSync().go();
	}

}
