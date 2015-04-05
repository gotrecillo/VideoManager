import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


@SuppressWarnings("serial")
public class MyJDialog extends JDialog{
	
	public MyJDialog(){
		super();
	}
	
	public MyJDialog(String title, JDialog owner, boolean b){
		super(owner, b);
		setTitle(title);
	}
	
	public MyJDialog(String title, Frame owner, boolean b){
		super(owner, b);
		setTitle(title);
	}
	
	public MyJDialog(String title, Frame owner){
		super(owner);
		setTitle(title);
	}
	
	public void setLookAndFeel(){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		    SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
}
