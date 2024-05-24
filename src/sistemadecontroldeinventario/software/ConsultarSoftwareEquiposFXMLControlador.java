/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.software;

import Modelo.DAO.HardwareDAO;
import Modelo.POJO.Hardware;
import Utilidades.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elian
 */
public class ConsultarSoftwareEquiposFXMLControlador implements Initializable {

    private ObservableList<Hardware> listaHardware; 
    
    @FXML
    private TableView<Hardware> tvEquiposComputo;
    @FXML
    private TableColumn tcMarca;
    @FXML
    private TableColumn tcModelo;
    @FXML
    private TableColumn tcPosicion;
    @FXML
    private TableColumn tcEstado;
    private TableColumn tcId;
    @FXML
    private Label lblSoftware;
    private int idConsulta;
    private boolean tablaVacia = false;
    private String softwareLbl;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
        
        
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) tvEquiposComputo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void eliminarSoftwareEquipo(ActionEvent event) {
        if(!tvEquiposComputo.getSelectionModel().isEmpty()){
            int idSeleccionado = tvEquiposComputo.getSelectionModel().getSelectedItem().getIdHardware();
            if(idSeleccionado > 0 ){
                if(Utilidades.mostrarDialogoConfirmacion("Eliminar software de equipo", "¿Desea eliminar el software del equipo?")){
                    try {
                        if(HardwareDAO.EliminarSoftwareHardware( idSeleccionado, idConsulta)){
                            Utilidades.mostrarAlertaSimple("Software eliminado", "Se ha eliminado correctamente el software del equipo", Alert.AlertType.INFORMATION);
                        }
                    } catch (SQLException e) {
                        Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                    }
                }else{
                    Utilidades.mostrarAlertaSimple("Eliminacion cancelada", "Eliminación de software cancelada", Alert.AlertType.INFORMATION);
                }

                Stage stage = (Stage) tvEquiposComputo.getScene().getWindow();
                stage.close();      
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un hardware", "Se debe seleccionar un hardware", Alert.AlertType.WARNING);
        }
    }
    
    private void configurarTabla(){       
        tcMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        tcPosicion.setCellValueFactory(new PropertyValueFactory("posicion"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    private void cargarTabla(){
        try{
            listaHardware = FXCollections.observableArrayList();
            ArrayList<Hardware> hardwareBD = HardwareDAO.recuperarHardwareSoftware(idConsulta);
            
            if(!hardwareBD.isEmpty()){
                listaHardware.addAll(hardwareBD);
                tvEquiposComputo.setItems(listaHardware);
            }else{
                Utilidades.mostrarAlertaSimple("No hay equipos de cómputo", "Aun no hay equipos de cómputo registrados.", Alert.AlertType.ERROR);
                tablaVacia = true;
            }
        }catch(SQLException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    public boolean inicializarSoftware(int idSoftware, String software) {
         idConsulta = idSoftware;
         softwareLbl = software;
         cargarLbl(softwareLbl);
         cargarTabla();
        if(tablaVacia){
            return true;
        }
        return false;
    }
    
    public void cargarLbl(String softwareLbl){
        lblSoftware.setText(softwareLbl);
    }
    
    
}
