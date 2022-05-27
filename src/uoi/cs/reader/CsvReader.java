package uoi.cs.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class CsvReader implements Reader
{
	@Override
	public FileContent readDocument(String path) 
	{
		try
		{
			final FileContent fileContent = new FileContent();
			final BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			
			while ((line = reader.readLine()) != null)
				fileContent.addLine(line);
			
			reader.close();
			return fileContent;
		}
		catch (final IOException e)
		{
			return null;
		}
	}
}