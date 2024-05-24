/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.software;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sistemadecontroldeinventario.VentanaPrincipalFXMLControlador;

/**
 * FXML Controller class
 *
 * @author johno
 */
public class MainSoftwareFXMLControlador implements Initializable {

    @FXML
    private Label lbFondo;

    private String cargoUsuario;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaPrincipal = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/VentanaPrincipalFXML.fxml"));
            Parent ventanaPrincipal = loaderVentanaPrincipal.load();
            VentanaPrincipalFXMLControlador controlador = loaderVentanaPrincipal.getController();
            Scene escenaVentanaPrincipal = new Scene(ventanaPrincipal);
            Stage stage = (Stage) lbFondo.getScene().getWindow();
            stage.setScene(escenaVentanaPrincipal);
            stage.setResizable(false);
            stage.setTitle("Ventana Principal");
            controlador.inicializarVentana(cargoUsuario);
            stage.show();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void desplegarVentanaConsultarSoftware(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaSoftware = new FXMLLoader(getClass().getResource("SoftwareFXML.fxml"));
            Parent ventanaSoftware = loaderVentanaSoftware.load();
            SoftwareFXMLControlador controlador = loaderVentanaSoftware.getController();
            Scene escenarioSoftware = new Scene(ventanaSoftware);
            Stage stageSoftware = (Stage) lbFondo.getScene().getWindow();
            stageSoftware.setScene(escenarioSoftware);
            stageSoftware.setResizable(false);
            stageSoftware.setTitle("Software");
            controlador.inicializarVentana(cargoUsuario);
            stageSoftware.show();            
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void desplegarVentanaAsignarHardwareSoftware(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaSoftware = new FXMLLoader(getClass().getResource("AsignarHardwareSoftwareFXML.fxml"));
            Parent ventanaSoftware = loaderVentanaSoftware.load();
            AsignarHardwareSoftwareFXMLControlador controlador = loaderVentanaSoftware.getController();
            Scene escenarioSoftware = new Scene(ventanaSoftware);
            Stage stageSoftware = (Stage) lbFondo.getScene().getWindow();
            stageSoftware.setScene(escenarioSoftware);
            stageSoftware.setResizable(false);
            stageSoftware.setTitle("Asignar Software");
            controlador.inicializarVentana(cargoUsuario);
            stageSoftware.show();           
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }
    
     @FXML
    private void cerrarSesion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", "¿Seguro que desea cerrar sesión?")){
            try {
                FXMLLoader loaderInicioSesion = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/InicioSesionFXML.fxml"));
                Parent inicioSesion = loaderInicioSesion.load();

                Scene escenaVentanaPrincipal = new Scene(inicioSesion);
                Stage stageInicioSesion = (Stage) lbFondo.getScene().getWindow();
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
    }
}
