package uoi.cs.search;

import java.util.List;

public interface Searcher<T>
{
	List<T> search(String searchText, SearchType type);
}