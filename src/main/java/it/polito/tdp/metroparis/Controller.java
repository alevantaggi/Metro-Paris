package it.polito.tdp.metroparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class Controller {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> boxArrivo;

    @FXML
    private ComboBox<Fermata> boxPartenza;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCalcola(ActionEvent event) {
    	Fermata partenza= boxPartenza.getValue();
    	Fermata arrivo= boxArrivo.getValue();
    	
    	if(!partenza.equals(arrivo) && partenza!=null && arrivo!=null) {
    		List<Fermata> percorso= model.percorso(partenza, arrivo);
    		txtResult.setText("Percorso tra: " +partenza.getNome()+" e "+arrivo.getNome()+"\n\n");
    		for(Fermata f: percorso) {
    			txtResult.appendText(f.getNome()+"\n");
    		}
    	}
    	else
    		txtResult.appendText("Devi selezionare stazione valide tra loro");


    }

    @FXML
    void handleCrea(ActionEvent event) {
    	this.model.creaGrafo();
    	
    	if(this.model.isGrafoLoad())
    		txtResult.setText("Grafo corretamente importato");

    }

    @FXML
    void initialize() {
        assert boxArrivo != null : "fx:id=\"boxArrivo\" was not injected: check your FXML file 'Metro.fxml'.";
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'Metro.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Metro.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model= model;
    	
    	List<Fermata> fermate= this.model.getAllFermate();
    	boxPartenza.getItems().setAll(fermate);
    	boxArrivo.getItems().setAll(fermate);
    }

}
