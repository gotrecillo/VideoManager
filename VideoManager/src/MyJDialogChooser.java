import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MyJDialogChooser extends MyJDialog{
	protected List<File> files = new ArrayList<File>();
	protected JButton getDir, organize;
	protected JLabel directory;
	protected JTextField dirPath;
	protected JFileChooser fc;
	protected File rootFile;
	protected int returnVal;
	
	
	public void initiate(){
		fc = new JFileChooser();
		fc.setDialogTitle("Elige un directorio");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		super.initiate();
	}
	public MyJDialogChooser(String title, Frame owner) {
		super(title, owner);
	}
	
	public MyJDialogChooser(String title, JDialog owner, boolean b){
		super(title, owner, b);
	}
	
	public MyJDialogChooser(String title, Frame owner, boolean b){
		super(title, owner, b);
	}
	
	
	//check if a directory has videos
	public boolean hasVideos(File root){
		File[] list = root.listFiles();
		for (int i = 0; i < list.length; i++) {
			if (isVideo(list[i])){
				return true;
			}
		}
		return false;
	}
	
	//check if a file is a video
	public boolean isVideo(File file){
		String[] formats = {".mkv", ".avi", ".mp4", ".flv", "wmv", ".mpg", ".mpeg"};
		for (int i = 0; i < formats.length; i++) {
			String name = file.getName();
			if (name.endsWith(formats[i])){
				return true;
			}
		}
		return false;
	}
	
	//Get the list of videos
	public void getFileList(File root, List<File> files){
		
        File[] list = root.listFiles();
        Arrays.sort(list);

        if (list == null) return;

        for ( File f : list ) {
	        	if ( f.isDirectory() ) {  
	        		getList(f, files );
	            } else if (isVideo(f)) {
	            	files.add(f);
	            }
        }
	}
	
	
	//Get the list of videos and their directories
	public void getList(File root, List<File> files) {

        File[] list = root.listFiles();
        Arrays.sort(list);

        if (list == null) return;

        for ( File f : list ) {
	        	if ( f.isDirectory() ) {  
	        		if (hasVideos(f)){
	        			files.add(f);
	            	}
	        		getList( f, files );
	            } else if (isVideo(f)) {
	            	files.add(f);
	            }
        }
	}
	
	//Open a file chooser and pick a directory
	public void chooseDirectoryPath(){
		returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION){
			rootFile = fc.getSelectedFile();
			dirPath.setText(rootFile.getAbsolutePath());
		}
	}
}
