package uoi.cs.movie;

public final class Movie
{
	private final int id;
	private final String title;
	private final String description;
	private final String language;
	private final int voteCount;
	private final double voteAverage;
	
	public Movie(int id, String title, String overview, String language, int voteCount, double voteAverage)
	{
		this.id = id;
		this.title = title;
		this.description = overview;
		this.language = language;
		this.voteCount = voteCount;
		this.voteAverage = voteAverage;
	}

	public final int getId()
	{
		return id;
	}
	
	public final String getTitle()
	{
		return title;
	}
	
	public final String getDescription()
	{
		return description;
	}
	
	public final String getLanguage()
	{
		return language;
	}
	
	public final int getVoteCount()
	{
		return voteCount;
	}
	
	public final double getVoteAverage()
	{
		return voteAverage;
	}
}