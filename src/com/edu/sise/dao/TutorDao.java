/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.dao;

import com.edu.sise.entidades.Tutor;
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
public class TutorDao {
    
    //atributos
    Connection cn = null;
    Statement st =null;
    ResultSet rs = null;
    CallableStatement cs = null;
    final String INSERTAR = "{call pa_insertar_tutores(?,?,?,?,?,?,?)}";
    final String MODIFICAR = "{call pa_modificar_tutor(?,?,?,?,?,?,?,?)}";
    final String ELIMINAR = "{call pa_eliminar_tutor(?)}";
    final String TODOS = "{call pa_listar_tutores()}";
    final String FILTRO = "{call pa_buscar_tutores(?)}";
    
    
    public TutorDao(){
        cn = new Conexion().getConn();
    }
    
    public List<Tutor> getAll(){
        
        List<Tutor> lista = new ArrayList<>();
        //String sql = "{call pa_listar_tutores()}";
        try {
            //st = cn.createStatement();
            cs = cn.prepareCall(TODOS);
            //rs = st.executeQuery(sql);
            rs = cs.executeQuery();
            while(rs.next()){ //recorro mi tabla virtual
                lista.add(new Tutor(
                        rs.getInt("id_tutor"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("papellido"),
                        rs.getString("sapellido"),
                        rs.getDate("fnacimiento").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getInt("id_prov")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
        return lista;
    }
    
    //metodo para agregar un registro en base de datos
    public boolean agregarTutor(Tutor o){
        
        int cantidad = 0;
        //crear nuestro script de agregar
        //String sql ="{call pa_insertar_tutores(?,?,?,?,?,?,?)}";
        
        try{
            cs = cn.prepareCall(INSERTAR);
            int x = 1;
            cs.setString(x++, o.getDni());
            cs.setString(x++, o.getNombre());
            cs.setString(x++, o.getPapellido());
            cs.setString(x++, o.getSapellido());
            cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
            cs.setString(x++, o.getTelefono());
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
    public boolean modificarTutor(Tutor o){
        
        int cantidad = 0;
        //crear nuestro script de modificar
        //String sql ="{call pa_modificar_tutor(?,?,?,?,?,?,?,?)}";
        
        try{
            cs = cn.prepareCall(MODIFICAR);
            int x = 1;
            cs.setString(x++, o.getDni());
            cs.setString(x++, o.getNombre());
            cs.setString(x++, o.getPapellido());
            cs.setString(x++, o.getSapellido());
            cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
            cs.setString(x++, o.getTelefono());
            cs.setInt(x++, o.getId_prov());
            cs.setInt(x++, o.getId_tutor());
            cantidad = cs.executeUpdate();
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    
    //metodo para eliminar un registro en base de datos
    public boolean eliminarTutor(Integer id){
        
        int cantidad = 0;
        //crear nuestro script de eliminar
        //String sql ="{call pa_eliminar_tutor(?)}";
        
        try{
            cs = cn.prepareCall(ELIMINAR);
            cs.setInt(1, id);
            cantidad = cs.executeUpdate();
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    
    public int[] cargaMasiva(List<Tutor> lista){
        int[] rpta = null;
        //String sql = "{call pa_insertar_tutores(?,?,?,?,?,?,?)}";
        
        try {
            cs = cn.prepareCall(INSERTAR);
            for(Tutor o: lista){
                int x = 1;
                cs.setString(x++, o.getDni());
                cs.setString(x++, o.getNombre());
                cs.setString(x++, o.getPapellido());
                cs.setString(x++, o.getSapellido());
                cs.setDate(x++, Date.valueOf(o.getFnacimiento()));
                cs.setString(x++, o.getTelefono());
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
    public List<Tutor> getSearch(String nombre){
        
        List<Tutor> lista = new ArrayList<>();
        //String sql = "{call pa_buscar_tutores(?)}";
        try {
            //st = cn.createStatement();
            //rs = st.executeQuery(sql);
            cs = cn.prepareCall(FILTRO);
            cs.setString(1, nombre);
            rs = cs.executeQuery();
            while(rs.next()) {
                lista.add(new Tutor(
                        rs.getInt("id_tutor"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("papellido"),
                        rs.getString("sapellido"),
                        rs.getDate("fnacimiento").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getInt("id_prov")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
        return lista;
    }

}
