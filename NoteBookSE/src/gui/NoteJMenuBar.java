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
 * @author ��ΰ��(andi)
 *	�˵���
 */
public class NoteJMenuBar extends JMenuBar {

	public NoteJMenuBar(){
		super();
		filemenu = new JMenu("�ļ�");
		setmenu = new JMenu("����");
		
		//��ʼ�Ӳ˵��ؼ�
		newFileItem = new MyJMenuItem("�½�");
		setFilterItem = new MyJMenuItem("������");
		saveFileItem = new MyJMenuItem("����");
		setPathItem = new MyJMenuItem("·��");
		
		//Ϊ�Ӳ˵��ؼ���ӿ�ݼ�
		newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		setFilterItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		setPathItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
		//���Ӳ˵��ؼ���ӵ��˵�
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
 *	�Զ���JMenuItem�����������ͷ��¼�
 */
class MyJMenuItem extends JMenuItem{
	private static final long serialVersionUID = 6299493920394502702L;
	MyJMenuItem(String name){
		super(name);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)  {
				
				if(e.getSource() == NoteJMenuBar.getNewFileItem()){
					//�½��ļ�
					NoteControl.saveOrNewFile(false);
				}else if(e.getSource() == NoteJMenuBar.getSaveFileItem()){
					//�����ļ�
					NoteControl.saveOrNewFile(true);
				}else if(e.getSource() == NoteJMenuBar.getSetFilterItem()){
					//�����ļ�������
					String filter = JOptionPane.showInputDialog("����Ҫ���˵��ļ���׺���磺*.txt*.java*.sol");	
					if(!NoteControl.setFilter(filter))
						JOptionPane.showMessageDialog(null, "�����ȡ������");
					
				}else if(e.getSource() == NoteJMenuBar.getSetPathItem()){
					//���ÿ�ʼ·��
					String path = JOptionPane.showInputDialog("���ó�ʼ·������ E:\\");	
					if(!NoteControl.setPath(path))
						JOptionPane.showMessageDialog(null, "·�������ȡ������");
						
				}
			}
		});
	}
}