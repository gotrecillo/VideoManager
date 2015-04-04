import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.*;

/**	
 *Program that lets you create a file with the list of directories containing videos and the videos themselves within a selected directory and its subdirectories.
 *The file while be saved in the selected directory as list.txt if the file already exists it will be overwritten.
 *
 *@author gotre
 *@version 0.2
 */

@SuppressWarnings("serial")
public class ListVideos extends JFrame implements Listeneable{

	private List<String> files = new ArrayList<String>();
	private JButton getDir, list;
	private JLabel directorio;
	private JTextField dirPath;
	private JFileChooser fc;
	private File fileSelected;
	private int returnVal;
	private final String os = System.getProperty("os.name").toLowerCase();
	private String filename;
	//constructor
	
	public ListVideos(){
		super("Listar peliculas");
		if (os.equals("win")) {
			filename = "\\list.txt";
		}else{
			filename = "/list.txt";
		}
		setSize(600,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout flow = new FlowLayout();
		setLayout(flow);
		
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		directorio = new JLabel("Directorio: ");
		add(directorio);
		
		dirPath = new JTextField("", 50);
		dirPath.setEditable(false);
		add(dirPath);
		
		getDir = new JButton("Cambiar directorio");
		add(getDir);
		getDir.addActionListener(this);
		
		list = new JButton("Listar");
		add(list);
		list.addActionListener(this);
		
		setVisible(true);
		
	}
	
	//Get the list of videos and their directories
	public void getVideos( String path, List<String> files ) {

        File root = new File( path );
        File[] list = root.listFiles();
        String absolutePath;
        Arrays.sort(list);

        if (list == null) return;

        for ( File f : list ) {
        	absolutePath = f.getAbsolutePath();
            if ( f.isDirectory() ) {  
            	if (hasVideos(absolutePath)){
            		files.add(f.getName());
            	}
                getVideos( absolutePath, files );
            }
            else {
            	if (isVideo(absolutePath)){
            		files.add("	" + absolutePath);
            	}
            }
        }
    }
	
	// Create the file with the list of files and directories
	public void createListFile(String path){

		try {
			File listFile = new File(path + filename);
			listFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Write in the file the list of files and directories
	public void writeList(List<String> list, String path){
		File listFile = new File(path+filename);
		 
		 try {
			FileWriter fw = new FileWriter(listFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i) + "\n");
			}
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//check if a file is a video
	public boolean isVideo(String path){
		String[] formats = {".mkv", ".avi", ".mp4", ".flv", "wmv", ".mpg", ".mpeg"};
		for (int i = 0; i < formats.length; i++) {
			if (path.endsWith(formats[i])){
				return true;
			}
		}
		return false;
	}
	
	//check if a directory has videos
	public boolean hasVideos(String path){
		File root = new File(path);
		File[] list = root.listFiles();
		for (int i = 0; i < list.length; i++) {
			if (isVideo(list[i].getAbsolutePath())){
				return true;
			}
		}
		return false;
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
		
		if (e.getSource() == list){
			if (dirPath.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Selecciona un directorio", "Directorio no seleccionado", JOptionPane.INFORMATION_MESSAGE);
			}else{
				files.clear();
				getVideos(fileSelected.getAbsolutePath(), files);
				createListFile(fileSelected.getAbsolutePath());
				writeList(files, fileSelected.getAbsolutePath());
				JOptionPane.showMessageDialog(null, "Archivo creado en: "+fileSelected.getAbsolutePath()+"/list.txt", "Archivo creado", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ListVideos mainFrame = new ListVideos();
	}

}
