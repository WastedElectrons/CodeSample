import java.net.*;
import java.io.*;
import java.util.Hashtable;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
*   Implements the menu for a server.  Calls other operations at the users request.
*
*   @see ServerOperation
*/
class BaseOperation extends ServerOperation
{  
    private AreaOperation areaOperation;
    private DateFormat dateFormat;

    /**
    *   @param db The DBConnection for this server instance.
    */
    public BaseOperation(DBconnection db)
    {
        super("Established Connection", "Connection Ended", db, "Base");
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        this.areaOperation = null;
    }
    
    /**
    *   Sends a welcome message to the clien
    *
    *   @see ServerOperation
    */
    protected void startOperation()
        throws IOException
    {
        String message = "How my I server you? (" + dateFormat.format(new Date()) + ")";
        System.out.println( message );
        DBconnect.sendMessage( message );
    }
    
    /**
    *   Does nothing.
    *
    *   @see ServerOperation
    */
    protected void endOperation() { }
    
    /**
    *   Looks for a ServerOperation that matches a command from the client and runs it.
    *
    *   @see ServerOperation.
    */
    protected void serveRequest()
        throws IOException
    {
        if(!beginInnerOperation(lastRequest.command))
        {
            switch (lastRequest.command)
            {
                case "thank":
                    if (lastRequest.getArg(0).equals("you"))
                    {
                        System.out.println("THANK YOU RECEIVED!");
                        terminate();
                    }
                    else
                    {
                        sendError("invalid command");
                    }
                    break;
                default:
                    sendError("invalid command");
                    break;
            }
        }
    }
}