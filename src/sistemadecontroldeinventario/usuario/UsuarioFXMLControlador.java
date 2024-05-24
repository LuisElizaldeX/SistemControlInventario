/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.usuario;


import Modelo.DAO.UsuarioDAO;
import Modelo.POJO.Usuario;
import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
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
import sistemadecontroldeinventario.VentanaPrincipalFXMLControlador;
/**
 * FXML Controller class
 *
 * @author Elian
 */
public class UsuarioFXMLControlador implements Initializable {
    private String cargoUsuario;
    private ObservableList<Usuario> listaUsuarios;
    
    @FXML
    private TableView<Usuario> tvUsuarios;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcCargo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
        cargarDatosTabla();
    }    
    
    @FXML
    private void cerrarVentana(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaPrincipal = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/VentanaPrincipalFXML.fxml"));
            Parent ventanaPrincipal = loaderVentanaPrincipal.load();
            VentanaPrincipalFXMLControlador controlador = loaderVentanaPrincipal.getController();                  
            Scene escenaVentanaPrincipal = new Scene(ventanaPrincipal);
            Stage stage = (Stage) tvUsuarios.getScene().getWindow();
            stage.setScene(escenaVentanaPrincipal);
            stage.setResizable(false);
            stage.setTitle("Ventana Principal");
            controlador.inicializarVentana(cargoUsuario);
            stage.show();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }       
    }
    
    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory ("nombreCompleto"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correoInstitucional"));
        tcCargo.setCellValueFactory(new PropertyValueFactory("cargo"));
    }
    
    private void cargarDatosTabla(){
        try{
            listaUsuarios = FXCollections.observableArrayList();
            ArrayList<Usuario> usuarioBD = UsuarioDAO.recuperarTodoUsuario();
            listaUsuarios.addAll(usuarioBD);
            tvUsuarios.setItems(listaUsuarios);
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {
        if(!tvUsuarios.getSelectionModel().isEmpty()){
            if(!tvUsuarios.getSelectionModel().getSelectedItem().getNombreCompleto().equalsIgnoreCase("TestAdministrador")){
               
                String usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem().getCorreoInstitucional();

                if(Utilidades.mostrarDialogoConfirmacion("Confirmar eliminación", "Desea eliminar al usuario con correo "+usuarioSeleccionado+" ?")){
                    UsuarioDAO.eliminarUsuario(usuarioSeleccionado);
                    Utilidades.mostrarAlertaSimple("Usuario eliminado", "El usuario se eliminó con éxito", Alert.AlertType.INFORMATION);
                    cargarDatosTabla();
                }
            }else{
                Utilidades.mostrarAlertaSimple("Error al eliminar", "No se puede eliminar el administrador", Alert.AlertType.ERROR);
            }    
            
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un usuario", "Se debe seleccionar un usuario", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void consultarUsuario(ActionEvent event) {
        if(!tvUsuarios.getSelectionModel().isEmpty()){
            String usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem().getCorreoInstitucional();
            try {
                FXMLLoader loaderVentanaConsultarUsuario = new FXMLLoader(getClass().getResource("ConsultarUsuarioFXML.fxml"));
                Parent ventanaConsultarUsuario = loaderVentanaConsultarUsuario.load();
            
                Scene escenarioUsuario = new Scene(ventanaConsultarUsuario);
                Stage stageUsuario = new Stage();
                stageUsuario.setScene(escenarioUsuario);
                stageUsuario.initModality(Modality.APPLICATION_MODAL);
                stageUsuario.setResizable(false);
                stageUsuario.setTitle("Consultar usuario");

                ConsultarUsuarioFXMLControlador controlador = (ConsultarUsuarioFXMLControlador) loaderVentanaConsultarUsuario.getController();
                controlador.inicializarUsuario(usuarioSeleccionado);
                stageUsuario.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(InicioSesionFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un usuario", "Se debe seleccionar un usuario", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void modificarUsuario(ActionEvent event) {
        if(!tvUsuarios.getSelectionModel().isEmpty()){
            String usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem().getCorreoInstitucional();
            try{
              FXMLLoader loaderVentanaConsultarUsuario = new FXMLLoader(getClass().getResource("ModificarUsuarioFXML.fxml"));
                    Parent ventanaConsultarUsuario = loaderVentanaConsultarUsuario.load();

                    Scene escenarioUsuario = new Scene(ventanaConsultarUsuario);
                    Stage stageSoftware = new Stage();
                    stageSoftware.setScene(escenarioUsuario);
                    stageSoftware.initModality(Modality.APPLICATION_MODAL);
                    stageSoftware.setResizable(false);
                    stageSoftware.setTitle("Modificar usuario");
                    ModificarUsuarioFXMLControlador controlador = (ModificarUsuarioFXMLControlador) loaderVentanaConsultarUsuario.getController();
                    controlador.inicializarUsuario(usuarioSeleccionado);
                    stageSoftware.showAndWait();
                    cargarDatosTabla();
            }catch(IOException e){
                e.getMessage();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un usuario", "Se debe seleccionar un usuario", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        try{
          FXMLLoader loaderVentanaConsultarUsuario = new FXMLLoader(getClass().getResource("RegistrarUsuarioFXML.fxml"));
                Parent ventanaConsultarUsuario = loaderVentanaConsultarUsuario.load();
            
                Scene escenarioUsuario = new Scene(ventanaConsultarUsuario);
                Stage stageSoftware = new Stage();
                stageSoftware.setScene(escenarioUsuario);
                stageSoftware.setResizable(false);
                stageSoftware.setTitle("Registrar usuario");
                stageSoftware.initModality(Modality.APPLICATION_MODAL);

                stageSoftware.showAndWait();
                cargarDatosTabla();
        }catch(IOException e){
            e.getMessage();
        }
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", "¿Seguro que desea cerrar sesión?")){
            try {
                FXMLLoader loaderInicioSesion = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/InicioSesionFXML.fxml"));
                Parent inicioSesion = loaderInicioSesion.load();

                Scene escenaVentanaPrincipal = new Scene(inicioSesion);
                Stage stageInicioSesion = (Stage) tvUsuarios.getScene().getWindow();
                stageInicioSesion.setScene(escenaVentanaPrincipal);
                stageInicioSesion.setResizable(false);
                stageInicioSesion.setTitle("Iniciar sesión");
                stageInicioSesion.show();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            }
        }
    }

    private void actualizarTabla(ActionEvent event) {
        cargarDatosTabla();
    }
        
    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
    }

    
}
