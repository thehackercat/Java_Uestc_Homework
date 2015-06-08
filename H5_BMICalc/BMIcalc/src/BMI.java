import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

@SuppressWarnings("unused")
public class BMI extends JFrame {

	private JPanel contentPane;
	private JTextField tf;
	private JTextField tf1;
	private JTextField tf2;

	/**
	 * BMI���ʼ�����
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BMI frame = new BMI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * GUI�������
	 */
	public BMI() {
		super("������BMI����������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 450, 350);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tf = new JTextField("0.0");
		tf.setBounds(96, 10, 113, 43);
		contentPane.add(tf);
		tf.setColumns(10);
		
		tf1 = new JTextField("0.0");
		tf1.setColumns(10);
		tf1.setBounds(96, 60, 113, 43);
		contentPane.add(tf1);
		
		tf2 = new JTextField("0.0");
		tf2.setColumns(10);
		tf2.setBounds(96,110,113,43);
		contentPane.add(tf2);
		
		JLabel lblHeight = new JLabel("��ߣ� ");
		lblHeight.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		lblHeight.setBounds(40, 16, 46, 29);
		contentPane.add(lblHeight);
		
		JLabel lblWeight = new JLabel("���أ� ");
		lblWeight.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		lblWeight.setBounds(40, 66, 46, 29);
		contentPane.add(lblWeight);
		
		JLabel lblGirth = new JLabel("��Χ�� ");
		lblGirth.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		lblGirth.setBounds(40, 116, 46, 29);
		contentPane.add(lblGirth);
		
		final JLabel label = new JLabel("");
		label.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 165, 414, 29);
		contentPane.add(label);
		
		final JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_1.setBounds(10, 205, 414, 29);
		contentPane.add(label_1);
		
		final JLabel label_2 = new JLabel("");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_2.setBounds(10, 245, 414, 29);
		contentPane.add(label_2);
		
		JButton btnNewButton = new JButton("����");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//��ȡ���������Χ
				double h = Double.parseDouble(tf.getText());
				double w = Double.parseDouble(tf1.getText());
				double g = Double.parseDouble(tf2.getText());
				
				//����Ƿ�ղ���
				if (h <= 0 || w <= 0 || g <= 0) {
					label.setText("������ߡ����ء���Χ�Ĳ�������Ϊ0�����������롣 =V= ");
				} else {
					double bmi = w/(h*h);
					double tzl = (g*0.74-(w*0.082+44.74))/w;
					label.setText("����BMIָ��Ϊ��  "+bmi);
					label_1.setText("������֬��Ϊ�� "+tzl);
					if (bmi > 0) {
						if (bmi < 18.5) label_2.setText("�������ع����� ~(�R���Q)~ �������Ҫ��Ե�Ӵ��");
						if ((18.5 < bmi) && (bmi < 24.9)) label_2.setText("��������ܽ��������������Ӵ ~(�R���Q)~ ");
						if ((25 < bmi) && (bmi < 29.9)) label_2.setText("���������ƺ��е�����΢�ء���΢�ء����ǲ���Ҫȥ�ܲ�����(�ţ� 3��)��");
						if ((30 < bmi) && (bmi < 34.9)) label_2.setText("���֣������ӣ����ȥ���ʰɣ��q(�s^�t)�r");
						if ((35 < bmi) && (bmi < 39.9)) label_2.setText("�Բۣ����ʷʣ������ж��û�˶��ǣ����q(�s^�t)�r");
						if (bmi > 40) label_2.setText("�������ʷʣ���û���ˣ�88 ( ^_^ )/~~");
					}
				}
			}
		});
		btnNewButton.setFont(new Font("΢���ź�", Font.BOLD, 14));
		btnNewButton.setBounds(311, 16, 113, 138);
		contentPane.add(btnNewButton);
		
		JLabel lblM = new JLabel("m");
		lblM.setFont(new Font("΢���ź�", Font.BOLD, 15));
		lblM.setHorizontalAlignment(SwingConstants.CENTER);
		lblM.setBounds(219, 11, 46, 43);
		contentPane.add(lblM);
		
		JLabel lblKg = new JLabel("kg");
		lblKg.setHorizontalAlignment(SwingConstants.CENTER);
		lblKg.setFont(new Font("΢���ź�", Font.BOLD, 15));
		lblKg.setBounds(219, 61, 46, 43);
		contentPane.add(lblKg);
		
		JLabel lblcm = new JLabel("cm");
		lblcm.setHorizontalAlignment(SwingConstants.CENTER);
		lblcm.setFont(new Font("΢���ź�", Font.BOLD, 15));
		lblcm.setBounds(219, 111, 46, 43);
		contentPane.add(lblcm);
		
	}
}