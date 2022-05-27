package uoi.cs.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import uoi.cs.reader.FileContent;
import uoi.cs.reader.Reader;

public final class LuceneDocumentParser implements Parser<List<Document>>
{
	private static final List<String> MOVIE_FIELDS = List.of("id", "title", "overview" , "original_language", "vote_count" , "vote_average");
	private final Reader reader;
	
	public LuceneDocumentParser(Reader reader)
	{
		this.reader = reader;
	}
	
	@Override
	public List<Document> parseDocument(String path) 
	{
		final FileContent fileContent = reader.readDocument(path);
		final List<Document> documents = new ArrayList<>();
		
		for(final String line : fileContent.getContentInLines())
		{
			final Document document = createNewDocument(line);
			documents.add(document);
		}
		
		return documents;
	}
	
	private final Document createNewDocument(String line)
	{
		final Document document = new Document();
		final List<String> tokens = parseTokens(line);
		for (int index = 0; index < MOVIE_FIELDS.size(); index++)
			document.add(new TextField(MOVIE_FIELDS.get(index), tokens.get(index), Field.Store.YES));
		
		return document;
	}
	
	private final List<String> parseTokens(String line) 
	{
		final List<String> tokens = new ArrayList<>();
		final StringBuilder builder = new StringBuilder();
		boolean inQuotes = false;
		char c;

		for (int i = 0; i < line.length(); i++) 
		{
			c = line.charAt(i);

			if (c == ',' && !inQuotes) 
			{
				tokens.add(builder.toString().trim());
				builder.setLength(0);
				continue;
			}

			if (c == '\"' && inQuotes)
			{	
				if (line.length() > (i + 1) && line.charAt(i + 1) == '\"') 
				{
					builder.append(line.charAt(i + 1));
					i++;
					continue;
				}
			}

			if (c == '\"') 
			{
				inQuotes = !inQuotes;
				continue;
			}
			
			if (Character.isWhitespace(c)) 
			{
				if (builder.length() > 0)
					builder.append(c);
			} 
			else 
				builder.append(c);
		}
		
		tokens.add(builder.toString().trim());
		
		return tokens;
	}	
}