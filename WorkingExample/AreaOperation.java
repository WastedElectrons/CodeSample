import java.net.*;
import java.io.*;
import java.util.Hashtable;

/**
*   An operation to calculate the area of a shape for a user.
*
*   @see ServerOperation
*/
class AreaOperation extends ServerOperation
{  
    private double result;
    
    /**
    *   Constructor
    *
    *   @param db The dbconnection for this server instance.
    */
    public AreaOperation(DBconnection db)
    {
        super("Clients request: area.", "Area function finished.", db, "area");
    }
    
    /**
    *   Sends a message to the client asking it to choose a shape.
    *
    *   @see ServerOperation.
    */
    protected void startOperation()
        throws IOException
    {
        DBconnect.sendMessage( "circle, rectangle" );
        
        result = 0.0;
    }

    /**
    *   Sends a confirmation message and the area to the client.
    *
    *   @see ServerOperation.
    */
    protected void endOperation()
        throws IOException
    {
        DBconnect.sendMessage("OK");
        DBconnect.sendMessage("the " + lastRequest + "'s area: ");
        DBconnect.sendDouble(result);
        
        result = 0.0;
    }

    /**
    *   Takes a command from the server as a shape and calculates it's area using the
    *   request's arguments.
    *
    *   @see ServerOperation
    */
    protected void serveRequest()
        throws IOException
    {
        try
        {  
            System.out.println(lastRequest.toString());
            
        
            if (lastRequest.numArgs() == 2 && lastRequest.command.equals("rectangle"))
            {
                double arg1 = Double.parseDouble(lastRequest.getArg(0));
                double arg2 = Double.parseDouble(lastRequest.getArg(1));
                result = arg1 * arg2;
                
                terminate();
            }
            else if (lastRequest.numArgs() == 1 && lastRequest.command.equals("circle"))
            {
                double arg1 = Double.parseDouble(lastRequest.getArg(0));
                result = arg1 * arg1 * Math.PI;
                
                terminate();
            }
            else
            {
                sendError("invalid command");
            }
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
        {
           sendError("invalid argument");
        }
    }
}