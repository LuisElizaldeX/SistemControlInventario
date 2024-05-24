/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.software;

import Modelo.ConexionBaseDeDatos;
import Modelo.DAO.HardwareDAO;
import Modelo.DAO.SoftwareDAO;
import Modelo.POJO.Hardware;
import Modelo.POJO.Software;
import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemadecontroldeinventario.VentanaPrincipalFXMLControlador;

/**
 * FXML Controller class
 *
 * @author johno
 */
public class AsignarHardwareSoftwareFXMLControlador implements Initializable {

    @FXML
    private TableView<Software> tvSoftware;
    @FXML
    private TableColumn<Software, String> tcNombre;
    @FXML
    private TableColumn<Software, String> tcPeso;
    @FXML
    private TableColumn<Software, String> tcArquitectura;
    @FXML
    private ComboBox<Hardware> cbHardware;
    @FXML
    private Label lbHardware;
    
    public ObservableList<Software> listaSoftware;
    
    public ObservableList<Hardware> listaHardware;
    
    private String cargoUsuario;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarComboBox();
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaSoftware = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/software/MainSoftwareFXML.fxml"));
            Parent ventanaSoftware = loaderVentanaSoftware.load();
            MainSoftwareFXMLControlador controlador = loaderVentanaSoftware.getController();
            Scene escenarioSoftware = new Scene(ventanaSoftware);
            Stage stageSoftware = (Stage) tvSoftware.getScene().getWindow();
            stageSoftware.setScene(escenarioSoftware);
            stageSoftware.setTitle("Software");
            stageSoftware.setResizable(false);
            controlador.inicializarVentana(cargoUsuario);
            stageSoftware.show();            
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void asignarHardwareSoftware(ActionEvent event) {
        try{
            if(camposValidos()){
                if(!tvSoftware.getSelectionModel().isEmpty()){
                    Software softwareAsignacion = verificarSoftwareSeleccionado();
                    Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
                    if(conexionBD != null){
                        if(softwareAsignacion != null){
                            if(Utilidades.mostrarDialogoConfirmacion("Asignar software","¿Desea asignar el software al equipo de cómputo?")){
                                HardwareDAO.asignarEquipoComputoASoftware(
                                        cbHardware.getSelectionModel().getSelectedItem().getIdHardware(),
                                        softwareAsignacion.getIdSoftware());
                                cargarDatosTabla(cbHardware.getSelectionModel().getSelectedItem().getIdHardware());
                            }
                        }
                    }
                }else{
                    Utilidades.mostrarAlertaSimple("Seleccion obligatoria", "Necesita seleccionar un software a relacionar", Alert.AlertType.WARNING);
                }
            }
        }catch(SQLException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cbNumeroSerieHardware(ActionEvent event) {
        try{
            Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
            if(conexionBD != null){
                configurarTabla();
                boolean tablaDatos = cargarDatosTabla(cbHardware.getSelectionModel().getSelectedItem().getIdHardware());
                if(!tablaDatos){
                    Utilidades.mostrarAlertaSimple("No hay software", 
                        "No se encontraron software disponibles para la asignación.", 
                        Alert.AlertType.ERROR);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void inicializarComboBox(){
        try{
            listaHardware = FXCollections.observableArrayList();
            ArrayList<Hardware> hardwareBD = HardwareDAO.recuperarTodoHardware();
            
            listaHardware.addAll(hardwareBD);
            
            for (Hardware hardware : listaHardware) {
                cbHardware.setItems(listaHardware);
            }
        }catch(SQLException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory ("nombre"));
        tcArquitectura.setCellValueFactory(new PropertyValueFactory("arquitectura"));
        tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));
    }
    
    private boolean cargarDatosTabla(int idHardware){
        boolean resultado = false;
        try{
            listaSoftware = FXCollections.observableArrayList();
            ArrayList<Software> softwareBD = SoftwareDAO.recuperarTodoHardwareSinSoftware(idHardware);
            if(!softwareBD.isEmpty()){
                listaSoftware.addAll(softwareBD);
                tvSoftware.setItems(listaSoftware);
                resultado = true;
            }else{
                tvSoftware.getItems().clear();
            }
        }catch(SQLException | NullPointerException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        return resultado;
    }
    
    private Software verificarSoftwareSeleccionado(){
        int filaSeleccionada = tvSoftware.getSelectionModel().getFocusedIndex();
        return filaSeleccionada >= 0 ? listaSoftware.get(filaSeleccionada) : null;
    }
    
    private boolean camposValidos(){
        boolean sonValidos = true;
        
        if(cbHardware.getSelectionModel().isEmpty()){
            lbHardware.setText("No se puede dejar vacío.");
            cbHardware.setStyle("-fx-border-color: red");
            sonValidos = false;
        }else{
            lbHardware.setText("");
            cbHardware.setStyle("");
        }

        return sonValidos;
    }
    
    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
    }
}
