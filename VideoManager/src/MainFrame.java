import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Program that organizes your movies in a directory and all subdirectories. 
 * Allows you to rename files, and save each file in a Directory with a name derived 
 * from the name of the file by default but can save files in the directory you want.
 * 
 * @author gotre
 * @version 0.1
 * */

@SuppressWarnings("serial")
public class MainFrame extends MyJFrame implements Listeneable{
	private JButton list, organize;
	
	 public MainFrame() {
		super("Organizador de videos");
		setSize(600,150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(flow);
		setLookAndFeel();
		setVisible(true);
		
		list = new JButton("Listar videos");
		add(list);
		list.addActionListener(this);
		
		organize = new JButton("Organizar videos");
		add(organize);
		organize.addActionListener(this);
		
	}
	 
	
	@Override
	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == list){
			ListVideos listFrame = new ListVideos(this, true);
		}
		if (e.getSource() == organize){
			OrganizeVideos organizeFrame = new OrganizeVideos(this, true);
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String []args){
		MainFrame app = new MainFrame();
	}
	
}
