package uoi.cs.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class PageList<E>
{
	private final Map<Integer, List<E>> pages = new HashMap<>();
	private final int pageLimit;
	
	private PageList(PageBuilder<E> builder)
	{
		pageLimit = Math.max(1, builder.getPageLimit());
		
		int currentPageIndex = 1;
		List<E> pageList = new ArrayList<>();
		
		for (final E entry : builder.getEntries())
		{
			pageList.add(entry);
			
			pages.put(currentPageIndex, pageList);
			
			if (pageList.size() == pageLimit)
			{
				pageList = new ArrayList<>();
				currentPageIndex++;
			}
		}
	}
	
	public final List<E> getPage(int pageIndex)
	{
		return pages.getOrDefault(pageIndex, new ArrayList<>());
	}
	
	public final int size()
	{
		return pages.size();
	}
	
	public static <E> PageBuilder<E> createNewBuilder(Collection<E> entries)
	{
		return new PageBuilder<E>(entries);
	}
	
	public static class PageBuilder<E>
	{
		private int pageLimit;
		private final List<E> entries = new ArrayList<>();
		
		public PageBuilder(Collection<E> entries)
		{
			entries.forEach(this.entries::add);
		}
		
		public PageBuilder<E> limit(int pageLimit)
		{
			if (pageLimit <= 0)
				throw new IllegalArgumentException("Page limit should be a positive number.");
			
			this.pageLimit = pageLimit;
			return this;
		}
		
		public PageBuilder<E> filter(Predicate<E> filter)
		{
			Objects.requireNonNull(filter);
			
			entries.removeIf(e -> !filter.test(e));
			return this;
		}
		
		public PageBuilder<E> sorted(Comparator<E> comparator)
		{
			Objects.requireNonNull(comparator);
			
			entries.sort(comparator);
			return this;
		}
		
		public PageBuilder<E> reverse()
		{
			Collections.reverse(entries);
			return this;
		}
		
		public PageBuilder<E> shuffle()
		{
			Collections.shuffle(entries);
			return this;
		}
		
		public PageList<E> build()
		{
			return new PageList<E>(this);
		}
		
		public final int getPageLimit()
		{
			return pageLimit;
		}
		
		public final List<E> getEntries()
		{
			return entries;
		}
	}
}