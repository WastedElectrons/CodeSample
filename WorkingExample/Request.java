import java.util.Arrays;

/**
*   A simple request of a server containing a command and several arguments.
*/
public class Request
{
    public String command;
    public String[] args;
    
    /**
    *   Default constructor initializes all member variables to defaults.
    */
    public Request()
    {
        this.command = "NONE";
        this.args = new String[0];
    }

    /**
    *   Default constructor initializes all member variables.
    *
    *   @param request A request as a string.  Formatted as a command followed by zero
    *                  or more arguments.  Is delimited by " ".
    */
    public Request(String request)
    {
        String[] splitRequest = request.split(" ");
        
        command = splitRequest[0];
        args = (splitRequest.length > 1)
                ? Arrays.copyOfRange(splitRequest, 1, splitRequest.length) 
                : new String[0];
    }
    
    /**
    *   Gets the command from this Request.
    *
    *   @returns This requests command.
    */
    public String getCommand()
    {
        return command;
    }
    
    /**
    *   Gets the nth argument from this request.
    *
    *   @param n The index of the desired argument.
    *
    *   @returns Argument n of this Request, or "".
    */
    public String getArg(int n)
    {
        if (n < args.length && n >= 0)
        {
            return args[n];
        }
        else
        {
            return "";
        }
    }
    
    /**
    *   Gets the number of arguments this request has.
    *
    *   @returns The number of arguments this request has.
    */
    public int numArgs()
    {
        return args.length;
    }
    
    /**
    *   @returns A string representation of this Request. Format is command followed by
                 arguments.  Delimited by " ".
    */
    public String toString()
    {
        String ret = command;
        for (String s : args)
        {
            ret += " " + s;
        }
        
        return ret;
    }
}