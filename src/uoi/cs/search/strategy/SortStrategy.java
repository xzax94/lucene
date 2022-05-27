package uoi.cs.search.strategy;

import java.util.Comparator;

import uoi.cs.movie.Movie;

public interface SortStrategy
{
	Comparator<Movie> getComparator();
}