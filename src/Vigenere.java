import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Vigenere {
	public static void main(String[] args){
		LexFrame lexframe = new LexFrame();
		lexframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lexframe.setResizable(false);
		lexframe.setVisible(true);
		lexframe.setTitle("Vigenere Encoding");
	}
	public Vigenere() {
		// TODO Auto-generated constructor stub
	}	
}
class LexFrame extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int LABEL_HEIGHT = 80;
	private final int TA_HEIGHT = 110;
	private final int BTN_HEIGHT = 580;
	private int keyLength;
	private JPanel main_panel;
	
	private JMenuBar main_menu_bar;
	private JMenu menu_file;
	private JMenu menu_run;
	private JMenuItem file_open;
	private JMenuItem exit;
	private JMenuItem item_clear;
	
	private JLabel lb_enco;
	private JLabel lb_deco;
	private JLabel lb_input;
	private JLabel lb_keylength;
	private JLabel lb_privatekey;
	private JLabel lb_normaldeco;
	private JTextArea tf_privateKey;
	private JScrollPane scrollpane_privateKey;
	
	private JTextField tf_keylength;
	
	private JButton normal_deco;
	private JButton start_enco;
	private JButton start_deco;
	private JTextArea ta_input;
	private JScrollPane scrollpane_input;
	private JTextArea ta_encoding;
	private JScrollPane scrollPane_encoding;
	private JTextArea ta_decoding;
	private JScrollPane scrollPane_decoding;
	private JTextArea ta_normal_decoding;
	private JScrollPane scrollPane_normal_decoding;
	private JFileChooser file_open_filechooser;
	
	
	public LexFrame(){
		this.setTitle("维吉尼亚密码");
		this.setSize(1250,700);
		initPanel();
	}
	
	public void initPanel(){
		main_menu_bar = new JMenuBar();
		menu_file = new JMenu("文件");
		menu_run = new JMenu("清除");
		
		file_open = new JMenuItem("打开");
		exit = new JMenuItem("退出");
		file_open.addActionListener(this);
		exit.addActionListener(this);
		menu_file.add(file_open);
		menu_file.add(exit);
		main_menu_bar.add(menu_file);
		
		item_clear = new JMenuItem("清除");
		item_clear.addActionListener(this);
		menu_run.add(item_clear);
		main_menu_bar.add(menu_run);
		this.setJMenuBar(main_menu_bar);
		
		main_panel = new JPanel();
		main_panel.setLayout(null);
		
		lb_keylength = new JLabel("请输入密钥长度");
		main_panel.add(lb_keylength);
		lb_keylength.setBounds(10, 10, 100, 20);
		tf_keylength = new JTextField();
		main_panel.add(tf_keylength);
		tf_keylength.setBounds(120, 10, 100, 20);
		
		lb_input = new JLabel("输入");
		main_panel.add(lb_input);
		lb_input.setBounds(10, LABEL_HEIGHT, 70, 20);
		ta_input = new JTextArea();
		scrollpane_input = new JScrollPane(ta_input);
		main_panel.add(scrollpane_input);
		scrollpane_input.setBounds(10, TA_HEIGHT, 300, 440);
//		scrollpane_input.setRowHeaderView(new LineNumberHeaderView());
		
		lb_privatekey = new JLabel("生成密钥");
		main_panel.add(lb_privatekey);
		lb_privatekey.setBounds(260, 10, 100, 20);
		tf_privateKey = new JTextArea();
		scrollpane_privateKey = new JScrollPane(tf_privateKey);
		main_panel.add(scrollpane_privateKey);
		scrollpane_privateKey.setBounds(340, 10, 200, 60);
		tf_privateKey.setEditable(false);
		
		lb_enco = new JLabel("加密结果");
		main_panel.add(lb_enco);
		lb_enco.setBounds(320, LABEL_HEIGHT, 80, 20);
		ta_encoding = new JTextArea();
		scrollPane_encoding = new JScrollPane(ta_encoding);
		main_panel.add(scrollPane_encoding);
		scrollPane_encoding.setBounds(320, TA_HEIGHT, 300, 440);
		
		lb_normaldeco = new JLabel("正常解密");
		main_panel.add(lb_normaldeco);
		lb_normaldeco.setBounds(630, LABEL_HEIGHT, 80, 20);
		ta_normal_decoding = new JTextArea();
		scrollPane_normal_decoding = new JScrollPane(ta_normal_decoding);
		main_panel.add(scrollPane_normal_decoding);
		scrollPane_normal_decoding.setBounds(630, TA_HEIGHT, 300, 440);
		
		lb_deco = new JLabel("暴力破解");
		main_panel.add(lb_deco);
		lb_deco.setBounds(940, LABEL_HEIGHT, 80, 20);
		ta_decoding = new JTextArea();
		scrollPane_decoding = new JScrollPane(ta_decoding);
		main_panel.add(scrollPane_decoding);
		scrollPane_decoding.setBounds(940, TA_HEIGHT, 300, 440);
		
		
		start_enco = new JButton("开始加密");
		main_panel.add(start_enco);
		start_enco.setBounds(320, BTN_HEIGHT, 100, 20);
		start_enco.addActionListener(this);
		
		normal_deco = new JButton("正常解密");
		main_panel.add(normal_deco);
		normal_deco.setBounds(630, BTN_HEIGHT, 100, 20);
		normal_deco.addActionListener(this);
		
		start_deco = new JButton("暴力破解");
		main_panel.add(start_deco);
		start_deco.setBounds(940, BTN_HEIGHT, 100, 20);
		start_deco.addActionListener(this);
		
		add(main_panel);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 置空
		
		if ((e.getSource() == start_enco)) {
			// 清空加密片段
			ta_encoding.setText("");
			if (tf_keylength.getText().equals("")) {
				JOptionPane.showMessageDialog(main_panel, "请输入密钥长度！", "提示", JOptionPane.ERROR_MESSAGE);
				System.out.println("fuck");
				return;
			}
			if (!tf_keylength.getText().equals("")) {
				try{
					keyLength = Integer.parseInt(tf_keylength.getText());
				}catch(Exception exception){
					exception.printStackTrace();
					JOptionPane.showMessageDialog(main_panel, "请输入数字", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			if(ta_input.getText().equals("")){
				JOptionPane.showMessageDialog(main_panel, "您什么都没有输入啊！", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				// 生成一个随机数组
				ArrayList<Integer> privateKeys = new ArrayList<Integer>();
				Random random = new Random();
				int k;
				for (int i = 0; i < keyLength; i++) {

					k = random.nextInt(26)%26+1;
					System.out.println("密钥偏移："+k);
					privateKeys.add(k);
				}
				tf_privateKey.setText(generateKey(privateKeys));
				VigenereEncoding encode = new VigenereEncoding(ta_input.getText(), privateKeys, ta_encoding);
				encode.startEncoding();
			}
		}
		// 正常解密
		else if(e.getSource() == normal_deco){
			ta_normal_decoding.setText("");
			if(ta_encoding.getText().equals("")){
				JOptionPane.showMessageDialog(main_panel, "您什么都没有输入啊！", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				// 解密
				ArrayList<Integer> privateKeys = new ArrayList<Integer>();
				String privateKey = tf_privateKey.getText();
				for (int i = 0; i < privateKey.length(); i++) {
					char ch = privateKey.charAt(i);
					privateKeys.add(26-(ch-'a'+1));
				}
				VigenereEncoding encode = new VigenereEncoding(ta_encoding.getText(), privateKeys, ta_normal_decoding);
				encode.startEncoding();
			}
		}
		
		// 暴力破解
		else if (e.getSource() == start_deco) {
			ta_decoding.setText("");
			if(ta_encoding.getText().equals("")){
				JOptionPane.showMessageDialog(main_panel, "您什么都没有输入啊！", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				// 基于Kasiski的暴力破解
				KasiskiDecoding kasiskiDecoding = new KasiskiDecoding(ta_encoding.getText());
				kasiskiDecoding.getResult();
				System.out.println("kasiski算法预测："+kasiskiDecoding.getMaxDivisor());
				
				// 基于重合指数暴力破解
				VigenereDecoding decode = new VigenereDecoding(ta_encoding.getText(), ta_decoding);
				decode.startDecoding();
				
			}
		}
		else if(e.getSource() == file_open){
			file_open_filechooser = new JFileChooser();
			file_open_filechooser.setCurrentDirectory(new File("."));
			file_open_filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = file_open_filechooser.showOpenDialog(main_panel);
			// 证明有选择
			if (result==JFileChooser.APPROVE_OPTION) {
				clearALLData();
				new Thread(){
					@Override
					public void run(){
						String file_name = file_open_filechooser.getSelectedFile().getPath();
						// 读取文件，写到JTextArea里面
						File file = new File(file_name);
						try{
							InputStream in = new FileInputStream(file);
							int tempbyte;
							while ((tempbyte=in.read()) != -1) {
								ta_input.append(""+(char)tempbyte);
							}
							in.close();
						}
						catch(Exception event){
							event.printStackTrace();
						}
					}
				}.start();
			}
			
		}
		else if (e.getSource() == item_clear) {
			ta_decoding.setText("");
			ta_encoding.setText("");
			ta_normal_decoding.setText("");
			ta_input.setText("");
			tf_keylength.setText("");
			tf_privateKey.setText("");
		}
		else if(e.getSource() == exit){
			System.exit(1);
		}
		else {
			System.out.println("nothing！");
		}
	}
	
	public void clearALLData(){
		// 清除数据
		ta_decoding.setText("");
		ta_encoding.setText("");
		ta_normal_decoding.setText("");
		ta_input.setText("");
		tf_keylength.setText("");
		tf_privateKey.setText("");
	}
	
	public String generateKey(ArrayList<Integer> privateKeys){
		StringBuffer stringBuffer = new StringBuffer();
		ArrayList<Integer> pk = privateKeys;
		char ch;
		for (int i = 0; i < pk.size(); i++) {
			ch = (char)('a'+(pk.get(i)-1));
			stringBuffer.append(ch);
		}
		return stringBuffer.toString();
	}
	
}