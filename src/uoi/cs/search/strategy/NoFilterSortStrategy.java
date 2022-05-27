package uoi.cs.search.strategy;

import java.util.Comparator;

import uoi.cs.movie.Movie;

public final class NoFilterSortStrategy implements SortStrategy
{
	@Override
	public Comparator<Movie> getComparator() 
	{
		return (movie1, movie2) -> 0;
	}
}