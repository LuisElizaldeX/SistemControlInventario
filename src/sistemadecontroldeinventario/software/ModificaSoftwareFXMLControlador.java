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
import java.util.ResourceBundle;
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
public class ModificaSoftwareFXMLControlador implements Initializable {

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
    
    private boolean esEdicion;
    
    private String cargoUsuario;

    private Software softwareModificado;
    
    ObservableList<String> ListaArquitecturas = FXCollections
            .observableArrayList("32", "64", "86");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarComponentesCombo();
    }   

    private void inicializarComponentesCombo(){
        cbArquitectura.setItems(ListaArquitecturas);
    }    

    @FXML
    private void modificarSoftware(ActionEvent event) {
        if(camposValidos()){
            String arquitectura = String.valueOf(softwareModificado.getArquitectura());
            if(tfNombre.getText() != softwareModificado.getNombre()
                || tfPeso.getText() != softwareModificado.getPeso()
                || cbArquitectura.getSelectionModel().getSelectedItem() != arquitectura){
                if(Utilidades.mostrarDialogoConfirmacion("Modificar software", "¿Desea modificar el software?")){
                    Software softwareModificadoNuevo = new Software();
                    
                    softwareModificadoNuevo.setIdSoftware(softwareModificado.getIdSoftware());
                    softwareModificadoNuevo.setNombre(tfNombre.getText());
                    softwareModificadoNuevo.setPeso(tfPeso.getText());
                    
                    if(cbArquitectura.getSelectionModel().isSelected(0)){
                        softwareModificadoNuevo.setArquitectura(32);
                    }else if (cbArquitectura.getSelectionModel().isSelected(1)){
                        softwareModificadoNuevo.setArquitectura(64);
                    }else if(cbArquitectura.getSelectionModel().isSelected(2)){
                        softwareModificadoNuevo.setArquitectura(86);
                    }
                    
                    try{
                        boolean repetido = SoftwareDAO.verificarSoftwareRepetido(softwareModificadoNuevo.getNombre(),
                                softwareModificadoNuevo.getPeso(), softwareModificadoNuevo.getArquitectura());
                        if(!repetido){
                        
                            if(SoftwareDAO.modificarSoftware(softwareModificadoNuevo)){
                                Utilidades.mostrarAlertaSimple("Equipo modificado", 
                                        "Se modificó el equipo de cómputo con éxito.", Alert.AlertType.INFORMATION);
                        
                                Stage stage = (Stage) tfNombre.getScene().getWindow();
                                stage.close();
                            }
                        }
                    } catch (SQLException e) {
                        Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getSQLState(), Alert.AlertType.ERROR);
                    }
                }
            }else{
                Utilidades.mostrarAlertaSimple("Software repetido",
                            "El software que intenta registrar ya se encuentra registrado en la base de datos.",
                            Alert.AlertType.INFORMATION);
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

    void inicializaValores(Software softwareModificar) {
        esEdicion = softwareModificar != null;
        if(esEdicion){
            cargarSoftwareModificar(softwareModificar);
        }
    }
    
    private void cargarSoftwareModificar(Software softwareModificado){
        this.softwareModificado = softwareModificado;
        tfNombre.setText(softwareModificado.getNombre());
        tfPeso.setText(softwareModificado.getPeso());
        int arquitectura = softwareModificado.getArquitectura();
        int index = 0;
        
        switch (arquitectura){
            case 32: 
                break;
            case 64:
                index = 1;
                break;
            case 86:
                index = 2;
                break;
        }        
        cbArquitectura.getSelectionModel().select(index);
    }
    
    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
    }
}
