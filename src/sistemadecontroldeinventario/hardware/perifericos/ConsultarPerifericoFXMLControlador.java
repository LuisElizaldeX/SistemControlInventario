/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.hardware.perifericos;

import Modelo.DAO.CentroComputoDAO;
import Modelo.POJO.Periferico;
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
public class ConsultarPerifericoFXMLControlador implements Initializable {
    private Periferico perifericoConsulta;

    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfNumeroSerie;
    @FXML
    private TextField tfTipo;
    @FXML
    private TextField tfTipoConexion;
    @FXML
    private TextField tfEstado;
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
            Stage stage = (Stage) tfEstado.getScene().getWindow();
            stage.close();
        } 
    }
    
    private void cargarDatos(){
        tfMarca.setText(perifericoConsulta.getMarca());
        tfModelo.setText(perifericoConsulta.getModelo());
        tfNumeroSerie.setText(perifericoConsulta.getNumeroSerie());
        tfTipo.setText(perifericoConsulta.getTipo());
        tfEstado.setText(perifericoConsulta.getEstado());
        if(perifericoConsulta.isInalambrico()){
            tfTipoConexion.setText("Inalámbrico");
        }else{
            tfTipoConexion.setText("Alámbrico");
        }
        
        if(perifericoConsulta.getIdCentroComputo() != 0){
            tfUbicacion.setText(perifericoConsulta.getAula());
        }else{
            tfUbicacion.setText("No aplica");
        }
    }
    
    public void inicializarVentana(Periferico perifericoConsulta){
        this.perifericoConsulta = perifericoConsulta;
        cargarDatos();
    }
}
