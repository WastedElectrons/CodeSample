import java.net.*;
import java.io.*;
import java.util.*;

/**
*   Wraps a Socket with commands to send and receive messages and requests.
*/
class DBconnection
{
    Socket socketConnection;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    
    /**
    *   Default constructor initializes all member variables to defaults.
    */
    public DBconnection()
    { }
    
    /**
    *   Sends a Request to the other machine.
    *
    *   @param r A Request to send.
    */
    public void sendRequest(Request r) 
        throws IOException
    {
        outputStream.writeUTF(r.toString());
        outputStream.flush();
    }
    
    /**
    *   Sends a Request to the other machine.
    *
    *   @param r A string representing a request.
    */
    public void sendRequest(String r)
        throws IOException
    {
        sendMessage(r);
    }
    
    /**
    *   Gets a Request from the other machine.
    *
    *   @returns A Request.
    */
    public Request getRequest()
        throws IOException
    {
        String msg = inputStream.readUTF();
        return new Request(msg);
    }
    
    /**
    *   Sends a message to the other machine.
    *
    *   @param msg A String message.
    */
    public void sendMessage(String msg)
        throws IOException
    {
        outputStream.writeUTF(msg);
        outputStream.flush();
    }
    
    /**
    *   Gets a message from the other machine.
    *
    *   @returns A String message.
    */
    public String getMessage()
        throws IOException
    {
        String msg = inputStream.readUTF();
        return msg;
    }
    
    /**
    *   Sends a double to the other machine.
    *
    *   @param d - A double to send.
    */
    public void sendDouble(Double d)
        throws IOException
    {
        outputStream.writeDouble(d);
        outputStream.flush();
    }
    
    /**
    *   Gets a double from the other machine.
    *
    *   @returns A double.
    */
    public double getDouble()
        throws IOException
    {
        Double d = inputStream.readDouble();
        return d;
    }
    
    /**
    *   Establishes a connection on a socket.
    *
    *   @param socket A Socket to listen on.
    */
    public void connect(Socket socket) throws IOException
    {
        socketConnection = socket;
        outputStream = new DataOutputStream( socketConnection.getOutputStream() );
        inputStream = new DataInputStream( socketConnection.getInputStream() );
    }
    
    /**
    *   Terminates current connection.
    */
    public void disconnect() throws IOException
    {
        outputStream.close();
        inputStream.close();
        socketConnection.close();
    }
}