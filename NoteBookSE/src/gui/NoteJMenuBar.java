package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import control.NoteControl;


/**
 * @author 刘伟艺(andi)
 *	菜单条
 */
public class NoteJMenuBar extends JMenuBar {

	public NoteJMenuBar(){
		super();
		filemenu = new JMenu("文件");
		setmenu = new JMenu("设置");
		
		//初始子菜单控件
		newFileItem = new MyJMenuItem("新建");
		setFilterItem = new MyJMenuItem("过滤器");
		saveFileItem = new MyJMenuItem("保存");
		setPathItem = new MyJMenuItem("路径");
		
		//为子菜单控件添加快捷键
		newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		setFilterItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		setPathItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
		//将子菜单控件添加到菜单
		filemenu.add(newFileItem);
		filemenu.add(saveFileItem);
		setmenu.add(setFilterItem);
		setmenu.add(setPathItem);
		this.add(filemenu);
		this.add(setmenu);
	}
	
	private JMenu  filemenu,setmenu;
	private static MyJMenuItem newFileItem,setFilterItem,setPathItem,saveFileItem;
	private static final long serialVersionUID = -6880190484954517632L;
	
	public static MyJMenuItem getNewFileItem() {
		return newFileItem;
	}
	public static MyJMenuItem getSetFilterItem() {
		return setFilterItem;
	}
	public static JMenuItem getSaveFileItem() {
		return saveFileItem;
	}
	public static MyJMenuItem getSetPathItem() {
		return setPathItem;
	}
	
}

/**
 * @author andi
 *	自定义JMenuItem，添加了鼠标释放事件
 */
class MyJMenuItem extends JMenuItem{
	private static final long serialVersionUID = 6299493920394502702L;
	MyJMenuItem(String name){
		super(name);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)  {
				
				if(e.getSource() == NoteJMenuBar.getNewFileItem()){
					//新建文件
					NoteControl.saveOrNewFile(false);
				}else if(e.getSource() == NoteJMenuBar.getSaveFileItem()){
					//保存文件
					NoteControl.saveOrNewFile(true);
				}else if(e.getSource() == NoteJMenuBar.getSetFilterItem()){
					//设置文件过滤器
					String filter = JOptionPane.showInputDialog("设置要过滤的文件后缀，如：*.txt*.java*.sol");	
					if(!NoteControl.setFilter(filter))
						JOptionPane.showMessageDialog(null, "错误或取消操作");
					
				}else if(e.getSource() == NoteJMenuBar.getSetPathItem()){
					//设置开始路径
					String path = JOptionPane.showInputDialog("设置初始路径，如 E:\\");	
					if(!NoteControl.setPath(path))
						JOptionPane.showMessageDialog(null, "路径错误或取消操作");
						
				}
			}
		});
	}
}