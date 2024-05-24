/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.ConexionBaseDeDatos;
import Modelo.POJO.Periferico;
import Utilidades.Utilidades;
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
public class PerifericoDAO {
    public static ArrayList<Periferico> recuperarTodoPeriferico() throws SQLException{
        ArrayList<Periferico> perifericosBD = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM perifericos";
                PreparedStatement consultaPerifericos = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = consultaPerifericos.executeQuery();
                perifericosBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Periferico perifericoTemporal = new Periferico();
                    perifericoTemporal.setIdPeriferico(resultadoConsulta.getInt("idPerifericos"));
                    perifericoTemporal.setTipo(resultadoConsulta.getString("tipo"));
                    perifericoTemporal.setMarca(resultadoConsulta.getString("marca"));
                    perifericoTemporal.setModelo(resultadoConsulta.getString("modelo"));
                    perifericoTemporal.setEstado(resultadoConsulta.getString("estado"));
                    perifericoTemporal.setNumeroSerie(resultadoConsulta.getString("numeroSerie"));
                    perifericoTemporal.setInalambrico(resultadoConsulta.getBoolean("inalambrico"));
                    perifericoTemporal.setAula(CentroComputoDAO.recuperarAulaCentroComputoPorIdCentroComputo(resultadoConsulta.getInt("CentroComputo_idCentroComputo")));
                    perifericosBD.add(perifericoTemporal);
                }
                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar los perifericos registrados: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        
        return perifericosBD;
    }
    
    public static boolean registrarPeriferico(Periferico perifericoNuevo) throws SQLException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "INSERT INTO perifericos (marca, modelo, numeroSerie, tipo, inalambrico, estado) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement consultaPerifericos = conexionBD.prepareStatement(consulta);
                consultaPerifericos.setString(1, perifericoNuevo.getMarca());
                consultaPerifericos.setString(2, perifericoNuevo.getModelo());
                consultaPerifericos.setString(3, perifericoNuevo.getNumeroSerie());
                consultaPerifericos.setString(4, perifericoNuevo.getTipo());
                consultaPerifericos.setBoolean(5, perifericoNuevo.isInalambrico());
                consultaPerifericos.setString(6, "Funcional");
                
                int filasAfectadas = consultaPerifericos.executeUpdate();
                
                if(filasAfectadas > 0){
                    resultadoOperacion = true;
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar registrar el periférico: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        
        return resultadoOperacion;
    }
    
    public static Periferico buscarPerifericoPorNumeroSerie(String numeroSerie) throws SQLException{
        Periferico perifericoBD = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM perifericos WHERE numeroSerie = ?";
                PreparedStatement consultaPeriferico = conexionBD.prepareStatement(consulta);
                consultaPeriferico.setString(1, numeroSerie);
                ResultSet resultadoConsulta = consultaPeriferico.executeQuery();
                
                if(resultadoConsulta.next()){
                    perifericoBD = new Periferico();
                    perifericoBD.setIdPeriferico(resultadoConsulta.getInt("idPerifericos"));
                    perifericoBD.setMarca(resultadoConsulta.getString("marca"));
                    perifericoBD.setModelo(resultadoConsulta.getString("modelo"));
                    perifericoBD.setEstado(resultadoConsulta.getString("estado"));
                    perifericoBD.setTipo(resultadoConsulta.getString("tipo"));
                    perifericoBD.setNumeroSerie(resultadoConsulta.getString("numeroSerie"));
                    perifericoBD.setInalambrico(resultadoConsulta.getBoolean("inalambrico"));
                    perifericoBD.setIdCentroComputo(resultadoConsulta.getInt("CentroComputo_idCentroComputo"));
                    perifericoBD.setAula(CentroComputoDAO.recuperarAulaCentroComputoPorIdCentroComputo(resultadoConsulta.getInt("CentroComputo_idCentroComputo")));
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar el periférico: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        
        return perifericoBD;
    }
    
    public static boolean modificarPeriferico(Periferico perifericoModificacion) throws SQLException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "UPDATE perifericos SET marca = ?, modelo = ?, numeroSerie = ?, tipo = ?, inalambrico = ?, estado = ?, CentroComputo_idCentroComputo = ? WHERE idPerifericos = ?";
                PreparedStatement consultaPeriferico = conexionBD.prepareStatement(consulta);
                consultaPeriferico.setString(1, perifericoModificacion.getMarca());
                consultaPeriferico.setString(2, perifericoModificacion.getModelo());
                consultaPeriferico.setString(3, perifericoModificacion.getNumeroSerie());
                consultaPeriferico.setString(4, perifericoModificacion.getTipo());
                consultaPeriferico.setBoolean(5, perifericoModificacion.isInalambrico());
                consultaPeriferico.setString(6, perifericoModificacion.getEstado());
                
                if(perifericoModificacion.getIdCentroComputo() != 0){
                    consultaPeriferico.setInt(7, perifericoModificacion.getIdCentroComputo());
                }else{
                    consultaPeriferico.setNull(7, java.sql.Types.INTEGER);
                }
                
                consultaPeriferico.setInt(8, perifericoModificacion.getIdPeriferico());
                
                int filasAfectadas = consultaPeriferico.executeUpdate();
                
                if(filasAfectadas > 0){
                    resultadoOperacion = true;
                }                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar modificar el periférico: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        
        return resultadoOperacion;
    }
    
    public static boolean eliminarPeriferico(int idPeriferico) throws SQLException{
        Utilidades mensajeRespuesta = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        boolean resultado = false;
        if(conexionBD != null){
            try{
                String consultaEliminar = "DELETE FROM perifericos WHERE idPerifericos = ?;";
                PreparedStatement consultaEliminarPerifericos = conexionBD.prepareStatement(consultaEliminar);
                consultaEliminarPerifericos.setInt(1, idPeriferico);
                int filasAfectadas = consultaEliminarPerifericos.executeUpdate();
                
                if(filasAfectadas > 0){
                    resultado = true;
                }else{
                    mensajeRespuesta.mostrarAlertaSimple("Operación fallida",
                            "No se pudo eliminar el periferico.",
                            Alert.AlertType.ERROR);
                }
            }catch(SQLException sqlException){
                mensajeRespuesta.mostrarAlertaSimple("Error",
                        "Algo ocurrió mal al intentar recuperar los perifericos registrados: " + sqlException.getMessage(),
                        Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            mensajeRespuesta.mostrarAlertaSimple("Error de conexion",
                    "No hay conexión con la base de datos.",
                    Alert.AlertType.ERROR);
        }
        return resultado;
    }
}
