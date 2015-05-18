package meituxiuxiu;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class xiuxiu extends JFrame
{
	String filepath = "";
	String filename = "";
	File file2;
	BufferedImage buffimg = null;
	JLabel jl;
	ImageIcon ic;       
	Image ia,l; 
	JMenuBar jmb;
	JMenu file,zoom,beautify,changecolor,view;
	
	int height = 180, width = 180;
	int rgbMatrix[][][];
	float hsbMatrix[][][];
	
	public xiuxiu(BufferedImage buffimg)
	{
		super("С��Ļ�ͼ����");
		
		jmb = new JMenuBar();
		
		file = new JMenu("�ļ�");
		JMenuItem open = new JMenuItem("  ��");
		open.addActionListener(new open_actionAdapter(this));
		file.add(open);
		file.addSeparator();
		
		JMenuItem save = new JMenuItem("  ���Ϊ");
		//save.addActionListener(new save_actionAdapter(this));
		file.add(save);
		file.addSeparator();
		
		JMenuItem exit = new JMenuItem("  �˳�");
		exit.addActionListener(new exit_actionAdapter(this));
		file.add(exit);
		
		zoom = new JMenu("����");
		JMenuItem lessen = new JMenuItem("  ��С");
		//lessen.addActionListener(new lessen_actionAdapter(this));
		zoom.add(lessen);
		zoom.addSeparator();
		
		JMenuItem vague = new JMenuItem("  ģ��");
		vague.addActionListener(new vague_actionAdapter(this));
		zoom.add(vague);
		
		beautify = new JMenu("����");
		JMenuItem bstcolor = new JMenuItem("  ���");
		bstcolor.addActionListener(new bstcolor_actionAdapter(this));
		beautify.add(bstcolor);
		beautify.addSeparator();
		
		JMenuItem drkconr = new JMenuItem("  ����");
		drkconr.addActionListener(new drkconr_actionAdapter(this));
		beautify.add(drkconr);
		beautify.addSeparator();
		
		JMenuItem blurconr = new JMenuItem("  ��Եģ��");
		blurconr.addActionListener(new blurconr_actionAdapter(this));
		beautify.add(blurconr);
		
		changecolor = new JMenu("��ɫ");
		JMenuItem cgecolor = new JMenuItem("  X6��ɫ");
		cgecolor.addActionListener(new cgecolor_actionAdapter(this));
		changecolor.add(cgecolor);
		changecolor.addSeparator();
		
		JMenuItem lomo1 = new JMenuItem("  LOMO�˾�");
		lomo1.addActionListener(new lomo1_actionAdapter(this));
		changecolor.add(lomo1);
		
		view = new JMenu("����");
		JMenuItem author = new JMenuItem("  ��������");
		author.addActionListener(new author_actionAdapter(this));
		view.add(author);
		view.addSeparator();
		
		JMenuItem version = new JMenuItem("  ���ڰ汾");
		version.addActionListener(new version_actionAdapter(this));
		view.add(version);
		
		jmb.add(file);
		jmb.add(zoom);
		jmb.add(beautify);
		jmb.add(changecolor);
		jmb.add(view);
		
		jl = new JLabel();
		JScrollPane pane = new JScrollPane(jl);
		
		this.setSize(800, 600);
		this.setLayout(new BorderLayout()); 
		this.setJMenuBar(jmb);
		this.add(pane,BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.buffimg = buffimg;
		this.rgbMatrix = new int[3][this.width][this.height];
		this.hsbMatrix = new float[3][this.width][this.height];
		for( int i = 0; i < this.width; i++ ){
			for ( int j = 0; j < this.height; j++){
				int pixel = buffimg.getRGB(i, j);
				this.rgbMatrix[0][i][j] = (pixel & 0xff0000) >> 16;
				this.rgbMatrix[1][i][j] = (pixel & 0xff00) >> 8;
				this.rgbMatrix[2][i][j] = (pixel & 0xff);
				float hsbvals[] = new float[3];
				Color.RGBtoHSB(this.rgbMatrix[0][i][j], this.rgbMatrix[1][i][j], this.rgbMatrix[2][i][j], 
					hsbvals);
				this.hsbMatrix[0][i][j] = hsbvals[0];
				this.hsbMatrix[1][i][j] = hsbvals[1];
				this.hsbMatrix[2][i][j] = hsbvals[2];
			}
		}
	}
	
	BufferedImage toBufferedImage(Image image) {
	    if (image instanceof BufferedImage) {
	        return (BufferedImage)image;
	     }
	 
	    // This code ensures that all the pixels in the image are loaded
	     image = new ImageIcon(image).getImage();
	 
	    // Determine if the image has transparent pixels; for this method's
	    // implementation, see e661 Determining If an Image Has Transparent Pixels
	    //boolean hasAlpha = hasAlpha(image);
	 
	    // Create a buffered image with a format that's compatible with the screen
	     BufferedImage bimage = null;
	     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    try {
	        // Determine the type of transparency of the new buffered image
	        int transparency = Transparency.OPAQUE;
	       /* if (hasAlpha) {
	         transparency = Transparency.BITMASK;
	         }*/
	 
	        // Create the buffered image
	         GraphicsDevice gs = ge.getDefaultScreenDevice();
	         GraphicsConfiguration gc = gs.getDefaultConfiguration();
	         bimage = gc.createCompatibleImage(
	         image.getWidth(null), image.getHeight(null), transparency);
	     } catch (HeadlessException e) {
	        // The system does not have a screen
	     }
	 
	    if (bimage == null) {
	        // Create a buffered image using the default color model
	        int type = BufferedImage.TYPE_INT_RGB;
	        //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
	        /*if (hasAlpha) {
	         type = BufferedImage.TYPE_INT_ARGB;
	         }*/
	         bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
	     }
	 
	    // Copy image to buffered image
	     Graphics g = bimage.createGraphics();
	 
	    // Paint the image onto the buffered image
	     g.drawImage(image, 0, 0, null);
	     g.dispose();
	 
	    return bimage;
	}
	
	BufferedImage cloneBuffer () 
	{
		Image ia=ic.getImage();
		BufferedImage b = toBufferedImage(ia);
		//BufferedImage b = new BufferedImage(this.width, this.height, this.buffimg.getType());
		Graphics g = b.getGraphics();
		g.drawImage(this.buffimg, 0, 0, null);
		g.dispose();
		return b;
	}
	
	//ģ��
	BufferedImage blur (ActionEvent e)
	{
		BufferedImage mdfImage = this.cloneBuffer();
		int rgbMatResized[][][] = new int[3][this.width][this.height];
		// ��С3x
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				// �õ�ͼƬrgbֵ
				int baseX = i / 3 * 3;
				int baseY = j / 3 * 3;
				for (int k = 0; k < 3; k++) {
					rgbMatResized[k][i][j] = (this.rgbMatrix[k][baseX][baseY] + 
					this.rgbMatrix[k][baseX+1][baseY] + 
					this.rgbMatrix[k][baseX+2][baseY] + 
					this.rgbMatrix[k][baseX][baseY+1] + 
					this.rgbMatrix[k][baseX+1][baseY+1] + 
					this.rgbMatrix[k][baseX+2][baseY+1] + 
					this.rgbMatrix[k][baseX][baseY+2] + 
					this.rgbMatrix[k][baseX+1][baseY+2] + 
					this.rgbMatrix[k][baseX+2][baseY+2])/9;
				}
				// ����ͼƬrgbֵ
				int rgb = new Color((rgbMatResized[0][i][j]+this.rgbMatrix[0][i][j])/2, 
					(rgbMatResized[1][i][j]+this.rgbMatrix[1][i][j])/2, 
					(rgbMatResized[2][i][j]+this.rgbMatrix[2][i][j])/2).getRGB();
				mdfImage.setRGB(i, j, rgb);
			}
		}
		
		return mdfImage;
	}
	
	BufferedImage enhance(ActionEvent e)
	{
		// ɫ����ǿ
		BufferedImage mdfImage = this.cloneBuffer();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				Color co = Color.getHSBColor(this.hsbMatrix[0][i][j], (float)((this.hsbMatrix[1][i][j]*1.5)>1 ? 1 : (this.hsbMatrix[1][i][j]*1.2)), this.hsbMatrix[2][i][j]);
				mdfImage.setRGB(i, j, co.getRGB());
			}
		}
		return mdfImage;
	}
	
	BufferedImage chameleon(ActionEvent e)
	{
		// ��ɫ
		BufferedImage mdfImage = this.cloneBuffer();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				Color co = Color.getHSBColor(this.hsbMatrix[0][i][j]*6, this.hsbMatrix[1][i][j], this.hsbMatrix[2][i][j]);
				mdfImage.setRGB(i, j, co.getRGB());
			}
		}
		return mdfImage;
	}
	
	BufferedImage darkerCorner(ActionEvent e)
	{
		// ����
		BufferedImage mdfImage = this.cloneBuffer();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				float ratio = (float)(1 - 
					(Math.pow(i-this.width/2, 2) + 
					Math.pow(j-this.height/2, 2)) / 
					(Math.pow(this.width/2, 2) + 
					Math.pow(this.height/2, 2)));
				Color co = Color.getHSBColor(this.hsbMatrix[0][i][j], this.hsbMatrix[1][i][j], (float)(this.hsbMatrix[2][i][j]*ratio));
				mdfImage.setRGB(i, j, co.getRGB());
			}
		}
		return mdfImage;
	}
	
	BufferedImage blurCorner(ActionEvent e)
	{
		BufferedImage mdfImage = this.blur(e);
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				float ratio = (float)(1 - 
					(Math.pow(i-this.width/2, 2) + 
					Math.pow(j-this.height/2, 2)) / 
					(Math.pow(this.width/2, 2) + Math.pow(this.height/2, 2)));
				Color co = new Color(
						(int)((this.rgbMatrix[0][i][j] * ratio) > 255 ? 255 : (this.rgbMatrix[0][i][j] * ratio)),
						(int)((this.rgbMatrix[1][i][j] * ratio) > 255 ? 255 : (this.rgbMatrix[1][i][j] * ratio)), 
						(int)((this.rgbMatrix[2][i][j] * ratio) > 255 ? 255 : (this.rgbMatrix[2][i][j] * ratio))
					);
					mdfImage.setRGB(i, j, co.getRGB());
				}
			}
			
			return mdfImage;
		}
		
		BufferedImage lomo(ActionEvent e)
		{
			// LOMO camera
			BufferedImage mdfImage = this.cloneBuffer();
			for (int i = 0; i < this.width; i++) {
				for (int j = 0; j < this.height; j++) {
					float ratio = (float)(1 - 
						(Math.pow(i-this.width/2, 2) + 
						Math.pow(j-this.height/2, 2)) / 
						(Math.pow(this.width/2, 2) + 
						Math.pow(this.height/2, 2)));
					Color co = Color.getHSBColor(this.hsbMatrix[0][i][j]*3, (float)((this.hsbMatrix[1][i][j]*1.5)>1 ? 1 : (this.hsbMatrix[1][i][j]*1.2)), (float)(this.hsbMatrix[2][i][j]*ratio));
					mdfImage.setRGB(i, j, co.getRGB());
				}
			}
			return mdfImage;
		}

	void open_actionPerformed(ActionEvent e)
	{            
		JFileChooser chooser =new JFileChooser();         
		if(JFileChooser.APPROVE_OPTION==chooser.showOpenDialog(this))            
		{              
			String strSelectedPath=chooser.getSelectedFile().getPath();           
			File file3=chooser.getSelectedFile();          
			try 
			{            
				buffimg = ImageIO.read(file3);             
			} catch (IOException a) {              
				a.printStackTrace();          
				}              
			filename = chooser.getSelectedFile().getName();             
			String cat = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();               
			if(cat.equalsIgnoreCase("JPG")||cat.equalsIgnoreCase("GIF")||cat.equalsIgnoreCase("PNG")||cat.equalsIgnoreCase("JPEG"))
			{              
				filepath=strSelectedPath;                     
				ic = new ImageIcon(filepath);                  
				ia = ic.getImage();                  
				int height = ic.getIconHeight();                   
				int width =ic.getIconWidth();                    
				jl.setSize(width,height);                    
				jl.setIcon(ic);                  
				repaint();                              
			}else{               
				JOptionPane.showMessageDialog(null,"�����ֻ֧��JPG��GIF��PNG��ʽ��ͼ���ļ�" ,"��ʾ",JOptionPane.INFORMATION_MESSAGE);             
				}          
			}             
		} 


	
	void exit_actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}
	
	void author_actionPerformed(ActionEvent e)
	{
		//JOptionPane.showMessageDialog(null, "����С���ħ���ĵ�����Java��ҵ----��ͼ����v1.2�棬�����ѵ���ը��\n"+"�ҵ�wechat: lexusscofield;\n"+"�ҵ�QQ: 404999849;\n"+"�ҵ�����΢��: С���ħ��;");
		JOptionPane.showMessageDialog(null,"����С���ħ���ĵ�����Java��ҵ----��ͼ����v1.2�棬�����ѵ���ը��\n"+"�ҵ�wechat: lexusscofield;\n"+"�ҵ�QQ: 404999849;\n"+"�ҵ�����΢��: С���ħ��;", "��������",JOptionPane.INFORMATION_MESSAGE);
	}
	
	void version_actionPerformed(ActionEvent e)
	{
		//JOptionPane.showMessageDialog(null, "���ǰ汾1.2�棬�ڰ汾1.1�����¼����ˡ�ͼ���������������ǡ�������Եģ��������LOMO�˾���������ɫ������ɫ����ǿ������ͼ��ģ�����Ĺ���,�Դ��Һܸ�л���ҿ�Դ�����������@Ivan-Jiang.����2015.05.04������16:18�ָ�����version1.2 ���� =3=");
		JOptionPane.showMessageDialog(null,"���ǰ汾1.2�棬����ڰ汾1.1�����¼�����"+"\n"+"��ͼ���������������ǡ�������Եģ��������LOMO�˾���������ɫ������ɫ����ǿ������ͼ��ģ�����Ĺ���,\n"+"�Դ��Һܸ�л���ҿ�Դ�����������@Ivan-Jiang.\n"+"����2015.05.04������16:18�ָ�����version1.2 ���� =3=", "���ڰ汾",JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		new xiuxiu(ImageIO.read(new FileInputStream("src/test.jpeg")));

	}

	class vague_actionAdapter implements ActionListener
	{
		xiuxiu huituxiuxiu;

		vague_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.enhance(e);
		}
		
	}
	
	class bstcolor_actionAdapter implements ActionListener
	{
		xiuxiu huituxiuxiu;

		bstcolor_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.enhance(e);
		}
		
	}
	
	class cgecolor_actionAdapter implements ActionListener
	{
		xiuxiu huituxiuxiu;

		cgecolor_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.chameleon(e);
		}
		
	}
	
	class drkconr_actionAdapter implements ActionListener
	{
		xiuxiu huituxiuxiu;

		drkconr_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.darkerCorner(e);
		}
		
	}
	
	class blurconr_actionAdapter implements ActionListener
	{
		xiuxiu huituxiuxiu;

		blurconr_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.blurCorner(e);
		}
		
	}
	
	class lomo1_actionAdapter implements ActionListener
	{
		xiuxiu huituxiuxiu;

		lomo1_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.lomo(e);
		}
		
	}
	
	class author_actionAdapter extends JFrame implements ActionListener
	{
		xiuxiu huituxiuxiu;

		author_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.author_actionPerformed(e);
		}
		
	}

	class version_actionAdapter extends JFrame implements ActionListener
	{
		xiuxiu huituxiuxiu;

		version_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.version_actionPerformed(e);
		}
		
	}
	
	class open_actionAdapter extends JFrame implements ActionListener
	{
		xiuxiu huituxiuxiu;

		open_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.open_actionPerformed(e);
		}
		
	}

	class exit_actionAdapter extends JFrame implements ActionListener
	{
		xiuxiu huituxiuxiu;

		exit_actionAdapter(xiuxiu huituxiuxiu)
		{
			this.huituxiuxiu = huituxiuxiu;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			huituxiuxiu.exit_actionPerformed(e);
		}
		
	}

	
}

