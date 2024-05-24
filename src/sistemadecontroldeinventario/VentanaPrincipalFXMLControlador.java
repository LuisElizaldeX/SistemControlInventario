/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario;

import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sistemadecontroldeinventario.CentroComputo.ConsultarCentroDeComputoController;
import sistemadecontroldeinventario.hardware.VentanaHardwareFXMLControlador;
import sistemadecontroldeinventario.software.MainSoftwareFXMLControlador;
import sistemadecontroldeinventario.usuario.UsuarioFXMLControlador;

/**
 * FXML Controller class
 *
 * @author froyl
 */
public class VentanaPrincipalFXMLControlador implements Initializable {
    
    private String cargoUsuario;
    @FXML
    private Button btnHardware;
    @FXML
    private Button btnCentrosComputo;
    @FXML
    private Button btnSoftware;
    @FXML
    private Button btnUsuarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void desplegarVentanaHardware(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaHardware = new FXMLLoader(getClass().getResource("hardware/VentanaHardwareFXML.fxml"));
            Parent ventanaHardware = loaderVentanaHardware.load();
            VentanaHardwareFXMLControlador controlador = loaderVentanaHardware.getController();
            Scene escenarioHardware = new Scene(ventanaHardware);
            Stage stageHardware = (Stage) btnHardware.getScene().getWindow();
            stageHardware.setScene(escenarioHardware);
            stageHardware.setTitle("Hardware");
            stageHardware.setResizable(false);
            controlador.inicializarVentana(cargoUsuario);
            stageHardware.show();            
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void desplegarVentanaSoftware(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaSoftware = new FXMLLoader(getClass().getResource("software/MainSoftwareFXML.fxml"));
            Parent ventanaSoftware = loaderVentanaSoftware.load();
            MainSoftwareFXMLControlador controlador = loaderVentanaSoftware.getController();
            Scene escenarioSoftware = new Scene(ventanaSoftware);
            Stage stageSoftware = (Stage) btnCentrosComputo.getScene().getWindow();
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
    private void desplegarVentanaUsuarios(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaUsuario = new FXMLLoader(getClass().getResource("usuario/UsuarioFXML.fxml"));
            Parent ventanaUsuario = loaderVentanaUsuario.load();
            UsuarioFXMLControlador controlador = loaderVentanaUsuario.getController();
            Scene escenarioUsuario = new Scene(ventanaUsuario);
            Stage stageUsuario = (Stage) btnCentrosComputo.getScene().getWindow();
            stageUsuario.setScene(escenarioUsuario);
            stageUsuario.setTitle("Usuarios");
            stageUsuario.setResizable(false);
            controlador.inicializarVentana(cargoUsuario);
            stageUsuario.show();            
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void desplegarVentanaCentrosComputo(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaCentrosDeComputo = new FXMLLoader(getClass().getResource("CentroComputo/ConsultarCentroDeComputo.fxml"));
            Parent ventanaCentrosDeComputo = loaderVentanaCentrosDeComputo.load();
            ConsultarCentroDeComputoController controlador = loaderVentanaCentrosDeComputo.getController();
            Scene escenarioCentrosDeComputo = new Scene(ventanaCentrosDeComputo);
            Stage stageCentrosDeComputo = (Stage) btnCentrosComputo.getScene().getWindow();
            stageCentrosDeComputo.setScene(escenarioCentrosDeComputo);
            stageCentrosDeComputo.setTitle("Centros de cómputo");
            stageCentrosDeComputo.setResizable(false);
            controlador.inicializarVentana(cargoUsuario);
            stageCentrosDeComputo.show();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", "¿Seguro que desea cerrar sesión?")){
            try {
                FXMLLoader loaderInicioSesion = new FXMLLoader(getClass().getResource("InicioSesionFXML.fxml"));
                Parent inicioSesion = loaderInicioSesion.load();

                Scene escenaVentanaPrincipal = new Scene(inicioSesion);
                Stage stageInicioSesion = (Stage) btnCentrosComputo.getScene().getWindow();
                stageInicioSesion.setScene(escenaVentanaPrincipal);
                stageInicioSesion.setResizable(false);
                stageInicioSesion.setTitle("Iniciar sesión");
                stageInicioSesion.show();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            }
        }        
    }
  
    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
        
        if(cargoUsuario.equalsIgnoreCase("encargado")){
            btnUsuarios.setVisible(false);
            btnCentrosComputo.setLayoutX(508);
            btnCentrosComputo.setLayoutY(393);
        }else if(cargoUsuario.equalsIgnoreCase("administrador")){
            btnSoftware.setVisible(false);
            btnHardware.setLayoutX(508);
            btnHardware.setLayoutY(47);
        }
    }
}
