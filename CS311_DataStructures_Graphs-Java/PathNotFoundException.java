/**
 * This exception indicates that when searching for a path in
 * a graph, that a path could not be found.  e.g. when searching
 * for a shortest path, this exception would indicate that no path
 * exists.
 */
public class PathNotFoundException extends Exception
{
	public PathNotFoundException()
	{
		super();
	}

	public PathNotFoundException( String message )
	{
		super(message);
	}
}