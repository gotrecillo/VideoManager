import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RenameDialog extends MyJDialog implements Listeneable{
	protected JButton ok, rename;
	protected JLabel originalNameLabel, newNameLabel;
	protected JTextField originalName, newName;
	protected File file;
	protected String fileName, extension, directorySeparator, newFileName; 
	
	//constructor and initiate method to create an object
	public RenameDialog(File file, JDialog owner, boolean b){
		super("Renombrar", owner, b);
		initiate(file);
	}
	
	public void initiate(File file){
		setSize(700, 150);
		this.file = file;
		fileName = file.getName();
		
		if (os.startsWith("win")){
			directorySeparator = "\\";
		}else{
			directorySeparator = "/";
		}
		
		originalNameLabel = new JLabel("Nombre original: ");
		add(originalNameLabel);
		
		originalName = new JTextField(fileName, 50);
		originalName.setEditable(false);
		add(originalName);
		
		newNameLabel = new JLabel("  Nuevo nombre: ");
		add(newNameLabel);
		
		newName = new JTextField(formatName(), 50);
		add(newName);
		
		ok = new JButton("Ok");
		add(ok);
		ok.addActionListener(this);
		
		rename = new JButton("Renombrar");
		add(rename);
		rename.addActionListener(this);
		
		super.initiate();
	}
	
	//check if a character is a separator
	public boolean isSeparator(char c){
		if((c == '[') || (c == '(')){
			return true;
		}else{
			return false;
		}
	}
	
	//Get if the the video is in 1080p or 720p
	public String getFormat(){
		String format = fileName.toLowerCase();
		if (format.contains("1080")){
			return " - 1080p";
		}else if (format.contains("720")){
			return " - 720p";
		}else{
			return "";
		}
	}
	
	//Look up for a char that separates the name from other data.
	public char getSeparator(){
		char separator = '0';
		char actual;
		for (int i = 0; ((i < fileName.length()) && (separator == '0')); i++) {
			actual = fileName.charAt(i);
			if (isSeparator(actual)){
				separator = actual;
			}
		}
		return separator;
	}
	
	//format the name of our file
	public String formatName(){
		char separator = getSeparator();
		if (separator == '0'){
			return fileName;
		}else{
			int separatorIndex = fileName.indexOf(separator);
			String formatedName = fileName.substring(0, separatorIndex);
			formatedName = capitalize(formatedName);
			return formatedName + getFormat() + getExtension();
		}
	}
	
	//return the string with all the first letters of a word in upper case;
	public String capitalize(String fileName){
		if (!fileName.isEmpty()){
			String []words = fileName.split(" ");
			fileName = "";
			for (int i = 0; i < words.length; i++) {
				char capLetter = Character.toUpperCase(words[i].charAt(0));
				fileName += " " + capLetter + words[i].substring(1, words[i].length());
			}
		}
		return fileName.trim();
	}
	
	public String getExtension(){
		int lastDotIndex = fileName.lastIndexOf('.');
		return fileName.substring(lastDotIndex, fileName.length());
	}
	
	public boolean rename(){
		String newFileName = file.getParent() + directorySeparator + newName.getText();
		File newFile = new File(newFileName);
		return file.renameTo(newFile);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rename){
			newFileName = file.getParent() + directorySeparator + newName.getText();
			File newF = new File(newFileName);
			if (!rename()){
				if (newF.exists()){
					JOptionPane.showMessageDialog(null, "Ya existe un archivo con el nombre elegido", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Sin permisos de escritura sobre el archivo", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "El archivo ha sido renombrado a: " +newFileName, "Nombre cambiado", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
