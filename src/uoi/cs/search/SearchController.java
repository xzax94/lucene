package uoi.cs.search;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import uoi.cs.html.HtmlCache;
import uoi.cs.movie.Movie;
import uoi.cs.page.PageList;

public final class SearchController implements Initializable
{
	private static final int MOVIES_PER_PAGE = 10;
	private final AtomicInteger pageIndex = new AtomicInteger(1);
	private final MovieSearchHistory history = new MovieSearchHistory();
	private final MovieSearcher movieSearcher = new MovieSearcher();
	
	private ContextMenu entriesPopup = new ContextMenu();
	
	@FXML
	private TextField textField;
	
	@FXML
	private Button previousButton;
	
	@FXML 
	private Button nextButton;
	
	@FXML
	private Label pageLabel;
	
	@FXML
	private ChoiceBox<String> choiceBox;
	
	@FXML
	private WebView webView;
	
	@FXML
	private final synchronized void onSearch()
	{
		pageIndex.set(1);
		final String searchText = textField.getText();
		final SearchType searchType = SearchType.findByName(choiceBox.getValue());
		final List<Movie> movies = movieSearcher.search(searchText, searchType);
		history.addNewSearch(new MovieSearch(searchText, movies));
		textField.setText("");
		updateTable();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) 
	{
		hideButton(previousButton);
		hideButton(nextButton);
		choiceBox.setItems(FXCollections.observableArrayList(List.of("All", "Title", "Overview")));
		choiceBox.setValue("All");
		
		textField.setOnKeyPressed(event -> {
			if (KeyCode.ENTER.equals(event.getCode()))
				onSearch();
		});
		
		textField.textProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) 
			{				
				final String searchText = textField.getText();
				
				if (isEmpty(searchText))
				{
					entriesPopup.hide();
					return;
				}
				final List<MovieSearch> searchHistory = history.findAll().stream().filter(search -> search.getSearchText().startsWith(searchText)).collect(Collectors.toList());
				Collections.sort(searchHistory, Comparator.comparing(Search::getCreateDate));
				
				final PageList<MovieSearch> pageList = PageList.createNewBuilder(history.findAll())
						.filter(search -> search.getSearchText().startsWith(searchText))
						.sorted(Comparator.comparing(Search::getCreateDate))
						.reverse()
						.limit(5)
						.build();
				
				final List<String> suggestions = pageList.getPage(1).stream().map(search -> search.getSearchText()).collect(Collectors.toList());
					
				if (!history.isEmpty())
				{
					populatePopup(suggestions);
					if (!entriesPopup.isShowing())
						entriesPopup.show(textField, Side.BOTTOM, 0, 0);
				} 
				else
					entriesPopup.hide();
			}
		});
		
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2)
			{
				entriesPopup.hide();
			}
		});
		
		updateTable();
	}
	
	private final boolean isEmpty(String string)
	{
		return string == null || string.length() == 0;
	}
	
	private void populatePopup(List<String> suggestions)
	{
		final List<CustomMenuItem> menuItems = new ArrayList<>();
		for (final String suggestion : suggestions)
		{
			final Label label = new Label(suggestion);
			final CustomMenuItem item = new CustomMenuItem(label);
			item.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent actionEvent) 
				{
					textField.setText(suggestion);
					entriesPopup.hide();
				}
			});
			
			menuItems.add(item);
		}
		
		entriesPopup.getItems().clear();
		entriesPopup.getItems().addAll(menuItems);
	}
	
	@FXML
	private final void displayPreviousPage()
	{
		pageIndex.decrementAndGet();
		
		updateTable();
	}
	
	@FXML
	private final void displayNextPage()
	{
		pageIndex.incrementAndGet();
		
		updateTable();
	}
	
	private final void updateTable()
	{
		final PageList<Movie> pageList = PageList.createNewBuilder(history.getLastSearch().getResults()).limit(MOVIES_PER_PAGE).build();
		final List<Movie> list = pageList.getPage(pageIndex.get());

		final StringBuilder builder = new StringBuilder();
		for (final Movie movie : list)
		{
			builder.append("<tr>");
			builder.append("<td width=150>" + movie.getTitle() + "</td>");
			builder.append("<td width=400>" + movie.getDescription() + "</td>");
			builder.append("<td width=100>" + movie.getLanguage() + "</td>");
			builder.append("<td width=100>" + movie.getVoteCount() + "</td>");
			builder.append("<td width=100>" + movie.getVoteAverage() + "</td>");
			builder.append("</tr>");
		}
	    
	    final boolean hasPreviousPage = pageIndex.get() > 1;
	    if (hasPreviousPage)
	    	showButton(previousButton);
	    else
	    	hideButton(previousButton);
	    
	    final boolean hasNextPage = pageIndex.get() < pageList.size();
	    if (hasNextPage)
	    	showButton(nextButton);
	    else
	    	hideButton(nextButton);
	    
	    if (pageList.size() > 0)
	    	pageLabel.setText("Page " + pageIndex.get() + "/" + pageList.size());
	    else
	    	pageLabel.setText("");
	   
	    final String content = HtmlCache.getInstance().getHtm("table.htm").replace("%movies%", builder.toString());
	    webView.getEngine().loadContent(content);
	}
	
	
	private final void showButton(Button button)
	{
		if (!button.isVisible())
			button.setVisible(true);
	}
	
	private final void hideButton(Button button)
	{
		if (button.isVisible())
			button.setVisible(false);
	}	
}