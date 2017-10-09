package impl;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.tree.DefaultMutableTreeNode;

import dao.Filter;

/**
 * ����һ���Լ������
 * @author ��ΰ��(andi)
 * �����״ͼ
 */
public class NodeJTree  {
	private static DefaultMutableTreeNode node;
	private static String path;
	public static String getPath() {
		return path;
	}
	public static void setPath(String path) {
		NodeJTree.path = path;
	}
	/**
	 * ��ʼ���ڵ�
	 * @param direc ��ʼ���ļ���
	 * @return �������ڵ�
	 */
	public static DefaultMutableTreeNode init(String direc){
		File directory = new File(direc);
		if(!directory.exists()){
			//�����ھ��׳��쳣
			try {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			NodeJTree.setPath(direc);
			node = new DefaultMutableTreeNode(direc);		
			File [] files = directory.listFiles(Filter.FILEFILTER);
			
			for(File file : files){
					if(file.isDirectory())
						addDirectory(file,node);
					else
						node.add(new DefaultMutableTreeNode(file.getName()));
			}
		}
		return node;
	}
	/**
	 * �ݹ����
	 * @param file ���ļ���
	 * @param node ���ڵ�
	 */
	public static void addDirectory(File file,DefaultMutableTreeNode node){
		DefaultMutableTreeNode n = new DefaultMutableTreeNode(file.getName());
		File fs[] = file.listFiles(Filter.FILEFILTER);
		for(File f : fs){
				if(f.isDirectory()) addDirectory(f,n);
				else n.add(new DefaultMutableTreeNode(f.getName()));
		}
		node.add(n);
	}
	
	public static DefaultMutableTreeNode getNode() {
		return node;
	}
	public static void setNode(DefaultMutableTreeNode node) {
		NodeJTree.node = node;
	}
	
}
