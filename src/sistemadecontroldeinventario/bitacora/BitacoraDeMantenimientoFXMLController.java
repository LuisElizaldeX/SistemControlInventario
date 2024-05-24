/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemadecontroldeinventario.bitacora;

import Modelo.DAO.BitacoraDAO;
import Modelo.DAO.MantenimientoDAO;
import Modelo.POJO.Bitacora;
import Modelo.POJO.Mantenimiento;
import Utilidades.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elotlan
 */
public class BitacoraDeMantenimientoFXMLController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private TextArea taBitacoraDescripcion;
    @FXML
    private Button btnRegistrar;
    @FXML
    private ComboBox<Mantenimiento> cbTipo;
    
    int idHardwareSeleccionado;
    public ObservableList<Mantenimiento> listaMantenimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            iniciarComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(BitacoraDeMantenimientoFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    
    private void iniciarComboBox() throws SQLException{
        try{
            listaMantenimiento = FXCollections.observableArrayList();
            ArrayList<Mantenimiento> mantenimientoBD = MantenimientoDAO.recuperarMantenimiento();
            
            listaMantenimiento.addAll(mantenimientoBD);
            
            for(Mantenimiento mantenimiento : listaMantenimiento){
                cbTipo.setItems(listaMantenimiento);
            }   
        }catch(SQLException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) throws SQLException, ParseException {        
        if(validarCamposVacios()){
            int idMantenimiento = cbTipo.getSelectionModel().getSelectedItem().getIdMantenimiento();
                    
            LocalDateTime localDateTime = LocalDateTime.now();
            String fecha = localDateTime.toString();
            
            Bitacora bitacora = new Bitacora();
            bitacora.setFecha(fecha);
            bitacora.setDescripcion(taBitacoraDescripcion.getText());
            bitacora.setIdHardware(idHardwareSeleccionado);
            bitacora.setIdMantenimiento(idMantenimiento);
            BitacoraDAO.RegistrarCentroComputo(bitacora);
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }else{
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Hay campos vacíos", Alert.AlertType.WARNING);
        }
    }
    
    private boolean validarCamposVacios(){
        boolean camposValidos = true;
        
        if(taBitacoraDescripcion.getText().isEmpty()){
            camposValidos = false;
        }
        if(cbTipo.getSelectionModel().isEmpty()){
            camposValidos = false;
        }
        return camposValidos;
    }
    
    public void recibirHardware(int idHardware){
        idHardwareSeleccionado = idHardware;
    }
}
