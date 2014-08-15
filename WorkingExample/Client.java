//Benjamin Snively 026-676-122

import java.net.*;
import java.io.*;
import java.util.*;

public class Client{

    public static void main(String[] args) 
    {
	Socket clientSocket;

	try 
    {
        //Connect to server
    
	    clientSocket = new Socket( InetAddress.getByName( "localhost" ), 8000 );

	    System.out.println( "Connected to " +
		                    clientSocket.getInetAddress().getHostName() );

	    DataOutputStream dosToServer = new DataOutputStream(
                                                clientSocket.getOutputStream() );
	    DataInputStream  disFromServer= new DataInputStream(
                                                clientSocket.getInputStream() );

	    System.out.println( "I/O streams connected to the socket" );

        Scanner keyboard = new Scanner( System.in );
        
        System.out.println( "echo from the server: " + disFromServer.readUTF() );
        
        //begin client loop
        
        for(boolean done = false; !done; )
        {
            String response;
            String request;
			try 
            {
                System.out.print("console >> ");
                request = keyboard.nextLine();
                
                switch (request)
                {

                    case "area":
                        String func = "empty";

                        dosToServer.writeUTF(request);
                        System.out.println( "CHOOSE: " + disFromServer.readUTF() );
                        
                        //Decode response
                        for (boolean argsValid = false; !argsValid; )
                        {
                            try
                            {
                                System.out.print("console >> ");
                                func = keyboard.nextLine();

                                String[] arguments = func.split(" ");

                                if (arguments[0].equals("rectangle"))
                                {
                                    Double.parseDouble(arguments[1]);
                                    Double.parseDouble(arguments[2]);
                                }
                                else if (arguments[0].equals("circle"))
                                {
                                    Double.parseDouble(arguments[1]);
                                }
                                else
                                {
                                    throw new Exception();
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                System.out.println("Invalid Number of Parameters");
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Invalid Parameters");
                            }
                            catch (Exception e)
                            {
                                System.out.println("Invalid Function");
                            }
                            
                            dosToServer.writeUTF(func);
                            
                            response = disFromServer.readUTF();
                            System.out.println("echo from the server: " + response);
                            
                            if (response.equals("OK"))
                            {
                                System.out.print(disFromServer.readUTF());
                                System.out.println(disFromServer.readDouble());
                                argsValid = true;
                            }
                            else
                            {
                                System.out.println("Server Error: Try again");
                            }
                            
                        }

                        break;
                    case "thank you":
                        dosToServer.writeUTF("thank you");
                        done = true;
                        break;
                    default:
                        System.out.println("Bad Command.");
                        break;
                }
            }
            catch( EOFException eof ) 
            {
                System.out.println( "The server has terminated connection!" ); 
            }
            catch(IOException e ) 
            { 
                System.out.println( "I/O errors in data exchange" );
                e.printStackTrace();
            }

	    }
        dosToServer.close();
        disFromServer.close();
        clientSocket.close();
        
        System.out.println( "Client: data exchange completed" );

	    // Close connection
 	    System.out.println( "Client: closing the connection..." );


	}
    catch( IOException ioe ) 
    { 
        System.out.println( "I/O errors in socket connection" );
        ioe.printStackTrace(); 
    }


    System.out.println( "Client has stopped." );

    }
}
