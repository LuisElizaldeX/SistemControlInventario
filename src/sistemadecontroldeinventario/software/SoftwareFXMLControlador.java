/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.software;

import Modelo.ConexionBaseDeDatos;
import Modelo.DAO.SoftwareDAO;
import Modelo.POJO.Software;
import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemadecontroldeinventario.InicioSesionFXMLControlador;

/**
 * FXML Controller class
 *
 * @author johno
 */
public class SoftwareFXMLControlador implements Initializable {

    @FXML
    private TableView<Software> tvSoftware;
    @FXML
    private TableColumn<Software, String> tcNombre;
    @FXML
    private TableColumn<Software, String> tcArquitectura;
    @FXML
    private TableColumn<Software, String> tcPeso;
    public ObservableList<Software> listaSoftware;
    
    private String cargoUsuario;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
    }  
    
    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory ("nombre"));
        tcArquitectura.setCellValueFactory(new PropertyValueFactory("arquitectura"));
        tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));
    }
    
    private void cargarDatosTabla(){
        try{
            listaSoftware = FXCollections.observableArrayList();
            ArrayList<Software> softwareBD = SoftwareDAO.recuperarTodoSoftware();
            if(!softwareBD.isEmpty()){
                listaSoftware.addAll(softwareBD);
                tvSoftware.setItems(listaSoftware);
            }else{
                Utilidades.mostrarAlertaSimple("No hay software", 
                        "Aun no hay software registrados.", 
                        Alert.AlertType.ERROR);
                
            }
        }catch(SQLException | NullPointerException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
      

    @FXML
    private void cerrarVentana(ActionEvent event) {
        try {
            FXMLLoader loaderMainSoftware = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/software/MainSoftwareFXML.fxml"));
            Parent mainSoftware = loaderMainSoftware.load();
            MainSoftwareFXMLControlador controlador = loaderMainSoftware.getController();
            Scene escenaMainSoftware = new Scene(mainSoftware);
            Stage stage = (Stage) tvSoftware.getScene().getWindow();
            stage.setScene(escenaMainSoftware);
            stage.setResizable(false);
            stage.setTitle("Software");
            controlador.inicializarVentana(cargoUsuario);
            stage.show();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminarSoftware(ActionEvent event) {
        if(!tvSoftware.getSelectionModel().isEmpty()){
            Software softwareEliminacion = verificarSoftwareSeleccionado();
            Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
            if(conexionBD != null){
                if(softwareEliminacion != null){                
                    if(Utilidades.mostrarDialogoConfirmacion("Eliminar registro de software", "¿Deseas eliminar la información del software?")){
                        try{
                            if(SoftwareDAO.eliminarSoftware(softwareEliminacion.getIdSoftware())){
                                cargarDatosTabla(); 
                            }else{
                                Utilidades.mostrarAlertaSimple("Operacion fallida", 
                                        "La eliminación del software ha fallado",
                                        Alert.AlertType.ERROR);
                            }
                        }catch(SQLException sqlException){
                            Utilidades.mostrarAlertaSimple("ERROR", 
                                    "Algo ocurrió mal al intentar recuperar los software registrados: " + sqlException.getMessage(),
                                    Alert.AlertType.ERROR);
                        }
                    }
                }
            }else{
                Utilidades.mostrarAlertaSimple("Error de conexion",
                        "No hay conexión con la base de datos.",
                        Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion obligatoria", "Necesita seleccionar un objeto a eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void consultarPorEquipoComputo(ActionEvent event) {
        if(!tvSoftware.getSelectionModel().isEmpty()){
            try {
                    int seleccionado = tvSoftware.getSelectionModel().getSelectedItem().getIdSoftware();
                    String software = tvSoftware.getSelectionModel().getSelectedItem().getNombre();
                    FXMLLoader loaderVentanaConsultarUsuario = new FXMLLoader(getClass().getResource("ConsultarSoftwareEquiposFXML.fxml"));
                    Parent ventanaConsultarUsuario = loaderVentanaConsultarUsuario.load();

                    Scene escenarioUsuario = new Scene(ventanaConsultarUsuario);
                    Stage stageSoftware = new Stage();
                    stageSoftware.setScene(escenarioUsuario);
                    stageSoftware.initModality(Modality.APPLICATION_MODAL);
                    stageSoftware.setResizable(false);
                    stageSoftware.setTitle("Consultar software por equipo de cómputo");

                    ConsultarSoftwareEquiposFXMLControlador controlador = (ConsultarSoftwareEquiposFXMLControlador) loaderVentanaConsultarUsuario.getController();
                    
                    if(!controlador.inicializarSoftware(seleccionado, software)){
                        stageSoftware.showAndWait();
                    }else{
                        //Utilidades.mostrarAlertaSimple("No hay equipos de cómputo", "Aun no hay equipos de cómputo registrados.", Alert.AlertType.ERROR);
                    }


                } catch (IOException ex) {
                    Logger.getLogger(InicioSesionFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un software", "Se debe seleccionar un software", Alert.AlertType.WARNING);
        }
            
    }

    @FXML
    private void modificarSoftware(ActionEvent event) {
        if(!tvSoftware.getSelectionModel().isEmpty()){
            Software softwareModificacion = verificarSoftwareSeleccionado();
            Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
            if(conexionBD != null){
                if(softwareModificacion != null){
                    if(Utilidades.mostrarDialogoConfirmacion("Modificar registro de software","¿Deseas modificar la información del software?")){
                        abrirFormularioSoftwareModificar(softwareModificacion);
                        cargarDatosTabla();
                    }
                }
            }else{
                Utilidades.mostrarAlertaSimple("Error de conexion",
                        "No hay conexión con la base de datos.",
                        Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion obligatoria", "Necesita seleccionar un objeto a modificar", Alert.AlertType.WARNING);
        }
    }
    
    private Software verificarSoftwareSeleccionado(){
        int filaSeleccionada = tvSoftware.getSelectionModel().getFocusedIndex();
        return filaSeleccionada >= 0 ? listaSoftware.get(filaSeleccionada) : null;
    }
    
    private void abrirFormularioSoftware(){
        try {
            FXMLLoader loaderVentanaRegistrarSoftware = new FXMLLoader(getClass().getResource("RegistroSoftwareFXML.fxml"));
            Parent ventanaRegistrarSoftware = loaderVentanaRegistrarSoftware.load();
            
            Scene escenarioEquiposDeComputo = new Scene(ventanaRegistrarSoftware);
            Stage stageFormularioSoftware = new Stage();
            stageFormularioSoftware.setScene(escenarioEquiposDeComputo);
            stageFormularioSoftware.initModality(Modality.APPLICATION_MODAL);
            stageFormularioSoftware.setResizable(false);
            stageFormularioSoftware.setTitle("Registrar software");
            stageFormularioSoftware.showAndWait();            
        } catch (IOException ex) {
            Logger.getLogger(InicioSesionFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void abrirFormularioSoftwareModificar(Software softwareModificar){
        try {
            FXMLLoader loaderVentanaModificarSoftware = new FXMLLoader(getClass().getResource("ModificaSoftwareFXML.fxml"));
            Parent ventanaModificarSoftware = loaderVentanaModificarSoftware.load();
            
            ModificaSoftwareFXMLControlador formularioModificar = 
                    loaderVentanaModificarSoftware.getController();
            
            formularioModificar.inicializaValores(softwareModificar);
            
            Scene escenarioSoftware = new Scene(ventanaModificarSoftware);
            Stage stageSoftware = new Stage();
            stageSoftware.setScene(escenarioSoftware);
            stageSoftware.initModality(Modality.APPLICATION_MODAL);
            stageSoftware.setResizable(false);
            stageSoftware.setTitle("Modificar software");
            stageSoftware.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(InicioSesionFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void registrarSoftware(ActionEvent event) {
        abrirFormularioSoftware();
        cargarDatosTabla();
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", "¿Seguro que desea cerrar sesión?")){
            try {
                FXMLLoader loaderInicioSesion = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/InicioSesionFXML.fxml"));
                Parent inicioSesion = loaderInicioSesion.load();

                Scene escenaVentanaPrincipal = new Scene(inicioSesion);
                Stage stageInicioSesion = (Stage) tvSoftware.getScene().getWindow();
                stageInicioSesion.setScene(escenaVentanaPrincipal);
                stageInicioSesion.setResizable(false);
                stageInicioSesion.setTitle("Iniciar sesión");
                stageInicioSesion.show();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            }
        }
    }
    
    public void inicializarVentana(String cargoUsuario) {
        this.cargoUsuario = cargoUsuario;
    }  
}
