package control;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFileChooser;

import config.SetConfig;
import dao.FileIOUtil;
import dao.Filter;
import gui.NoteJFrame;
import gui.NoteJMenuBar;
import gui.NoteJPane;
import gui.TreesPanel;

/**
 * @author andi
 *	主控制面板，数据控制中心，中转战
 */
public class NoteControl {
	
	private static NoteJPane pane;//主面板
	private static NoteJMenuBar menuBar;//主菜单条
	private static NoteJFrame frame;//主窗口
	private static SetConfig config;
	private static JFileChooser fileChooser;
	private static String nodePath;
	public static void main(String[] args) {
		init();
	}
	
	/**
	 * 初始化
	 */
	private static void init(){
		config = new SetConfig();//初始化配置		
	
		frame = new NoteJFrame();
		menuBar = new NoteJMenuBar();
		pane = new NoteJPane(frame);
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(Filter.fileChooserFilter(SetConfig.getFilters()));
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(pane);
		frame.setVisible(true);
	}
	
	/**
	 * 更新左边的树形导航
	 */
	public static void setTree(){
		config = new SetConfig();
		TreesPanel tree = new TreesPanel(frame);
		pane.setTree(tree);
	}
	/**
	 * 设置左边编辑面板的内容
	 * @param context 
	 */
	public static void setJTestPanelContext(String context){
		pane.setTextPaneText(context);
	}
	
	/**
	 * @return 返回树形导航的根路径
	 */
	public static String getPath() {
		return config.getPath();
	}

	/**
	 * 设置树形导航的根路径
	 * @param path 新的路径（绝对路径）
	 * @return 路径存在返回true，不存在返回false
	 */
	public static boolean setPath(String path) {
		if(path!=null&&!path.equals("")){
			if(new File(path).exists()){
				System.out.println(path);
				config.writePath(path);
				NoteControl.setTree();
				NoteControl.setJTestPanelContext("");
				return true;
			}
		}
		return false;
	}

	/**
	 * 保存或者新建文件
	 * @param isSavefile true是保存文件，false是新建文件
	 */
	public static void saveOrNewFile(boolean isSavefile) {
		
		if(getNodePath()!=null)
			fileChooser.setCurrentDirectory(new File(getNodePath()));
		else 
			fileChooser.setCurrentDirectory(new File(getPath()));
		
		
		if(isSavefile)
			fileChooser.showSaveDialog(frame);
		else 
			fileChooser.showDialog(frame, "新建");
		
		if(fileChooser.getSelectedFile()!=null){
			File file = fileChooser.getSelectedFile();
			if(!file.exists()){
				try {
					file.createNewFile();
					setTree();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(isSavefile)
				FileIOUtil.write(file.getAbsolutePath(), false, pane.getTextPaneText());
			
		}
		
	}

	/**
	 * 返回当前节点的路径
	 * @return 当前节点路径
	 */
	public static String getNodePath() {
		return nodePath;
	}

	/**
	 * 设置当前节点路径
	 * @param nodePath 新路径
	 */
	public static void setNodePath(String nodePath) {
		NoteControl.nodePath = nodePath;
	}

	public static boolean setFilter(String filters) {
		if(filters!=null&&!filters.equals("")){
			String fs[] = filters.split("\\*");
			for (String s : fs) {
				System.out.println(s);
			}
			config.writeFilter(fs);
			setTree();
			return true;
		}
		return false;
	}

	public static FilenameFilter getFilenameIOFILTER() {
		return config.getFilenameIOFILTER();
	}
}
