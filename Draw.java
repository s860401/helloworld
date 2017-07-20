import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Draw extends JPanel implements MouseListener, MouseMotionListener
{
   private int x,y,x2,y2;
   private int a[][];
   private int step;
   private int st;
  public Draw(){
    x = 0;
    y = 0;
    x2 = 0;
    y2 = 0;
    step = 0;
    a = new int[10000][1000];
    addMouseListener(this);
    addMouseMotionListener(this);
    setBackground(Color.red);
  }
  public void paintComponent(Graphics g){
    Graphics2D g2 = (Graphics2D)g;
    g2.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
      for( int i = 1 ; i <= step ; i ++ )
        for( int j = 1 ; j < a[i].length ; j++ )
          if( a[i][j] != 0 )
            g.drawLine(a[i][j]/1000,a[i][j]%1000,a[i][j-1]/1000,a[i][j-1]%1000);

  //  System.out.println("***");
   // g.drawLine(x,y,x2,y2);
    
  }
  public void mousePressed(MouseEvent event){
  	  x = event.getX();
  	  y = event.getY();
	step ++;
	st = 0;
 
	a[step][st] += 1000*x;
	a[step][st] += y;
	if(x2!= 0 && y2 != 0){
	  x2 =x;
	  y2 =y;
	}
	//setStroke(new BasicStroke(4.0f, 	BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        repaint();
     }

  
  public void actionPerformed(ActionEvent e) {
   
  }
  public void mouseReleased(MouseEvent event){}
  public void mouseClicked(MouseEvent event){
    Graphics g = getGraphics();
    Graphics2D g2 = (Graphics2D)g;
    if(event.getButton()==MouseEvent.BUTTON1){
      if( ( (event.getX()>20) && (event.getX()<40) )&&( (event.getY()>20) && (event.getY()<40) ) )
        g.setColor(Color.BLACK);     
      else if( ((event.getX()>50)&&(event.getX()<80))&&((event.getY()>20)&&(event.getY()<40)) )
        g.setColor(Color.RED);
    }


  }
  public void mouseEntered(MouseEvent event){}
  public void mouseExited(MouseEvent event){}
  

  public void mouseDragged( MouseEvent event ){
if((x>=20)&&(y>=20)){
    x2 = x;
    y2 = y;
    x = event.getX();
    y = event.getY();
    st++;
    if(st == 999){
      step++;
      st= 0;
    }
    a[step][st] += 1000*x;
    a[step][st] += y;
    repaint();
    System.out.println("x,y:"+x+","+y+"x2,y2:"+x2+","+y2);
	
  /*    for( int i = 0 ; i < step ; i ++ )
        for( int j = 0 ; j < st-1 ; j++ )
   		 System.out.print(a[i][j]+",");
    */

  }

}
  public void mouseMoved( MouseEvent event){
	
  }
  public void mouseReleased(){
/*
	x=0;
	y=0;
	x2=0;
	y2=0;
*/
}
 public static void main(String args[])
  {
    Draw a = new Draw();
    JFrame f1 = new JFrame("Drawer");

    a.setBackground(Color.red);
   f1.setLayout(null);
    a.setBounds(20,20,800,600);
    f1.add(a); 
    //f1.setBackground(Color.green);
    f1.setSize(1200,800);
    
    f1.setVisible(true);
    f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    

  }
}