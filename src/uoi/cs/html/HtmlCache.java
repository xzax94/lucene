package uoi.cs.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlCache
{
	private final Map<Integer, String> _htmCache = new HashMap<>();
	
	protected HtmlCache()
	{
	}
	
	/**
	 * Loads and stores the HTM file content.
	 * @param file : The file to be cached.
	 * @return the content of the file under a {@link String}.
	 */
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
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns the HTM content given by filename. Test the cache first, then try to load the file if unsuccessful.
	 * @param path : The path to the HTM.
	 * @return the {@link String} content if filename exists, otherwise returns null.
	 */
	public String getHtm(String path)
	{
		if (path == null || path.isEmpty())
			return null;
		
		return loadFile(path);
	}
	
	/**
	 * Return content of html message given by filename. In case filename does not exist, returns notice.
	 * @param path : The path to the HTM.
	 * @return the {@link String} content if filename exists, otherwise returns formatted default message.
	 */
	public String getHtmForce(String path)
	{
		String content = getHtm(path);
		if (content == null)
		{
			content = "<html><body>My html is missing:<br>" + path + "</body></html>";
		}
		
		return content;
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