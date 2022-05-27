package uoi.cs.search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import uoi.cs.movie.Movie;

public final class MovieSearcher implements Searcher<Movie>
{
	private static final String INDEX_PATH = "index";
	
	@Override
	public List<Movie> search(String searchText, SearchType type)
	{
		final List<Movie> movies = new ArrayList<>();
		try
		{
		    final IndexSearcher searcher = createNewIndexSearcher();
			final QueryParserFactory parserFactory = new QueryParserFactory();
			final QueryParser queryParser = parserFactory.createNewInstance(type);
			final Query query = queryParser.parse(searchText);
			final TopDocs hits = searcher.search(query, Integer.MAX_VALUE);
	
		    final Formatter formatter = new SimpleHTMLFormatter();
		    final QueryScorer scorer = new QueryScorer(query);
		    final Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 10000);
		    final Highlighter highlighter = new Highlighter(formatter, scorer);
		    highlighter.setTextFragmenter(fragmenter);
		        
			for (final ScoreDoc scoreDoc : hits.scoreDocs)		
			{
				final Document document = searcher.doc(scoreDoc.doc);
				
				final TokenStream titleStream = TokenSources.getTokenStream("title", null, document.get("title"), queryParser.getAnalyzer(), 10000);
				final String title = document.get("title");
				final String[] titleFragments = highlighter.getBestFragments(titleStream, title, 10000);
				final String formattedTitle = arrayIsEmpty(titleFragments) ? document.get("title") : mergeStrings(titleFragments);
				
				final TokenStream overViewStream = TokenSources.getTokenStream("overview", null, document.get("overview"), queryParser.getAnalyzer(), 10000);
				final String overview = document.get("overview");
				final String[] overviewFragments = highlighter.getBestFragments(overViewStream, overview, 100);
				final String formattedOverview = arrayIsEmpty(overviewFragments) ? document.get("overview") :mergeStrings(overviewFragments);
				
				final Movie movie = new Movie(Integer.parseInt(document.get("id")), formattedTitle, formattedOverview, document.get("original_language"), Integer.parseInt(document.get("vote_count")), Double.parseDouble(document.get("vote_average")));
				movies.add(movie);
			}		     
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		
		return movies;
	}
	
	private final String mergeStrings(String ... strings)
	{	
		final StringBuilder builder = new StringBuilder();
		
		for (final String string : strings)
			builder.append(string);
		
		return builder.toString();
	}
	
	private final boolean arrayIsEmpty(String ... array)
	{
		return array == null || array.length == 0;
	}
	
	private final IndexSearcher createNewIndexSearcher() throws IOException 
	{
		final Directory dir = FSDirectory.open(Paths.get(INDEX_PATH));
	    final IndexReader reader = DirectoryReader.open(dir);
	    final IndexSearcher searcher = new IndexSearcher(reader);
	    return searcher;
	}
}