/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.hardware.equiposComputo;

import Modelo.DAO.CentroComputoDAO;
import Modelo.DAO.HardwareDAO;
import Modelo.POJO.CentroComputo;
import Modelo.POJO.Hardware;
import Utilidades.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class ModificarEquipoComputoFXMLControlador implements Initializable {

    private Hardware hardwareModificacion;
    
    @FXML
    private Label lbErrorAlmacenamiento;
    @FXML
    private Label lbErrorRam;
    @FXML
    private TextField tfRam;
    @FXML
    private TextField tfAlmacenamiento;
    @FXML
    private Label lbErrorDireccionIp;
    @FXML
    private Label lbErrorDireccionMac;
    @FXML
    private Label lbErrorGrafica;
    @FXML
    private Label lbErrorSistemaOperativo;
    @FXML
    private Label lbErrorTarjetaMadre;
    @FXML
    private Label lbErrorArquitectura;
    @FXML
    private Label lbErrorProcesador;
    @FXML
    private Label lbErrorNumeroSerie;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorMarca;
    @FXML
    private TextField tfDireccionIp;
    @FXML
    private TextField tfDireccionMac;
    @FXML
    private TextField tfGrafica;
    @FXML
    private TextField tfSistemaOperativo;
    @FXML
    private TextField tfTarjetaMadre;
    @FXML
    private ComboBox<String> cbArquitectura;
    @FXML
    private TextField tfProcesador;
    @FXML
    private TextField tfNumeroSerie;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfMarca;
    @FXML
    private ComboBox<CentroComputo> cbUbicacion;
    @FXML
    private ComboBox<String> cbLetra;
    @FXML
    private ComboBox<String> cbNumero;
    @FXML
    private Label lbErrorUbicacion;
    @FXML
    private Label lbErrorLetra;
    @FXML
    private Label lbErrorNumero;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCentrosComputo();
        cargarComboBoxes();
        iniciarListeners();
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cancelar", "¿Seguro que desea cancelar los cambios y volver a la ventana anterior?")){
            Stage stage = (Stage) tfAlmacenamiento.getScene().getWindow();
            stage.close();
        }        
    }

    @FXML
    private void cancelarOperacion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cancelar", "¿Seguro que desea descartar los cambios?")){
            cargarDatos();
        }
    }

    @FXML
    private void modificarEquipoComputo(ActionEvent event) {
        if(camposValidos()){
            if(Utilidades.mostrarDialogoConfirmacion("Modificar equipo de cómputo", "¿Desea modificar el equipo de cómputo?")){
                Hardware equipoComputoModificado = new Hardware();
                equipoComputoModificado.setIdHardware(hardwareModificacion.getIdHardware());
                equipoComputoModificado.setMarca(tfMarca.getText());
                equipoComputoModificado.setModelo(tfModelo.getText());
                equipoComputoModificado.setNumeroSerie(tfNumeroSerie.getText());
                equipoComputoModificado.setProcesador(tfProcesador.getText());
                equipoComputoModificado.setTarjetaMadre(tfTarjetaMadre.getText());
                equipoComputoModificado.setSistemaOperativo(tfSistemaOperativo.getText());
                equipoComputoModificado.setGrafica(tfGrafica.getText());
                equipoComputoModificado.setRam(Float.valueOf(tfRam.getText()));
                equipoComputoModificado.setDireccionMac(tfDireccionMac.getText());
                equipoComputoModificado.setDireccionIp(tfDireccionIp.getText());
                equipoComputoModificado.setAlmacenamiento(Float.valueOf(tfAlmacenamiento.getText()));
                equipoComputoModificado.setEstado(hardwareModificacion.getEstado());
                equipoComputoModificado.setFechaIngreso(hardwareModificacion.getFechaIngreso());

                if(cbArquitectura.getSelectionModel().isSelected(0)){
                    equipoComputoModificado.setArquitectura(32);
                }else if (cbArquitectura.getSelectionModel().isSelected(1)){
                    equipoComputoModificado.setArquitectura(64);
                }else if(cbArquitectura.getSelectionModel().isSelected(2)){
                    equipoComputoModificado.setArquitectura(86);
                }
                
                if(cbUbicacion.getSelectionModel().isEmpty()){
                    equipoComputoModificado.setIdCentroComputo(0);
                    equipoComputoModificado.setPosicion("No aplica.");
                }else{
                    equipoComputoModificado.setIdCentroComputo(cbUbicacion.getSelectionModel().getSelectedItem().getIdCentroComputo());
                    equipoComputoModificado.setPosicion(cbLetra.getSelectionModel().getSelectedItem() + cbNumero.getSelectionModel().getSelectedItem());
                }
                
                if(!hardwareModificacion.equals(equipoComputoModificado)){
                    try {
                        if(HardwareDAO.modificarEquipoDeComputo(equipoComputoModificado)){
                            Utilidades.mostrarAlertaSimple("Equipo modificado", "Se modificó el equipo de cómputo con éxito.", Alert.AlertType.INFORMATION);

                            Stage stage = (Stage) tfDireccionIp.getScene().getWindow();
                            stage.close();
                        }
                    } catch (SQLException e) {
                        Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                    }
                }else{
                    Utilidades.mostrarAlertaSimple("No hay cambios", "No se detectaron cambios al equipo de computo.", Alert.AlertType.WARNING);
                }
            }
        }
    }
    
    @FXML
    private void quitarUbicacion(ActionEvent event) {
        cbUbicacion.getSelectionModel().clearSelection();
        cbLetra.getSelectionModel().clearSelection();
        cbNumero.getSelectionModel().clearSelection();
    }
    
    private void cargarDatos(){
        tfMarca.setText(hardwareModificacion.getMarca());
        tfModelo.setText(hardwareModificacion.getModelo());
        tfNumeroSerie.setText(hardwareModificacion.getNumeroSerie());
        tfProcesador.setText(hardwareModificacion.getProcesador());
        tfTarjetaMadre.setText(hardwareModificacion.getTarjetaMadre());
        tfSistemaOperativo.setText(hardwareModificacion.getSistemaOperativo());
        tfGrafica.setText(hardwareModificacion.getGrafica());
        tfRam.setText(hardwareModificacion.getRam() + "");
        tfDireccionMac.setText(hardwareModificacion.getDireccionMac());
        tfDireccionIp.setText(hardwareModificacion.getDireccionIp());
        tfAlmacenamiento.setText(hardwareModificacion.getAlmacenamiento() + "");

        switch(hardwareModificacion.getArquitectura()){
            case 32:
                cbArquitectura.getSelectionModel().select(0);
                break;
            case 64:
                cbArquitectura.getSelectionModel().select(1);
                break;
            case 86:
                cbArquitectura.getSelectionModel().select(2);
                break;
        }    
        
        if(!hardwareModificacion.getPosicion().equalsIgnoreCase("no aplica.")){
            cbLetra.getSelectionModel().select(cbLetra.getItems().indexOf(String.valueOf(hardwareModificacion.getPosicion().charAt(0))));
            cbNumero.getSelectionModel().select(cbNumero.getItems().indexOf(String.valueOf(hardwareModificacion.getPosicion().charAt(1))));
        }
        //seleccionar combobox con el centro de cómputo del hardware.
        try {
            if(hardwareModificacion.getIdCentroComputo() != 0){
                CentroComputo centroSeleccion = CentroComputoDAO.recuperarCentroComputoPorId(hardwareModificacion.getIdCentroComputo());
                for (CentroComputo centroComboBox : cbUbicacion.getItems()) {
                    if(centroComboBox.getIdCentroComputo() == centroSeleccion.getIdCentroComputo()){
                        cbUbicacion.getSelectionModel().select(centroComboBox);
                    }
                }
            }else{
                cbLetra.setDisable(true);
                cbNumero.setDisable(true);
            }            
        }catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", "Algo salió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarCentrosComputo(){
        try {
            ArrayList<CentroComputo> centrosComputoBD = CentroComputoDAO.recuperarTodoCentroDeComputo();
            cbUbicacion.getItems().addAll(centrosComputoBD);
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", "Algo salió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarComboBoxes(){
        cbArquitectura.getItems().addAll("x32 bits", "x64 bits", "x86 bits");
        
        for(int i = 65; i < 91; i++){
            cbLetra.getItems().add(String.valueOf((char) i));
        }
        
        for(int i = 1; i < 21; i++){
            cbNumero.getItems().add(String.valueOf(i));
        }
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
            }else if(HardwareDAO.buscarHardwarePorNumeroSerie(tfNumeroSerie.getText()) != null && HardwareDAO.buscarHardwarePorNumeroSerie(tfNumeroSerie.getText()).getIdHardware() != hardwareModificacion.getIdHardware()){
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
                lbErrorArquitectura.setText("Debe seleccionar una arquitectura.");
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
            
            if(!cbUbicacion.getSelectionModel().isEmpty()){
                if(HardwareDAO.buscarIdHardwarePorPosicion(cbUbicacion.getSelectionModel().getSelectedItem().getIdCentroComputo(), cbLetra.getSelectionModel().getSelectedItem() + cbNumero.getSelectionModel().getSelectedItem(), hardwareModificacion.getIdHardware()) > 0){
                    lbErrorLetra.setText("Esa posición ya esta ocupada.");
                    cbLetra.setStyle("-fx-border-color: red");
                    cbNumero.setStyle("-fx-border-color: red");
                    sonValidos = false;
                }else{
                    lbErrorLetra.setText("");
                    cbLetra.setStyle("");
                    cbNumero.setStyle(""); 
                }
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
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
    
    private void iniciarListeners(){
        cbUbicacion.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                cbLetra.setDisable(true);
                cbNumero.setDisable(true);
            }else{
                cbLetra.setDisable(false);
                cbNumero.setDisable(false);
            }
        });
    }

    public void inicializarVentana(Hardware hardwareModificacion){
        this.hardwareModificacion = hardwareModificacion;
        cargarDatos();
    }
}
