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
 * ��̬����������
 * @author ��ΰ��(andi)
 *
 */
public class TreesPanel extends JPanel {
 
	private static final long serialVersionUID = 310585799130135713L;
	private DefaultMutableTreeNode root, chosen;
	private JTree tree;
 
    public TreesPanel() {
    	super();
        setLayout(new BorderLayout());
        // ���ڵ���г�ʼ��
        root = new DefaultMutableTreeNode("E:\\");
        // �����г�ʼ������������Դ��root����
        tree = new JTree(root);
        // �ѹ��������ӵ�Trees��
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
					System.out.println(path+" ��һ���ļ�");
				}
			}
        });
        
       
    }
    /**
     * Ϊ��ѡ�Ľڵ�����ӽڵ㣨��������ӽڵ㣩
     * @param data �ýڵ�ľ���·��
     * @param node �ýڵ�
     */
    public static void branch(String data,DefaultMutableTreeNode node) {
    	
        File file = new File(data);
        String [] files = file.list(Filter.FILENAMEFILTER);
        for (String f : files)
        	node.add(new DefaultMutableTreeNode(f));
        // ���ڵ�node��Ӷ���ӽڵ�
    }

}