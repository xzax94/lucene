package uoi.cs.pattern;

public interface Factory<C, T>
{
	C createNewInstance(T type);
}