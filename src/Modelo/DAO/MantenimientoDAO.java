/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Modelo.ConexionBaseDeDatos;
import Modelo.POJO.Hardware;
import Modelo.POJO.Mantenimiento;
import Utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author Elotlan
 */
public class MantenimientoDAO {
    public static ArrayList<Mantenimiento> recuperarMantenimiento() throws SQLException{
        ArrayList<Mantenimiento> mantenimientoBD = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM mantenimiento";
                PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = consultaHardware.executeQuery();
                mantenimientoBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Mantenimiento mantenimientoTemporal = new Mantenimiento();
                    mantenimientoTemporal.setIdMantenimiento(resultadoConsulta.getInt("idBitacora"));
                    mantenimientoTemporal.setTipo(resultadoConsulta.getString("tipo"));
                    mantenimientoBD.add(mantenimientoTemporal);
                }
                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar los tipos de mantenimiento registrados: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        return mantenimientoBD;
    }
}
