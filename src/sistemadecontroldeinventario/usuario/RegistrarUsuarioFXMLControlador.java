/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.usuario;

import Modelo.DAO.UsuarioDAO;
import Modelo.POJO.Usuario;
import Utilidades.Utilidades;
import java.awt.FileDialog;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * FXML Controller class
 *
 * @author Elian
 */
public class RegistrarUsuarioFXMLControlador implements Initializable {
    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfnombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContrasenia;
    private File archivoFoto;
    @FXML
    private ComboBox<String> cbCargo;
    private ObservableList<String> listaCargos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarComboBox();
    }    

    
    
    @FXML
    private void cancelarOperacion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cancelar operación", "Desea cancelar la operación y borrar los campos?")){
            tfnombre.clear();
            cbCargo.setSelectionModel(null);
            tfContrasenia.clear();
            tfCorreo.clear();
            ivFoto.setImage(null);
        }
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) cbCargo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registrarUsuario(ActionEvent event)  {
        if(camposValidos()){
            if(tfCorreo.getText().toLowerCase().endsWith("@uv.mx")){
                try{
                    boolean repetido = UsuarioDAO.verificarUsuarioRepetido(tfCorreo.getText());
                    if(!repetido){
                        Usuario usuarioRegistro = new Usuario();
                        usuarioRegistro.setNombreCompleto(tfnombre.getText());
                        usuarioRegistro.setCorreoInstitucional(tfCorreo.getText());
                        usuarioRegistro.setCargo(cbCargo.getSelectionModel().getSelectedItem().toString());
                        usuarioRegistro.setContrasenia(tfContrasenia.getText());
                        if(archivoFoto != null){
                            try{
                                usuarioRegistro.setFoto(Files.readAllBytes(archivoFoto.toPath()));

                            }catch(IOException e){
                                e.getMessage();
                            }
                        }
                        guardarRegistroUsuario(usuarioRegistro);
                    }    
                }catch(SQLException e){
                    e.getMessage();
                }
            }else{
                Utilidades.mostrarAlertaSimple("Correo inválido", "El correo debe ser de la UV (@uv.mx)", Alert.AlertType.WARNING);
            }
                
        }else{
            Utilidades.mostrarAlertaSimple("Campos inválidos", "No se pueden dejar campos vacios", Alert.AlertType.WARNING);
        }
    }
    
    private void guardarRegistroUsuario(Usuario usuario){
        try{
            if(UsuarioDAO.registrarUsuario(usuario, archivoFoto)){
                Utilidades.mostrarAlertaSimple("Registro exitoso", "El usuario se registró con exito", Alert.AlertType.INFORMATION);
               
                Stage stage = (Stage) cbCargo.getScene().getWindow();
                stage.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistrarUsuarioFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void subirFoto(ActionEvent event) throws IOException {
        
        FileChooser dialogoImagen = new FileChooser();
        dialogoImagen.setTitle("Selecciona una foto");
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos JPG (*.jpg)", "*.JPG");
        dialogoImagen.getExtensionFilters().add(filtroImg);
        Stage escenarioActual = (Stage) cbCargo.getScene().getWindow();
        archivoFoto = dialogoImagen.showOpenDialog(escenarioActual);
        
        if(archivoFoto != null){
            try {
                BufferedImage bufferImg = ImageIO.read(archivoFoto);
                Image imagenFoto = SwingFXUtils.toFXImage(bufferImg, null);
                ivFoto.setImage(imagenFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean camposValidos(){
        boolean sonValidos = true;
        
        if(tfnombre.getText().equals("")){
            sonValidos = false;
        }
   
        if(tfCorreo.getText().equals("")){
            sonValidos = false;
        }
        
        if(tfContrasenia.getText().equals("")){
            sonValidos = false;
        }
        
        if(cbCargo.getSelectionModel().isEmpty()){
            sonValidos = false;
        }
        
        if(ivFoto.getImage() == null){
            sonValidos = false;
        }


        return sonValidos;
    }
    
    private void cargarComboBox(){
        listaCargos = FXCollections.observableArrayList();
        listaCargos.addAll("Administrador", "Encargado");
        cbCargo.setItems(listaCargos);
    }
}
