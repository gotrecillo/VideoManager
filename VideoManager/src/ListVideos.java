import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

/**	
 *Program that lets you create a file with the list of directories containing videos and the videos themselves within a selected directory and its subdirectories.
 *The file while be saved in the selected directory as list.txt if the file already exists it will be overwritten.
 *
 *@author gotre
 *@version 0.2
 */

@SuppressWarnings("serial")
public class ListVideos extends MyJDialogChooser implements Listeneable{

	private JButton  list;
	private JLabel  formatOption;
	private JTextField dirPath;
	private JCheckBox onlyFiles, onlyDirectories, both, shortFiles, shortDirectories;
	private ButtonGroup formatGroup;
	private final String os = System.getProperty("os.name").toLowerCase();
	private String filename;
	
	//constructors and initiate method to create the window.
	private void initiate(){
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
		super("Listar videos", null);
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
	
	//create the file that is going to contain the list
	public void createListFile(String path){

		try {
			File listFile = new File(path + filename);
			listFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Write in the file the list of files and directories
	public void writeFullList(List<String> list, String path){
		File listFile = new File(path+filename);
		File actualFile;
		String actualPath, actualName;
		
		 try {
			FileWriter fw = new FileWriter(listFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				actualPath = list.get(i);
				actualFile = new File(actualPath);
				if (isVideo(actualPath)) {
					if (shortFiles.isSelected()){
						actualName = actualFile.getName();
						bw.write(actualName + "\n");
					}else{
						bw.write(actualPath + "\n");
					}
				}else{
					if (shortDirectories.isSelected()){
						actualName = actualFile.getName();
						bw.write(actualName + "\n");
					}else{
						bw.write(actualPath + "\n");
					}
				}
			}
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//write in the file the list of the movies
	public void writeFilesList(List<String> list, String path){
		File listFile = new File(path+filename);
		File actualFile;
		String actualPath, actualName;
		
		 try {
			FileWriter fw = new FileWriter(listFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				actualPath = list.get(i);
				actualFile = new File(actualPath);
				if (isVideo(actualPath)) {
					if (shortFiles.isSelected()){
						actualName = actualFile.getName();
						bw.write(actualName + "\n");
					}else{
						bw.write(actualPath + "\n");
					}
				}
			}
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//write in the file the list of the directories
	public void writeDirectoriesList(List<String> list, String path){
		File listFile = new File(path+filename);
		File actualFile;
		String actualPath, actualName;
		
		 try {
			FileWriter fw = new FileWriter(listFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				actualPath = list.get(i);
				actualFile = new File(actualPath);
				if (isVideo(actualPath)) {
					if (shortFiles.isSelected()){
						actualName = actualFile.getName();
						bw.write(actualName + "\n");
					}else{
						bw.write(actualPath + "\n");
					}
				}
			}
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//choose the directory path
			if (e.getSource() == getDir){
				returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION){
					fileSelected = fc.getSelectedFile();
					dirPath.setText(fileSelected.getAbsolutePath());
				}
			}
			
			//create the list with the selected options
			if (e.getSource() == list){
				String path = fileSelected.getAbsolutePath();
				if (dirPath.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Selecciona un directorio", "Directorio no seleccionado", JOptionPane.INFORMATION_MESSAGE);
				}else{
					files.clear();
					getList(path, files);
					createListFile(path);
					if (both.isSelected()){
						writeFullList(files, path);
					}else if (onlyDirectories.isSelected()){
						writeDirectoriesList(files, path);
					}else{
						writeFilesList(files, path);
					}
					setVisible(false);
					JOptionPane.showMessageDialog(null, "Archivo creado en: "+path+filename, "Archivo creado", JOptionPane.INFORMATION_MESSAGE);
					ShowListVideos listFrame = new ShowListVideos(path+filename, this, true);
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
