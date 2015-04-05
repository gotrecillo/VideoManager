import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
	private JScrollPane scrollPane;
	private JTextArea text;
	private FlowLayout dis;
	private File file;
	
	//Constructor y metodo initiate para crear la ventana
	private void initiate(String path){
		setSize(1200, 780);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		text = new JTextArea("", 50, 100);
		dis = new FlowLayout();
		setLayout(dis);
		add(text);
		fillText();
		text.setEditable(false);
		setVisible(true);
		scrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
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
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ShowListVideos app = new ShowListVideos("/home/gotre/list.txt", null, true);
	}

}
