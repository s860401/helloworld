import java.io.*;
import java.net.*;
import java.swing.*;
public class server extends JFrame{
  public static void main(String args[]){
    try{
      ServerSocket ss = new ServerSocktet(2010);
      Socket s = ss.acccept();
      System.out.println(s+ "is connecting");
      FileOutputStream fout = new FileOutputStream("s0.jpg");
      byte[] buffer = new byte[74752];
      DataInputStream din = 


    }



  }




}