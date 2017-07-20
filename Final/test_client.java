import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.*;


class Drawc extends JPanel implements MouseListener, MouseMotionListener
{
   int x,y,x2,y2;
   int a[][];
   int b[][];
   int step;
   int step2;
   int  st;
  public Drawc(){
    x = 0;
    y = 0;
    x2 = 0;
    y2 = 0;
    step = 0;
    a = new int[10000][1000];
    b = new int[10000][1000];
    addMouseListener(this);
    addMouseMotionListener(this);
    
  }
  public int getstep(){
	return step;
	}
  public void geta(int x[][]) {
    for( int i = 0 ; i < step ; i ++ )
    	for( int j = 0 ; j <a[i].length ; j ++ )
    		x[i][j] = a[i][j];

}
public void setb(int x[][], int xs){
    for( int i = 0 ; i < xs ; i ++ )
    	for( int j = 0 ; j <x[i].length ; j ++ )
    		b[i][j] = x[i][j];
    step2 = xs;
	
}

  public void paintComponent(Graphics g){
	  super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
    g.setColor(Color.black);
      for( int i = 1 ; i <= step ; i ++ ){
    	if( a[i][0] != 0 )
    		g.drawLine(a[i][0]/1000,a[i][0]%1000,a[i][0]/1000,a[i][0]%1000);
        for( int j = 1 ; j < a[i].length ; j++ ){
          if( (a[i][j] > 0 )   )//&&(a[i-1][j-1]>0)
            g.drawLine(a[i][j]/1000,a[i][j]%1000,a[i][j-1]/1000,a[i][j-1]%1000);
         }
     	}
      for( int i = 1 ; i <= step2 ; i ++ ){
    	if( test_client.froms[i][0] != 0 )
    		g.drawLine(test_client.froms[i][0]/1000,test_client.froms[i][0]%1000,test_client.froms[i][0]/1000,test_client.froms[i][0]%1000);
        for( int j = 1 ; j < test_client.froms[i].length ; j++ ){
          if( (test_client.froms[i][j] > 0 ) )//&&(test_client.froms[i-1][j-1]>0)
            g.drawLine(test_client.froms[i][j]/1000,test_client.froms[i][j]%1000,test_client.froms[i][j-1]/1000,test_client.froms[i][j-1]%1000);
         }
     	}    
   // g.drawLine(x,y,x2,y2);

	//System.out.print("flag = "+test_client.cs.flag_send);
  }
  public void mousePressed(MouseEvent event){
	step ++;
	st = 0;
	System.out.print("step="+step);
  	  x = event.getX();
  	  y = event.getY();
	a[step][st] += 1000*x;
	a[step][st] += y;
	if(x2!= 0 && y2 != 0){
	  x2 =x;
	  y2 =y;
	}
      test_client.cs.flag_send = 2;
	//setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        repaint();
  }
  public void actionPerformed(ActionEvent e) {
  }
  public void mouseReleased(MouseEvent event){
      test_client.cs.flag_send = 2;
}

  public void mouseClicked(MouseEvent event){}
  public void mouseEntered(MouseEvent event){}
  public void mouseExited(MouseEvent event){}
  public void mouseDragged( MouseEvent event ){
    x2 = x;
    y2 = y;
    x = event.getX();
    y = event.getY();
    st++;
    if(st == 1000){
      step++;
      st= 0;
    }
    if( x>=0 && y>=0 ){
    	a[step][st] += 1000*x;
    	a[step][st] += y;
    	
   	 test_client.cs.flag_send = 2;	
    	repaint();
    }
    else
    	st--;
  }
  public void mouseMoved( MouseEvent event){
	
  }
  public void mouseReleased(){
    test_client.cs.flag_send = 2;
}
  
}//Draw end


class CClient_send extends Thread
{
   static int flag_send=0;
   public void run()
   {
      try
      {
         test_client.txa2.addKeyListener(new KeyLis());
                 
         Socket s = new Socket(test_client.txf1.getText(),2526);
         test_client.txa1.append("Connected with server for sending successfully!!\n");
         System.out.println("Connected with server for sending successfully!!");
        
         System.out.println("Data transfering...");
         DataOutputStream out= new DataOutputStream(s.getOutputStream());
         String str;
         while(true)
         {
            if(flag_send==1)
            {
              out.writeInt(-1);
              str=test_client.txa2.getText();
              out.writeUTF(str);
              flag_send=0;
		out.flush();
              System.out.print("Send:"+str);
              sleep((int)(100*Math.random())); 
            }
            if(flag_send == 2){
		out.writeInt(3);
              test_client.d.geta(test_client.sends);
		test_client.step=test_client.d.getstep();
        	System.out.println("step send = "+ test_client.step);


              out.writeInt(test_client.step);	



             for(int i = 0 ; i <= test_client.step ; i++ ){
         		for(int j =  0 ; j <test_client.sends[i].length; j ++)
				if(test_client.sends[i][j]!=0){
                		  out.writeInt(test_client.sends[i][j]);
				 //System.out.println(test_client.sends[i][j]);
				}
			out.writeInt(-999);
	      }
              flag_send=0;                  
            }
           sleep((int)(100*Math.random())); 
           // sleep(100); 
         }
         //in.close();
         //out.close();
         //s.close();
      }
      catch(Exception e)
      {
         System.out.println("Error:"+e);
      }
   }
   static class KeyLis extends KeyAdapter
   {
      public void keyPressed(KeyEvent e)
      {
         if(e.getKeyCode()==KeyEvent.VK_ENTER)
         { 
            flag_send=1;
         }
      }
      public void keyReleased(KeyEvent e)
      {  
         if(e.getKeyCode()==KeyEvent.VK_ENTER)
         { 
            test_client.txa1.append("Client: "+test_client.txa2.getText());
            test_client.txa2.setText("\r");
         } 
          
      }  
   }
}
class CClient_Recv extends Thread
{
   public void run()
   {
      int stf = -100;
      try
      {
         Socket s=new Socket(test_client.txf1.getText(),2525);
         test_client.txa1.append("Connected with server for receiving successfully!!\n");
         System.out.println("Connected with server for receiving successfully!!");
        
         DataInputStream in= new DataInputStream(s.getInputStream());
         int n;

         while(true)
         {
            stf=in.readInt();
            //System.out.println(stf);
            //System.out.println(" stf=" + stf);
            if(stf == -1){
              System.out.println(" stf=" + stf);
              String tmp;
              tmp = in.readUTF();
             
              test_client.txa1.append("Server: "+ tmp);
              System.out.print("Received from server: ");
              System.out.print(tmp);
		stf = 0;
            }
            if(stf == 3){
		System.out.println(" stf=" + stf);
                test_client.stepre =in.readInt() ;
		System.out.print("stepfrom server = "+ test_client.stepre);
 		for(int i = 0 ; i <= test_client.stepre; i++)	
            	 	 for(int j = 0 ; j < 1000; j++){
				n = in.readInt();
				if(n>=0)
                  			test_client.froms[i][j] = n;	
				else
					j = 1111;		
		}

		for(int i = 0 ; i <= test_client.stepre; i++)	
            	  for(int j = 0 ; j < 1000; j++)
			if(test_client.froms[i][j]!=0)
				System.out.println(test_client.froms[i][j]);

		stf =0;
                test_client.d.setb(test_client.froms, test_client.stepre);
		test_client.d.repaint();
            } 

            sleep((int)(100*Math.random())); 
         }
         //in.close();
         //s.close();
      }
      catch(Exception e)
      {
         System.out.println("Error:"+e);
	
	 test_client.txa1.append( " "+e);
      }
   }
}


public class test_client
{
   static Frame frm=new Frame("Chat room");
   static Button btn1=new Button("Connect");
   static Button btn2=new Button("Exit");
   static Label lab1=new Label("Server IP Address");
   static Label title=new Label("Client");
   static TextArea txa1=new TextArea("",6,10,TextArea.SCROLLBARS_VERTICAL_ONLY);
   static TextArea txa2=new TextArea("",6,10,TextArea.SCROLLBARS_NONE);
   static TextField txf1=new TextField("127.0.0.1");
   static CClient_Recv cr=new CClient_Recv();
   static CClient_send cs=new CClient_send();
   static Drawc d = new Drawc();
   static int froms[][];
   static int sends[][];
   static int step;
   static int stepre;
   public static void main (String[] args)
   {
     froms = new int[10000][1000];
     sends = new int[10000][1000];
     try
      {
         btn1.addActionListener(new ActLis());
         btn2.addActionListener(new ActLis());
         frm.addWindowListener(new WinLis());
         frm.setLayout(null);
         d.setBounds(400,65,350,500);

	     frm.add(d);
	 
         title.setBounds(20,40,75,40);
         btn1.setBounds(280,40,100,20);
         btn2.setBounds(280,65,100,20);
         frm.setBounds(450,150,800,600);//450,150,400,300
         frm.setBackground(new Color(151,255,255));
         lab1.setBounds(100,40,160,20);
         txa1.setBounds(20,95,360,440);
         txa2.setBounds(20,540,360,40);
         txf1.setBounds(100,65,160,20);
         txa1.setEditable(false);
         title.setFont(new Font("Serief",Font.BOLD+Font.ITALIC,18));
         title.setForeground(Color.BLUE);
         frm.add(title);
         frm.add(btn1);frm.add(btn2);
         frm.add(lab1);
         frm.add(txa1);
         frm.add(txa2);
         frm.add(txf1);
         frm.setVisible(true);
        
      }
      catch(Exception e)
      {
         System.out.println("Error:"+e);
      }
   }
  
   static class ActLis implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         Button btn=(Button) e.getSource();
         if(btn==btn1)
         {
            test_client.txa1.setText("Waiting for connecting with server("+txf1.getText()+")...\n");
            System.out.println("Waiting for connecting with server...");
            txf1.setEditable(false);
            cs.start();
            cr.start();
         }
         else if(btn==btn2)
            System.exit(0);
         else
            System.out.println("No Button Click!");
      }
   }
   static class WinLis extends WindowAdapter
   {
      public void windowClosing(WindowEvent e)
      {
         frm.dispose();
         System.exit(0);
      }
   }
}