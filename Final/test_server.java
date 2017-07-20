import java.net.*;

import javax.swing.JPanel;

import java.io.*;
import java.awt.*;
import java.awt.event.*;


class Draw extends JPanel implements MouseListener, MouseMotionListener
{
   int x,y,x2,y2;
   int a[][];
   int b[][];
   int step;
   int step2;
   int  st;
  public Draw(){
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
    	if( test_server.froms[i][0] != 0 )
    		g.drawLine(test_server.froms[i][0]/1000,test_server.froms[i][0]%1000,test_server.froms[i][0]/1000,test_server.froms[i][0]%1000);
        for( int j = 1 ; j < test_server.froms[i].length ; j++ ){
	//System.out.print("bbb");
          if( (test_server.froms[i][j] > 0 ) )
            g.drawLine(test_server.froms[i][j]/1000,test_server.froms[i][j]%1000,test_server.froms[i][j-1]/1000,test_server.froms[i][j-1]%1000);
         }
     	}    

   // g.drawLine(x,y,x2,y2);
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
          test_server.ss.flag_send = 2;
	}
        repaint();
  }
  public void actionPerformed(ActionEvent e) {
   
  }
  public void mouseReleased(MouseEvent event){
      test_server.ss.flag_send = 2;
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
    
    test_server.ss.flag_send = 2;
    	repaint();
    }
    else
    	st--;
  }
  public void mouseMoved( MouseEvent event){
	
  }
  public void mouseReleased(){
	test_server.ss.flag_send = 2;
}
  
}//Draw end



class CServer_send extends Thread
{
   static int flag_send=0;
   public void run()
   {
      try
      {
         test_server.txa2.addKeyListener(new KeyLis());
         ServerSocket svs = new ServerSocket(2525);
         Socket s=svs.accept();
         test_server.txa1.append("Clinet connecting for sending successfully!!\n");
         System.out.println("Clinet connecting for sending successfully!!");
        
         System.out.println("Data transfering...");
         DataOutputStream out= new DataOutputStream(s.getOutputStream());
         String str;
         while(true)
         {
            if(flag_send==1)
            {
               out.writeInt(-1);
               str=test_server.txa2.getText();
               out.writeUTF(str);
               flag_send=0;
               System.out.print("Send:"+str);
               sleep((int)(100*Math.random())); 
            }
            if(flag_send == 2){
		out.writeInt(3);
            	test_server.d.geta(test_server.sends);
		test_server.step = test_server.d.getstep();
		 System.out.print("send step ="+test_server.step);
            	out.writeInt(test_server.step);
                for(int i = 0 ; i <= test_server.step ; i++ ){
                  for(int j =  0 ; j <test_server.sends[i].length; j ++)
			if(test_server.sends[i][j]!=0)
                   		 out.writeInt(test_server.sends[i][j]);
		out.writeInt(-999);
		}

                flag_send=0;                  
             }
           // sleep((int)(100*Math.random())); 
            sleep(100);
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
            test_server.txa1.append("Server: "+test_server.txa2.getText());
            test_server.txa2.setText("\r");
         } 
          
      }  
   }
}
class CServer_Recv extends Thread
{
   public void run()
   {
      byte buff[] = new byte[1024];
      int stf = -100;
      try
      {
         ServerSocket svs = new ServerSocket(2526);
        
         Socket s=svs.accept();
         test_server.txa1.append("Clinet connecting for receiving successfully!!\n");
         System.out.println("Clinet connecting for receiving successfully!!");
        
         DataInputStream in=new DataInputStream(s.getInputStream());
         int n;
         while(true)
         {
            stf = in.readInt();
            if(stf == -1){
              String tmp;
              tmp = in.readUTF();
              test_server.txa1.append("Client: "+ tmp );
              System.out.print("Received from client: ");
              System.out.println(tmp);
	      stf = 0;
            }  
            if(stf == 3 ){
              test_server.stepre = in.readInt(); 
	      System.out.println("stepre="+test_server.stepre );
              for(int i = 0 ; i <= test_server.stepre; i++)	
            	  for(int j = 0 ; j < 1000; j++){
			n=in.readInt();
			//System.out.println(n);
			if(n>=0)
                		test_server.froms[i][j] = n;
			else
				j = 1111;

							
		}
		//System.out.println("setb");
              test_server.d.setb(test_server.froms, test_server.stepre);
              for(int i = 0 ; i <= test_server.stepre; i++)	
            	  for(int j = 0 ; j < 1000; j++)
			if(test_server.froms[i][j]!=0)
			System.out.println(test_server.froms[i][j]);
	      stf = 0;
		test_server.d.repaint();
            }

           // sleep(100);   
            sleep((int)(100*Math.random())); 
         }
         //in.close();
         //s.close();
      }
      catch(Exception e)
      {
         System.out.println("Error:"+e);
      }
   }
}

public class test_server
{
   static Frame frm=new Frame("Chat room");
   static Button btn1=new Button("Start");
   static Button btn2=new Button("Exit");
   static Label lab1=new Label("Host IP Address");
   static Label title=new Label("Server");
   static TextArea txa1=new TextArea("",6,10,TextArea.SCROLLBARS_VERTICAL_ONLY);
   static TextArea txa2=new TextArea("",6,10,TextArea.SCROLLBARS_NONE);
   static TextField txf1=new TextField("127.0.0.1");
   static CServer_send ss=new CServer_send();
   static CServer_Recv sr=new CServer_Recv();
   static int froms[][];
   static int sends[][];
   static int step;
   static int stepre;
   static Draw d = new Draw();
  
   public static void main (String[] args)
   {
       froms = new int[10000][1000];
       sends = new int[10000][1000];	
     try
      {
    	 
         InetAddress adr=InetAddress.getLocalHost();
         txf1.setText(adr.getHostAddress());
         btn1.addActionListener(new ActLis());
         btn2.addActionListener(new ActLis());
         frm.addWindowListener(new WinLis());	 
         
         frm.setLayout(null);
         

         
         title.setBounds(20,40,75,40);
         btn1.setBounds(280,40,100,20);
         btn2.setBounds(280,65,100,20);
         frm.setBounds(100,100,800,600);
         frm.setBackground(new Color(151,255,255));
         lab1.setBounds(100,40,160,20);
         txa1.setBounds(20,95,360,440);
         

         d.setBounds(400,65,350,500);
         frm.add(d);
         
         txa2.setBounds(20,540,360,40);
         txf1.setBounds(100,65,160,20);
         txa1.setEditable(false);
         title.setFont(new Font("Serief",Font.BOLD+Font.ITALIC,18));
         title.setForeground(Color.BLUE);
         frm.add(title);
         frm.add(btn1);frm.add(btn2);
         frm.add(lab1);
         frm.add(txa1);frm.add(txa2);
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
            txa1.setText("Waiting for connecting("+txf1.getText()+")...\n");
            System.out.println("Waiting for connecting...");
            txf1.setEditable(false);
            ss.start();
            sr.start();
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