package gui;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;


/**
 * @author ��ΰ��(andi)
 * �����
 */
public class NoteJPane extends JSplitPane {
	
	public NoteJPane(NoteJFrame frame) {
		super();
		
		//�ɱ༭���
		textPane = new JTextPane();
		this.addImpl(new JScrollPane(textPane), JSplitPane.RIGHT, 0);
		//���ε���
		tree = new TreesPanel(frame);
		this.addImpl(tree, JSplitPane.LEFT, 0);
       
	}
	
	private static final long serialVersionUID = -18563738454324076L;
	private static JTextPane textPane;
	public static TreesPanel tree;
	/**
	 * ������߱༭��������
	 * @param context ����
	 */
	public void setTextPaneText(String context){
		textPane.setSelectedTextColor(Color.red);
		textPane.setText(context);
	}
	/**
	 * getter and setter
	 */
	public String getTextPaneText() {
		return textPane.getText();
	}
	public TreesPanel getTree() {
		return tree;
	}
	/**
	 * @param tree �µ����ε������
	 */
	public void setTree(TreesPanel tree) {
		this.remove(this.getTree());
		this.addImpl(tree, JSplitPane.LEFT, 0);
		NoteJPane.tree = tree;
		this.validate();
	}
	
	
}

