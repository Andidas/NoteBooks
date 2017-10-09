package gui;

import java.awt.Dimension;

import javax.swing.JFrame;


/**
 * @author 刘伟艺(andi)
 * 主窗口
 */
public class NoteJFrame extends JFrame {

	public NoteJFrame(){
		super();	
		this.setTitle("Note");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//窗口在屏幕居中
		Dimension screenSize = getToolkit().getScreenSize();
		screenX = (screenSize.width - SCREENWIDTH)/2;
		screenY = (screenSize.height - SCREENHEIGHT)/2;	
		this.setBounds(screenX, screenY, SCREENWIDTH, SCREENHEIGHT);
	}

	private static final long serialVersionUID = -8689779435073642284L;
	
	private static final int SCREENWIDTH = 800;	//width
	private static final int SCREENHEIGHT = 600; // height
	
	private int screenX ;//窗口左上角x坐标
	private int screenY ;//窗口左上角y坐标


}
