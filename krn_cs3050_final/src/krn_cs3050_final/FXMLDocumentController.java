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
                Scanner scanner = new Scanner(file);
                
                // Add prices from file
                while (scanner.hasNextInt()) {
                    // may need to have cases here to check the values of nextInt()
                    // before adding something to the pricelist
                    priceList.add(scanner.nextInt());
                }
                
                scanner.close();

                // Test printing the ArrayList
                for (Integer i: priceList) {
                    System.out.println(i);
                    outputArea.appendText(i + "\n");
                }
                
                // Print error message if no data was read in
                if (priceList.isEmpty()) {
                    outputArea.setText("Could not output numbers. Invalid data from file.");
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void handleRun(ActionEvent event)
    {
        Integer numDays, rAmount;
        
        if(priceList.isEmpty()) // was if priceList == null
        {
            outputArea.setText("Please choose a correctly formatted file first!");
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
            for(int count = 0; count < strat.size(); count++)
            {
                outputArea.appendText(strat.get(count).getStart() + "\n" + strat.get(count).getEnd() + "\n");
            }
        }
    }
}
