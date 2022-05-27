package uoi.cs.search;

import java.util.Collections;

public final class MovieSearchHistory extends SearchHistory<MovieSearch>
{
	private static final MovieSearch DEFAULT_SEARCH = new MovieSearch(null, Collections.emptyList());

	@Override
	public MovieSearch getDefaultSearch() 
	{
		return DEFAULT_SEARCH;
	}
	
	public final MovieSearch findBySearchText(String searchText)
	{
		return findAll().stream().filter(search -> search.getSearchText().equals(searchText)).findAny().orElse(null);
	}
}