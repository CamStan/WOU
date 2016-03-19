/**
 * A class representing a pairing of Actors, which includes their names and the
 * number of movies they have acted in together.
 * 
 * TODO: You must correctly implement the 4 empty methods below.
 */
public class Colleagues implements Comparable<Colleagues> {
	public String actor1;
	public String actor2;
	public int numMoviesShared;

	public Colleagues(String a1, String a2, int n) {
		actor1 = a1;
		actor2 = a2;
		numMoviesShared = n;
	}

	public int compareTo(Colleagues o) {
		if (this.numMoviesShared < o.numMoviesShared)
			return 1;
		else if (this.numMoviesShared > o.numMoviesShared)
			return -1;
		else
			return 0;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Colleagues))
			return false;
		else {
			Colleagues c = (Colleagues) o;
			if (this.actor1 == c.actor1 && this.actor2 == c.actor2) {
				return true;
			} else if (this.actor1 == c.actor2 && this.actor2 == c.actor1) {
				return true;
			} else
				return false;
		}
	}

	public int hashCode() {
		int newHash = 17;
		newHash = 37 * newHash + (actor1.hashCode() + actor2.hashCode());
		return newHash;
	}

	public String toString() {
		return "Actor1: " + actor1 + "\r\nActor2: " + actor2 + "\r\n#Movies: "
				+ numMoviesShared;
	}
}
