package uoi.cs.search.strategy;

import java.util.Comparator;

import uoi.cs.movie.Movie;

public final class VoteAverageAscendingStrategy implements SortStrategy
{
	@Override
	public Comparator<Movie> getComparator() 
	{
		return Comparator.comparing(Movie::getVoteAverage);
	}
}