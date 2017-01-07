

/*
 * Author: Bradley Cundari
 * Date: 2016
 * Open Copyright
 */

package helpDeskGUI;


//JavaFX imports a ton of unnecessary depenedencies
import helpDeskGUI.Ticket;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


/**
 *
 * @author Brad Cundari
 */
public class TicketViewController implements Initializable {
    
    
    //Instantiation of all GUI objects
    @FXML
    private TableView<Ticket> employeeTable;
    @FXML   
    private TableColumn<Ticket, String>  employeeColumn;   
    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, Integer>  statusColumn;
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
    private Label label;
    
    //Instance of the class Main
    private Main mainApp;

    
    private void handleButtonAction(ActionEvent event) 
    { //Begin Method
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    } // End Method
    
    
    /**
    * Is called by the main application to give a reference
    * back to itself.
     		* 
     		* @param mainApp
    */
    
    public void setMainApp(Main mainApp) 
    { //Begin Method
       //Reference itself
       this.mainApp = mainApp;

       // Add observable list data to the table
       ticketTable.setItems(mainApp.getTicketData());
       employeeTable.setItems(mainApp.getEmployeeData());
       this.setFiltering(); 
    } // End Method

    @FXML
    private void closeMenuItemAction(ActionEvent event)
    { //End Method
        System.exit(0);  // Close the application
    } //Begin Method
    
    @FXML
    private void handleClearButtonAction(ActionEvent event)
    { //Begin Method
        filterField.clear();  // Clear the text from the TextField
    } // End Method
    
    private void setFiltering() 
    { // Begin Method
    // Wrap the ObservableList in a FilteredList. The p -> true
    // is a predicate used to filter the data.   
    // In this case, a simple “true” means “show all data”.
    FilteredList<Ticket> filteredData = new FilteredList<>(mainApp.getTicketData(), p -> true);
    // Set the filter Predicate whenever the filter changes.
    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
    filteredData.setPredicate(ticket -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person 
		// with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (ticket.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (ticket.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

    // FilteredLists cannot be modified. Therefore, wrap the   
    // FilteredList in a SortedList to allow for sorting. 
    SortedList<Ticket> sortedData = new SortedList<>(filteredData);

    // Bind the SortedList comparator to the TableView 
    // comparator.
    sortedData.comparatorProperty().bind(ticketTable.comparatorProperty());

    // Add sorted (and filtered) data to the table.
    ticketTable.setItems(sortedData);
    } // End Method

    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    { //Begin method
        
    // Initialize the 4 ticket table columns.
    statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asObject());
    firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    dateRequestedColumn.setCellValueFactory(cellData -> cellData.getValue().dateRequestedProperty());
    assignedToColumn.setCellValueFactory(cellData -> cellData.getValue().assignedToProperty());
    descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    employeeColumn.setCellValueFactory(cellData -> cellData.getValue().assignedToProperty());
    } //End method   
    
    
    
    private void clearButtonMouseExited(MouseEvent event)
    { //Begin method
        // Removes all effects from the Clear button 
        clearButton.setEffect(null);
    } //End method

    private void clearButtonMouseEntered(MouseEvent event)
    { // Begin Method
        // Define a black drop shadow 5 pixels wide
        DropShadow buttonShadow = new DropShadow(5,Color.BLACK);
        // Apply the drop shadow to the Clear button
        clearButton.setEffect(buttonShadow);
    } //End Method

    @FXML
    private void clearButtonMouseExited(javafx.scene.input.MouseEvent event) 
    {
        //Don't need this but may become useful
    }

  
    @FXML
    private void clearButtonMouseEntered(javafx.scene.input.MouseEvent event) 
    {
        //Don't need this but may become useful
    } 

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
    String fullName = "";
    //form a dialogue box
   

    Dialog<Ticket> dialog = new Dialog();
    dialog.setTitle("Update Ticket");
    dialog.setHeaderText("Edit the contents of this ticket: ");
    dialog.setResizable(true);
    
    ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
 
   
    Label statusLabel = new Label("Status: ");
    Label firstNameLabel = new Label("First Name: ");
    Label lastNameLabel = new Label("Last Name: ");
    Label dateRequestedLabel = new Label("Date Requested (YYYY/MM/DD): ");   
    Label employeeAssignedLabel = new Label("Employee Assigned: ");
    Label descriptionLabel = new Label("Description: ");
   
   
    TextField statusTextField = new TextField(String.valueOf(previousItem.getStatus()));
    TextField firstNameTextField = new TextField(previousItem.getFirstName());
    TextField lastNameTextField = new TextField(previousItem.getLastName());
    //assuming that the previous ticket has a correct localdate
    TextField dateRequestedTextField = new TextField(previousItem.getDateRequested().toString().substring(23,33));
    TextField employeeAssignedTextField = new TextField(previousItem.getAssignedTo());
    TextField descriptionTextField = new TextField(previousItem.getDescription());
 
    GridPane grid = new GridPane();
    grid.add(statusLabel, 1, 1);
    grid.add(statusTextField, 2, 1);
    
    grid.add(firstNameLabel, 1, 2);
    grid.add(firstNameTextField, 2, 2);
    
    grid.add(lastNameLabel, 1, 3);
    grid.add(lastNameTextField, 2, 3);
    
    grid.add(dateRequestedLabel, 1, 4);
    grid.add(dateRequestedTextField, 2, 4);
    
    
    grid.add(employeeAssignedLabel, 1, 6);
    grid.add(employeeAssignedTextField, 2, 6);
    
    grid.add(descriptionLabel, 1, 7);
    grid.add(descriptionTextField, 2, 7);
        
    dialog.getDialogPane().setContent(grid);
    
    Optional<Ticket> result = dialog.showAndWait();

    /*
     This first control block will create a ticket
     if it is EXACTLY the same as the standard
     ticket object. Error handling will put empty objects were no response is present.
    */ 
    if (result.isPresent()) {
        
    //string mutations to match constructor
    int status = Integer.parseInt(statusTextField.getText());
    String placeHolder = dateRequestedTextField.getText().substring(0,4);
    int newYear = Integer.parseInt(placeHolder);
    placeHolder = dateRequestedTextField.getText().substring(5,7);
    int newMonth = Integer.parseInt(placeHolder);
    placeHolder = dateRequestedTextField.getText().substring(8,10);
    int newDay = Integer.parseInt(placeHolder);
    LocalDate newDate = LocalDate.of(newYear, newMonth, newDay);
    
    
    fullName = fullName + firstNameTextField.getText() + " " + lastNameTextField.getText();
    Ticket newTicket = new Ticket(status, firstNameTextField.getText(), lastNameTextField.getText(),newDate, employeeAssignedTextField.getText(),
    descriptionTextField.getText(), fullName);
    
    originalList.remove(previousItem);
    originalList.add(newTicket);
    }   
    
    } //End method

    @FXML
    private void addClicked(javafx.scene.input.MouseEvent event) {
    ObservableList<Ticket> originalList = mainApp.getTicketData();
    String fullName = "";
    //form a dialogue box
   

    Dialog<Ticket> dialog = new Dialog();
    dialog.setTitle("Add a new ticket");
    dialog.setHeaderText("Please enter the contents of the new ticket or press cancel.");
    dialog.setResizable(true);
    
    ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
 
    
    Label statusLabel = new Label("Status: ");
    Label firstNameLabel = new Label("First Name: ");
    Label lastNameLabel = new Label("Last Name: ");
    Label dateRequestedLabel = new Label("Date Requested (YYYY/MM/DD): ");   
    Label employeeAssignedLabel = new Label("Employee Assigned: ");
    Label descriptionLabel = new Label("Description: ");
    Label fullNameLabel = new Label("Full Name: ");   
   
    TextField statusField = new TextField();
    TextField firstNameField = new TextField();
    TextField lastNameField = new TextField();
    TextField dateRequestedField = new TextField();
    TextField employeeAssignedField = new TextField();
    TextField descriptionField = new TextField();
 
    GridPane grid = new GridPane();
    grid.add(statusLabel, 1, 1);
    grid.add(statusField, 2, 1);
    
    grid.add(firstNameLabel, 1, 2);
    grid.add(firstNameField, 2, 2);
    
    grid.add(lastNameLabel, 1, 3);
    grid.add(lastNameField, 2, 3);
    
    grid.add(dateRequestedLabel, 1, 4);
    grid.add(dateRequestedField, 2, 4);
     
    grid.add(employeeAssignedLabel, 1, 6);
    grid.add(employeeAssignedField, 2, 6);
    
    grid.add(descriptionLabel, 1, 7);
    grid.add(descriptionField, 2, 7);
        
    dialog.getDialogPane().setContent(grid);
    
    Optional<Ticket> result = dialog.showAndWait();
	
 
    
    
    
    if (result.isPresent()) {
        
    //string mutations to match constructor
    int status = Integer.parseInt(statusField.getText());
    String placeHolder = dateRequestedField.getText().substring(0,4);
    int newYear = Integer.parseInt(placeHolder);
    placeHolder = dateRequestedField.getText().substring(5,7);
    int newMonth = Integer.parseInt(placeHolder);
    placeHolder = dateRequestedField.getText().substring(8,10);
    int newDay = Integer.parseInt(placeHolder);
    LocalDate newDate = LocalDate.of(newYear, newMonth, newDay);
    
    
    fullName = fullName + firstNameField.getText() + " " + lastNameField.getText();
    Ticket newTicket = new Ticket(status, firstNameField.getText(), lastNameField.getText(),newDate, 
    employeeAssignedField.getText(), descriptionField.getText(), fullName);
    

    originalList.add(newTicket);
    }

    
   
        
        
  
    }

}
