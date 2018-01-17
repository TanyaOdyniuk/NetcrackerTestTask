package com.netcracker.view;

import com.netcracker.entity.Book;
import com.netcracker.view.util.CustomRestTemplate;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@SpringUI(path = "/testtask")
public class MainUI extends UI {
    private Grid<Book> grid;
    private TextField searchAuthorTextField;
    private TextField searchPublishingHouseTextField;
    private Button searchButton;
    private HorizontalLayout searchLayout;
    private VerticalLayout mainLayout;
    @Autowired
    public MainUI(){
        mainLayout = new VerticalLayout();
        searchLayout = new HorizontalLayout();
        mainLayout.addComponent(searchLayout);
        grid = getGrid();
        getAllBooks();
        searchAuthorTextField = new TextField();
        searchAuthorTextField.setPlaceholder("Type author");
        searchPublishingHouseTextField = new TextField();
        searchPublishingHouseTextField.setPlaceholder("Type publishing house");
        searchPublishingHouseTextField.setWidthUndefined();
        searchButton = new Button("Search", VaadinIcons.SEARCH);
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getFilteredBooks();
            }
        });
    }
    private Grid<Book> getGrid() {
        Grid<Book> tempGrid = new Grid<>();
        tempGrid.setSizeFull();
        Grid.Column bookColumn = tempGrid.addColumn(book -> book.getBookInfo()).setCaption("Book Info").setSortable(false);
        Grid.Column authorColumn = tempGrid.addColumn(book -> book.getAuthor().getAuthorInfo()).setCaption("Author Info").setSortable(false);
        Grid.Column publishingHouseColumn = tempGrid.addColumn(book -> book.getPublishingHouse().getPublishingHouseInfo()).setCaption("Publishing house Info").setSortable(false);
        return tempGrid;
    }
    private void dropGrid(){
        mainLayout.removeComponent(grid);
    }
    private void addGrid(){
        if(mainLayout.getComponentCount() < 2){
            mainLayout.addComponent(grid);
        }
    }
    private void checkResponse(List<Book> books, String message){
        if(books.isEmpty()){
            dropGrid();
            Notification.show(message);
        } else{
            addGrid();
            grid.setItems(books);
        }
    }
    private void getAllBooks() {
        List<Book> books = Arrays.asList(
                CustomRestTemplate.getInstance().customGetForObject(
                        "/book", Book[].class));
        checkResponse(books, "You have not any books yet!");
    }
    private void getFilteredBooks(){
        List<Book> books = Arrays.asList(CustomRestTemplate.getInstance().customGetForObject(
                "/book/filter?authorInfo=" + searchAuthorTextField.getValue()
                        + "&publishingHouseInfo=" + searchPublishingHouseTextField.getValue(), Book[].class));
        checkResponse(books, "No books with that parameters were found!");
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        searchLayout.addComponent(searchAuthorTextField);
        searchLayout.addComponent(searchPublishingHouseTextField);
        searchLayout.addComponent(searchButton);
        //mainLayout.addComponent(searchLayout);
        mainLayout.addComponent(grid);
        setContent(mainLayout);
    }
}
