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
			System.out.println("data："+s[i]);
		}
	}*/

	private  String path;//根节点的路径
	//private  String binPath;//..NoteBookSE/bin/config/path.txt的路径
	private  String srcPath;//..NoteBookSE/src/config/path.txt的路径
	private  static String [] filters;
	private static enum Config {PATH,FILTER};//枚举，与配置有关
	public  SetConfig(){
		//获得path.txt的路径
		//binPath = SetConfig.class.getResource("./").getPath().substring(1)+"path.txt";
		//srcPath = binPath.replace("bin", "src");
		srcPath = "D:\\PATH.ini";
		//配置文件不存在就创建
		File file = new File(this.srcPath);
		if(!file.exists()){
			try {
				System.out.println("创建配置文件是否成功："+file.createNewFile());
				FileIOUtil.write(srcPath, false, "PATHD:\\PATH#");
				FileIOUtil.write(srcPath, true, "FILTER.txt*.java*FILTER#");
				//文件隐藏
				//Runtime.getRuntime().exec("attrib"+" "+file.getAbsolutePath().trim()+" "+"+");
				System.out.println("配置文件写入成功");
			} catch (IOException e) {
				System.err.println("配置文件写入失败");
				e.printStackTrace();
			}
		}
		//从配置文件中读取到配置字符串
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
	 * 修改过滤字符，新的路径用PATH包围起来（FILTERfiltersFILTER）
	 * @param fs 新的过滤字符串数组
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
	 * 修改path配置，新的路径用PATH包围起来（PATHnpathPATH）
	 * @param npath 新的路径
	 */
	public void writePath(String npath){
		String newpath = "PATH"+npath.replace("\\", "\\\\")+"PATH";
		modifyConfig(Config.PATH,newpath);
		this.path = npath;
	}
	/**
	 * 修改配置文件
	 * 如果是枚举类型是PATH，则修改PATH字符串包围起来的内容
	 * @param config 枚举类型enum Cnofig{PATH,FILTER...}
	 * @param context 修改的类型
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
	 * 从配置文件读取
	 * 如果是config.PATH，读到的就是被PATH包围的context字符串（PATHcontextPATH）
	 * @return 从配置文件中读取到的字符串
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
	 * 用于初始化path，外部无法访问
	 * @return 配置中的路径
	 */
	private String readPath(){
		return readConfig(Config.PATH).trim();
	}
	/**
	 * 用于初始化filters，外部无法访问
	 * @return 配置中的过滤字符串
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
