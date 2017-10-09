package impl;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.tree.DefaultMutableTreeNode;

import dao.Filter;

/**
 * 树是一次性加载完成
 * @author 刘伟艺(andi)
 * 左边树状图
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
	 * 初始化节点
	 * @param direc 开始的文件名
	 * @return 整个树节点
	 */
	public static DefaultMutableTreeNode init(String direc){
		File directory = new File(direc);
		if(!directory.exists()){
			//不存在就抛出异常
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
	 * 递归添加
	 * @param file 根文件夹
	 * @param node 根节点
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
