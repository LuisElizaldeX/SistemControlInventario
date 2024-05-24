/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemadecontroldeinventario.CentroComputo;

import Modelo.POJO.CentroComputo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elotlan
 */
public class InformacionCentroDeComputoController implements Initializable {    
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfFacultad;
    @FXML
    private TextField tfEdificio;
    @FXML
    private TextField tfAula;
    @FXML
    private TextField tfPiso;
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    CentroComputo centroComputoInfo;
    public void recibirCentroComputo(CentroComputo centroComputo){
        centroComputoInfo = centroComputo;
        mostrarInformacionCentroComputo();
    }
    
    private void mostrarInformacionCentroComputo(){
        tfDireccion.setText(centroComputoInfo.getDireccion());
        tfAula.setText(centroComputoInfo.getAula());
        tfFacultad.setText(centroComputoInfo.getFacultad());
        tfEdificio.setText(centroComputoInfo.getEdificio());
        tfPiso.setText(centroComputoInfo.getPiso());
        

        tfDireccion.setEditable(false);
        tfAula.setEditable(false);
        tfFacultad.setEditable(false);
        tfEdificio.setEditable(false);
        tfPiso.setEditable(false);
    }

    @FXML
    private void clicCancelar(ActionEvent event) {         
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    
}
