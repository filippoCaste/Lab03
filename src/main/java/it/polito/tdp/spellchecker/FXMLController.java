/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.net.URL;
import java.rmi.UnexpectedException;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.spellchecker.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Model dizionario;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnClearText"
    private Button btnClearText; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpellCheck"
    private Button btnSpellCheck; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLingue"
    private ComboBox<String> cmbLingue; // Value injected by FXMLLoader

    @FXML // fx:id="txtCompletionTime"
    private Label txtCompletionTime; // Value injected by FXMLLoader

    @FXML // fx:id="txtError"
    private Label txtError; // Value injected by FXMLLoader

    @FXML // fx:id="txtInput"
    private TextArea txtInput; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    // eventualmente sarebbe possibile anche fare un metodo apposta per il caricamento
    // del dizionario

    @FXML
    void doClearText(ActionEvent event) {
    	txtResult.clear();
    	txtInput.clear();   	
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	String lingua = this.cmbLingue.getValue();
    	dizionario.clear();
    	try {
    		dizionario.loadDictionary(lingua);
    	} catch (UnexpectedException ue) {
    		txtResult.setText(ue.getMessage());
    		ue.printStackTrace();
    		return;
    	} 
    	
    	String word = txtInput.getText();
    	
    	long startL = System.nanoTime();
    	Set <String> res = dizionario.spellCheckerLinear(word.replaceAll("“[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "")); //
    	long endL = System.nanoTime();
    	
    	// long startD = System.currentTimeMillis();
    	Set <String> res2 = dizionario.spellCheckerDichotomic(word.replaceAll("“[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "")); //.replaceAll("“[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "")
    	long endD = System.nanoTime();
    	
    	for(String s : res) {
    		txtResult.appendText(s+"\n");
    	}
    	txtResult.appendText("-----------------------\n");
    	for(String s : res2) {
    		txtResult.appendText(s+"\n");
    	}
    	
    	txtInput.clear();
    	
    	this.txtCompletionTime.setText("Completion time (linear): "+ (endL-startL)+" nanoseconds"+"\nCompletion Time (dichotomic): "+ (endD-endL)+" nanoseconds");
    }
    
    
    
    public void setModel(Model model) {
    	this.dizionario = model;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLingue != null : "fx:id=\"cmbLingue\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCompletionTime != null : "fx:id=\"txtCompletionTime\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtError != null : "fx:id=\"txtError\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        txtResult.setDisable(true);
        cmbLingue.setItems(FXCollections.observableArrayList("English", "Italian"));
    }

}


