package gui;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;


/**
 * @author 刘伟艺(andi)
 * 主面板
 */
public class NoteJPane extends JSplitPane {
	
	public NoteJPane(NoteJFrame frame) {
		super();
		
		//可编辑面板
		textPane = new JTextPane();
		this.addImpl(new JScrollPane(textPane), JSplitPane.RIGHT, 0);
		//树形导航
		tree = new TreesPanel(frame);
		this.addImpl(tree, JSplitPane.LEFT, 0);
       
	}
	
	private static final long serialVersionUID = -18563738454324076L;
	private static JTextPane textPane;
	public static TreesPanel tree;
	/**
	 * 设置左边编辑面板的内容
	 * @param context 内容
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
	 * @param tree 新的树形导航面板
	 */
	public void setTree(TreesPanel tree) {
		this.remove(this.getTree());
		this.addImpl(tree, JSplitPane.LEFT, 0);
		NoteJPane.tree = tree;
		this.validate();
	}
	
	
}

