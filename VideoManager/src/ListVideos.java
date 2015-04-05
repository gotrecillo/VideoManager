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
public class ListVideos extends MyJDialog implements Listeneable{

	private List<String> files = new ArrayList<String>();
	private JButton getDir, list;
	private JLabel directory, formatOption;
	private JTextField dirPath;
	private JCheckBox onlyFiles, onlyDirectories, both, shortFiles, shortDirectories;
	private ButtonGroup formatGroup;
	private JFileChooser fc;
	private File fileSelected;
	private int returnVal;
	private final String os = System.getProperty("os.name").toLowerCase();
	private String filename;
	
	//constructores y metodo initiate para crear la ventana.
	public void initiate(){
		setLookAndFeel();
		if (os.startsWith("win")) {
			filename = "\\list.txt";
		}else{
			filename = "/list.txt";
		
		}
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
		
		formatOption = new JLabel("Formato de la lista: ");
		add(formatOption);
		
		onlyFiles = new JCheckBox("Solo videos", false);
		add(onlyFiles);
		onlyFiles.addActionListener(this);
		
		onlyDirectories = new JCheckBox("Solo carpetas", false);
		add(onlyDirectories);
		onlyDirectories.addActionListener(this);
		
		both = new JCheckBox("Ambos", true);
		add(both);
		both.addActionListener(this);
		
		shortDirectories = new JCheckBox("Solo nombre de directorios", true);
		add(shortDirectories);
		shortDirectories.addActionListener(this);
		
		shortFiles = new JCheckBox("Solo nombre de videos", false);
		add(shortFiles);
		shortFiles.addActionListener(this);
		
		formatGroup = new ButtonGroup();
		formatGroup.add(both);
		formatGroup.add(onlyDirectories);
		formatGroup.add(onlyFiles);
		
		getDir = new JButton("Cambiar directorio");
		add(getDir);
		getDir.addActionListener(this);
		
		list = new JButton("Listar");
		add(list);
		list.addActionListener(this);
		
		setVisible(true);	
	}
	public ListVideos(){
		super();
		initiate();
	}
	
	public ListVideos(Frame owner){
		super("Listar videos", owner);
		initiate();
	}
	
	public ListVideos(Frame owner, boolean b){
		super("Listar videos", owner, b);
		initiate();
	}
	
	//Get the list of the directories that has videos	
	public void getDirectoriesList(String path, List<String> files){
		
		 File root = new File( path );
	        File[] list = root.listFiles();
	        String absolutePath, fileName;
	        Arrays.sort(list);

	        if (list == null) return;

	        for ( File f : list ) {
	            if ( f.isDirectory() ) {  
	            	fileName = f.getName();
	            	absolutePath = f.getAbsolutePath();
	            	if (hasVideos(absolutePath)){
	            		if (shortDirectories.isSelected()){
	            			files.add(fileName);
	            		}else{
	            			files.add(absolutePath);
	            		}
	            	}
	                getDirectoriesList(absolutePath, files );
	            }
	        }
	    }
	
	//Get the list of videos
	public void getFileList(String path, List<String> files){
		File root = new File( path );
        File[] list = root.listFiles();
        String absolutePath, fileName;
        Arrays.sort(list);
        
        if (list == null) return;
        for ( File f : list ) {
        	absolutePath = f.getAbsolutePath();
        	fileName = f.getName();
            if ( f.isDirectory() ) {  
                getFileList( absolutePath, files );
            }
            else {
            	if (isVideo(absolutePath)){
            		if (shortFiles.isSelected()){
            			files.add(fileName); 
            		}else{
            			files.add( absolutePath);
            		}
            	}
            }
        }
	}
	
	//Get the list of videos and their directories
	public void getFullList(String path, List<String> files) {

        File root = new File( path );
        File[] list = root.listFiles();
        String absolutePath, fileName;
        Arrays.sort(list);

        if (list == null) return;

        for ( File f : list ) {
        	absolutePath = f.getAbsolutePath();
        	fileName = f.getName();
            if ( f.isDirectory() ) {  
            	if (hasVideos(absolutePath)){
            		if (shortDirectories.isSelected()){
            			files.add(fileName);
            		}else{
            			files.add(absolutePath);
            		}
            	}
                getFullList( absolutePath, files );
            }
            else {
            	if (isVideo(absolutePath)){
            		if (shortFiles.isSelected()){
            			files.add("   " + fileName);
            		}else{
            			files.add("   " + absolutePath);
            		}
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
	
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == getDir){
				returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION){
					fileSelected = fc.getSelectedFile();
					dirPath.setText(fileSelected.getAbsolutePath());
				}
			}
			
			if (e.getSource() == list){
				String path = fileSelected.getAbsolutePath();
				if (dirPath.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Selecciona un directorio", "Directorio no seleccionado", JOptionPane.INFORMATION_MESSAGE);
				}else{
					files.clear();
					if (both.isSelected()){
						getFullList(path, files);
					}else if (onlyDirectories.isSelected()){
						getDirectoriesList(path, files);
					}else{
						getFileList(path, files);
					}
					createListFile(path);
					writeList(files, path);
					JOptionPane.showMessageDialog(null, "Archivo creado en: "+path+filename, "Archivo creado", JOptionPane.INFORMATION_MESSAGE);
					ShowListVideos listFrame = new ShowListVideos(path+filename, null, true);
				}
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Directorio con permisos especiales del sistema operativo", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ListVideos mainFrame = new ListVideos(null);
	}

}
