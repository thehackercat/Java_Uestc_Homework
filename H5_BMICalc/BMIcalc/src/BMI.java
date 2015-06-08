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
	 * BMI体质计算器
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
	 * GUI界面设计
	 */
	public BMI() {
		super("调华的BMI健康计算器");
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
		
		JLabel lblHeight = new JLabel("身高： ");
		lblHeight.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblHeight.setBounds(40, 16, 46, 29);
		contentPane.add(lblHeight);
		
		JLabel lblWeight = new JLabel("体重： ");
		lblWeight.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblWeight.setBounds(40, 66, 46, 29);
		contentPane.add(lblWeight);
		
		JLabel lblGirth = new JLabel("腰围： ");
		lblGirth.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblGirth.setBounds(40, 116, 46, 29);
		contentPane.add(lblGirth);
		
		final JLabel label = new JLabel("");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 165, 414, 29);
		contentPane.add(label);
		
		final JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setBounds(10, 205, 414, 29);
		contentPane.add(label_1);
		
		final JLabel label_2 = new JLabel("");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_2.setBounds(10, 245, 414, 29);
		contentPane.add(label_2);
		
		JButton btnNewButton = new JButton("计算");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//获取身高体重腰围
				double h = Double.parseDouble(tf.getText());
				double w = Double.parseDouble(tf1.getText());
				double g = Double.parseDouble(tf2.getText());
				
				//检查是否空参数
				if (h <= 0 || w <= 0 || g <= 0) {
					label.setText("错误！身高、体重、腰围的参数不能为0，请重新输入。 =V= ");
				} else {
					double bmi = w/(h*h);
					double tzl = (g*0.74-(w*0.082+44.74))/w;
					label.setText("您的BMI指数为：  "+bmi);
					label_1.setText("您的体脂率为： "+tzl);
					if (bmi > 0) {
						if (bmi < 18.5) label_2.setText("您的体重过低辣 ~(≧▽≦)~ ，请务必要多吃点哟。");
						if ((18.5 < bmi) && (bmi < 24.9)) label_2.setText("您的身体很健康，请继续保持哟 ~(≧▽≦)~ ");
						if ((25 < bmi) && (bmi < 29.9)) label_2.setText("您的体重似乎有点略显微重。。微重。。是不是要去跑步辣？(づ￣ 3￣)づ");
						if ((30 < bmi) && (bmi < 34.9)) label_2.setText("哇咧，死胖子，快滚去减肥吧！╭(╯^╰)╮");
						if ((35 < bmi) && (bmi < 39.9)) label_2.setText("卧槽，死肥肥，您是有多久没运动惹？！╭(╯^╰)╮");
						if (bmi > 40) label_2.setText("超级死肥肥，你没治了，88 ( ^_^ )/~~");
					}
				}
			}
		});
		btnNewButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
		btnNewButton.setBounds(311, 16, 113, 138);
		contentPane.add(btnNewButton);
		
		JLabel lblM = new JLabel("m");
		lblM.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblM.setHorizontalAlignment(SwingConstants.CENTER);
		lblM.setBounds(219, 11, 46, 43);
		contentPane.add(lblM);
		
		JLabel lblKg = new JLabel("kg");
		lblKg.setHorizontalAlignment(SwingConstants.CENTER);
		lblKg.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblKg.setBounds(219, 61, 46, 43);
		contentPane.add(lblKg);
		
		JLabel lblcm = new JLabel("cm");
		lblcm.setHorizontalAlignment(SwingConstants.CENTER);
		lblcm.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblcm.setBounds(219, 111, 46, 43);
		contentPane.add(lblcm);
		
	}
}