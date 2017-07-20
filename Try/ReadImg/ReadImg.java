

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;



public class ReadImg extends Frame {
    Image[] shooter;      
 
    public static void main(String arg[]) {
        ReadImg start=new ReadImg();
    }
 
    public ReadImg() {
        super("ReadImg");
        setSize(350,350);
        setVisible(true);
 
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);       
 
        Toolkit tk=Toolkit.getDefaultToolkit();
        shooter=new Image[3];
        for(int i=1;i<4;i++) {
            shooter[i-1]=tk.getImage("./../PrintScreen/test.jpg");
            if(shooter[i-1]!=null) {
                System.out.println("get");
            }
	setBackground(shooter[1]);
        }
    }
}