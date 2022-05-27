package uoi.cs.search;

import java.util.stream.Stream;

public enum SearchType
{
	ALL("All"),
	NAME("Title"),
	DESCRIPTION("Overview");
	
	private final String name;
	
	SearchType(String name)
	{
		this.name = name;
	}
	
	public final String getName()
	{
		return name;
	}
	
	public static SearchType findByName(String name)
	{
		return Stream.of(values()).filter(v -> v.getName().equalsIgnoreCase(name)).findAny().orElse(ALL);
	}
}