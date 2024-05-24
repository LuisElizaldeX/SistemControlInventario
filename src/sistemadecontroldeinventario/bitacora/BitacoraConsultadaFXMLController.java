/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemadecontroldeinventario.bitacora;

import Modelo.DAO.HardwareDAO;
import Modelo.POJO.Bitacora;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elotlan
 */
public class BitacoraConsultadaFXMLController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Label lblNumSerie;
    @FXML
    private Label lblFecha;
    
    Bitacora bitacoraConsulta;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    
    public void inicializarVentana(Bitacora bitacoraConsulta){
        this.bitacoraConsulta = bitacoraConsulta;
        
        taDescripcion.setText(bitacoraConsulta.getDescripcion());
        lblFecha.setText(bitacoraConsulta.getFecha());
        lblNumSerie.setText(bitacoraConsulta.getNumeroSerie());
    }
}
