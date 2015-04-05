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
	protected List<String> files = new ArrayList<String>();
	protected JButton getDir, organize;
	protected JLabel directory;
	protected JTextField dirPath;
	protected JFileChooser fc;
	protected File fileSelected;
	protected int returnVal;
	
	
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
	
	//Get the list of videos
	public void getFileList(String path, List<String> files){

		File root = new File( path );
        File[] list = root.listFiles();
        String absolutePath;
        Arrays.sort(list);

        if (list == null) return;

        for ( File f : list ) {
        	absolutePath = f.getAbsolutePath();
	        	if ( f.isDirectory() ) {  
	        		getList( absolutePath, files );
	            } else if (isVideo(absolutePath)) {
	            	files.add(absolutePath);
	            }
        }
	}
	
	
	//Get the list of videos and their directories
	public void getList(String path, List<String> files) {

        File root = new File( path );
        File[] list = root.listFiles();
        String absolutePath;
        Arrays.sort(list);

        if (list == null) return;

        for ( File f : list ) {
        	absolutePath = f.getAbsolutePath();
	        	if ( f.isDirectory() ) {  
	        		if (hasVideos(absolutePath)){
	        			files.add(absolutePath);
	            	}
	        		getList( absolutePath, files );
	            } else if (isVideo(absolutePath)) {
	            	files.add(absolutePath);
	            }
        }
	}
}
