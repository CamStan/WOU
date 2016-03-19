
/**
 * Invalid Parameter Exception is thrown when a parameter is used that does not meet the accepted
 * requirements.
 * 
 * @author Cameron Stanavige
 * @version 5/26/2014
 */
public class InvalidParameterException extends Exception
{
    public InvalidParameterException(String details)
    {
        super(details);
    }
}
