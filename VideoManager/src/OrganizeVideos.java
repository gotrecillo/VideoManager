import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class OrganizeVideos extends MyJDialogChooser implements Listeneable{
	private JButton organize;
	
	private void initiate(){
		setLookAndFeel();
		setSize(600,150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		FlowLayout flow = new FlowLayout();
		setLayout(flow);
		
		fc = new JFileChooser();
		fc.setDialogTitle("Elige un directorio");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
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
		
		setVisible(true);	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getDir){
			returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				fileSelected = fc.getSelectedFile();
				dirPath.setText(fileSelected.getAbsolutePath());
			}
		}
		
		if (e.getSource() == organize){
			String path = fileSelected.getAbsolutePath();
			if (dirPath.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Selecciona un directorio", "Directorio no seleccionado", JOptionPane.INFORMATION_MESSAGE);
			}else{
				files.clear();
				getFileList(path, files);
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		OrganizeVideos app = new OrganizeVideos();	
	}

}
