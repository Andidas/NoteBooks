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
 * ��̬����������
 * @author ��ΰ��(andi)
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
        // ���ڵ���г�ʼ��
        root = new DefaultMutableTreeNode(NoteControl.getPath());
        // �����г�ʼ������������Դ��root����
        tree = new JTree(root);

		pop = new JPopupMenu();
		del = new JMenuItem("ɾ��");
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e){
				if(e.getButton() == 1){
					if(path!=null){
						if(new File(path).isDirectory()){
							if(JOptionPane.showConfirmDialog(tree, "�Ƿ�ɾ���ļ���")==0){
								if(FileUtil.delDir(path, true)){
									NoteControl.setTree();
								}
							}
						}else if(new File(path).isFile()){
							if(JOptionPane.showConfirmDialog(tree, "�Ƿ�ɾ���ļ�")==0){
								if(FileUtil.delFile(path)){
									NoteControl.setTree();
								}
							}
						}
					}else{
						JOptionPane.showMessageDialog(tree, "��ѡ���ļ�");
					}
				}
			}
		});
		pop.add(del);
        // �ѹ��������ӵ�Trees��
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
     * Ϊ��ѡ�Ľڵ�����ӽڵ㣨��������ӽڵ㣩
     * @param data �ýڵ�ľ���·��
     * @param node �ýڵ�
     */
    public static void branch(String data,DefaultMutableTreeNode node) {
    	
        File file = new File(data);
        String [] files = file.list(NoteControl.getFilenameIOFILTER());
        if(files.length == 0)
        	JOptionPane.showMessageDialog(null, "���ļ���");
        else
        for (String f : files)
        	node.add(new DefaultMutableTreeNode(f));
        // ���ڵ�node��Ӷ���ӽڵ�
    }

}