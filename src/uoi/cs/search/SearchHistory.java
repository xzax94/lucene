package uoi.cs.search;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class SearchHistory<T> implements History<T>
{
	private final Map<Integer, T> history = new ConcurrentHashMap<>();

	@Override
	public void addNewSearch(T search) 
	{
		history.put(history.size(), search);
	}

	@Override
	public T getLastSearch() 
	{
		return history.getOrDefault(history.size() - 1, getDefaultSearch());
	}

	@Override
	public List<T> findAll() 
	{
		return history.values().stream().collect(Collectors.toList());
	}
	
	@Override 
	public final boolean isEmpty()
	{
		return history.isEmpty();
	}
}