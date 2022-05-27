package uoi.cs.search.strategy;

import uoi.cs.pattern.Factory;

public final class SortStrategyFactory implements Factory<SortStrategy, String>
{
	@Override
	public SortStrategy createNewInstance(String type) 
	{
		return switch(type)
				{
		case "Vote Count Asc" -> new VoteCountAscendingStrategy();
		case "Vote Count Desc" -> new VoteCountDescendingStrategy();
		case "Vote Average Asc"-> new VoteAverageAscendingStrategy();
		case "Vote Average Desc" -> new VoteAverageDescendingStrategy();
		default -> new NoFilterSortStrategy();
				};
	}
}