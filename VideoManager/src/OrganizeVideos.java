import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class OrganizeVideos extends MyJDialogChooser implements Listeneable{
	private JButton organize;
	
	public void initiate(){
		setSize(600,150);
		
		directory = new JLabel("Directorio: ");
		add(directory);
		
		dirPath = new JTextField("", 50);
		dirPath.setEditable(false);
		add(dirPath);
		
		getDir = new JButton("Cambiar directorio");
		add(getDir);
		getDir.addActionListener(this);
		
		organize = new JButton("Organizar");
		add(organize);
		organize.addActionListener(this);
		super.initiate();;
	}
	
	public OrganizeVideos() {
		super("Organiza los videos", null);
		initiate();
	}
	
	public OrganizeVideos(Frame owner){
		super("Organiza los videos", null);
		initiate();
	}
	
	public OrganizeVideos(Frame owner, boolean b){
		super("Organiza los videos", owner, b);
		initiate();
	}
	
	
	public void chooseNewName(File file){
		
	}
	
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getDir){
			chooseDirectoryPath();
		}
		
		if (e.getSource() == organize){
			if (dirPath.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Selecciona un directorio", "Directorio no seleccionado", JOptionPane.INFORMATION_MESSAGE);
			}else{
				files.clear();
				getFileList(rootFile, files);
				RenameDialog renameFrame = new RenameDialog(new File("test.txt"),this, true);
			}
		}
	}
}
