/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.hardware.equiposComputo;

import Modelo.DAO.CentroComputoDAO;
import Modelo.POJO.Hardware;
import Utilidades.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author froyl
 */
public class ConsultarEquipoComputoFXMLControlador implements Initializable {

    private Hardware hardwareConsulta;
    
    @FXML
    private TextField tfRam;
    @FXML
    private TextField tfAlmacenamiento;
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
    private TextField tfProcesador;
    @FXML
    private TextField tfNumeroSerie;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfArquitectura;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfFechaIngreso;
    @FXML
    private TextField tfUbicacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Regresar", "¿Seguro que desea volver a la ventana anterior?")){
            Stage stage = (Stage) tfDireccionIp.getScene().getWindow();
            stage.close();
        } 
    }
    
    private void cargarDatos(){
        tfMarca.setText(hardwareConsulta.getMarca());
        tfModelo.setText(hardwareConsulta.getModelo());
        tfNumeroSerie.setText(hardwareConsulta.getNumeroSerie());
        tfProcesador.setText(hardwareConsulta.getProcesador());
        tfArquitectura.setText("x" + hardwareConsulta.getArquitectura() + " bits");
        tfTarjetaMadre.setText(hardwareConsulta.getTarjetaMadre());
        tfSistemaOperativo.setText(hardwareConsulta.getSistemaOperativo());
        
        if(hardwareConsulta.getGrafica() != null){
            tfGrafica.setText(hardwareConsulta.getGrafica());
        }else{
            tfGrafica.setText("No aplica.");
        }
        
        tfRam.setText(hardwareConsulta.getRam() + " GB");
        tfDireccionMac.setText(hardwareConsulta.getDireccionMac());
        tfDireccionIp.setText(hardwareConsulta.getDireccionIp());
        tfAlmacenamiento.setText(hardwareConsulta.getAlmacenamiento() + " GB");
        tfEstado.setText(hardwareConsulta.getEstado());
        tfFechaIngreso.setText(hardwareConsulta.getFechaIngreso().toString());
        
        if(hardwareConsulta.getIdCentroComputo() != 0){
            tfUbicacion.setText(hardwareConsulta.getAula());
        }else{
            tfUbicacion.setText("No aplica.");
        }
        
    }
    
    public void inicializarVentana(Hardware hardwareConsulta){
        this.hardwareConsulta = hardwareConsulta;
        cargarDatos();
    }
    
}
