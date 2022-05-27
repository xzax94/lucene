package uoi.cs.reader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileContent
{
	private final Map<Integer, String> contentLines = new HashMap<>();
	
	public FileContent()
	{
	}
	
	public final void addLine(String line)
	{
		contentLines.put(contentLines.size(), line);
	}
	
	public final Collection<String> getContentInLines()
	{
		return contentLines.values();
	}
	
	public final String getContentInLine(int lineIndex)
	{
		return contentLines.get(lineIndex);
	}
}