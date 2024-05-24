/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemadecontroldeinventario.CentroComputo;

import Modelo.DAO.CentroComputoDAO;
import Modelo.POJO.CentroComputo;
import Modelo.POJO.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemadecontroldeinventario.InicioSesionFXMLControlador;

/**
 * FXML Controller class
 *
 * @author Elotlan
 */
public class RegistrarCentroDeComputoController implements Initializable {

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
    private Button btnRegistrar;
    @FXML
    private Button btnSalir;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }        
    
    @FXML
    private void clicRegistrar(ActionEvent event) throws SQLException {
        if(validarCamposVacios() == true){
            CentroComputo centroComputo = new CentroComputo();
        
            centroComputo.setDireccion(tfDireccion.getText());
            centroComputo.setFacultad(tfFacultad.getText());
            centroComputo.setEdificio(tfEdificio.getText());
            centroComputo.setAula(tfAula.getText());
            centroComputo.setPiso(tfPiso.getText());

            CentroComputoDAO centroDAO = new CentroComputoDAO();
            centroDAO.RegistrarCentroComputo(centroComputo);
            limpiarCampos();
        }
    }

    private void limpiarCampos(){
        tfDireccion.setText("");
        tfFacultad.setText("");
        tfEdificio.setText("");
        tfAula.setText("");
        tfPiso.setText("");
    }
    
    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    
    private boolean validarCamposVacios(){
        String Aula = tfAula.getText();
        String Direccion = tfDireccion.getText();
        String Edificio = tfEdificio.getText();
        String Facultad = tfFacultad.getText();
        String Piso = tfPiso.getText();
        boolean camposValidos = true;
        if(Aula.isEmpty() || Aula.equals(" ")){
            tfAula.setStyle("-fx-border-color: red");
            camposValidos = false;
        }else{
            tfAula.setStyle("");
        }
        
        if(Direccion.isEmpty() || Direccion.equals(" ")){
            tfDireccion.setStyle("-fx-border-color: red");
            camposValidos = false;
        }else{
            tfDireccion.setStyle("");
        }
        
        if(Edificio.isEmpty() || Edificio.equals(" ")){
            tfEdificio.setStyle("-fx-border-color: red");
            camposValidos = false;
        }else{
            tfEdificio.setStyle("");
        }
        
        if(Facultad.isEmpty() || Facultad.equals(" ")){
            tfFacultad.setStyle("-fx-border-color: red");
            camposValidos = false;
        }else{
            tfFacultad.setStyle("");
        }
        
        if(Piso.isEmpty() || Piso.equals(" ")){
            tfPiso.setStyle("-fx-border-color: red");
            camposValidos = false;
        }else{
            tfPiso.setStyle("");
        }
                
        return camposValidos;
    }
    
}
