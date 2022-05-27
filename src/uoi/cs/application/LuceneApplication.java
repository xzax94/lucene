package uoi.cs.application;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uoi.cs.index.Indexer;
import uoi.cs.index.LuceneIndexer;
import uoi.cs.parser.LuceneDocumentParser;
import uoi.cs.parser.Parser;
import uoi.cs.reader.CsvReader;

public final class LuceneApplication extends Application
{
	private static final String MOVIES_PATH = "movies.csv";
	private static final String INDEXING_PATH = "index";
	
	@Override
	public void start(Stage stage) throws IOException 
	{
		final Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/search.fxml"));
		stage.setTitle("Lucene");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}
	
	@Override
	public void init() throws IOException
	{
		final Parser<List<Document>> parser = new LuceneDocumentParser(new CsvReader());
		final List<Document> documents = parser.parseDocument(MOVIES_PATH);
		indexDocuments(documents, INDEXING_PATH);
	}
	
	private final void indexDocuments(List<Document> documents, String path) throws IOException
	{
		final Indexer indexer = new LuceneIndexer();
		indexer.indexDocuments(documents, path);
	}
}