/*
 * Author: Bradley Cundari
 * Date: 2016
 * Open Copyright
 */
package helpDeskGUI;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Ticket.
 *
 */
public class Ticket {

    private final IntegerProperty ticketNumber;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final ObjectProperty<LocalDate> dateRequested;
    private final StringProperty assignedTo;
    private final StringProperty description;
    private final StringProperty employee;

    public Ticket() {
        this(0, null, null, null, null, null, null);
    }

    /**
     * Constructor with initial data.
     *
     * @param ticketNumber
     * @param firstName
     * @param lastName
     * @param dateRequested
     * @param assignedTo
     * @param description
     * @param employee
     */
    public Ticket(int ticketNumber, String firstName, String lastName,
            LocalDate dateRequested, String assignedTo,
            String description, String employee) {
        this.ticketNumber = new SimpleIntegerProperty(ticketNumber);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateRequested = new SimpleObjectProperty<LocalDate>(dateRequested);
        this.assignedTo = new SimpleStringProperty(assignedTo);
        this.description = new SimpleStringProperty(description);
        this.employee = new SimpleStringProperty(employee);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getEmployee() {
        return employee.get();
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public StringProperty employeeProperty() {
        return employee;
    }

    public String getAssignedTo() {
        return assignedTo.get();
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo.set(assignedTo);
    }

    public StringProperty assignedToProperty() {
        return assignedTo;
    }

    public ObjectProperty<LocalDate> getDateRequested() {
        return dateRequested;
    }

    public String getLastName() {

        return this.lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public LocalDate getHireDate() {
        return dateRequested.get();
    }

    public void setHireDate(LocalDate dateRequested) {
        this.dateRequested.set(dateRequested);
    }

    public ObjectProperty<LocalDate> dateRequestedProperty() {
        return dateRequested;
    }

    public int getTicketNumber() {

        return this.ticketNumber.get();
    }

    public String getTicketString() {
        return Integer.toString(ticketNumber.get());
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber.set(ticketNumber);
    }

    public IntegerProperty ticketNumberProperty() {
        return ticketNumber;
    }

    public boolean anyNull() {
        if (ticketNumber == null || ticketNumber.getValue() == 0) {
            return true;
        }
        if (firstName == null) {
            return true;
        }
        if (dateRequested == null) {
            return true;
        }
        if (assignedTo == null) {
            return true;
        }
        if (description == null) {
            return true;
        }
        if (employee == null) {
            return true;
        }
        return false;
    }

}
