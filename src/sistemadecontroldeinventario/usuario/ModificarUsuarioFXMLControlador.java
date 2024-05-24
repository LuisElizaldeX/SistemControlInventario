/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.usuario;

import Modelo.DAO.UsuarioDAO;
import Modelo.POJO.Usuario;
import Utilidades.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Elian
 */
public class ModificarUsuarioFXMLControlador implements Initializable {

    @FXML
    private TextField tfnombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContrasenia;
    @FXML
    private ImageView ivFoto;
    @FXML
    private ComboBox<String> cbCargo;
    private File archivoFoto;
    private String usuarioModificar;
    private String correoAntiguo;
    private Boolean modificoImagen = false;
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
    private void modificarUsuario(ActionEvent event) {
        if(camposValidos()){
            if(modificoImagen){
                if(tfCorreo.getText().equalsIgnoreCase(correoAntiguo)){
                    Usuario usuarioRegistro = new Usuario();
                    usuarioRegistro.setNombreCompleto(tfnombre.getText());
                    usuarioRegistro.setCorreoInstitucional(tfCorreo.getText());
                    usuarioRegistro.setCargo(cbCargo.getSelectionModel().getSelectedItem());
                    usuarioRegistro.setContrasenia(tfContrasenia.getText());
                    try{
                        usuarioRegistro.setFoto(Files.readAllBytes(archivoFoto.toPath()));
                    }catch(IOException e){
                        e.getMessage();
                    }
                    guardarModificaciónUsuarioFoto(usuarioRegistro); 
                }else{
                    try{
                        boolean repetido = UsuarioDAO.verificarUsuarioRepetido(tfCorreo.getText());
                        if(!repetido){
                            Usuario usuarioRegistro = new Usuario();
                            usuarioRegistro.setNombreCompleto(tfnombre.getText());
                            usuarioRegistro.setCorreoInstitucional(tfCorreo.getText());
                            usuarioRegistro.setCargo(cbCargo.getSelectionModel().getSelectedItem());
                            usuarioRegistro.setContrasenia(tfContrasenia.getText());
                            try{
                                usuarioRegistro.setFoto(Files.readAllBytes(archivoFoto.toPath()));
                            }catch(IOException e){
                                e.getMessage();
                            }
                            guardarModificaciónUsuarioFoto(usuarioRegistro);
                        }    
                    }catch(SQLException e){
                        e.getMessage();
                    }
                }
            }else{
                if(tfCorreo.getText().equalsIgnoreCase(correoAntiguo)){
                    Usuario usuarioRegistro = new Usuario();
                    usuarioRegistro.setNombreCompleto(tfnombre.getText());
                    usuarioRegistro.setCorreoInstitucional(tfCorreo.getText());
                    usuarioRegistro.setCargo(cbCargo.getSelectionModel().getSelectedItem());
                    usuarioRegistro.setContrasenia(tfContrasenia.getText());

                    guardarModificaciónUsuario(usuarioRegistro); 
                }else{
                    try{
                        boolean repetido = UsuarioDAO.verificarUsuarioRepetido(tfCorreo.getText());
                        if(!repetido){
                            Usuario usuarioRegistro = new Usuario();
                            usuarioRegistro.setNombreCompleto(tfnombre.getText());
                            usuarioRegistro.setCorreoInstitucional(tfCorreo.getText());
                            usuarioRegistro.setCargo(cbCargo.getSelectionModel().getSelectedItem());
                            usuarioRegistro.setContrasenia(tfContrasenia.getText());

                            guardarModificaciónUsuario(usuarioRegistro);
                        }    
                    }catch(SQLException e){
                        e.getMessage();
                    }
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple("Campos vacios", "No se pueden dejar campos vacios", Alert.AlertType.WARNING);
        }

        
        

        
    }

    private void guardarModificaciónUsuario(Usuario usuario){
        try{
            if(UsuarioDAO.modificarUsuario(usuario, usuarioModificar)){
                Utilidades.mostrarAlertaSimple("Registro exitoso", "El usuario se modificó con exito", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) cbCargo.getScene().getWindow();
                stage.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistrarUsuarioFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void guardarModificaciónUsuarioFoto(Usuario usuario){
        try{
            if(UsuarioDAO.modificarUsuarioFoto(usuario, archivoFoto, usuarioModificar)){
                Utilidades.mostrarAlertaSimple("Registro exitoso", "El usuario se modificó con exito", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) cbCargo.getScene().getWindow();
                stage.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistrarUsuarioFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void cancelarOperacion(ActionEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cancelar operación", "Desea cancelar la operación y borrar los campos?")){
            try{
                Usuario usuario = UsuarioDAO.recuperarTodoUsuarioPorCorreo(usuarioModificar);
                tfnombre.setText(usuario.getNombreCompleto());
                tfCorreo.setText(usuario.getCorreoInstitucional());
                correoAntiguo = usuario.getCorreoInstitucional();
                tfContrasenia.setText(usuario.getContrasenia());
                cbCargo.getSelectionModel().select(usuario.getCargo());
            }catch(SQLException e){
                e.getMessage();
            }
        }
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) cbCargo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void subirFoto(ActionEvent event) {
        
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
                modificoImagen = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            modificoImagen = false;
        }
    }
    
    public void inicializarUsuario(String usuarioSeleccionado) throws IOException{
        usuarioModificar = usuarioSeleccionado;
        try{
            Usuario usuario = UsuarioDAO.recuperarTodoUsuarioPorCorreo(usuarioSeleccionado);
            tfnombre.setText(usuario.getNombreCompleto());
            tfCorreo.setText(usuario.getCorreoInstitucional());
            correoAntiguo = usuario.getCorreoInstitucional();
            tfContrasenia.setText(usuario.getContrasenia());
            cbCargo.getSelectionModel().select(usuario.getCargo());
            
            if(usuario.getFoto()!=null){
                Image img = new Image(new ByteArrayInputStream(usuario.getFoto()));
                ivFoto.setImage(img);
            }

        }catch(SQLException e){
            e.getMessage();
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

        return sonValidos;
    }
    
    private void cargarComboBox(){
        listaCargos = FXCollections.observableArrayList();
        listaCargos.addAll("Administrador", "Encargado");
        cbCargo.setItems(listaCargos);
    }
    
}
