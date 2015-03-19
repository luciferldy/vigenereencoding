import java.util.ArrayList;

import javax.swing.JTextArea;


public class VigenereEncoding {
	private String src;
	private ArrayList<Integer> privateKeys;
	private JTextArea ta_encoding;
	char ming;
	char mi;
	long lengthMing;
	int lengthKey;
	
	public VigenereEncoding(String src, ArrayList<Integer> privateKeys, JTextArea ta_encoding){
		this.src = src;
		this.privateKeys = new ArrayList<Integer>();
		this.privateKeys = privateKeys;
		this.ta_encoding = ta_encoding;
	}
	
	// 加密
	public void startEncoding(){
		
		lengthMing= src.length();
		lengthKey = privateKeys.size();
		
		System.out.println("要加密的文章的长度："+lengthMing+"  "+"密钥的长度："+lengthKey);
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int index = 0;
				for (int i = 0; i < lengthMing; i++) {
					ming = src.charAt(i);
					if ( ming <= 'z' && ming >= 'a') {
						mi = encodingChar(ming, privateKeys.get(index%lengthKey), 1);
						index++;
					}else if (ming <= 'Z' && ming >= 'A') {
						mi = encodingChar(ming, privateKeys.get(index%lengthKey), 2);
						index++;
					}else{
						mi = ming;
					}
					ta_encoding.append(mi+"");
				}
			}
		}.start();
	}
	
	public char encodingChar(char src, int offset, int flag){
		char c;
		if (flag == 1) {
			c = (char)((src-'a'+offset)%26+'a');
		}
		else {
			c = (char)((src-'A'+offset)%26+'A');
		}		
		return c;
	}
	
}
