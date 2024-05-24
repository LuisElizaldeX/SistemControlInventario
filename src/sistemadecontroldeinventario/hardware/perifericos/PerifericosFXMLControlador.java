/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.hardware.perifericos;

import Modelo.DAO.PerifericoDAO;
import Modelo.POJO.Hardware;
import Modelo.POJO.Periferico;
import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemadecontroldeinventario.hardware.VentanaHardwareFXMLControlador;

/**
 * FXML Controller class
 *
 * @author froyl
 */
public class PerifericosFXMLControlador implements Initializable {
    
    private String cargoUsuario;
    private ObservableList<Periferico> listaPerifericos; 

    @FXML
    private TableColumn tcMarca;
    @FXML
    private TableColumn tcModelo;
    @FXML
    private TableColumn tcNumeroSerie;
    @FXML
    private TableColumn tcEstado;
    @FXML
    private TableView<Periferico> tvPerifericos;
    @FXML
    private TextField tfBusqueda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarTabla();
        inicializarBusquedaPerifericos();
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        try {
            FXMLLoader loaderHardware = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/hardware/VentanaHardwareFXML.fxml"));
            Parent hardware = loaderHardware.load();
            VentanaHardwareFXMLControlador controlador = loaderHardware.getController();
            Scene escenaVentanaHardware = new Scene(hardware);
            Stage stage = (Stage) tvPerifericos.getScene().getWindow();
            stage.setScene(escenaVentanaHardware);
            stage.setResizable(false);
            stage.setTitle("Hardware");
            controlador.inicializarVentana(cargoUsuario);
            stage.show();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }      
    }

    @FXML
    private void eliminarPeriferico(ActionEvent event) {
        if(verificarSeleccion()){
            if(Utilidades.mostrarDialogoConfirmacion("Eliminar periférico", "¿Desea eliminar el periférico seleccionado?")){
                try{
                    if(PerifericoDAO.eliminarPeriferico(tvPerifericos.getSelectionModel().getSelectedItem().getIdPeriferico())){
                        Utilidades.mostrarAlertaSimple("Eliminación exitosa.", "Se eliminó exitosamente el periférico.", Alert.AlertType.INFORMATION);
                    }
                }catch(SQLException e){
                    Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple("Periférico no seleccionado", "No se ha seleccionado el periférico a eliminar.", Alert.AlertType.WARNING);
        }
        
        cargarTabla();
        inicializarBusquedaPerifericos();
    }

    @FXML
    private void consultarPeriferico(ActionEvent event) {
        if(verificarSeleccion()){
            try{
                FXMLLoader loaderVentanaConsultarPeriferico = new FXMLLoader(getClass().getResource("ConsultarPerifericoFXML.fxml"));
                Parent ventanaConsultarPeriferico = loaderVentanaConsultarPeriferico.load();

                ConsultarPerifericoFXMLControlador controlador = loaderVentanaConsultarPeriferico.getController();
                controlador.inicializarVentana(PerifericoDAO.buscarPerifericoPorNumeroSerie(tvPerifericos.getSelectionModel().getSelectedItem().getNumeroSerie()));

                Scene escenarioConsultarPeriferico = new Scene(ventanaConsultarPeriferico);
                Stage stagePerifericos = new Stage();
                stagePerifericos.setScene(escenarioConsultarPeriferico);
                stagePerifericos.initModality(Modality.APPLICATION_MODAL);
                stagePerifericos.setResizable(false);
                stagePerifericos.setTitle("Consultar periférico");
                stagePerifericos.showAndWait();
                inicializarBusquedaPerifericos();
            }catch(IOException | SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Periférico no seleccionado", "No se ha seleccionado el periférico a consultar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void modificarPeriferico(ActionEvent event) {
        if(verificarSeleccion()){
            try {
                FXMLLoader loaderVentanaModificarPeriferico = new FXMLLoader(getClass().getResource("ModificarPerifericoFXML.fxml"));
                Parent ventanaModificarPeriferico = loaderVentanaModificarPeriferico.load();

                ModificarPerifericoFXMLControlador controlador = loaderVentanaModificarPeriferico.getController();
                controlador.inicializarVentana(PerifericoDAO.buscarPerifericoPorNumeroSerie(tvPerifericos.getSelectionModel().getSelectedItem().getNumeroSerie()));

                Scene escenarioModificarPeriferico = new Scene(ventanaModificarPeriferico);
                Stage stagePerifericos = new Stage();
                stagePerifericos.setScene(escenarioModificarPeriferico);
                stagePerifericos.initModality(Modality.APPLICATION_MODAL);
                stagePerifericos.setResizable(false);
                stagePerifericos.setTitle("Modificar periférico");
                stagePerifericos.showAndWait();                
                cargarTabla();
                inicializarBusquedaPerifericos();
            } catch (IOException | SQLException e) {
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Periférico no seleccionado", "No se ha seleccionado el periférico a modificar.", Alert.AlertType.WARNING);
        }        
    }

    @FXML
    private void registrarPeriferico(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaRegistrarPeriferico = new FXMLLoader(getClass().getResource("RegistrarPerifericoFXML.fxml"));
            Parent ventanaRegistrarPeriferico = loaderVentanaRegistrarPeriferico.load();
            
            Scene escenarioRegistrarPeriferico = new Scene(ventanaRegistrarPeriferico);
            Stage stagePerifericos = new Stage();
            stagePerifericos.setScene(escenarioRegistrarPeriferico);
            stagePerifericos.initModality(Modality.APPLICATION_MODAL);
            stagePerifericos.setResizable(false);
            stagePerifericos.setTitle("Registrar periférico");
            stagePerifericos.showAndWait(); 
            cargarTabla();
            inicializarBusquedaPerifericos();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void configurarTabla(){
        tcMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        tcNumeroSerie.setCellValueFactory(new PropertyValueFactory("numeroSerie"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    private void cargarTabla(){
        try{
            listaPerifericos = FXCollections.observableArrayList();
            ArrayList<Periferico> perifericosBD = PerifericoDAO.recuperarTodoPeriferico();            

            listaPerifericos.addAll(perifericosBD);
            tvPerifericos.setItems(listaPerifericos);

        }catch(SQLException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", "¿Seguro que desea cerrar sesión?")){
            try {
                FXMLLoader loaderInicioSesion = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/InicioSesionFXML.fxml"));
                Parent inicioSesion = loaderInicioSesion.load();

                Scene escenaVentanaPrincipal = new Scene(inicioSesion);
                Stage stageInicioSesion = (Stage) tvPerifericos.getScene().getWindow();
                stageInicioSesion.setScene(escenaVentanaPrincipal);
                stageInicioSesion.setResizable(false);
                stageInicioSesion.setTitle("Iniciar sesión");
                stageInicioSesion.show();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
            }
        }      
    }
    
    private void inicializarBusquedaPerifericos(){
        if(listaPerifericos.size() > 0){
            FilteredList<Periferico> filtro = new FilteredList<>(listaPerifericos, p -> true);
            
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtro.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        
                        String filtroMinusculas = newValue.toLowerCase();
                        if(busqueda.getNumeroSerie().toLowerCase().contains(filtroMinusculas)){
                            return true;
                        }
                        
                        if(busqueda.getEstado().toLowerCase().contains(filtroMinusculas)){
                            return true;
                        }
                        
                        if(busqueda.getMarca().toLowerCase().contains(filtroMinusculas)){
                            return true;
                        }
                        
                        if(busqueda.getModelo().toLowerCase().contains(filtroMinusculas)){
                            return true;
                        }
                        
                        if(busqueda.getAula().toLowerCase().contains(filtroMinusculas)){
                            return true;
                        }
                        
                        return false;
                    });
                }
                
            });
            
            SortedList<Periferico> sortedPerifericos = new SortedList<>(filtro);
            sortedPerifericos.comparatorProperty().bind(tvPerifericos.comparatorProperty());
            tvPerifericos.setItems(sortedPerifericos);
        }
    }
    
    private boolean verificarSeleccion(){
        return tvPerifericos.getSelectionModel().getSelectedItem() != null;
    }
    
    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
    }
}
