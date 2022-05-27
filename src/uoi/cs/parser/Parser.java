package uoi.cs.parser;

public interface Parser<T>
{
	T parseDocument(String path);
}