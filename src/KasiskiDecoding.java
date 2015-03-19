public class KasiskiDecoding {
	// 源文
	String src;
	// 前十个匹配段的距离
	int[] distances = new int[10];
	
	public KasiskiDecoding(String src){
		this.src = src;
		modifyMi();
		initDistances();
		scanMi();
	}
	
	// 修改密文
	public void modifyMi(){
		StringBuilder builder = new StringBuilder();
		char ch;
		for (int i = 0; i < src.length(); i++) {
			ch = src.charAt(i);
			if (ch <= 'z' && ch >= 'a') {
				builder.append(ch);
			}else if (ch <= 'Z' && ch >= 'A') {
				ch = (char) ('a'+ch-'A');
				builder.append(ch);
			}else {
			}
		}
		src = builder.toString();
	}
	
	// 初始化距离数组
	public void initDistances(){
		for (int i = 0; i < 10; i++) {
			distances[i]=0;
		}
	}
	
	// 扫描文件寻找距离,并更新距离
	public void scanMi(){
		char first;
		char second;
		char third;
		int ditance;
		// 确定读文章的次数
		for (int i = 0; i < distances.length; i++) {
			// 从某一特定点开始读文章
			for (int j = (i+1)*3; j < src.length(); j++) {
				first = src.charAt(j);
				// 若是第一个字母通过，直接比较后两个字母，若是重合的话更新距离
				if (first == src.charAt(i*3)) {
					second = src.charAt(j+1);
					third = src.charAt(j+2);
					if (second == src.charAt(i*3+1) && third == src.charAt(i*3+2)) {
						ditance = j-i*3;
						if (ditance < distances[i] || distances[i] == 0) {
							distances[i] = ditance;
						}
					}
				}
			}
		}
	}
	
	public void getResult(){
		for (int i = 0; i < distances.length; i++) {
			System.out.println(src.charAt(i*3)+src.charAt(i*3+1)+src.charAt(i*3+2)
					+" 最短距离为 "+distances[i]);
		}
	}
}
