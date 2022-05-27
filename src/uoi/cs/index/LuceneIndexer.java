package uoi.cs.index;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public final class LuceneIndexer implements Indexer
{
	@Override
	public void indexDocuments(List<Document> documents, String path) throws IOException 
	{
		final IndexWriter writer = createNewWriter(path);
			
		for (final Document document : documents)
			writer.addDocument(document);
			
		writer.commit();
		writer.close();
	}
	
	private final IndexWriter createNewWriter(String path) throws IOException
	{
		final Directory directory = FSDirectory.open(Paths.get(path));
		final IndexWriterConfig writerConfig = new IndexWriterConfig(new StandardAnalyzer());
		writerConfig.setOpenMode(OpenMode.CREATE);
		return new IndexWriter(directory, writerConfig);
	}
}