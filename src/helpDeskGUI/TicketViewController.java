
/*
 * Author: Bradley Cundari
 * Date: 2016
 * Open Copyright
 */

package helpDeskGUI;


import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Brad Cundari
 */
public class TicketViewController implements Initializable
{

	// Instantiation of all GUI objects
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TableView<Ticket> ticketTable;
        @FXML
	private TableColumn<Ticket, String> employeeColumn;
	@FXML
	private TableColumn<Ticket, Integer> ticketNumberColumn;
	@FXML
	private TableColumn<Ticket, String> lastNameColumn;
	@FXML
	private TableColumn<Ticket, LocalDate> dateRequestedColumn;
	@FXML
	private TableColumn<Ticket, String> firstNameColumn;
	@FXML
	private TableColumn<Ticket, String> assignedToColumn;
	@FXML
	private TableColumn<Ticket, String> descriptionColumn;
	@FXML
	private TextField filterField;
	@FXML
	private Button clearButton;

	// Instance of the class Main
	private Main mainApp;
        private static double xOffset = 0;
        private static double yOffset = 0;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */

	public void setMainApp(Main mainApp)
	{ // Begin Method
		// Reference itself
		this.mainApp = mainApp;

		// Add observable list data to the table
		ticketTable.setItems(mainApp.getTicketData());
		ticketNumberColumn.setStyle( "-fx-alignment: CENTER;");
                
                
                
                
                this.setFiltering();
	} // End Method

	// Event Listener on BorderPane.onMouseDragged
	@FXML
	public void onMenuMouseDragged(MouseEvent event)
	{
	Stage stage = (Stage)anchorPane.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
	}

	// Event Listener on BorderPane.onMousePressed
	@FXML
	public void onMenuMousePressed(MouseEvent event)
	{
		System.out.println("Pressed");
	Stage stage = (Stage)anchorPane.getScene().getWindow();
	xOffset = stage.getX() - event.getScreenX();
	yOffset = stage.getY() - event.getScreenY();
	}

	@FXML
	private void closeMenuItemAction(ActionEvent event)
	{ // End Method
		System.exit(0); // Close the application
	} // Begin Method

	@FXML
	private void handleClearButtonAction(ActionEvent event)
	{ // Begin Method
		filterField.clear(); // Clear the text from the TextField
	} // End Method

	private void setFiltering()
	{ // Begin Method
		FilteredList<Ticket> filteredData = new FilteredList<>(mainApp.getTicketData(), p -> true);
		// Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(ticket -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty())
				{
					return true;
				}

				// Compare first name and last name of every person
				// with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (ticket.getFirstName().toLowerCase().contains(lowerCaseFilter))
				{
					return true; // Filter matches first name.
				} else if (ticket.getLastName().toLowerCase().contains(lowerCaseFilter))
				{
					return true; // Filter matches last name.
				} else if (ticket.getDescription().toLowerCase().contains(lowerCaseFilter))
				{
					return true; // Filter matches the description
				}
                                 else if (ticket.getAssignedTo().toLowerCase().contains(lowerCaseFilter))
				{
					return true; // Filter matches last name.
				}                          
                                 else if (ticket.getTicketString().toLowerCase().contains(lowerCaseFilter))
				{
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});
		// FilteredList in a SortedList to allow for sorting.
		SortedList<Ticket> sortedData = new SortedList<>(filteredData);

		// Bind the SortedList comparator to the TableView
		sortedData.comparatorProperty().bind(ticketTable.comparatorProperty());

		// Add sorted (and filtered) data to the table.
		ticketTable.setItems(sortedData);
	} // End Method

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{ // Begin method
		// Initialize the 4 ticket table columns.
		ticketNumberColumn.setCellValueFactory(cellData -> cellData.getValue().ticketNumberProperty().asObject());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		dateRequestedColumn.setCellValueFactory(cellData -> cellData.getValue().dateRequestedProperty());
		assignedToColumn.setCellValueFactory(cellData -> cellData.getValue().assignedToProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
                //align center
      		ticketNumberColumn.setStyle( "-fx-alignment: CENTER;");
		firstNameColumn.setStyle( "-fx-alignment: CENTER;");
		lastNameColumn.setStyle( "-fx-alignment: CENTER;");
		dateRequestedColumn.setStyle( "-fx-alignment: CENTER;");
		assignedToColumn.setStyle( "-fx-alignment: CENTER;");
     
        } // End method

	@FXML
	private void clearButtonMouseExited(MouseEvent event)
	{ // Begin method
		// Removes all effects from the Clear button
		clearButton.setEffect(null);
	} // End method

	@FXML
	private void clearButtonMouseEntered(MouseEvent event)
	{ // Begin Method
		// A black drop shadow 5 pixels wide
		DropShadow buttonShadow = new DropShadow(5, Color.BLACK);
		// Apply the drop shadow to the Clear button
		clearButton.setEffect(buttonShadow);
	} // End Method

	@FXML
	private void deleteClicked(javafx.scene.input.MouseEvent event)
	{ // Begin Method
		ObservableList<Ticket> originalList = mainApp.getTicketData();
		Ticket selectedItem = ticketTable.getSelectionModel().getSelectedItem();
		originalList.remove(selectedItem);
	} // End Method

	@FXML
	private void updateClicked(javafx.scene.input.MouseEvent event)
	{ // Begin Method

		ObservableList<Ticket> originalList = mainApp.getTicketData();
		Ticket previousItem = ticketTable.getSelectionModel().getSelectedItem();
                
                //listener to force a numeric value
                ChangeListener<String> forceNumberListener = (observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*"))
                        ((StringProperty) observable).set(oldValue);
                    };
                
                
		// form a dialogue box

		Dialog<ButtonType> dialog = new Dialog();
		dialog.setTitle("Update Ticket");
		dialog.setHeaderText("Edit the contents of this ticket: ");
		dialog.setResizable(false);

		ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

		Label ticketNumberLabel = new Label("Ticket Number: ");
		Label firstNameLabel = new Label("First Name: ");
		Label lastNameLabel = new Label("Last Name: ");
		Label dateRequestedLabel = new Label("Date Requested: ");
		Label employeeAssignedLabel = new Label("Employee Assigned: ");
		Label descriptionLabel = new Label("Description: ");

		TextField ticketNumberField = new TextField(String.valueOf(previousItem.getTicketNumber()));
                ticketNumberField.textProperty().addListener(forceNumberListener);
                TextField firstNameTextField = new TextField(previousItem.getFirstName());
		TextField lastNameTextField = new TextField(previousItem.getLastName());
                DatePicker dateRequested = new DatePicker();
                dateRequested.setValue(previousItem.getDateRequested().getValue());        
		TextField employeeAssignedTextField = new TextField(previousItem.getAssignedTo());
		TextField descriptionTextField = new TextField(previousItem.getDescription());

		// making the description field bigger so its easy to use
		descriptionTextField.setPrefWidth(160);
		descriptionTextField.setPrefHeight(80);

		GridPane grid = new GridPane();
		grid.add(ticketNumberLabel, 1, 1);
		grid.add(ticketNumberField, 2, 1);

		grid.add(firstNameLabel, 1, 2);
		grid.add(firstNameTextField, 2, 2);

		grid.add(lastNameLabel, 1, 3);
		grid.add(lastNameTextField, 2, 3);

		grid.add(dateRequestedLabel, 1, 4);
                grid.add(dateRequested, 2, 4);
                
		grid.add(employeeAssignedLabel, 1, 6);
		grid.add(employeeAssignedTextField, 2, 6);

		grid.add(descriptionLabel, 1, 7);
		grid.add(descriptionTextField, 2, 7);

		dialog.getDialogPane().setContent(grid);
                
                //Control for unlocking the confirm button
                dialog.getDialogPane().lookupButton(confirmButtonType).disableProperty().bind(
                ticketNumberField.textProperty().isEmpty()
                .or(firstNameTextField.textProperty().isEmpty())
                .or(lastNameTextField.textProperty().isEmpty())
                .or(employeeAssignedTextField.textProperty().isEmpty())
                .or(descriptionTextField.textProperty().isEmpty())     
                );

                
                
		Optional<ButtonType> result = dialog.showAndWait();


		if (result.isPresent() && result.get().getText().equals("Confirm"))
		{

			// string mutations to match constructor
			int ticketNumber = Integer.parseInt(ticketNumberField.getText());
                        LocalDate newDate = dateRequested.getValue();
                        
	
			Ticket newTicket = new Ticket(ticketNumber, firstNameTextField.getText(), lastNameTextField.getText(), newDate,
					employeeAssignedTextField.getText(), descriptionTextField.getText(), "");

			originalList.remove(previousItem);
			originalList.add(newTicket);
		}

	} // End method

	@FXML
	private void addClicked(javafx.scene.input.MouseEvent event)
	{
		ObservableList<Ticket> originalList = mainApp.getTicketData();
                
                //Listener to force a numeric input
                ChangeListener<String> forceNumberListener = (observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*"))
                        ((StringProperty) observable).set(oldValue);
                    };


		// form a dialogue box
                Dialog<ButtonType> dialog = new Dialog();
		dialog.setTitle("Add a new ticket");
		dialog.setHeaderText("Please enter the contents of the new ticket:");
		dialog.setResizable(false);

		ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

		Label ticketNumberLabel = new Label("Ticket Number: ");
		Label firstNameLabel = new Label("First Name: ");
		Label lastNameLabel = new Label("Last Name: ");
		Label dateRequestedLabel = new Label("Date Requested : ");
		Label employeeAssignedLabel = new Label("Employee Assigned: ");
		Label descriptionLabel = new Label("Description: ");

                
                //initialize textfields and apply listeners
		TextField ticketNumberField = new TextField();
		ticketNumberField.textProperty().addListener(forceNumberListener);
                TextField firstNameField = new TextField();
		TextField lastNameField = new TextField();
		TextField dateRequestedField = new TextField();
		TextField employeeAssignedField = new TextField();
		TextField descriptionField = new TextField();
                DatePicker dateRequested = new DatePicker();
               
                
                
                //repeated logic that can be reduced via a new method
		GridPane grid = new GridPane();
		grid.add(ticketNumberLabel, 1, 1);
		grid.add(ticketNumberField, 2, 1);
		grid.add(firstNameLabel, 1, 2);
		grid.add(firstNameField, 2, 2);
		grid.add(lastNameLabel, 1, 3);
		grid.add(lastNameField, 2, 3);
		grid.add(dateRequestedLabel, 1, 4);
		grid.add(dateRequested, 2, 4);
		grid.add(employeeAssignedLabel, 1, 6);
		grid.add(employeeAssignedField, 2, 6);
		grid.add(descriptionLabel, 1, 7);
		grid.add(descriptionField, 2, 7);


                
                
		dialog.getDialogPane().setContent(grid);
                dialog.getDialogPane().lookupButton(confirmButtonType).disableProperty().bind(
                ticketNumberField.textProperty().isEmpty()
                .or(firstNameField.textProperty().isEmpty())
                .or(lastNameField.textProperty().isEmpty())
                .or(employeeAssignedField.textProperty().isEmpty())
                .or(descriptionField.textProperty().isEmpty())     
                );

                
                
                
                
		Optional<ButtonType> result = dialog.showAndWait();
                System.out.println(result.get());
		if (result.isPresent() && result.get().getText().equals("Confirm"))
		{
			System.out.println(result.get());
			int ticketNumber = Integer.parseInt(ticketNumberField.getText());
			LocalDate newDate = dateRequested.getValue();
                            
			
			Ticket newTicket = new Ticket(ticketNumber, firstNameField.getText(), lastNameField.getText(), newDate,
					employeeAssignedField.getText(), descriptionField.getText(), "");

			originalList.add(newTicket);
		}
                 System.out.println(result.get() == ButtonType.OK) ;
	}
        
        

        
        
        
        
        
        
        
        
        
        
        
        
}
