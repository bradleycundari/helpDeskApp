
/*
 * Author: Bradley Cundari
 * Date: 2016
 * Open Copyright
 */
package helpDeskGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;


/**
 *
 * @author Brad
 */
public class Main extends Application {
    
private ObservableList<Ticket> ticketData = 
		FXCollections.observableArrayList();
private static BorderPane mainWindow = new BorderPane();


    

 public Main()
       {
        ticketData.add(new Ticket(1, "Herman", "Munster",
 				LocalDate.of(2010, 4, 1), "Bob C", "Reinstall Windows", "Herman Munster"));
        ticketData.add(new Ticket(2, "John", "Jackson",
                            LocalDate.of(1980, 1, 1), "Jeff D.", "Reimage BIOS", "John Jackson"));
        ticketData.add(new Ticket(3, "Bill", "Gates",
                            LocalDate.of(1804, 7, 3), "Ben L.", "Kill All Connections on Network at 12PM", "Bill Gates"));
   
       
       }
    
   
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TicketView.fxml"));
        Parent root = loader.load();
        // Give the controller access to the main app.
        TicketViewController controller = loader.getController();
        controller.setMainApp(this);
        
       
        
        Scene scene = new Scene(root);
        
        

       
            
        stage.setScene(scene);
        stage.setTitle("Help Desk Application");
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.getIcons().add(new Image("http://www.nexlevelnet.com/images/home/helpdesk_icon.png"));
        stage.show();
    }

    /**
     	* Returns the data as an observable list of Employees. 
     	* @return
    */
   public ObservableList<Ticket> getTicketData() {
        	return ticketData;
    }
 
  
   
   /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    

        
        launch(args);
    }


  
}
 
    
    
    
    
    
    
    

