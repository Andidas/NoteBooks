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
 *	��������壬���ݿ������ģ���תս
 */
public class NoteControl {
	
	private static NoteJPane pane;//�����
	private static NoteJMenuBar menuBar;//���˵���
	private static NoteJFrame frame;//������
	private static SetConfig config;
	private static JFileChooser fileChooser;
	private static String nodePath;
	public static void main(String[] args) {
		init();
	}
	
	/**
	 * ��ʼ��
	 */
	private static void init(){
		config = new SetConfig();//��ʼ������		
	
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
	 * ������ߵ����ε���
	 */
	public static void setTree(){
		config = new SetConfig();
		TreesPanel tree = new TreesPanel(frame);
		pane.setTree(tree);
	}
	/**
	 * ������߱༭��������
	 * @param context 
	 */
	public static void setJTestPanelContext(String context){
		pane.setTextPaneText(context);
	}
	
	/**
	 * @return �������ε����ĸ�·��
	 */
	public static String getPath() {
		return config.getPath();
	}

	/**
	 * �������ε����ĸ�·��
	 * @param path �µ�·��������·����
	 * @return ·�����ڷ���true�������ڷ���false
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
	 * ��������½��ļ�
	 * @param isSavefile true�Ǳ����ļ���false���½��ļ�
	 */
	public static void saveOrNewFile(boolean isSavefile) {
		
		if(getNodePath()!=null)
			fileChooser.setCurrentDirectory(new File(getNodePath()));
		else 
			fileChooser.setCurrentDirectory(new File(getPath()));
		
		
		if(isSavefile)
			fileChooser.showSaveDialog(frame);
		else 
			fileChooser.showDialog(frame, "�½�");
		
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
	 * ���ص�ǰ�ڵ��·��
	 * @return ��ǰ�ڵ�·��
	 */
	public static String getNodePath() {
		return nodePath;
	}

	/**
	 * ���õ�ǰ�ڵ�·��
	 * @param nodePath ��·��
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
