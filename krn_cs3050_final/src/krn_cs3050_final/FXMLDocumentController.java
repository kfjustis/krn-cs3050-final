/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krn_cs3050_final;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Kynan
 */
public class FXMLDocumentController implements Initializable {
    
    private ArrayList<Prospect> strat = new ArrayList<Prospect>();
    
    @FXML
    private AnchorPane root;

    @FXML
    private TextArea outputArea;
    
    @FXML
    private TextField numDaysField;
    
    @FXML
    private TextField rField;
    
    private ArrayList<Integer> priceList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void handleOpen(ActionEvent event) {
        Stage stage = (Stage)root.getScene().getWindow();
        priceList = new ArrayList<>();
        
        // Clear the TextArea
        outputArea.setText("");
        
        // Create FileChoopser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("TXT files (*.txt)","*.txt"));
        
        // Get input from file
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                
                // Check for invalid characters
                Scanner scanner1 = new Scanner(file);
                Boolean hadParseError = false;
                while (scanner1.hasNext()) {
                    String line = scanner1.next();
                    
                    System.out.print("Printing line: ");
                    System.out.println(line);
                    
                    // Letters
                    for (char ch : line.toCharArray()) {
                        System.out.println(ch);
                        if (Character.isLetter(ch)) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Could not parse file due to invalid character(s)!");
                            alert.showAndWait();
                            
                            hadParseError = true;
                        }
                    }
                }
                scanner1.close();
                
                // Add prices from file
                Scanner scanner2 = new Scanner(file);
                while (scanner2.hasNextInt()) {
                    // may need to have cases here to check the values of nextInt()
                    // before adding something to the pricelist
                    Integer currentNum = scanner2.nextInt();
                    if (currentNum > 0) {
                        priceList.add(currentNum);
                    } else {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Could not parse file due to price with improper sign!");
                        alert.showAndWait();
                        
                        hadParseError = true;
                    }
                }
                scanner2.close();

                // Show prices from data read in no errors
                if (!hadParseError) {
                    System.out.println();
                    for (Integer i: priceList) {
                        System.out.println(i);
                        outputArea.appendText(i + "\n");
                    }
                } else {
                    outputArea.setText("Could not load prices due to parsing error.\n"
                            + "Please load a different file.");
                }
                
                // Print error message if no data was read in
                if (priceList.isEmpty()) {
                    // Show error
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No prices available. Please load file "
                                         + "with proper input.");
                    alert.showAndWait();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void handleClear(ActionEvent event) {
        outputArea.clear();
    }
    
    @FXML
    public void handlePrices(ActionEvent event) {
        if(priceList != null) {
            for(Integer i : priceList) {
                outputArea.appendText(i + "\n");
            }
        }
    }
    
    @FXML
    public void handleRun(ActionEvent event)
    {
        Integer numDays, rAmount;
        
        // Check values in numDays and R fields for being integers
        try {
            Integer.parseInt(numDaysField.getText());
            Integer.parseInt(rField.getText());
        } catch (NumberFormatException e) {
            // Show error
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Number of Days and R must be integers!");
            alert.showAndWait();
        }
        
        //Check to ensure values in numDays and R fields are positive
        if(Integer.parseInt(numDaysField.getText()) <= 0 || Integer.parseInt(rField.getText()) <= 0) {
            //Display error message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The values for number of days and R must be positive!");
            alert.showAndWait();
        }
        
        if (priceList == null) {
            // Show error
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No prices available. Please load file "
                                 + "with proper input.");
            alert.showAndWait();
        }
        else if(priceList.isEmpty()) // was if priceList == null
        {
            // Show error
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No prices available. Please load file "
                                 + "with proper input.");
            alert.showAndWait();
        }
        else if (Integer.parseInt(numDaysField.getText()) != priceList.size())
        {
            // Show error if numDays is wrong
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Number of days does not match input!");
            alert.showAndWait();
        }
        else if (Integer.parseInt(rField.getText()) < 1)
        {
            // Show error if R is less than 1
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("R must be at least 1.");
            alert.showAndWait();
            
        }
        else
        {   
            // Set the values from TextFields
            numDays = Integer.parseInt(numDaysField.getText());
            rAmount = Integer.parseInt(rField.getText());
            
            // Build final output from sorting
            strat = Stock_Sorter.sortStocks(numDays.intValue(), rAmount.intValue(), priceList);
            
            // Clear area before displaying
            outputArea.setText("");
            
            // Display final output in TextArea
            if (strat.size() > 0) {
                for(int count = 0; count < strat.size(); count++)
                {
                    outputArea.appendText(strat.get(count).getStart() + "\n" + strat.get(count).getEnd() + "\n");
                }
            } else {
                // Show error
                Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Output Warning");
                            alert.setHeaderText(null);
                            alert.setContentText("Program determined never to buy or sell!");
                            alert.showAndWait();
            }
        }
    }
}
