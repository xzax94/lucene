package uoi.cs.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;

import uoi.cs.pattern.Factory;

public final class QueryParserFactory implements Factory<QueryParser, SearchType>
{

	@Override
	public QueryParser createNewInstance(SearchType type) 
	{
		return switch (type)
				{
				case ALL -> new MultiFieldQueryParser( new String[] {"title", "overview"},  new StandardAnalyzer());
				case NAME -> new QueryParser("title", new StandardAnalyzer());
				case DESCRIPTION -> new QueryParser("overview", new StandardAnalyzer());
				};
	}
	
}