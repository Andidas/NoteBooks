package config;

import java.io.File;
import java.io.IOException;
import dao.FileIOUtil;
import dao.Filter;

public class SetConfig {

	/*public static void main(String[] args) {
		SetConfig config = new SetConfig();
		String filter []= {".txt",".sol",".java"};
		config.writeFilter(filter);
		config.writePath("E:\\");
		String s[] = config.readFilter();
		System.out.println(config.readPath());
		System.out.println(s.length);
		for(int i =0;i<s.length;i++){
			System.out.println("data��"+s[i]);
		}
	}*/

	private  String path;//���ڵ��·��
	//private  String binPath;//..NoteBookSE/bin/config/path.txt��·��
	private  String srcPath;//..NoteBookSE/src/config/path.txt��·��
	private  static String [] filters;
	private static enum Config {PATH,FILTER};//ö�٣��������й�
	public  SetConfig(){
		//���path.txt��·��
		//binPath = SetConfig.class.getResource("./").getPath().substring(1)+"path.txt";
		//srcPath = binPath.replace("bin", "src");
		srcPath = "D:\\PATH.ini";
		//�����ļ������ھʹ���
		File file = new File(this.srcPath);
		if(!file.exists()){
			try {
				System.out.println("���������ļ��Ƿ�ɹ���"+file.createNewFile());
				FileIOUtil.write(srcPath, false, "PATHD:\\PATH#");
				FileIOUtil.write(srcPath, true, "FILTER.txt*.java*FILTER#");
				//�ļ�����
				//Runtime.getRuntime().exec("attrib"+" "+file.getAbsolutePath().trim()+" "+"+");
				System.out.println("�����ļ�д��ɹ�");
			} catch (IOException e) {
				System.err.println("�����ļ�д��ʧ��");
				e.printStackTrace();
			}
		}
		//�������ļ��ж�ȡ�������ַ���
		this.path = readPath().trim();
		filters = readFilter();

	}

	public String getPath() {
		return path;
	}

	public String[] getFilter() {
		return filters;
	}

	
	/**
	 * �޸Ĺ����ַ����µ�·����PATH��Χ������FILTERfiltersFILTER��
	 * @param fs �µĹ����ַ�������
	 */
	public void writeFilter(String [] fs){
		StringBuffer newfilter = new StringBuffer("FILTER");
		for (String filter : fs) {
			newfilter.append("*").append(filter);
		}
		newfilter.append("FILTER");
		modifyConfig(Config.FILTER,newfilter.toString());
		filters = fs;
		
	}
	
	/**
	 * �޸�path���ã��µ�·����PATH��Χ������PATHnpathPATH��
	 * @param npath �µ�·��
	 */
	public void writePath(String npath){
		String newpath = "PATH"+npath.replace("\\", "\\\\")+"PATH";
		modifyConfig(Config.PATH,newpath);
		this.path = npath;
	}
	/**
	 * �޸������ļ�
	 * �����ö��������PATH�����޸�PATH�ַ�����Χ����������
	 * @param config ö������enum Cnofig{PATH,FILTER...}
	 * @param context �޸ĵ�����
	 */
	private void modifyConfig(Config config,String context){
		String oldconfig = FileIOUtil.read(srcPath);
		switch(config){
		case PATH:
			String newpath = oldconfig.replaceAll("PATH.*PATH", context);
			FileIOUtil.write(this.srcPath, false, newpath);
			break;
		case FILTER:
			String newfilter = oldconfig.replaceAll("FILTER.*FILTER",context);
			FileIOUtil.write(this.srcPath, false, newfilter);
			break;
		}
	}
	/**
	 * �������ļ���ȡ
	 * �����config.PATH�������ľ��Ǳ�PATH��Χ��context�ַ�����PATHcontextPATH��
	 * @return �������ļ��ж�ȡ�����ַ���
	 */
	private String readConfig(Config config){
		String oldconfig = FileIOUtil.read(srcPath);
		String configs[] = oldconfig.split("#");
		switch(config){
		case PATH:
			return configs[0].replace("PATH", "");
		case FILTER:
			return configs[1].replace("FILTER", "");
		}
		return null;
	}
	/**
	 * ���ڳ�ʼ��path���ⲿ�޷�����
	 * @return �����е�·��
	 */
	private String readPath(){
		return readConfig(Config.PATH).trim();
	}
	/**
	 * ���ڳ�ʼ��filters���ⲿ�޷�����
	 * @return �����еĹ����ַ���
	 */
	private String[] readFilter(){
		
		String fs[] = readConfig(Config.FILTER).split("\\*+");
		for(int i=0;i<fs.length;i++){
			fs[i].trim();
		}
		return fs;
	}

	public java.io.FileFilter getFileIOFILTER() {
		return Filter.fileIOFilter(filters);
	}

	public java.io.FilenameFilter getFilenameIOFILTER() {
		return Filter.filenameIOFilter(filters);
	}

	public static String[] getFilters() {
		return filters;
	}


}
