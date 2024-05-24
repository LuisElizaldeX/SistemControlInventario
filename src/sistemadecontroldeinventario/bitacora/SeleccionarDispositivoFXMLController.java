/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemadecontroldeinventario.bitacora;

import Modelo.DAO.BitacoraDAO;
import Modelo.DAO.HardwareDAO;
import Modelo.POJO.Hardware;
import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemadecontroldeinventario.hardware.VentanaHardwareFXMLControlador;

/**
 * FXML Controller class
 *
 * @author Elotlan
 */
public class SeleccionarDispositivoFXMLController implements Initializable {
    private String cargoUsuario;
    private ObservableList<Hardware> listaHardware; 
    
    @FXML
    private TableView<Hardware> tbDispositivo;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colNumeroSerie;
    @FXML
    private TableColumn colEstado;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnConsultar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarTabla();
    }    
    
    @FXML
    private void clicRegistrar(ActionEvent event) throws SQLException, ParseException {
        if(verificarSeleccion()){
            int idDispositivo = tbDispositivo.getSelectionModel().getSelectedItem().getIdHardware();
            BitacoraDAO bitacoraDAO = new BitacoraDAO();
            if(bitacoraDAO.buscarHardwareBitacora(idDispositivo) == 1){
                boolean respuestaUsuario = Utilidades.mostrarDialogoContinuar("Alerta", "este dispositivo ya tiene su registro semestral, ¿Desea agregar uno nuevo?");
                if(respuestaUsuario){
                   try{
                        FXMLLoader loaderVentanaModificarEquipoDeComputo = new FXMLLoader(getClass().getResource("BitacoraDeMantenimientoFXML.fxml"));
                        Parent ventanaModificarEquipoDeComputo = loaderVentanaModificarEquipoDeComputo.load();

                        BitacoraDeMantenimientoFXMLController controlador = loaderVentanaModificarEquipoDeComputo.getController();
                        controlador.recibirHardware(idDispositivo);

                        Scene escenarioModificarEquipoDeComputo = new Scene(ventanaModificarEquipoDeComputo);
                        Stage stageEquiposDeComputo = new Stage();
                        stageEquiposDeComputo.setScene(escenarioModificarEquipoDeComputo);
                        stageEquiposDeComputo.initModality(Modality.APPLICATION_MODAL);
                        stageEquiposDeComputo.setResizable(false);
                        stageEquiposDeComputo.setTitle("Registrar Bitácora");
                        stageEquiposDeComputo.showAndWait();
                    }catch (IOException e) {
                        Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                        e.printStackTrace();
                    } 
                }
            }else{
                try{
                        FXMLLoader loaderVentanaModificarEquipoDeComputo = new FXMLLoader(getClass().getResource("BitacoraDeMantenimientoFXML.fxml"));
                        Parent ventanaModificarEquipoDeComputo = loaderVentanaModificarEquipoDeComputo.load();

                        BitacoraDeMantenimientoFXMLController controlador = loaderVentanaModificarEquipoDeComputo.getController();
                        controlador.recibirHardware(idDispositivo);

                        Scene escenarioModificarEquipoDeComputo = new Scene(ventanaModificarEquipoDeComputo);
                        Stage stageEquiposDeComputo = new Stage();
                        stageEquiposDeComputo.setScene(escenarioModificarEquipoDeComputo);
                        stageEquiposDeComputo.initModality(Modality.APPLICATION_MODAL);
                        stageEquiposDeComputo.setResizable(false);
                        stageEquiposDeComputo.setTitle("Registrar Bitácora");
                        stageEquiposDeComputo.showAndWait();

                    }catch (IOException e) {
                        Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
                        e.printStackTrace();
                    } 
            }

        }else{
            Utilidades.mostrarAlertaSimple("Equipo no seleccionado", "No se ha seleccionado el equipo de cómputo a consultar.", Alert.AlertType.WARNING);
        } 
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        try {
            FXMLLoader loaderVentanaHardware = new FXMLLoader(getClass().getResource("/sistemadecontroldeinventario/hardware/VentanaHardwareFXML.fxml"));
            Parent ventanaHardware = loaderVentanaHardware.load();
            VentanaHardwareFXMLControlador controlador = loaderVentanaHardware.getController();
            Scene escenarioHardware = new Scene(ventanaHardware);
            Stage stageHardware = (Stage) tbDispositivo.getScene().getWindow();
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
    private void clicConsultar(ActionEvent event) throws SQLException, ParseException {
        try{
            FXMLLoader loaderVentanaConsultarBitacora = new FXMLLoader(getClass().getResource("ConsultarBitacoraFXML.fxml"));
            Parent ventanaModificarEquipoDeComputo = loaderVentanaConsultarBitacora.load();

            ConsultarBitacoraFXMLController controlador = loaderVentanaConsultarBitacora.getController();
            Scene escenarioModificarEquipoDeComputo = new Scene(ventanaModificarEquipoDeComputo);
            Stage stageEquiposDeComputo = new Stage();
            stageEquiposDeComputo.setScene(escenarioModificarEquipoDeComputo);
            stageEquiposDeComputo.initModality(Modality.APPLICATION_MODAL);
            stageEquiposDeComputo.setResizable(false);
            stageEquiposDeComputo.setTitle("Registrar Bitácora");
            stageEquiposDeComputo.showAndWait();
            
        }catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
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
                Stage stageInicioSesion = (Stage) tbDispositivo.getScene().getWindow();
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
    
    private void configurarTabla(){
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colNumeroSerie.setCellValueFactory(new PropertyValueFactory("numeroSerie"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    private void cargarTabla(){
        try{
            listaHardware = FXCollections.observableArrayList();
            ArrayList<Hardware> hardwareBD = HardwareDAO.recuperarTodoHardware();            

            listaHardware.addAll(hardwareBD);
            tbDispositivo.setItems(listaHardware);

        }catch(SQLException e){
            Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private boolean verificarSeleccion(){
        return tbDispositivo.getSelectionModel().getSelectedItem() != null;
    }
    
    public void inicializarVentana(String cargoUsuario){
        this.cargoUsuario = cargoUsuario;
    }
}
