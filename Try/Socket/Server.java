//package idv.steven.concurrency;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    public static final int LISTEN_PORT = 22;
    
    public void listenRequest()
    {
        ServerSocket serverSocket = null;
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        try
        {
            serverSocket = new ServerSocket( LISTEN_PORT );
            System.out.println("Server listening requests...");
            while ( true )
            {
                Socket socket = serverSocket.accept();
                threadExecutor.execute( new RequestThread( socket ) );
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( threadExecutor != null )
                threadExecutor.shutdown();
            if ( serverSocket != null )
                try
                {
                    serverSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args )
    {
        Server server = new Server();
        server.listenRequest();
    }
    
    /**
     * �B�zClient�ݪ�Request������C
     *
     * @version
     */
    class RequestThread implements Runnable
    {
        private Socket clientSocket;
        
        public RequestThread( Socket clientSocket )
        {
            this.clientSocket = clientSocket;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run()
        {
            System.out.printf("��%s�s�u�i��!\n", clientSocket.getRemoteSocketAddress() );
            DataInputStream input = null;
            DataOutputStream output = null;
            
            try
            {
                input = new DataInputStream( this.clientSocket.getInputStream() );
                output = new DataOutputStream( this.clientSocket.getOutputStream() );
                while ( true )
                {
		    System.out.print(input.readUTF() );
                    output.writeUTF( String.format("Hi, %s!\n", clientSocket.getRemoteSocketAddress() ) );
                    output.flush();
                    // TODO �B�zIO�A�o��w�qprotocol��w�I�I

                    break;
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            finally 
            {
                try
                {
                    if ( input != null )
                        input.close();
                    if ( output != null )
                        output.close();
                    if ( this.clientSocket != null && !this.clientSocket.isClosed() )
                        this.clientSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
    }
}