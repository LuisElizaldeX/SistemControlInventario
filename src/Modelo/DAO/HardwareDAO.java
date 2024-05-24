/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.ConexionBaseDeDatos;
import Modelo.POJO.Hardware;
import Utilidades.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author froyl
 */
public class HardwareDAO {
    public static ArrayList<Hardware> recuperarTodoHardware() throws SQLException{
        ArrayList<Hardware> hardwareBD = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM hardware";
                PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = consultaHardware.executeQuery();
                hardwareBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Hardware hardwareTemporal = new Hardware();
                    hardwareTemporal.setIdHardware(resultadoConsulta.getInt("idHardware"));
                    hardwareTemporal.setMarca(resultadoConsulta.getString("marca"));
                    hardwareTemporal.setModelo(resultadoConsulta.getString("modelo"));
                    hardwareTemporal.setNumeroSerie(resultadoConsulta.getString("numeroSerie"));
                    hardwareTemporal.setEstado(resultadoConsulta.getString("estado"));
                    hardwareTemporal.setAula(CentroComputoDAO.recuperarAulaCentroComputoPorIdCentroComputo(resultadoConsulta.getInt("CentroComputo_idCentroComputo")));
                    hardwareBD.add(hardwareTemporal);
                }
                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar los hardware registrados: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        return hardwareBD;
    }
    
    public static boolean registrarEquipoComputo(Hardware equipoComputoNuevo) throws SQLException{
        boolean resultadoOperacion = false;
        
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "INSERT INTO hardware (modelo, marca, numeroSerie, procesador, almacenamiento, ram, direccionMac, direccionIp, sistemaOperativo, arquitectura, grafica, tarjetaMadre, estado, fechaIngreso, posicion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement sentenciaHardware = conexionBD.prepareStatement(consulta);
                sentenciaHardware.setString(1, equipoComputoNuevo.getModelo());
                sentenciaHardware.setString(2, equipoComputoNuevo.getMarca());
                sentenciaHardware.setString(3, equipoComputoNuevo.getNumeroSerie());
                sentenciaHardware.setString(4, equipoComputoNuevo.getProcesador());
                sentenciaHardware.setFloat(5, equipoComputoNuevo.getAlmacenamiento());
                sentenciaHardware.setFloat(6, equipoComputoNuevo.getRam());
                sentenciaHardware.setString(7, equipoComputoNuevo.getDireccionMac());
                sentenciaHardware.setString(8, equipoComputoNuevo.getDireccionIp());
                sentenciaHardware.setString(9, equipoComputoNuevo.getSistemaOperativo());
                sentenciaHardware.setInt(10, equipoComputoNuevo.getArquitectura());
                sentenciaHardware.setString(11, equipoComputoNuevo.getGrafica());
                sentenciaHardware.setString(12, equipoComputoNuevo.getTarjetaMadre());
                sentenciaHardware.setString(13, "Funcional");                
                
                LocalDateTime fechaHoraActual = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                sentenciaHardware.setString(14, fechaHoraActual.format(formatter));
                
                sentenciaHardware.setString(15, "No aplica.");
                
                int filasAfectadas = sentenciaHardware.executeUpdate();
                
                if(filasAfectadas > 0){
                    resultadoOperacion = true;
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar registrar el equipo de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        
        return resultadoOperacion;
    }
    
    public static Hardware buscarHardwarePorNumeroSerie(String numeroSerie) throws SQLException{
        Hardware equipoComputoBusqueda = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
            String consulta = "SELECT * FROM hardware WHERE numeroSerie = ?";
            PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
            consultaHardware.setString(1, numeroSerie);
            ResultSet resultadoConsulta = consultaHardware.executeQuery();
                       
            if(resultadoConsulta.next()){
                equipoComputoBusqueda = new Hardware();
                equipoComputoBusqueda.setIdHardware(resultadoConsulta.getInt("idHardware"));
                equipoComputoBusqueda.setMarca(resultadoConsulta.getString("marca"));
                equipoComputoBusqueda.setModelo(resultadoConsulta.getString("modelo"));
                equipoComputoBusqueda.setNumeroSerie(resultadoConsulta.getString("numeroSerie"));
                equipoComputoBusqueda.setProcesador(resultadoConsulta.getString("procesador"));
                equipoComputoBusqueda.setArquitectura(resultadoConsulta.getInt("arquitectura"));
                equipoComputoBusqueda.setTarjetaMadre(resultadoConsulta.getString("tarjetaMadre"));
                equipoComputoBusqueda.setSistemaOperativo(resultadoConsulta.getString("sistemaOperativo"));
                equipoComputoBusqueda.setGrafica(resultadoConsulta.getString("grafica"));
                equipoComputoBusqueda.setRam(resultadoConsulta.getFloat("ram"));
                equipoComputoBusqueda.setDireccionMac(resultadoConsulta.getString("direccionMac"));
                equipoComputoBusqueda.setDireccionIp(resultadoConsulta.getString("direccionIp"));
                equipoComputoBusqueda.setAlmacenamiento(resultadoConsulta.getFloat("almacenamiento"));
                equipoComputoBusqueda.setEstado(resultadoConsulta.getString("estado"));
                equipoComputoBusqueda.setFechaIngreso(resultadoConsulta.getDate("fechaIngreso"));
                equipoComputoBusqueda.setPosicion(resultadoConsulta.getString("posicion"));
                equipoComputoBusqueda.setIdCentroComputo(resultadoConsulta.getInt("CentroComputo_idCentroComputo"));
                equipoComputoBusqueda.setAula(CentroComputoDAO.recuperarAulaCentroComputoPorIdCentroComputo(resultadoConsulta.getInt("CentroComputo_idCentroComputo")));
            }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar el equipo de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        
        return equipoComputoBusqueda;
    }
    
    public static boolean eliminarEquipoComputo(int idHardware) throws SQLException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "DELETE FROM hardware WHERE idHardware = ?";
                PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
                consultaHardware.setInt(1, idHardware);
                int filasAfectadas = consultaHardware.executeUpdate();
                
                if(filasAfectadas > 0){
                    resultadoOperacion = true;
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar eliminar el equipo de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde.", Alert.AlertType.ERROR);
        }
        
        return resultadoOperacion;
    }
    
    public static boolean EliminarSoftwareHardware(int idSoftware, int idHardware) throws SQLException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "delete from hardwaresoftware where Hardware_idHardware = ? and Software_idSoftware = ?";
                PreparedStatement eliminarSoftwareHardware = conexionBD.prepareStatement(consulta);
                eliminarSoftwareHardware.setInt(1, idSoftware);
                eliminarSoftwareHardware.setInt(2, idHardware);
                int numFilas = eliminarSoftwareHardware.executeUpdate();
                
                if(numFilas > 0){
                    resultadoOperacion = true;
                }else{
                    Utilidades.mostrarAlertaSimple("Error", 
                        "Error al intentar eliminar el software del hardware ",
                        Alert.AlertType.ERROR);
                    conexionBD.close();
                }
            }catch(SQLException e){
                e.getMessage();
            }finally{
                conexionBD.close();
            }
           
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", 
                    "No hay conexión con la base de datos, inténtelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
        return resultadoOperacion;
    }
    
    public static ArrayList<Hardware> recuperarHardwareSoftware(int idSoftware) throws SQLException{
        ArrayList<Hardware> hardwareBD = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idHardware, modelo, estado, posicion, marca FROM hardware inner join hardwaresoftware where hardwaresoftware.Software_idSoftware = ? and hardwaresoftware.Hardware_idHardware = idHardware";
                PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
                consultaHardware.setInt(1, idSoftware);
                ResultSet resultadoConsulta = consultaHardware.executeQuery();
                hardwareBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Hardware hardwareTemporal = new Hardware();
                    hardwareTemporal.setIdHardware(resultadoConsulta.getInt("idHardware"));
                    hardwareTemporal.setMarca(resultadoConsulta.getString("marca"));
                    hardwareTemporal.setModelo(resultadoConsulta.getString("modelo"));
                    hardwareTemporal.setPosicion(resultadoConsulta.getString("posicion"));
                    hardwareTemporal.setEstado(resultadoConsulta.getString("estado"));
                    hardwareBD.add(hardwareTemporal);
                }
                
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar los hardware registrados: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde..", Alert.AlertType.ERROR);
        }
        return hardwareBD;
    }
    
    public static boolean modificarEquipoDeComputo(Hardware equipoComputo) throws SQLException{
        boolean resultadoOperacion = false;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "UPDATE hardware SET marca = ?, modelo = ?, numeroSerie = ?, procesador = ?, arquitectura = ?, tarjetaMadre = ?, sistemaOperativo = ?, grafica = ?, ram = ?, direccionMac = ?, direccionIp = ?, almacenamiento = ?, CentroComputo_idCentroComputo = ?, posicion = ? WHERE idHardware = ?";
                PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
                consultaHardware.setString(1, equipoComputo.getMarca());
                consultaHardware.setString(2, equipoComputo.getModelo());
                consultaHardware.setString(3, equipoComputo.getNumeroSerie());
                consultaHardware.setString(4, equipoComputo.getProcesador());
                consultaHardware.setInt(5, equipoComputo.getArquitectura());
                consultaHardware.setString(6, equipoComputo.getTarjetaMadre());
                consultaHardware.setString(7, equipoComputo.getSistemaOperativo());
                consultaHardware.setString(8, equipoComputo.getGrafica());
                consultaHardware.setFloat(9, equipoComputo.getRam());
                consultaHardware.setString(10, equipoComputo.getDireccionMac());
                consultaHardware.setString(11, equipoComputo.getDireccionIp());
                consultaHardware.setFloat(12, equipoComputo.getAlmacenamiento());
                
                if(equipoComputo.getIdCentroComputo() != 0){
                    consultaHardware.setInt(13, equipoComputo.getIdCentroComputo());
                }else{
                    consultaHardware.setNull(13, java.sql.Types.INTEGER);
                }
                consultaHardware.setString(14, equipoComputo.getPosicion());
                consultaHardware.setInt(15, equipoComputo.getIdHardware());
                
                int filasAfectadas = consultaHardware.executeUpdate();
                if(filasAfectadas > 0){
                    resultadoOperacion = true;
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar modificar el equipo de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde..", Alert.AlertType.ERROR);
        }
        
        return resultadoOperacion;
    }
    
    public static boolean asignarEquipoComputoASoftware(int idHardware, int idSoftware) throws SQLException{
        Utilidades mensajeRespuesta = null;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        boolean resultado = false;
        if(conexionBD != null){
            try{
                String consultaRelacionHardwareSoftware = "INSERT INTO hardwaresoftware(Hardware_idHardware,Software_idSoftware)  VALUES (?,?); ";
                PreparedStatement consultaHardwareSoftware = conexionBD.prepareStatement(consultaRelacionHardwareSoftware);
                consultaHardwareSoftware.setInt(1, idHardware);
                consultaHardwareSoftware.setInt(2, idSoftware);
                int filasAfectadas = consultaHardwareSoftware.executeUpdate();
                 
                if(filasAfectadas > 0){
                    mensajeRespuesta.mostrarAlertaSimple("Operación finalizada con éxito",
                            "Se asignó el software al equipo de cómputo correctamente.",
                            Alert.AlertType.INFORMATION);
                    resultado = true;
                }else{
                    mensajeRespuesta.mostrarAlertaSimple("Operación fallida",
                            "No se pudo asignar el software al equipo de cómputo.",
                            Alert.AlertType.ERROR);
                }
            }catch(SQLException sqlException){
                mensajeRespuesta.mostrarAlertaSimple("Error",
                        "Algo ocurrió mal al intentar recuperar los software o hardware registrados: " + sqlException.getMessage(),
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
    
    public static int buscarIdHardwarePorPosicion(int idCentroComputo, String posicion, int idHardware) throws SQLException{
        int idHardwareConsulta = 0;
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idHardware FROM hardware WHERE CentroComputo_idCentroComputo = ? AND posicion = ? AND idHardware <> ?";
                PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
                consultaHardware.setInt(1, idCentroComputo);
                consultaHardware.setString(2, posicion);
                consultaHardware.setInt(3, idHardware);
                ResultSet resultadoConsulta = consultaHardware.executeQuery();
                if(resultadoConsulta.next()){
                    idHardwareConsulta = resultadoConsulta.getInt("idHardware");
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar el equipo de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde..", Alert.AlertType.ERROR);
        }
        
        return idHardwareConsulta;
    }
    
    public static String buscarNumeroSeriePorIdHardware(int idHardware) throws SQLException{
        String numeroSerie = "";
        Connection conexionBD = ConexionBaseDeDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT numeroSerie FROM hardware WHERE idHardware = ?";
                PreparedStatement consultaHardware = conexionBD.prepareStatement(consulta);
                consultaHardware.setInt(1, idHardware);
                ResultSet resultadoConsulta = consultaHardware.executeQuery();
                
                if(resultadoConsulta.next()){
                    numeroSerie = resultadoConsulta.getString("numeroSerie");
                }
            }catch(SQLException e){
                Utilidades.mostrarAlertaSimple("Error", "Algo ocurrió mal al intentar recuperar el equipo de cómputo: " + e.getMessage(), Alert.AlertType.ERROR);
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexión con la base de datos, inténtelo más tarde..", Alert.AlertType.ERROR);
        }
        
        return numeroSerie;
    }
}
