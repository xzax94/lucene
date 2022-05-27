package uoi.cs.search;

import java.util.List;

import uoi.cs.movie.Movie;

public final class MovieSearch extends Search<Movie>
{
	private final List<Movie> results;
	
	public MovieSearch(String searchText, List<Movie> results) 
	{
		super(searchText);
		
		this.results = results;
	}

	@Override
	public List<Movie> getResults() 
	{
		return results;
	}
}