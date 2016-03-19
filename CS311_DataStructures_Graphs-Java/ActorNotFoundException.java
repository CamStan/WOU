
/**
 * This exception indicates that when searching in the graph of movies
 * and actors, that an actor could not be found when searching for 
 * by name.
 */
public class ActorNotFoundException extends Exception
{
	public ActorNotFoundException()
	{
		super();
	}

	public ActorNotFoundException( String message )
	{
		super(message);
	}
}