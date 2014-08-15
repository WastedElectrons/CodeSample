import java.net.*;
import java.io.*;
import java.util.Hashtable;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Server
{
    int portNumber;
    DBconnection dbconnection;

    public Server(int portNumber)
    {
        this.portNumber = portNumber;
    }
    
    public void run()
    {
        try
        {
            //Establish connection
            dbconnection = new DBconnection();
            ServerSocket serverSocket = new ServerSocket( portNumber );

            while (true)
            {
                System.out.println( "listening for a connection..." );
                dbconnection.connect(serverSocket.accept());

                System.out.println( "Connected to client" );
                
                try
                {
                    //Start interaction with client
                    BaseOperation base = new BaseOperation(dbconnection);
                    base.addInnerOperation(new AreaOperation(dbconnection));
                    
                    base.begin();
                }
                catch( EOFException eof )
                {  
                    System.out.println( "Connection terminated by client." );
                }
                catch(IOException e ) 
                {  
                    System.out.println( "I/O error in data exchange" );
                    e.printStackTrace();
                }

                System.out.println( "Closing connection..." );
                dbconnection.disconnect();
            }                
        }
        catch(IOException e )
        {
            System.out.println( "Socket connection failed - I/O error." );
            e.printStackTrace();
        }        
    }

    public static void main(String[] args) 
    {
        Server server = new Server(8000);
        server.run();
    }
}

