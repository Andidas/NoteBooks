package impl;

import gui.NoteJPane;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.*;

import dao.FileIOUtil;
import dao.Filter;
 
/**
 * 动态添加树，面板
 * @author 刘伟艺(andi)
 *
 */
public class TreesPanel extends JPanel {
 
	private static final long serialVersionUID = 310585799130135713L;
	private DefaultMutableTreeNode root, chosen;
	private JTree tree;
 
    public TreesPanel() {
    	super();
        setLayout(new BorderLayout());
        // 根节点进行初始化
        root = new DefaultMutableTreeNode("E:\\");
        // 树进行初始化，其数据来源是root对象
        tree = new JTree(root);
        // 把滚动面板添加到Trees中
        add(new JScrollPane(tree));       
        
        tree.addTreeSelectionListener((TreeSelectionEvent e) -> {
			
        	TreePath [] tpath = e.getPaths();
			for (TreePath t : tpath) {
				System.out.println(t);
			}
			String path = tpath[0].toString().replace("\\", "").replace(", ", "\\").replace("[", "").replace("]", "");
			
			chosen = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			if(chosen.isLeaf()){
				if(new File(path).isDirectory()){
					branch(path,chosen);
				}else{
					NoteJPane.setTestPaneText(FileIOUtil.read(path));
					System.out.println(path+" 是一个文件");
				}
			}
        });
        
       
    }
    /**
     * 为所选的节点添加子节点（如果存在子节点）
     * @param data 该节点的绝对路径
     * @param node 该节点
     */
    public static void branch(String data,DefaultMutableTreeNode node) {
    	
        File file = new File(data);
        String [] files = file.list(Filter.FILENAMEFILTER);
        for (String f : files)
        	node.add(new DefaultMutableTreeNode(f));
        // 给节点node添加多个子节点
    }

}