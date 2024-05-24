/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.ConexionBaseDeDatos;
import Modelo.POJO.Usuario;
import Utilidades.Utilidades;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author froyl
 */
public class UsuarioDAO {
    public static Usuario verificarUsuario(String correoInstitucional, String contrasenia) throws SQLException{
        Usuario usuarioSesion = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM usuario WHERE correoInstitucional = ? AND contrasenia = ?";
                
                PreparedStatement consultaLogin = conexionBD.prepareStatement(consulta);
                consultaLogin.setString(1, correoInstitucional);
                consultaLogin.setString(2, contrasenia);
                ResultSet resultadoConsulta = consultaLogin.executeQuery();
                usuarioSesion = new Usuario();
                
                if(resultadoConsulta.next()){
                    usuarioSesion.setIdUsuario(resultadoConsulta.getInt("idUsuario"));
                    usuarioSesion.setCargo(resultadoConsulta.getString("cargo"));
                    usuarioSesion.setNombreCompleto(resultadoConsulta.getString("nombreCompleto"));
                    usuarioSesion.setCorreoInstitucional(resultadoConsulta.getString("correoInstitucional"));
                    usuarioSesion.setContrasenia(resultadoConsulta.getString("contrasenia"));
                    usuarioSesion.setIdCentroComputo(resultadoConsulta.getInt("CentroComputo_idCentroComputo"));                 
                }else{
                    usuarioSesion.setIdUsuario(0);
                }
            } catch (SQLException e) {
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar iniciar sesión: " + e.getMessage(), Alert.AlertType.ERROR);
            } finally {
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
        
        return usuarioSesion;
    }
    
    public static ArrayList<Usuario> recuperarTodoUsuario() throws SQLException{
        ArrayList<Usuario> usuarioBD = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT nombreCompleto, correoInstitucional, cargo FROM usuario";
                PreparedStatement consultaUsuario = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = consultaUsuario.executeQuery();
                usuarioBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Usuario usuarioTemporal = new Usuario();
                    usuarioTemporal.setNombreCompleto(resultadoConsulta.getString("nombreCompleto"));
                    usuarioTemporal.setCorreoInstitucional(resultadoConsulta.getString("correoInstitucional"));
                    usuarioTemporal.setCargo(resultadoConsulta.getString("cargo"));
                    usuarioBD.add(usuarioTemporal);
                }
                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", 
                        "Algo ocurrió mal al intentar recuperar los software registrados: " + e.getMessage(),
                        Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexión con la base de datos, inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
        return usuarioBD;
    }
    
    public static Usuario recuperarTodoUsuarioPorCorreo(String correoInstitucional) throws SQLException{
        Usuario usuarioTemporal = new Usuario();
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT nombreCompleto, correoInstitucional, cargo, contrasenia, foto FROM usuario where correoInstitucional=?";
                PreparedStatement consultaUsuario = conexionBD.prepareStatement(consulta);
                consultaUsuario.setString(1, correoInstitucional);
                ResultSet resultadoConsulta = consultaUsuario.executeQuery();
                if (resultadoConsulta.next()){

                    usuarioTemporal.setNombreCompleto(resultadoConsulta.getString("nombreCompleto"));
                    usuarioTemporal.setCorreoInstitucional(resultadoConsulta.getString("correoInstitucional"));
                    usuarioTemporal.setCargo(resultadoConsulta.getString("cargo"));
                    usuarioTemporal.setContrasenia(resultadoConsulta.getString("contrasenia"));
                    usuarioTemporal.setFoto(resultadoConsulta.getBytes("foto"));
                }   
                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", 
                        "Algo ocurrió mal al intentar recuperar los software registrados: " + e.getMessage(),
                        Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexión con la base de datos, inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
        return usuarioTemporal;
    }
    
    public static boolean registrarUsuario(Usuario usuario, File foto) throws FileNotFoundException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "INSERT INTO usuario (nombreCompleto, correoInstitucional, cargo, contrasenia,CentroComputo_idCentroComputo, foto) values (?,?,?,?,1,?) ";
                PreparedStatement registrarUsuario = conexionBD.prepareStatement(consulta);
                registrarUsuario.setString(1, usuario.getNombreCompleto());
                registrarUsuario.setString(2, usuario.getCorreoInstitucional());
                registrarUsuario.setString(3, usuario.getCargo());
                registrarUsuario.setString(4, usuario.getContrasenia());
                FileInputStream fotoUsuario = new FileInputStream(foto);
                registrarUsuario.setBlob(5, fotoUsuario);
                int numFilas = registrarUsuario.executeUpdate();
                
                if(numFilas > 0){
                    resultadoOperacion = true;
                }else{
                    Utilidades.mostrarAlertaSimple("Error", 
                        "Error al intentar registrar el usuario ",
                        Alert.AlertType.ERROR);
                    conexionBD.close();
                }
            }catch(SQLException e ){
                    e.getMessage();
            }

        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexión con la base de datos, inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
        return resultadoOperacion;
    }
    
    public static boolean modificarUsuario(Usuario usuario, String usuarioModificar) throws FileNotFoundException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "update usuario set nombreCompleto = ?, correoInstitucional=?, cargo=?, contrasenia= ? where correoInstitucional = ? ";
                PreparedStatement modificarUsuario = conexionBD.prepareStatement(consulta);
                modificarUsuario.setString(1, usuario.getNombreCompleto());
                modificarUsuario.setString(2, usuario.getCorreoInstitucional());
                modificarUsuario.setString(3, usuario.getCargo());
                modificarUsuario.setString(4, usuario.getContrasenia());
                modificarUsuario.setString(5, usuarioModificar);
                int numFilas = modificarUsuario.executeUpdate();
                
                if(numFilas > 0){
                    resultadoOperacion = true;
                }else{
                    Utilidades.mostrarAlertaSimple("Error", 
                        "Error al intentar modificar el usuario ",
                        Alert.AlertType.ERROR);
                    conexionBD.close();
                }
            }catch(SQLException e ){
                    e.getMessage();
            }

        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexión con la base de datos, inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
        return resultadoOperacion;
    }
    
    public static boolean modificarUsuarioFoto(Usuario usuario, File foto, String usuarioModificar) throws FileNotFoundException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "update usuario set nombreCompleto = ?, correoInstitucional=?, cargo=?, contrasenia= ?, foto = ? where correoInstitucional = ? ";
                PreparedStatement modificarUsuario = conexionBD.prepareStatement(consulta);
                modificarUsuario.setString(1, usuario.getNombreCompleto());
                modificarUsuario.setString(2, usuario.getCorreoInstitucional());
                modificarUsuario.setString(3, usuario.getCargo());
                modificarUsuario.setString(4, usuario.getContrasenia());
                FileInputStream fotoUsuario = new FileInputStream(foto);
                modificarUsuario.setBlob(5, fotoUsuario);
                modificarUsuario.setString(6, usuarioModificar);
                int numFilas = modificarUsuario.executeUpdate();
                
                if(numFilas > 0){
                    resultadoOperacion = true;
                }else{
                    Utilidades.mostrarAlertaSimple("Error", 
                        "Error al intentar modificar el usuario ",
                        Alert.AlertType.ERROR);
                    conexionBD.close();
                }
            }catch(SQLException e ){
                    e.getMessage();
            }

        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexión con la base de datos, inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
        return resultadoOperacion;
    }
    
    public static boolean eliminarUsuario(String correo){
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "DELETE FROM usuario where correoInstitucional = ?";
                PreparedStatement eliminarUsuario = conexionBD.prepareStatement(consulta);
                eliminarUsuario.setString(1, correo);
                int numFilas = eliminarUsuario.executeUpdate();
                
                if(numFilas > 0){
                    resultadoOperacion = true;
                }else{
                    Utilidades.mostrarAlertaSimple("Error", 
                        "Error al intentar eliminar el usuario ",
                        Alert.AlertType.ERROR);
                    conexionBD.close();
                }
            }catch(SQLException e){
                e.getMessage();
            }
           
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexión con la base de datos, inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
        return resultadoOperacion;
    }
    
    public static boolean verificarUsuarioRepetido(String correo) throws SQLException{
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        boolean resultadoOperacion = false;
        if(conexionBD != null){
            try {
                String consulta = "select correoInstitucional FROM usuario where correoInstitucional=?";
                
                PreparedStatement prepararVerificarSoftware = conexionBD.prepareStatement(consulta);
                prepararVerificarSoftware.setString(1, correo);

                ResultSet numeroFilas = prepararVerificarSoftware.executeQuery();
                if(numeroFilas.next()){
                    Utilidades.mostrarAlertaSimple("Usuario repetido",
                            "El usuario con correo "+correo+" ya existe en la base de datos",
                            Alert.AlertType.INFORMATION);
                    resultadoOperacion = true;
                }
            }catch(SQLException sqlException){
                Utilidades.mostrarAlertaSimple("Error", 
                        "Algo ocurrió mal al intentar buscar los usuarios repetidos: " + sqlException.getMessage(),
                        Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexion con la base de datos.", 
                    Alert.AlertType.ERROR);
        }
        return resultadoOperacion;
    }
}
