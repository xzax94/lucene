package uoi.cs.html;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlCache
{
	private final Map<Integer, String> cache = new HashMap<>();
	
	private HtmlCache()
	{
	}
	
	public String getHtm(String path)
	{
		if (path == null || path.isEmpty())
			return null;
		
		String content = cache.get(path.hashCode());
		if (content == null)
			content = loadFile(path);
		
		return content;
	}

	private String loadFile(String path)
	{
		try
		{
			final StringBuilder builder = new StringBuilder();
			final BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			
			while ((line = reader.readLine()) != null)
				builder.append(line);
			
			reader.close();
			return builder.toString();
		}
		catch (final IOException e)
		{
			return null;
		}
	}

	public static HtmlCache getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final HtmlCache INSTANCE = new HtmlCache();
	}
}