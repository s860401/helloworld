import java.awt.*;
import javax.swing.*;

public class tx extends JPanel{
  private int a = 0;

  public tx(){
	a = 1;
}
  public void paintComponent( Graphics g ){
	super.paintComponent( g );
	g.drawLine(20,40,200,400);
        System.out.print("asdas");
}
}