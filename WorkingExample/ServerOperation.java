import java.net.*;
import java.io.*;
import java.util.Hashtable;

/**
*   ServerOperation is for creating interactive client server programs.  Each server operation
*   handles on type of request from a user.  For example, an operation may a shape and it's 
*   dimensions from the client and return it's area.
*
*   A server is created by nesting ServerOperation inside of one one another.  For example,
*   a menu operation takes requests for different functions from the user.  Nested inside of it
*   are ServerOperations that can handle different request.  When a request is revived the
*   menu operation passes control to it's held area operation to complete the request.
*
*   Each ServerOperation is designed to be self contained.  Thus, they have no access to the
*   state of the calling ServerOperations unless it is specifically passed to them.
*/
abstract class ServerOperation
{
    protected DBconnection DBconnect;

    private String operationName;
    
    private String startMessage;
    private String endMessage;
    
    //Operations that can be called by this ServerOperation
    protected Hashtable<String, ServerOperation> innerOperations;
    
    protected Request lastRequest;
    
    //true if the ServerOperation has finished it's current task.
    private boolean isTerminated;

    /**
    *   Takes and executes a request made by the user.
    */
    protected abstract void serveRequest() throws IOException;
    
    /**
    *   Reinitializes the operation when begin() is called.
    */
    protected abstract void startOperation() throws IOException;
    
    /**
    *   Handles clean-up after request is served.  Sends results to client.
    */
    protected abstract void endOperation() throws IOException;
    
    /**
    *   Begins this ServerOperation.  
    *
    *   The operation will first call beginOperation() to set up the operation and send
    *   any necessary messages to the client.  Then, it will send a request to the client
    *   and call serveRequest() to handle the clients request. This is repeated until
    *   the operation is finished.
    *
    *   If the operation has finished handling user requests, serveRequest calls terminate().
    *   endOperation() will be called after serveRequest() finishes.
    *   
    */
    public void begin() throws IOException
    {
        System.out.println("Started: " + operationName);
        isTerminated = false;
        startOperation();
        
        //loop until i
        for( ; !isTerminated; )
        {   
            lastRequest = DBconnect.getRequest();
            System.out.println("Request: " + lastRequest.toString());
            serveRequest();
        }
        
        endOperation();
        System.out.println("Ended: " + operationName);
        
        this.lastRequest = new Request();
        System.out.println(endMessage);
    }
    
    /**
    *   Adds new ServerOperations for this ServerOperation to call.
    *
    *   @param s A ServerOperation to nest.
    */
    protected void addInnerOperation(ServerOperation s)
    {
        innerOperations.put(s.operationName, s);
    }
    
    /**
    *   Calls a nested ServerOperation by name
    *
    *   @param name The name of the ServerOperation to call.
    *
    *   @return True if an operation was found, false otherwise.
    */
    protected boolean beginInnerOperation(String name)
        throws IOException
    {
        ServerOperation s = innerOperations.get(name);
        
        if (s != null)
        {
            s.begin();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
    *   Stops this operation at the end of serveRequest().
    */
    protected void terminate()
    {
        isTerminated = true;
    }
    
    /**
    *   Sends an error message to the client with the previous request attached.
    *   Displays this error.
    *
    *   @param msg A message for the client
    */
    protected void sendError(String msg)
        throws IOException
    {
        DBconnect.sendMessage("ERROR: " + msg + " " + lastRequest.toString());
        System.out.println("ERROR: " + msg + " " + lastRequest.toString());
    }
    
    /**
    *   Constructor for use by other ServerOperations - Sets messages.
    */
    protected ServerOperation(String startMessage, String endMessage, DBconnection db, String name)
    {
        this.operationName = name;
    
        this.startMessage = startMessage;
        this.endMessage = endMessage;
        this.lastRequest = new Request();
        
        this.innerOperations = new Hashtable<String, ServerOperation>();
        this.DBconnect = db;
        
        this.isTerminated = false;
    }
    
    /**
    *   Constructor for use by other classes.
    */
    public ServerOperation(DBconnection db, String name)
    {
        this("Beginning Operation", "Ending Operation", db, name);
    }
}
