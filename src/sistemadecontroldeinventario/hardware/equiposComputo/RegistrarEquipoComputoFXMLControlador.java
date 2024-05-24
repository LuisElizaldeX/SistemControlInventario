/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.hardware.equiposComputo;

import Modelo.DAO.HardwareDAO;
import Modelo.POJO.Hardware;
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
public class RegistrarEquipoComputoFXMLControlador implements Initializable {

    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfNumeroSerie;
    @FXML
    private TextField tfProcesador;
    @FXML
    private ComboBox<String> cbArquitectura;
    @FXML
    private TextField tfTarjetaMadre;
    @FXML
    private TextField tfSistemaOperativo;
    @FXML
    private TextField tfGrafica;
    @FXML
    private TextField tfDireccionMac;
    @FXML
    private TextField tfDireccionIp;
    @FXML
    private Label lbErrorMarca;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorNumeroSerie;
    @FXML
    private Label lbErrorProcesador;
    @FXML
    private Label lbErrorArquitectura;
    @FXML
    private Label lbErrorTarjetaMadre;
    @FXML
    private Label lbErrorSistemaOperativo;
    @FXML
    private Label lbErrorGrafica;
    @FXML
    private Label lbErrorDireccionMac;
    @FXML
    private Label lbErrorDireccionIp;
    @FXML
    private TextField tfAlmacenamiento;
    @FXML
    private TextField tfRam;
    @FXML
    private Label lbErrorRam;
    @FXML
    private Label lbErrorAlmacenamiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarComboBox();
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Regresar", "¿Seguro que desea cancelar la operación y volver a la ventana anterior?")){
            Stage stage = (Stage) tfDireccionIp.getScene().getWindow();
            stage.close();
        }      
    }

    @FXML
    private void registrarEquipoComputo(ActionEvent event) {
        if(camposValidos()){
            if(Utilidades.mostrarDialogoConfirmacion("Registrar equipo de cómputo", "¿Desea registrar el equipo de cómputo?")){
                Hardware equipoComputoNuevo = new Hardware();
                equipoComputoNuevo.setMarca(tfMarca.getText());
                equipoComputoNuevo.setModelo(tfModelo.getText());
                equipoComputoNuevo.setNumeroSerie(tfNumeroSerie.getText());
                equipoComputoNuevo.setProcesador(tfProcesador.getText());

                if(cbArquitectura.getSelectionModel().isSelected(0)){
                    equipoComputoNuevo.setArquitectura(32);
                }else if (cbArquitectura.getSelectionModel().isSelected(1)){
                    equipoComputoNuevo.setArquitectura(64);
                }else if(cbArquitectura.getSelectionModel().isSelected(2)){
                    equipoComputoNuevo.setArquitectura(86);
                }

                equipoComputoNuevo.setTarjetaMadre(tfTarjetaMadre.getText());
                equipoComputoNuevo.setSistemaOperativo(tfSistemaOperativo.getText());
                equipoComputoNuevo.setGrafica(tfGrafica.getText());
                equipoComputoNuevo.setDireccionMac(tfDireccionMac.getText());
                equipoComputoNuevo.setDireccionIp(tfDireccionIp.getText());
                equipoComputoNuevo.setAlmacenamiento(Float.parseFloat(tfAlmacenamiento.getText()));
                equipoComputoNuevo.setRam(Float.parseFloat(tfRam.getText()));

                try{
                    if(HardwareDAO.registrarEquipoComputo(equipoComputoNuevo)){
                        Utilidades.mostrarAlertaSimple("Equipo Registrado", "Se registró el nuevo equipo de cómputo con éxito.", Alert.AlertType.INFORMATION);

                        Stage stage = (Stage) tfDireccionIp.getScene().getWindow();
                        stage.close();
                    }
                } catch (SQLException e) {
                    Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }            
        }
    }

    @FXML
    private void cancelarOperacion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cancelar", "¿Desea cancelar la operación y borrar los campos?")){
            tfMarca.setText("");
            tfModelo.setText("");
            tfAlmacenamiento.setText("");
            tfDireccionIp.setText("");
            tfDireccionMac.setText("");
            tfGrafica.setText("");
            tfNumeroSerie.setText("");
            tfProcesador.setText("");
            tfRam.setText("");
            tfSistemaOperativo.setText("");
            tfTarjetaMadre.setText("");
            cbArquitectura.getSelectionModel().clearSelection();
        }
    }
    
    private void iniciarComboBox(){
        cbArquitectura.getItems().add("x32 bits");
        cbArquitectura.getItems().add("x64 bits");
        cbArquitectura.getItems().add("x86 bits");
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
            }else if(HardwareDAO.buscarHardwarePorNumeroSerie(tfNumeroSerie.getText()) != null){
                lbErrorNumeroSerie.setText("Este número de serie ya existe.");
                tfNumeroSerie.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorNumeroSerie.setText("");
                tfNumeroSerie.setStyle("");
            }
            
            if(tfProcesador.getText().equals("")){
                lbErrorProcesador.setText("No se puede dejar vacío.");
                tfProcesador.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorProcesador.setText("");
                tfProcesador.setStyle("");
            }
            
            if(cbArquitectura.getSelectionModel().isEmpty()){
                lbErrorArquitectura.setText("Debe seleccionar una opción");
                cbArquitectura.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorArquitectura.setText("");
                cbArquitectura.setStyle("");
            }
            
            if(tfTarjetaMadre.getText().equals("")){
                lbErrorTarjetaMadre.setText("No se puede dejar vacío.");
                tfTarjetaMadre.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorTarjetaMadre.setText("");
                tfTarjetaMadre.setStyle("");
            }
            
            if(tfSistemaOperativo.getText().equals("")){
                lbErrorSistemaOperativo.setText("No se puede dejar vacío.");
                tfSistemaOperativo.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorSistemaOperativo.setText("");
                tfSistemaOperativo.setStyle("");
            }
            
            if(tfGrafica.getText().equals("")){
                lbErrorGrafica.setText("No se puede dejar vacío.");
                tfGrafica.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorGrafica.setText("");
                tfGrafica.setStyle("");
            }
            
            if(tfDireccionMac.getText().equals("")){
                lbErrorDireccionMac.setText("No se puede dejar vacío.");
                tfDireccionMac.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorDireccionMac.setText("");
                tfDireccionMac.setStyle("");
            }
            
            if(tfDireccionIp.getText().equals("")){
                lbErrorDireccionIp.setText("No se puede dejar vacío.");
                tfDireccionIp.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorDireccionIp.setText("");
                tfDireccionIp.setStyle("");
            }
            
            if(tfRam.getText().equals("")){
                lbErrorRam.setText("No se puede dejar vacío.");
                tfRam.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else if(!esNumerico(tfRam.getText())){
                lbErrorRam.setText("Debe ingresar valores numéricos");
                tfRam.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorRam.setText("");
                tfRam.setStyle("");
            }
            
            if(tfAlmacenamiento.getText().equals("")){
                lbErrorAlmacenamiento.setText("No se puede dejar vacío.");
                tfAlmacenamiento.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else if(!esNumerico(tfAlmacenamiento.getText())){
                lbErrorAlmacenamiento.setText("Debe ingresar valores numéricos");
                tfAlmacenamiento.setStyle("-fx-border-color: red");
                sonValidos = false;
            }else{
                lbErrorAlmacenamiento.setText("");
                tfAlmacenamiento.setStyle("");
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getSQLState(), Alert.AlertType.ERROR);
        }
        return sonValidos;
    }
    
    private boolean esNumerico(String cadena){
        try {
            Float.parseFloat(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
