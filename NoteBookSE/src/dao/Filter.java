package dao;

import java.io.File;
/**
 * @author ��ΰ��(andi)
 * �ļ�������
 * ����ʹ����lambda���ʽ��ֻ��java8���ϲ�֧��
 */

public class Filter {
	
	/**
	 * �������˲����ϵ��ַ������ļ���
	 * �磺".txt",".doc"�����ǹ������ص��ļ��кͷ�".txt",".doc"�ļ���
	 * �̳�java.io.FileFilter
	 * @param contends ���������ļ��кͷ��ϵ��ַ���,�磺��.txt��
	 * @return FileFilter��������ʵ��
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
	 * �������˲����ϵ��ַ����ͣ�
	 * �磺".txt",".doc"�����ǹ������ص��ļ��кͷ�".txt",".doc"�ļ���
	 * �̳�java.io.FileFilter
	 * @param contends �������˵��ַ���
	 * @return FilenameFilter ��������ʵ��
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
	 * �������˲����ϵ��ַ����ͣ�
	 * �磺".txt",".doc"�����ǹ������ص��ļ��кͷ�".txt",".doc"�ļ���
	 * �̳�javax.swing.filechooser.FileFilter
	 * @param contends �������˵��ַ���
	 * @return FileFilter ��������ʵ��
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
