/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.software;

import Modelo.DAO.SoftwareDAO;
import Modelo.POJO.Software;
import Utilidades.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author johno
 */
public class RegistroSoftwareFXMLControlador implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfPeso;
    @FXML
    private ComboBox<String> cbArquitectura;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbPeso;
    @FXML
    private Label lbArquitectura;
    
    private String cargoUsuario;

    ObservableList<String> ListaArquitecturas = FXCollections
            .observableArrayList("32", "64", "86");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarComponentesCombo();
    }    

    private void inicializarComponentesCombo(){
        cbArquitectura.setItems(ListaArquitecturas);
    }
    
    @FXML
    private void registrarSoftware(ActionEvent event) {
        
        if(camposValidos()){
            if(Utilidades.mostrarDialogoConfirmacion("Registrar software", "¿Desea registrar el software?")){
                String nombre = tfNombre.getText();
                String peso = tfPeso.getText();
                int arquitectura = 1;

                Software softwareNuevo = new Software();

                if(cbArquitectura.getSelectionModel().isSelected(0)){
                    arquitectura = 32;
                }else if (cbArquitectura.getSelectionModel().isSelected(1)){
                    arquitectura = 64;
                }else if (cbArquitectura.getSelectionModel().isSelected(2)){
                    arquitectura = 86;
                }
                    
                softwareNuevo.setNombre(nombre);
                softwareNuevo.setPeso(peso);
                softwareNuevo.setArquitectura(arquitectura);
                try{
                    boolean repetido = SoftwareDAO.verificarSoftwareRepetido(nombre, peso, arquitectura);
                    if(!repetido){
                        if(SoftwareDAO.registrarSoftware(softwareNuevo)){
                            Utilidades.mostrarAlertaSimple("Software registrado", 
                            "El software ha sido registrado exitosamente",
                            Alert.AlertType.INFORMATION);
                            tfNombre.setText("");
                            tfPeso.setText("");
                            cbArquitectura.getSelectionModel().selectFirst();
                        }else{
                            Utilidades.mostrarAlertaSimple("Software no registrado", 
                            "El registro del software ha fallado",
                            Alert.AlertType.INFORMATION);
                        }
                    }else{ 
                        Utilidades.mostrarAlertaSimple("Software repetido", 
                                "El software ya esta registrado.", Alert.AlertType.INFORMATION);
                    }
                }catch (SQLException sqlException) {
                    Utilidades.mostrarAlertaSimple("Error de conexión", 
                        "Algo ocurrió mal al intentar recuperar los software registrados: " + sqlException.getMessage(),
                        Alert.AlertType.ERROR);
                }
            }
        }
    }
    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Regresar", 
                "¿Seguro que desea borrar todos los datos de registro?")){
            Stage escenarioPrincipal = (Stage) tfNombre.getScene().getWindow();
            escenarioPrincipal.close();
        }
    }

    private boolean camposValidos(){
        boolean sonValidos = true;
        
        if(tfNombre.getText().isEmpty()){
            lbNombre.setText("No se puede dejar vacío.");
            tfNombre.setStyle("-fx-border-color: red");
            sonValidos = false;
        }else{
            lbNombre.setText("");
            tfNombre.setStyle("");
        }
        
        String[] unidadesDeAlmacenamiento = {"kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb"};
        
        if(tfPeso.getText().isEmpty()){
            lbPeso.setText("No se puede dejar vacío.");
            tfPeso.setStyle("-fx-border-color: red");
            sonValidos = false;
        }else if(tfPeso.getText().toLowerCase().endsWith("b")){
            
            String pesoNumerico = tfPeso.getText().substring(0, tfPeso.getText().length() - 2);
            String pesoExtension = tfPeso.getText().substring(tfPeso.getText().length() - 2);
            if(esNumerico(pesoNumerico)){
                lbPeso.setText("");
                tfPeso.setStyle("");
                
                for (String unidad : unidadesDeAlmacenamiento) {
                    if (!pesoExtension.toLowerCase().endsWith(unidad)) {
                        lbPeso.setText("");
                        tfPeso.setStyle("");
                        break;
                    }else{
                        lbPeso.setText("Ingrese una unidad correcta. Ej. 2Kb, 1Mb, 3Gb, etc.");
                        tfPeso.setStyle("-fx-border-color: red");
                        sonValidos = false;
                    }
                }
            }else{
                lbPeso.setText("Ingrese una unidad correcta. Ej. 2Kb, 1Mb, 3Gb, etc.");
                tfPeso.setStyle("-fx-border-color: red");
                sonValidos = false;
            }
        }else{
            lbPeso.setText("Ingrese una unidad correcta. Ej. 2Kb, 1Mb, 3Gb, etc.");
            tfPeso.setStyle("-fx-border-color: red");
            sonValidos = false;
        }
        

        if(cbArquitectura.getSelectionModel().isEmpty()){
            lbArquitectura.setText("No se puede dejar vacío.");
            cbArquitectura.setStyle("-fx-border-color: red");
            sonValidos = false;
        }else{
            lbArquitectura.setText("");
            cbArquitectura.setStyle("");
        }

        return sonValidos;
    }
    
    private boolean esNumerico(String cadena){
        try {
            Float.parseFloat(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
    }
}
