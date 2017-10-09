package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.*;

import control.NoteControl;
import dao.FileIOUtil;
import dao.FileUtil;
 
/**
 * 动态添加树，面板
 * @author 刘伟艺(andi)
 *
 */
public class TreesPanel extends JPanel {
 
	private static final long serialVersionUID = 310585799130135713L;
	private static DefaultMutableTreeNode root;
	private DefaultMutableTreeNode chosen;
	private JTree tree;
	private JPopupMenu pop;
	private JMenuItem del;
	private static String path;
    public TreesPanel(NoteJFrame frame) {
    	super();
    	setPreferredSize(new Dimension(frame.getWidth()/5, frame.getHeight()));
        setLayout(new BorderLayout());
        // 根节点进行初始化
        root = new DefaultMutableTreeNode(NoteControl.getPath());
        // 树进行初始化，其数据来源是root对象
        tree = new JTree(root);

		pop = new JPopupMenu();
		del = new JMenuItem("删除");
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e){
				if(e.getButton() == 1){
					if(path!=null){
						if(new File(path).isDirectory()){
							if(JOptionPane.showConfirmDialog(tree, "是否删除文件夹")==0){
								if(FileUtil.delDir(path, true)){
									NoteControl.setTree();
								}
							}
						}else if(new File(path).isFile()){
							if(JOptionPane.showConfirmDialog(tree, "是否删除文件")==0){
								if(FileUtil.delFile(path)){
									NoteControl.setTree();
								}
							}
						}
					}else{
						JOptionPane.showMessageDialog(tree, "请选择文件");
					}
				}
			}
		});
		pop.add(del);
        // 把滚动面板添加到Trees中
        add(new JScrollPane(tree));       
        
        tree.addTreeSelectionListener((TreeSelectionEvent e) -> {	
        	TreePath [] tpath = e.getPaths();
			/*for (TreePath t : tpath) {
				System.out.println(t);
			}*/
			path = tpath[0].toString().replaceAll("\\[|\\]|", "").replaceAll(",\\s", "\\\\");
			System.out.println("path:"+path);
			NoteControl.setNodePath(path);
			chosen = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			if(chosen.isLeaf()){
				if(new File(path).isDirectory()){
					branch(path,chosen);
				}else{
					NoteControl.setJTestPanelContext(FileIOUtil.read(path));
				}
			}
        });
        tree.addMouseListener(new MouseAdapter()  {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == 3){
					pop.show(tree, e.getX(), e.getY());
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
        String [] files = file.list(NoteControl.getFilenameIOFILTER());
        if(files.length == 0)
        	JOptionPane.showMessageDialog(null, "空文件夹");
        else
        for (String f : files)
        	node.add(new DefaultMutableTreeNode(f));
        // 给节点node添加多个子节点
    }

}