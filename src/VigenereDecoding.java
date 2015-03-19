import java.util.ArrayList;

import javax.swing.JTextArea;


public class VigenereDecoding {
	private String src;
	private int keyLength = 0;
	private ArrayList<StringBuffer> fragmentsrc;
	private JTextArea ta_decoding;
	
	public VigenereDecoding(String src, JTextArea ta_decoding){
		this.src = src;
		this.ta_decoding = ta_decoding;	
	}
	
	public void startDecoding(){
		int index = 0;
		System.out.println("要破解的密文的长度："+src.length());
		caculateLength();
		
		// 计算偏移
		ArrayList<Integer> privateKeys = new ArrayList<Integer>();
		for (int i = 0; i < keyLength; i++) {
			int[] p = caculatePofLetter(fragmentsrc.get(i).toString());
			index = findIndexOfMax(p);
			// 能找出加密的密码了，就是'a'+index
			// 这是解密的偏移
			index = index-5+1;
			if (index <= 0) {
				index = index+26;
			}
			System.out.println("破解出的偏移："+index);
			privateKeys.add(26-index);
		}
		if (keyLength == 0) {
			System.out.println("木有解密成功");
			return;
		}
		// 解密
		VigenereEncoding de = new VigenereEncoding(src, privateKeys, ta_decoding);
		de.startEncoding();
	}
	
	public int findIndexOfMax(int[] p){
		int[] pp = p;
		int index = 0;
		int max = pp[0];
		for (int i = 0; i < pp.length; i++) {
			if (pp[i] > max) {
				max = pp[i];
				index = i;
			}
		}
		return index;
	}
	
	// 计算密钥长度
	public void caculateLength(){
		ArrayList<StringBuffer> fragments;
		int flag;
		// 第一重循环假设密钥长度
		for (int i = 1; i <= src.length(); i++) {
			flag = 0;
			// 
			fragments = new ArrayList<StringBuffer>();
			//初始化ArrayList里面的数组
			for (int j = 0; j < i; j++) {
				StringBuffer bf = new StringBuffer();
				fragments.add(bf);
			}
			// 第二重循环对字母进行分组
			char ch;
			int index = 0;
			for (int j = 0; j < src.length(); j++) {
				ch = src.charAt(j);
				fragments.get(index%i).append(ch);
				if (ch <= 'z' && ch >= 'a') {
					index++;
				}else if (ch <= 'Z' && ch >= 'A') {
					index++;
				}else{
				}
			}
			// 循环结束后对每组字符串进行计算重合指数
			for (int j = 0; j < i; j++) {
				if (!caculateIC(fragments.get(j).toString())) {
					flag = 1;
					break;
				}
			}
			// 当标志位没有发生变化时，表明正确的长度已经诞生
			if (flag == 0) {
				keyLength = i;
				fragmentsrc = fragments;
				return;
			}
		}
	}
	
	// 计算每个字母出现的频数
	public int[] caculatePofLetter(String fragmentsrc){
		String fs = fragmentsrc;
		
	    // 初始化
	    int[] pLetter = new int[26];
	    for (int i = 0; i < pLetter.length; i++) {
			pLetter[i]=0;
		}
	    
	    // 计算每个字母出现的次数
	    char ch;
	    for (int i = 0; i < fs.length(); i++) {
	    	ch = fs.charAt(i);
			if ( ch >= 'a' && ch <= 'z' ) {
				pLetter[(int)(ch-'a')]++;
			}else if ( ch >= 'A' && ch <= 'Z') {
				pLetter[(int)(ch-'A')]++;
			}else{
			}
		}
	    return pLetter;
	}
	
	// 计算重合指数
	public boolean caculateIC(String fragmentsrc){
	    
	    // 初始化
	    int[] pLetter = caculatePofLetter(fragmentsrc);
	    long letterCount = 0;
	    for (int i = 0; i < pLetter.length; i++) {
			letterCount += pLetter[i];
		}
	    System.out.println("字母数量： "+letterCount);
	    // 计算重合指数
	    float pIC = 0;
	    for (int i = 0; i < pLetter.length; i++) {
	    	System.out.println("密文的字母频率："+(char)('a'+i)+" "+pLetter[i]*1.0/letterCount);
			pIC += (float) ((pLetter[i]*1.0/letterCount)*(pLetter[i]*1.0/letterCount));
		}
	    // 取小数点后两位
//	    BigDecimal b = new BigDecimal(pIC);
//	    pIC = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	    // 判断是否正确
	    System.out.println(pIC);
	    pIC = (float)0.065-pIC;
	    if (pIC > -0.01 && pIC < 0.01) {
			return true;
		}else {
			return false;
		}
	}
	
}
