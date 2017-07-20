import java.io.*;
improt java.net.*;
public class client{
  static String ip = "127.0.0.1";
  static int port = 2010;

  public static void main(String args[]){
    try{
      Socket s = new Socket(ip,port);
      FileInputStream fin = new FileInputStream("0.jpg")// 73*1024
      byte buffer[] = new byte[fin.available()];
      fin.read(buffer) // read into buffer
      DataOutputStream out = new DataOutputStream (new BufferedOutputStream(s.getOutputStream()));
      out.write(buffer);
    }    
    catch(IOException e ){
      System.out.println(e);
    }

  }


}