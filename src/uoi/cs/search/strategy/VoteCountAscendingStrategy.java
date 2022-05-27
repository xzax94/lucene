package uoi.cs.search.strategy;

import java.util.Comparator;

import uoi.cs.movie.Movie;

public final class VoteCountAscendingStrategy implements SortStrategy
{

	@Override
	public Comparator<Movie> getComparator() 
	{
		return Comparator.comparing(Movie::getVoteCount);
	}
	
}