import java.io.*;
import java.util.*;
import javax.swing.*;
public class tryy{
  private int a;
  private Scanner s ;
  public tryy( ) {
   a = 999;
   s = new Scanner(System.in);
}

  public void pri(){
    System.out.println("a = " + a);
}
  public void seta(){
    String x;
    a = s.nextInt();
    x = JOptionPane.showInputDialog( "a has been set successfully" );
    a = Integer.parseInt(x);
    JOptionPane.showMessageDialog(null, "a has been set successfully")	;

}
  public int geta(){
	return a;
}
  public static int mm(int a, int b){
    return a*b;

}
  public static void main(String args[]) 
  {
    int x = 100;
    tryy q = new tryy();
    System.out.print(mm(x,q.a));
    q.seta();
    q.pri();
    //System.out.print(args);

  }

} //end of class tryy()
