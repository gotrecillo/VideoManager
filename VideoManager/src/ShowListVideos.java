import javax.swing.JDialog;
import javax.swing.JTextArea;

import java.io.*;
import java.util.Scanner;

/**
 * Programa que nos mostrara el contenido de un archivo de texto como un JDialog, en este caso lo usaremos para ver la lista que creamos.
 * @author gotre
 * @version 0.9
 */

@SuppressWarnings("serial")
public class ShowListVideos extends MyJDialog{
	private JTextArea text;
	private File file;
	
	//Constructor y metodo initiate para crear la ventana
	private void initiate(String path){
		setSize(1200, 780);
		text = new JTextArea("", 50, 100);
		add(text);
		fillText();
		text.setEditable(false);
		super.initiate();
		
	}
	
	public ShowListVideos(String path, JDialog owner, boolean b){
		super("Listado de videos", owner, b);
		file = new File(path);
		initiate(path);
	}
	
	//LLena el text Area con el contenido de un archivo
	public void fillText(){
		try {
			Scanner in = new Scanner(file);
			while (in.hasNextLine()){
				text.append(in.nextLine()+"\n");
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
