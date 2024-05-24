/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package sistemadecontroldeinventario;

import Modelo.DAO.UsuarioDAO;
import Modelo.POJO.Usuario;
import Utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author froyl
 */
public class InicioSesionFXMLControlador implements Initializable {
    
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfContrasenia;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorContrasenia;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void iniciarSesion(ActionEvent event) {
        if(camposValidos()){
            validarUsuario(tfUsuario.getText(), pfContrasenia.getText());
        }       
    }
    
    private void desplegarVentanaPrincipal(Usuario usuarioSesion){
        try {
            FXMLLoader loaderVentanaPrincipal = new FXMLLoader(getClass().getResource("VentanaPrincipalFXML.fxml"));
            Parent ventanaPrincipal = loaderVentanaPrincipal.load();
            VentanaPrincipalFXMLControlador controladorVentanaPrincipal = loaderVentanaPrincipal.getController();
            
            Scene escenaVentanaPrincipal = new Scene(ventanaPrincipal);
            Stage stageVentanaPrincipal = (Stage) tfUsuario.getScene().getWindow();
            stageVentanaPrincipal.setScene(escenaVentanaPrincipal);
            stageVentanaPrincipal.setResizable(false);
            stageVentanaPrincipal.setTitle("Ventana Principal");
            controladorVentanaPrincipal.inicializarVentana(usuarioSesion.getCargo());
            stageVentanaPrincipal.show();
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Algo salió mal", "Algo salio mal: " + e.getMessage() + ".", Alert.AlertType.ERROR);
        }
    }

    private boolean camposValidos() {
        boolean sonValidos = true;
        
        if(tfUsuario.getText().equals("")){
            lbErrorUsuario.setText("Debe ingresar un correo institucional.");
            sonValidos = false;
        }else if(!tfUsuario.getText().endsWith("@uv.mx")){
            lbErrorUsuario.setText("El correo ingresado debe pertenecer \na la universidad veracruzana.");
            sonValidos = false;
        }else{           
            lbErrorUsuario.setText("");
        }
                
        if(pfContrasenia.getText().equals("")){
            lbErrorContrasenia.setText("Debe ingresar su contraseña");
            sonValidos = false;
        }else{
            lbErrorContrasenia.setText("");
        }
        
        return sonValidos;
    }
    
    private void validarUsuario(String correoInstitucional, String contrasenia){
        try {
            Usuario usuarioSesion = UsuarioDAO.verificarUsuario(correoInstitucional, contrasenia);
            if(usuarioSesion.getIdUsuario()!= 0){
                Utilidades.mostrarAlertaSimple("Bienvenid@", "Bienvenid@ " + usuarioSesion.getNombreCompleto()+ ".", Alert.AlertType.INFORMATION);
                desplegarVentanaPrincipal(usuarioSesion);
            }else{
                Utilidades.mostrarAlertaSimple("Credenciales incorrectas", "El correo institucional y/o contraseña es incorrecto, favor de verificar", Alert.AlertType.WARNING);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
