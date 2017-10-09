package gui;

import java.awt.Dimension;

import javax.swing.JFrame;


/**
 * @author ��ΰ��(andi)
 * ������
 */
public class NoteJFrame extends JFrame {

	public NoteJFrame(){
		super();	
		this.setTitle("Note");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//��������Ļ����
		Dimension screenSize = getToolkit().getScreenSize();
		screenX = (screenSize.width - SCREENWIDTH)/2;
		screenY = (screenSize.height - SCREENHEIGHT)/2;	
		this.setBounds(screenX, screenY, SCREENWIDTH, SCREENHEIGHT);
	}

	private static final long serialVersionUID = -8689779435073642284L;
	
	private static final int SCREENWIDTH = 800;	//width
	private static final int SCREENHEIGHT = 600; // height
	
	private int screenX ;//�������Ͻ�x����
	private int screenY ;//�������Ͻ�y����


}
