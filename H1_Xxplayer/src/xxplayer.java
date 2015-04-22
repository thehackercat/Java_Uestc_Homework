import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;

import javazoom.jl.converter.*;
import javazoom.jl.decoder.*;
import javazoom.jl.player.*;
import javazoom.jl.player.advanced.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.google.code.mp3fenge.Mp3Fenge;
import com.google.code.mp3fenge.Mp3Info;

public class xxplayer extends JFrame implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	xxplayer  gui;
	
	myButton play = new myButton ("播放",this);
	myButton pause = new myButton ("暂停",this);
	myButton stop = new myButton("停止",this);
	myButton next = new myButton("下一首",this);
	myButton add = new myButton("添加歌曲",this);
	myButton delete = new myButton("删除歌曲",this);
	
	JPanel Panel_playbox = new JPanel();
	JPanel Panel_button = new JPanel();
	
	JPanel mlistPanel = new JPanel();
	JPanel minfoPanel = new JPanel();
	
	TextArea songs = new TextArea("",40,60,1);
	TextArea list = new TextArea("",40,60,2);
	
	int full;
	int left;
	int nowPlaying = 0;
	Boolean notPaused = true;
	Player player;
	FileInputStream in;
	File file;
	Mp3Fenge mp3;
	Mp3Info info;
	static ArrayList<String>  musics = new ArrayList<String> ();
	
	public xxplayer()
	{
		super ("小虚播放器 ");
		
		this.setLayout(new BorderLayout()); 
		this.setSize(450*2,310*2);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel_playbox.setLayout(new GridLayout(1,2));
		
		mlistPanel.setBackground(Color.WHITE);
		minfoPanel.setBackground(Color.GRAY);
		
		Panel_playbox.add(mlistPanel);
		Panel_playbox.add(minfoPanel);
		this.add(Panel_playbox,BorderLayout.CENTER);
		this.add(Panel_button,BorderLayout.SOUTH);

		
		//playbutton.setBackground(Color.RED);
		//pausebutton.setBackground(Color.WHITE);
		//stopbutton.setBackground(Color.BLUE);
		
		Panel_button.setBackground(Color.LIGHT_GRAY);
		Panel_button.setLayout(new FlowLayout());
		Panel_button.add(play);
		Panel_button.add(pause);
		Panel_button.add(stop);
		Panel_button.add(next);
		Panel_button.add(add);
		Panel_button.add(delete);
	
		play.setEnabled(true);
		//playbutton.addActionListener(gui);
		play.setActionCommand("play");
		pause.setEnabled(true);
		//pausebutton.addActionListener(gui);
		pause.setActionCommand("pause");
		stop.setEnabled(true);
		//stopbutton.addActionListener(gui);
		stop.setActionCommand("stop");
		next.setEnabled(true);
		//nextbutton.addActionListener(gui);
		next.setActionCommand("next");
		add.setEnabled(true);
		//addbutton.addActionListener(gui);
		add.setActionCommand("add");
		delete.setEnabled(true);
		//deletebutton.addActionListener(gui);
		delete.setActionCommand("delete");
		
		mlistPanel.add(list);
		minfoPanel.add(songs);
		list.setEditable(false);
		songs.setEditable(false);
	}

	class myButton extends JButton
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8609342699288668430L;

		public myButton(String name,ActionListener action)
		{
			this.setText(name);
			this.addActionListener(action);
		}
	}
	
	class Play implements Runnable{
		
		Play(){
			Thread playThread = new Thread(this);
			playThread.setPriority(10);
			playThread.start();
		}
		
		public void run() {
			play.setEnabled(false);
			stop.setEnabled(true);

			new Update();
			
			pause.setEnabled(true);
			
			file=new File(musics.get(nowPlaying));
			mp3=new Mp3Fenge(file);
			info=mp3.getMp3Info();
			//frames=info.getTrackLength()*1000/26;
			
			
			//songs.setText("Title: "+"\n"+"   "+info.getTitle());
			//songs.append("\n"+"Album: "+"\n"+"   "+info.getAlbum());
			//songs.append("\n"+"Artist: "+"\n"+"   "+info.getArtist());
			//songs.append("\n"+"Length: "+"\n"+"   "+info.getTrackLengthAsString());
			
			try{
				in=new FileInputStream(file);
				player=new Player(in);
				full=in.available();
				
				if(notPaused){
					player.play();
					
					if(in.available()==0){
						if(nowPlaying==musics.size()-1){
							nowPlaying=0;
							new Play();
						}else {
							nowPlaying++;
							new Play();
						}
					}
					
				}else {
					in=new FileInputStream(file);
					in.skip(full-left);
					player=new Player(in);
					player.play();
				}
			}catch (JavaLayerException | IOException e) {
				e.printStackTrace();
			}
		}	
	}	
	
	
	class Pause implements Runnable{
			
		Pause(){
			new Thread(this).start();
		}
		public void run() {
			
			try {
				left=in.available();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			player.close();
			
			pause.setEnabled(false);
			play.setEnabled(true);
			notPaused=false;
			
		}	
	}
	
	
	class Stop implements Runnable{
		
		Stop(){
			new Thread(this).start();
		}

		public void run() {
			
			
			player.close();
	
			play.setEnabled(true);
			pause.setEnabled(false);
			stop.setEnabled(false);
			notPaused=true;
			
		}
		
	}
	
	class Next implements Runnable{
		
		Next() {
			new Thread(this).start();
		}

		public void run() {
			
			
			if(nowPlaying==musics.size()-1)
				nowPlaying=-1;
			
			nowPlaying++;
			
			
			player.close();
			
			notPaused=true;
			pause.setEnabled(true);
			
			new Play();
		}	
	}
	
	
	class Add implements Runnable{
		
		Add(){
			new Thread(this).start();
		}
		
		public void run() {
			FileDialog fd=new FileDialog(gui,"open",FileDialog.LOAD);
			fd.setVisible(true);
			String Address=fd.getDirectory();
			String Filename=fd.getFile();
			musics.add(Address+Filename);
			new Update();
		}
	}
	
	
	class Delete implements Runnable{
		
		Delete(){
			new Thread(this).start();
		}
		
		public void run() {
			
		
			player.close();
			
			musics.remove(nowPlaying);
			notPaused=true;
			
			if(musics.isEmpty()){
				songs.setText("MUSICS NOT FOUND!");
				list.setText("MUSICS NOT FOUND!");
			}else {
				if(nowPlaying==musics.size())
					nowPlaying--;
				new Play();
			}
		}
	}
	
	
	class Update implements Runnable{
		
		Update() {
			new Thread(this).start();
		}
		
		public void run(){
			int j;
			File file;
			Mp3Info mp3Info;
			list.setText("Music List: "+ "");
			songs.setText("Artist: ");
			for(j=0;j<musics.size();j++){
				file=new File(musics.get(j));
				mp3Info=new Mp3Fenge(file).getMp3Info(); 
				//list.append("\n"+"("+(j+1)+")"+" "+mp3Info.getTitle());
				//songs.append("\n"+mp3Info.getArtist());
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {

		String arg=e.getActionCommand();
		
		if(arg.equals("play")){
			new Play();
		}else if(arg.equals("stop")){
			new Stop();
		}else if(arg.equals("pause")){
			new Pause();
		}else if(arg.equals("next")){
			new Next();
		}else if(arg.equals("add")){
			new Add();
		}else if(arg.equals("delete")){
			new Delete();
		}
	}
	
	
	public static void main(String[] args)  
	{
		musics.add("musics/梶浦由記-the breath of the era.mp3");
		musics.add("musics/G.E.M.邓紫棋-泡沫.mp3");
		musics.add("musics/陈奕迅 - 十年.mp3");
		musics.add("musics/陈奕迅-好久不见.mp3");
		musics.add("musics/丁当 - 漂洋过海来看你.mp3");
		musics.add("musics/莫文蔚 - 如果没有你.mp3");
		musics.add("musics/莫文蔚 - 外面的世界.mp3");
		musics.add("musics/朴树-平凡之路 (电影《后会无期》主题歌).mp3");

		new xxplayer();
		
		

	}
	
}

