package dao;

import java.io.File;
/**
 * @author 刘伟艺(andi)
 * 文件过滤器
 * 里面使用了lambda表达式，只有java8以上才支持
 */

public class Filter {
	
	/**
	 * 用来过滤不符合的字符串和文件夹
	 * 如：".txt",".doc"，就是过滤隐藏的文件夹和非".txt",".doc"文件。
	 * 继承java.io.FileFilter
	 * @param contends 用来过滤文件夹和符合的字符串,如：“.txt”
	 * @return FileFilter过滤器的实现
	 */
	public static java.io.FileFilter fileIOFilter(String [] contends){
		return (File file) -> {	
			if(!file.isHidden()&file.isDirectory())return true;
			for (String c : contends) {
				if(file.getName().endsWith(c)) return true;
			}		
			return false;
		};
	}

	/**
	 * 用来过滤不符合的字符串和，
	 * 如：".txt",".doc"，就是过滤隐藏的文件夹和非".txt",".doc"文件。
	 * 继承java.io.FileFilter
	 * @param contends 用来过滤的字符串
	 * @return FilenameFilter 过滤器的实现
	 */	
	public static java.io.FilenameFilter filenameIOFilter(String [] contends){
		return (File dir, String name) -> {
			File file = new File(dir.getAbsoluteFile()+"\\"+name);
			if(file.isDirectory()&!file.isHidden()){
				return true;
			}
			for (String c : contends) {
				if(name.endsWith(c)) return true;
			}
			return false;			
		};
	}
	/**
	 * 用来过滤不符合的字符串和，
	 * 如：".txt",".doc"，就是过滤隐藏的文件夹和非".txt",".doc"文件。
	 * 继承javax.swing.filechooser.FileFilter
	 * @param contends 用来过滤的字符串
	 * @return FileFilter 过滤器的实现
	 */
	public static javax.swing.filechooser.FileFilter fileChooserFilter(String [] contends){
		return new javax.swing.filechooser.FileFilter(){
			@Override
			public String getDescription() {
				StringBuffer sb = new StringBuffer();
				for (String c : contends) {
					sb.append(c);
				}
				return sb.toString();
			}
			
			@Override
			public boolean accept(File file) {
				if(!file.isHidden()&file.isDirectory())return true;
				for (String c : contends) {
				if(file.getName().endsWith(c)) return true;
			}		
			return false;
			}	
		};
	}
/*	public static void main(String[] args) {
		File file = new File("E:\\");
		String [] fs = file.list(FILENAMEFILTER);
		for (String s : fs) {
			System.out.println(s);
		}
	}*/
}
