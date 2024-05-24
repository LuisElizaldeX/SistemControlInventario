/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.hardware;

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
import sistemadecontroldeinventario.VentanaPrincipalFXMLControlador;
import sistemadecontroldeinventario.bitacora.SeleccionarDispositivoFXMLController;
import sistemadecontroldeinventario.hardware.equiposComputo.EquiposDeComputoFXMLControlador;
import sistemadecontroldeinventario.hardware.perifericos.PerifericosFXMLControlador;

/**
 * FXML Controller class
 *
 * @author froyl
 */
public class VentanaHardwareFXMLControlador implements Initializable {
    private String cargoUsuario;
    
    @FXML
    private Button btnEquiposComputo;
    @FXML
    private Button btnBitacora;
    @FXML
    private Button btnPerifericos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void desplegarVentanaEquiposComputo(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaEquiposDeComputo = new FXMLLoader(getClass().getResource("equiposComputo/EquiposDeComputoFXML.fxml"));
            Parent ventanaEquiposDeComputo = loaderVentanaEquiposDeComputo.load();
            EquiposDeComputoFXMLControlador controlador = loaderVentanaEquiposDeComputo.getController();
            Scene escenarioEquiposDeComputo = new Scene(ventanaEquiposDeComputo);
            Stage stageEquiposDeComputo = (Stage) btnEquiposComputo.getScene().getWindow();
            stageEquiposDeComputo.setScene(escenarioEquiposDeComputo);
            stageEquiposDeComputo.setResizable(false);
            stageEquiposDeComputo.setTitle("Equipos de cómputo");
            controlador.inicializarVentana(cargoUsuario);
            stageEquiposDeComputo.show();            
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void desplegarVentanaPerifericos(ActionEvent event) {
        try{
            FXMLLoader loaderVentanaPerifericos = new FXMLLoader(getClass().getResource("perifericos/PerifericosFXML.fxml"));
            Parent ventanaPerifericos = loaderVentanaPerifericos.load();
            PerifericosFXMLControlador controlador = loaderVentanaPerifericos.getController();
            Scene escenarioPerifericos = new Scene(ventanaPerifericos);
            Stage stagePerifericos = (Stage) btnEquiposComputo.getScene().getWindow();
            stagePerifericos.setScene(escenarioPerifericos);
            stagePerifericos.setResizable(false);
            stagePerifericos.setTitle("Periféricos");
            controlador.inicializarVentana(cargoUsuario);
            stagePerifericos.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaPrincipal = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/VentanaPrincipalFXML.fxml"));
            Parent ventanaPrincipal = loaderVentanaPrincipal.load();

            VentanaPrincipalFXMLControlador controlador = loaderVentanaPrincipal.getController();       
            
            Scene escenaVentanaPrincipal = new Scene(ventanaPrincipal);
            Stage stage = (Stage) btnEquiposComputo.getScene().getWindow();
            stage.setScene(escenaVentanaPrincipal);
            stage.setResizable(false);
            stage.setTitle("Ventana Principal");
            controlador.inicializarVentana(cargoUsuario);
            stage.show();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            e.printStackTrace();
        }       
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", "¿Seguro que desea cerrar sesión?")){
            try {
                FXMLLoader loaderInicioSesion = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/InicioSesionFXML.fxml"));
                Parent inicioSesion = loaderInicioSesion.load();

                Scene escenaVentanaPrincipal = new Scene(inicioSesion);
                Stage stageInicioSesion = (Stage) btnEquiposComputo.getScene().getWindow();
                stageInicioSesion.setScene(escenaVentanaPrincipal);
                stageInicioSesion.setResizable(false);
                stageInicioSesion.setTitle("Iniciar sesión");
                stageInicioSesion.show();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }        
    }

    @FXML
    private void desplegarBitacora(ActionEvent event) {
        try{
            FXMLLoader loaderVentanaPerifericos = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/bitacora/SeleccionarDispositivoFXML.fxml"));
            Parent ventanaPerifericos = loaderVentanaPerifericos.load();
            SeleccionarDispositivoFXMLController controlador = loaderVentanaPerifericos.getController();
            Scene escenarioPerifericos = new Scene(ventanaPerifericos);
            Stage stagePerifericos = (Stage) btnEquiposComputo.getScene().getWindow();
            stagePerifericos.setScene(escenarioPerifericos);
            stagePerifericos.setResizable(false);
            stagePerifericos.setTitle("Bitacora");
            controlador.inicializarVentana(cargoUsuario);
            stagePerifericos.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
        
        if(cargoUsuario.equalsIgnoreCase("administrador")){
            btnBitacora.setVisible(false);
            btnPerifericos.setVisible(false);
            btnEquiposComputo.setLayoutX(514);
            btnEquiposComputo.setLayoutY(194);
        }
    }  
}
