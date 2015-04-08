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
	private JCheckBox onlyFiles, onlyDirectories, both, shortFiles, shortDirectories;
	private ButtonGroup formatGroup;
	private String listFileName;
	
	//constructors and initiate method to create the window.
	public void initiate(){
		setSize(600,150);
		
		if (os.startsWith("win")) {
			listFileName = "\\list.txt";
		}else{
			listFileName = "/list.txt";
		
		}
		
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
		super.initiate();
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
	public void createListFile(File root){
		try {
			String path = root.getAbsolutePath();
			File listFile = new File(path + listFileName);
			listFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Write in the file the list of files and directories
	public void writeFullList(List<File> list, File root){
		File listFile = new File(root.getAbsolutePath()+listFileName);
		File actualFile;
		String actualPath, actualName;
		
		 try {
			FileWriter fw = new FileWriter(listFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				actualPath = list.get(i).getAbsolutePath();
				actualFile = new File(actualPath);
				if (isVideo(actualFile)) {
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
	public void writeFilesList(List<File> list, File root){
		File listFile = new File(root.getAbsolutePath()+listFileName);
		File actualFile;
		String actualPath, actualName;
		
		 try {
			FileWriter fw = new FileWriter(listFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				actualPath = list.get(i).getAbsolutePath();
				actualFile = new File(actualPath);
				if (isVideo(actualFile)) {
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
	public void writeDirectoriesList(List<File> list, File root){
		File listFile = new File(root.getAbsolutePath()+listFileName);
		File actualFile;
		String actualPath, actualName;
		
		 try {
			FileWriter fw = new FileWriter(listFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				actualPath = list.get(i).getAbsolutePath();
				actualFile = new File(actualPath);
				if (isVideo(actualFile)) {
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
		
		//choose the directory path
		if (e.getSource() == getDir){
			chooseDirectoryPath();
		}
		
		//create the list with the selected options
		if (e.getSource() == list){
			if (dirPath.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Selecciona un directorio", "Directorio no seleccionado", JOptionPane.INFORMATION_MESSAGE);
			}else{
				try {
					files.clear();
					getList(rootFile, files);
					createListFile(rootFile);
					if (both.isSelected()){
						writeFullList(files, rootFile);
					}else if (onlyDirectories.isSelected()){
						writeDirectoriesList(files, rootFile);
					}else{
						writeFilesList(files, rootFile);
					}
					this.dispose();
					JOptionPane.showMessageDialog(null, "Archivo creado en: "+rootFile.getAbsolutePath()+listFileName, "Archivo creado", JOptionPane.INFORMATION_MESSAGE);
					ShowListVideos listFrame = new ShowListVideos(rootFile.getAbsolutePath()+listFileName, this, true);
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Directorio con permisos especiales del sistema operativo", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
	}
}
