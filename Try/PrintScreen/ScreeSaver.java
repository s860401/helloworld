
import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;




public class ScreeSaver {
public ScreeSaver() {
}
static void save(String path) throws Exception {
Robot robot = new Robot();
Dimension d = new Dimension(800,600);
//Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
Rectangle rect = new Rectangle(0, 0, d.width, d.height);
BufferedImage image = robot.createScreenCapture(rect);
ImageIO.write(image, "jpg", new File(path));

}
public static void main(String[] args) {
try{
ScreeSaver.save("./../screecapture.jpg");
}catch(Exception e){
e.printStackTrace();
}
}
}