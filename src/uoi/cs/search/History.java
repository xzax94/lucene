package uoi.cs.search;

import java.util.List;

public interface History<T>
{
	void addNewSearch(T search);
	
	T getLastSearch();
	
	List<T> findAll();
	
	T getDefaultSearch();
	
	boolean isEmpty();
}