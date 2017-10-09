package impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

import dao.Filter;


/**
 * ����ƶ�·���µ����е��ļ����ļ���
 * @author Administrator
 *
 */
public class AllFile {
	private static String rootPath;
	private static Vector<File> allFiles ;
	
	public static void main(String[] args) {
		AllFile.setrootPath("E:\\");
		Vector<File> m = AllFile.setAllFile();
		System.out.println(getrootPath());
		for (File s : m) {
			System.out.println(s);
		}
	}
	/**
	 * ��ʼ��Vector<File>
	 * @return ָ��·���µ�����file
	 */
	public static Vector<File> setAllFile(){
		
		allFiles = new  Vector<File>();
		File file = new File(getrootPath());
		if(!file.exists()){
			try {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else addFile(file);
		return allFiles;
	}
	
	/**
	 * �������file
	 * @param file
	 */
	public static void addFile(File file){
		File fs[] = file.listFiles(Filter.FILEFILTER);
		for(File f: fs){
			if(!f.isHidden()){
				if(f.isDirectory()){
					allFiles.add(f);
					addFile(f);
				}
				else allFiles.add(f);
			}
		}
	}
	
	
	 //getter and setter
	public static String getrootPath() {
		return rootPath;
	}
	public static void setrootPath(String path) {
		AllFile.rootPath = path;
	}
	public static Vector<File> getAllFiles() {
		return allFiles;
	}


}
