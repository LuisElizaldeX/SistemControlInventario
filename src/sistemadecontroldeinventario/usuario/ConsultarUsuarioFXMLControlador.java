/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadecontroldeinventario.usuario;

import Modelo.DAO.UsuarioDAO;
import Modelo.POJO.Usuario;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Elian
 */
public class ConsultarUsuarioFXMLControlador implements Initializable {

    @FXML
    private ImageView ivFoto;
    private Label lblNombre;
    private Label lblCorreo;
    private Label lblContrasenia;
    private Label lblCargo;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfCargo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ivFoto.getScene().getWindow();
        stage.close();
    }
    
    public void inicializarUsuario(String usuarioSeleccionado) throws IOException{
        try{
            Usuario usuario = UsuarioDAO.recuperarTodoUsuarioPorCorreo(usuarioSeleccionado);
            tfNombre.setText(usuario.getNombreCompleto());
            tfCorreo.setText(usuario.getCorreoInstitucional());
            tfPassword.setText(usuario.getContrasenia());
            tfCargo.setText(usuario.getCargo());
            
            if(usuario.getFoto()!=null){
                Image img = new Image(new ByteArrayInputStream(usuario.getFoto()));
                ivFoto.setImage(img);
            }
        }catch(SQLException e){
            e.getMessage();
        }
        
    }
}
