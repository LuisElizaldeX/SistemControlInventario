/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.hardware.perifericos;

import Modelo.DAO.PerifericoDAO;
import Modelo.POJO.Periferico;
import Utilidades.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author froyl
 */
public class RegistrarPerifericoFXMLControlador implements Initializable {

    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfNumeroSerie;
    @FXML
    private TextField tfTipo;
    @FXML
    private ComboBox<String> cbTipoConexion;
    @FXML
    private Label lbErrorMarca;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorNumeroSerie;
    @FXML
    private Label lbErrorTipo;
    @FXML
    private Label lbErrorTipoConexion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarComboBox();
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Regresar", "¿Seguro que desea cancelar la operación y volver a la ventana anterior?")){
            Stage stage = (Stage) tfMarca.getScene().getWindow();
            stage.close();
        }        
    }

    @FXML
    private void cancelarOperacion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cancelar", "¿Desea cancelar la operación y borrar los campos?")){
            tfMarca.setText("");
            tfModelo.setText("");
            tfNumeroSerie.setText("");
            tfTipo.setText("");
            cbTipoConexion.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void registrarPeriferico(ActionEvent event) {
        if(camposValidos()){
            if(Utilidades.mostrarDialogoConfirmacion("Registrar periférico", "¿Desea registrar el periférico?")){
                Periferico perifericoNuevo = new Periferico();
                perifericoNuevo.setMarca(tfMarca.getText());
                perifericoNuevo.setModelo(tfModelo.getText());
                perifericoNuevo.setNumeroSerie(tfNumeroSerie.getText());
                perifericoNuevo.setTipo(tfTipo.getText());
                
                if(cbTipoConexion.getSelectionModel().isSelected(0)){
                    perifericoNuevo.setInalambrico(false);
                }else if(cbTipoConexion.getSelectionModel().isSelected(1)){
                    perifericoNuevo.setInalambrico(true);
                }
                
                try {
                    if(PerifericoDAO.registrarPeriferico(perifericoNuevo)){
                        Utilidades.mostrarAlertaSimple("Periférico Registrado", "Se registró el nuevo periférico con éxito.", Alert.AlertType.INFORMATION);

                        Stage stage = (Stage) tfMarca.getScene().getWindow();
                        stage.close();
                    }
                } catch (SQLException e) {
                    Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }        
    }
    
    private void cargarComboBox(){
        cbTipoConexion.getItems().addAll("Alambrico", "Inalambrico");
    }
    
    private boolean camposValidos(){
        boolean sonValidos = true;
        try {           
            if(tfMarca.getText().equals("")){
                lbErrorMarca.setText("No se puede dejar vacío.");
                tfMarca.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorMarca.setText("");
                tfMarca.setStyle("");
            }
            
            if(tfModelo.getText().equals("")){
                lbErrorModelo.setText("No se puede dejar vacío.");
                tfModelo.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorModelo.setText("");
                tfModelo.setStyle("");
            }
            
            if(tfNumeroSerie.getText().equals("")){
                lbErrorNumeroSerie.setText("No se puede dejar vacío.");
                tfNumeroSerie.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else if(PerifericoDAO.buscarPerifericoPorNumeroSerie(tfNumeroSerie.getText()) != null){
                lbErrorNumeroSerie.setText("Este número de serie ya existe.");
                tfNumeroSerie.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorNumeroSerie.setText("");
                tfNumeroSerie.setStyle("");
            }
            
            if(tfTipo.getText().equals("")){
                lbErrorTipo.setText("No se puede dejar vacío.");
                tfTipo.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorTipo.setText("");
                tfTipo.setStyle("");
            }
            
            if(cbTipoConexion.getSelectionModel().isEmpty()){
                lbErrorTipoConexion.setText("Debe seleccionar una opción.");
                cbTipoConexion.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorTipoConexion.setText("");
                cbTipoConexion.setStyle("");
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        
        return sonValidos;
    }
}
