/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediateca.form.audio;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import mediateca.form.Conexion;

/**
 *
 * @author maria
 */
public class ArtistaCrud {
//Nos apoyamos de la llave primaria auto incrementablede MySql
//por lo que se omite el campo de id
//Se utiliz aun prepareStatement, por lo que podemos
//utilizar parametros (signos de ?)
//los cuales posteriormente será sustituidos por el parametro respectivo

    private final String SQL_INSERT = "INSERT INTO artista (nombre) VALUES(?)";
    private final String SQL_UPDATE = "UPDATE artista SET nombres=? WHERE id=?";
    private final String SQL_DELETE = "DELETE FROM artista WHERE id = ?";
    private final String SQL_SELECT = "SELECT id, nombre FROM artista ORDER BY nombre";

    ;
/**
* Metodo que inserta un registro en la tabla de Artista
* @param nombres
* @return rows el numero de filas afectadas
*/
public int insert(String nombres) {

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0; //registrosafectados
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;//contador de columnas
            stmt.setString(index++, nombres);//param 2 => ?
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();//no. registrosafectados
            System.out.println("Registros afectados:" + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    /**
     * Metodo que actualiza un registro existente
     *
     * @param nombres
     * @return 
     * @returnint No. de registros modificados
     */
    public int update(String nombres) {

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, nombres);//param 2 => ?
            rows = stmt.executeUpdate();
            System.out.println("Registros actualizados:" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    /**
     * Metodo que elimina un registro existente
     *
     * @param id Es la llave primaria
     * @return int No. registros afectados
     */
    public int delete(int id) {

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    /**
     * Metodo que regresa el contenido de la tabla de estudiantes
     */
    public DefaultTableModel selectArtistas() {

        DefaultTableModel dtm = new DefaultTableModel();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            //Formando encabezado
            for (int i = 1; i <= numberOfColumns; i++) {
                dtm.addColumn(meta.getColumnLabel(i));
            }
            //Creandolasfilaspara el JTable
            while (rs.next()) {
                Object[] fila = new Object[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                dtm.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return dtm;
    }
}
