/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.ConexionBaseDeDatos;
import Modelo.POJO.CentroComputo;
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
public class CentroComputoDAO {
    public static String recuperarAulaCentroComputoPorIdCentroComputo(int idCentroComputo) throws SQLException{
        String aula = "";
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT aula FROM centroComputo WHERE idCentroComputo = ?";
                PreparedStatement consultaCentroComputo = conexionBD.prepareStatement(consulta);
                consultaCentroComputo.setInt(1, idCentroComputo);
                ResultSet resultadoConsulta = consultaCentroComputo.executeQuery();
                
                if(resultadoConsulta.next()){
                    aula = resultadoConsulta.getString("aula");
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar el aula del centro de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
        
        return aula;
    }
    
    public static ArrayList<CentroComputo> recuperarTodoCentroDeComputo() throws SQLException{
        ArrayList<CentroComputo> centrosComputo = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM centroComputo";
                PreparedStatement consultaCentroComputo = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = consultaCentroComputo.executeQuery();
                centrosComputo = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    CentroComputo centroComputoTemporal = new CentroComputo();
                    centroComputoTemporal.setIdCentroComputo(resultadoConsulta.getInt("idCentroComputo"));
                    centroComputoTemporal.setEdificio(resultadoConsulta.getString("edificio"));
                    centroComputoTemporal.setDireccion(resultadoConsulta.getString("direccion"));
                    centroComputoTemporal.setFacultad(resultadoConsulta.getString("facultad"));
                    centroComputoTemporal.setAula(resultadoConsulta.getString("aula"));
                    centroComputoTemporal.setPiso(resultadoConsulta.getString("piso"));
                    centrosComputo.add(centroComputoTemporal);
                }               
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar los centros de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
        return centrosComputo;
    }
    
    public static CentroComputo recuperarCentroComputoPorId(int idCentroComputo) throws SQLException{
        CentroComputo centroComputo = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM centroComputo WHERE idCentroComputo = ?";
                PreparedStatement consultaCentroComputo = conexionBD.prepareStatement(consulta);
                consultaCentroComputo.setInt(1, idCentroComputo);
                ResultSet resultadoConsulta = consultaCentroComputo.executeQuery();
                
                if(resultadoConsulta.next()){
                    centroComputo = new CentroComputo();
                    centroComputo.setIdCentroComputo(resultadoConsulta.getInt("idCentroComputo"));
                    centroComputo.setDireccion(resultadoConsulta.getString("direccion"));
                    centroComputo.setEdificio(resultadoConsulta.getString("edificio"));
                    centroComputo.setFacultad(resultadoConsulta.getString("facultad"));
                    centroComputo.setPiso(resultadoConsulta.getString("piso"));
                }
                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar los centros de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
        
        return centroComputo;
    }
    
    
    //Fernando
    public int RegistrarCentroComputo(CentroComputo centroComputo) throws SQLException{
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String query = "INSERT INTO centrocomputo (facultad, direccion, aula, edificio, piso) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conexionBD.prepareStatement(query);
                statement.setString(1, centroComputo.getFacultad());
                statement.setString(2, centroComputo.getDireccion());
                statement.setString(3, centroComputo.getAula());
                statement.setString(4, centroComputo.getEdificio());
                statement.setString(5, centroComputo.getPiso());
                statement.executeUpdate();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText("Registro exitoso");
                alert.setContentText("El centro de cómputo fue creado exitosamente");
                alert.showAndWait();
            }catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        
        return 0;
    }
    
    public ArrayList<CentroComputo> recuperarCentroComputo() throws SQLException{
        ArrayList<CentroComputo> centrosComputo = new ArrayList<>();
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        try{
            String query = "SELECT * FROM centrocomputo";
            PreparedStatement statement = conexionBD.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                do{
                    int idCentroComputo = resultSet.getInt("idCentroComputo");
                    String facultad = resultSet.getString("facultad");
                    String direccion = resultSet.getString("direccion");
                    String aula = resultSet.getString("aula");
                    String edificio = resultSet.getString("edificio");
                    String piso = resultSet.getString("piso");
                    CentroComputo centrosDeComputo = new CentroComputo();
                    centrosDeComputo.setIdCentroComputo(idCentroComputo);
                    centrosDeComputo.setFacultad(facultad);
                    centrosDeComputo.setDireccion(direccion);
                    centrosDeComputo.setAula(aula);
                    centrosDeComputo.setEdificio(edificio);
                    centrosDeComputo.setPiso(piso);
                    centrosComputo.add(centrosDeComputo);
                }while(resultSet.next());
            }else{
                throw new SQLException("no hay centros de cómputo");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
                conexionBD.close();
        }
        return centrosComputo;
    }
    
    public static boolean eliminarCentroComputo(int idCentroComputo) throws SQLException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "DELETE FROM centrocomputo WHERE idCentroComputo = ?";
                PreparedStatement consultaCC = conexionBD.prepareStatement(consulta);
                consultaCC.setInt(1, idCentroComputo);
                int filasAfectadas = consultaCC.executeUpdate();
                
                if(filasAfectadas > 0){
                    resultadoOperacion = true;
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar eliminar el centro de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
        
        return resultadoOperacion;
    }
    
    public static boolean modificarCentroComputo (CentroComputo centroComputo) throws SQLException{
        int id = centroComputo.getIdCentroComputo();
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "update centrocomputo set facultad = ?, direccion = ?, aula = ?, edificio = ?, piso = ? where idCentroComputo = ?";
                PreparedStatement modificarCentroComputo = conexionBD.prepareStatement(consulta);
                modificarCentroComputo.setString(1, centroComputo.getFacultad());
                modificarCentroComputo.setString(2, centroComputo.getDireccion());
                modificarCentroComputo.setString(3, centroComputo.getAula());
                modificarCentroComputo.setString(4, centroComputo.getEdificio());
                modificarCentroComputo.setString(5, centroComputo.getPiso());
                modificarCentroComputo.setInt(6, centroComputo.getIdCentroComputo());
                int numFilas = modificarCentroComputo.executeUpdate();
                
                if(numFilas > 0){
                    resultadoOperacion = true;
                }else{
                    Utilidades.mostrarAlertaSimple("error", " "+ id , Alert.AlertType.ERROR);
                    conexionBD.close();
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar eliminar el centro de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }
        return resultadoOperacion;
    }
    
}
