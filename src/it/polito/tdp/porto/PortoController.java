package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	
    	Author ins=boxPrimo.getValue();
    	if(ins==null){
    		txtResult.appendText("Seleziona un autore.\n");
    		return;
    	}
    	model.getGrafo();
    	model.creaListaArticoliPerAutore();
    	
    	for(Author a: model.getCoAutoriGrafo(ins)){
    		txtResult.appendText(a.toString()+"\n");
    	}
    	boxSecondo.getItems().addAll(model.getAutoriSecondatendina(ins));
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.clear();
    	Author ins1=boxPrimo.getValue();
    	Author ins2=boxSecondo.getValue();
    	if(ins1==null || ins2==null){
    		txtResult.appendText("Seleziona un autore.\n");
    		return;
    	}
    	txtResult.appendText("Sequenza di articoli:\n");
    	for(Paper p: model.getListaArticoli(ins1, ins2)){
    		txtResult.appendText(p.getTitle()+"\n");
}
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";     
    }
    
    Model model;
	public void setModel(Model model) {
		this.model=model;
		boxPrimo.getItems().addAll(model.getAutori());
	}
}
