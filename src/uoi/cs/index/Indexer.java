package uoi.cs.index;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;

public interface Indexer
{
	void indexDocuments(List<Document> documents, String path) throws IOException;
}