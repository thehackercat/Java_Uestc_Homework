import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import org.jfugue.player.Player;
import org.jfugue.midi.MidiDictionary;

public class Guitar extends JFrame implements ActionListener,Runnable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8127392809935280592L;
	
	char ch;
	
	JPanel panel;
	
	strings s1 = new strings("s1",this);
	strings s2 = new strings("s2",this);
	strings s3 = new strings("s3",this);
	strings s4 = new strings("s4",this);
	strings s5 = new strings("s5",this);
	strings s6 = new strings("s6",this);
	
	public Guitar()
	{
		super("小虚midi-guitar");
		
		this.setSize(1100,430);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				char ch = e.getKeyChar();
				System.out.println("你按了<"+ch+">键");
				
				if(ch=='1')
				{
					new Thread(new g1()).start();
				}
				
				if(ch=='2')
				{
					new Thread(new g2()).start();
				}
				
				if(ch=='3')
				{
					new Thread(new g3()).start();
				}
				
				if(ch=='4')
				{
					new Thread(new g4()).start();
				}
				
				if(ch=='5')
				{
					new Thread(new g5()).start();
				}
				
				if(ch=='6')
				{
					new Thread(new g6()).start();
				}
			}
			
		});
		
		this.setLayout(new FlowLayout());
		this.add(s1);
		this.add(s2);
		this.add(s3);
		this.add(s4);
		this.add(s5);
		this.add(s6);
	}
	
	class strings extends JButton
	{
		public strings(String name,ActionListener e)
		{
			this.addActionListener(e);
			this.setActionCommand(name);
			this.setIcon(new ImageIcon("StringsofGuitar.png"));
			this.setFocusable(false);
		}
	}	
	
	class g1 extends Thread implements Runnable
	{
		public void run()
		{
			Player play = new  Player();
			play.play("I[Guitar] c");
		}
	}
	
	class g2 extends Thread implements Runnable
	{
		public void run()
		{
			Player play = new Player();
			play.play("I[Guitar] d");
		}
	}
	
	class g3 extends Thread implements Runnable
	{
		public void run()
		{
			Player play = new Player();
			play.play("I[Guitar] e");
		}
	}
	
	class g4 extends Thread implements Runnable
	{
		public void run()
		{
			Player play = new Player();
			play.play("I[Guitar] f");
		}
	}
	
	class g5 extends Thread implements Runnable
	{
		public void run()
		{
			Player play = new Player();
			play.play("I[Guitar] a");
		}
	}
	
	class g6 extends Thread implements Runnable
	{
		public void run()
		{
			Player play = new Player();
			play.play("I[Guitar] b");
		}
	}
	
	
	public static void main(String[] args) 
	{
		new Guitar();
	}





	@Override
	public void run() 
	{
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO 自动生成的方法存根
		String e = arg0.getActionCommand();
		
		if (e.equals("s1"))
		{
			new Thread (new g1()).start();
		}
		
		if (e.equals("s2"))
		{
			new Thread (new g2()).start();
		}
		
		if (e.equals("s3"))
		{
			new Thread (new g3()).start();
		}
		
		if (e.equals("s4"))
		{
			new Thread (new g4()).start();
		}
		
		if (e.equals("s5"))
		{
			new Thread (new g5()).start();
		}
		
		if (e.equals("s6"))
		{
			new Thread (new g6()).start();
		}
	}
}