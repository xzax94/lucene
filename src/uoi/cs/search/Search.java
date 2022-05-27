package uoi.cs.search;

import java.util.Date;
import java.util.List;

public abstract class Search<T>
{
	private final String searchText;
	private final Date createDate;
	
	public Search(String searchText)
	{
		this.searchText = searchText;
		createDate = new Date(System.currentTimeMillis());
	}
	
	public final String getSearchText()
	{
		return searchText;
	}
	
	public final Date getCreateDate()
	{
		return createDate;
	}
	
	abstract List<T> getResults();
}