/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.dao;

import com.edu.sise.entidades.Profesor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ProfesorDao {
    
    //atributos
    Connection cn = null;
    Statement st =null;
    ResultSet rs = null;
    CallableStatement cs = null;
    final String INSERTAR = "{call pa_insertar_profesores(?,?,?,?,?,?,?,?)}";
    final String MODIFICAR = "{call pa_modificar_profesor(?,?,?,?,?,?,?,?,?)}";
    final String ELIMINAR = "{call pa_eliminar_profesor(?)}";
    final String TODOS = "{call pa_listar_profesores()}";
    final String FILTRO = "{call pa_buscar_profesores(?)}";
    
    
    public ProfesorDao(){
        cn = new Conexion().getConn();
    }
    
    public List<Profesor> getAll(){
        
        List<Profesor> lista = new ArrayList<>();
        //String sql = "{call pa_listar_profesores()}";
        try {
            //st = cn.createStatement();
            cs = cn.prepareCall(TODOS);
            //rs = st.executeQuery(sql);
            rs = cs.executeQuery();
            while(rs.next()){ //recorro mi tabla virtual
                lista.add(new Profesor(
                        rs.getInt("id_profe"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("papellido"),
                        rs.getString("sapellido"),
                        rs.getDate("fnacimiento").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getInt("id_carrera"),
                        rs.getInt("id_prov")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
        return lista;
    }
    
    //metodo para agregar un registro en base de datos
    public boolean agregarProfesor(Profesor o){
        
        int cantidad = 0;
        //crear nuestro script de agregar
        //String sql ="{call pa_insertar_profesores(?,?,?,?,?,?,?)}";
        
        try{
            cs = cn.prepareCall(INSERTAR);
            int x = 1;
            cs.setString(x++, o.getDni());
            cs.setString(x++, o.getNombre());
            cs.setString(x++, o.getPapellido());
            cs.setString(x++, o.getSapellido());
            cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
            cs.setString(x++, o.getTelefono());
            cs.setInt(x++, o.getId_carrera());
            cs.setInt(x++, o.getId_prov());
            cantidad = cs.executeUpdate();
            //st = cn.createStatement();
            //cantidad = st.executeUpdate(sql);
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    
    //metodo para modificar un registro en base de datos
    public boolean modificarProfesor(Profesor o){
        
        int cantidad = 0;
        //crear nuestro script de modificar
        //String sql ="{call pa_modificar_profesor(?,?,?,?,?,?,?,?)}";
        
        try{
            cs = cn.prepareCall(MODIFICAR);
            int x = 1;
            cs.setString(x++, o.getDni());
            cs.setString(x++, o.getNombre());
            cs.setString(x++, o.getPapellido());
            cs.setString(x++, o.getSapellido());
            cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
            cs.setString(x++, o.getTelefono());
            cs.setInt(x++, o.getId_carrera());
            cs.setInt(x++, o.getId_prov());
            cs.setInt(x++, o.getId_profe());
            cantidad = cs.executeUpdate();
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    
    //metodo para eliminar un registro en base de datos
    public boolean eliminarProfesor(Integer id){
        
        int cantidad = 0;
        //crear nuestro script de eliminar
        //String sql ="{call pa_eliminar_profesor(?)}";
        
        try{
            cs = cn.prepareCall(ELIMINAR);
            cs.setInt(1, id);
            cantidad = cs.executeUpdate();
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    
    public int[] cargaMasiva(List<Profesor> lista){
        int[] rpta = null;
        //String sql = "{call pa_insertar_profesores(?,?,?,?,?,?,?)}";
        
        try {
            cs = cn.prepareCall(INSERTAR);
            for(Profesor o: lista){
                int x = 1;
                cs.setString(x++, o.getDni());
                cs.setString(x++, o.getNombre());
                cs.setString(x++, o.getPapellido());
                cs.setString(x++, o.getSapellido());
                cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
                cs.setString(x++, o.getTelefono());
                cs.setInt(x++, o.getId_carrera());
                cs.setInt(x++, o.getId_prov());
                cs.addBatch();
            }
            
            rpta = cs.executeBatch();
            
        } catch (Exception ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
        return rpta;
    }
    
    //metodo de busqueda por nombre
    public List<Profesor> getSearch(String nombre){
        
        List<Profesor> lista = new ArrayList<>();
        //String sql = "{call pa_buscar_profesores(?)}";
        try {
            //st = cn.createStatement();
            //rs = st.executeQuery(sql);
            cs = cn.prepareCall(FILTRO);
            cs.setString(1, nombre);
            rs = cs.executeQuery();
            while(rs.next()) {
                lista.add(new Profesor(
                        rs.getInt("id_profe"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("papellido"),
                        rs.getString("sapellido"),
                        rs.getDate("fnacimiento").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getInt("id_carrera"),
                        rs.getInt("id_prov")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
        return lista;
    }

}
