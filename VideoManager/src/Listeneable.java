import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface Listeneable extends ActionListener{

    void actionPerformed(ActionEvent e);
}
