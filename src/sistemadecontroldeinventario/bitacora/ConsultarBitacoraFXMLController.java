/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemadecontroldeinventario.bitacora;

import Modelo.DAO.BitacoraDAO;
import Modelo.POJO.Bitacora;
import Modelo.POJO.Hardware;
import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elotlan
 */
public class ConsultarBitacoraFXMLController implements Initializable {
    private ObservableList<Bitacora> listaBitacora;
    
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnConsultar;
    @FXML
    private TableView<Bitacora> tbBitacora;
    @FXML
    private TableColumn colNumSerie;
    @FXML
    private TableColumn colFecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarTabla();
    }    

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clicConsultar(ActionEvent event) throws SQLException, ParseException, IOException {
        if(verificarSeleccion()){
            try{
                FXMLLoader loaderBitacoraConsultada = new FXMLLoader(getClass().getResource("BitacoraConsultadaFXML.fxml"));
                Parent ventanaInformacionCentroDeComputo = loaderBitacoraConsultada.load();               
                BitacoraConsultadaFXMLController controlador = loaderBitacoraConsultada.getController();
                controlador.inicializarVentana(tbBitacora.getSelectionModel().getSelectedItem());
                Scene escenarioInformacionCentroDeComputo = new Scene(ventanaInformacionCentroDeComputo);
                Stage stageInformacionCentroDeComputo = new Stage();
                stageInformacionCentroDeComputo.setScene(escenarioInformacionCentroDeComputo);
                stageInformacionCentroDeComputo.initModality(Modality.APPLICATION_MODAL);
                stageInformacionCentroDeComputo.setResizable(false);
                stageInformacionCentroDeComputo.showAndWait();
            }catch (IOException ex) {
                Logger.getLogger(BitacoraConsultadaFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Equipo no seleccionado", "No se ha seleccionado el equipo de cómputo a consultar.", Alert.AlertType.WARNING);
        }
    }
    
    private void configurarTabla(){
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        colNumSerie.setCellValueFactory(new PropertyValueFactory("numeroSerie"));
    }
    
    private void cargarTabla(){
        try{
            listaBitacora = FXCollections.observableArrayList();
            ArrayList<Bitacora> bitacoraBD = BitacoraDAO.recuperarTodaBitacora();
            listaBitacora.addAll(bitacoraBD);
            tbBitacora.setItems(listaBitacora);
        }catch(SQLException | ParseException e){
            Utilidades.mostrarAlertaSimple("Algo salio mal", "Algo salió mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }
    
    private boolean verificarSeleccion(){
        return tbBitacora.getSelectionModel().getSelectedItem() != null;
    }
    
    
}
