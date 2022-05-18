/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.dao;

import com.edu.sise.entidades.Carrera;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class CarreraDao {
    
    //atributos
    Connection cn = null;
    Statement st =null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    
    public CarreraDao(){
        cn = new Conexion().getConn();
        System.out.println("Se instancio CarreraDAO");
    }
    
    private Carrera getRS(ResultSet record) throws SQLException{
        return new Carrera(
                        record.getInt("id_carrera"),
                        record.getString("nombre"));
    }
    
    public List<Carrera> getAll(){
        
        List<Carrera> lista = new ArrayList<>();
        String sql = "select * from carreras";
        try {
            //st = cn.createStatement();
            //rs = st.executeQuery(sql);
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) lista.add(getRS(rs));
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
        return lista;
    }
    //metodo de busqueda por nombre
    public List<Carrera> getSearch(String nombre){
        
        List<Carrera> lista = new ArrayList<>();
        String sql = "select * from carreras where nombre like ?";
        try {
            //st = cn.createStatement();
            //rs = st.executeQuery(sql);
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%"+nombre+"%");
            rs = ps.executeQuery();
            while(rs.next()) lista.add(getRS(rs));
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
        return lista;
    }
    
    //metodo para agregar un registro en base de datos
    public boolean agregarCarrera(Carrera o){
        
        int cantidad = 0;
        //crear nuestro script de agregar
        String sql ="insert into carreras(nombre) values(?)";
        
        try{
//            st = cn.createStatement();
//            cantidad = st.executeUpdate(sql);
            ps = cn.prepareStatement(sql);
            ps.setString(1, o.getNombre());
            cantidad = ps.executeUpdate();
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    
    //metodo para modificar un registro en base de datos
    public boolean modificarCarrera(Carrera o){
        
        int cantidad = 0;
        //crear nuestro script de modificar
        String sql ="update carreras set nombre = ? where id_carrera = ? " ;
        
        try{
//            st = cn.createStatement();
//            cantidad = st.executeUpdate(sql);
              ps = cn.prepareStatement(sql);
              int x =1;
              ps.setString(x++, o.getNombre());
              ps.setInt(x++, o.getId_carrera());
              cantidad = ps.executeUpdate();
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    
    //metodo para eliminar un registro en base de datos
    public boolean eliminarCarrera(Integer id){
        
        int cantidad = 0;
        //crear nuestro script de eliminar
        String sql ="delete from carreras where id_carrera = ?";
        
        try{
//            st = cn.createStatement();
//            cantidad = st.executeUpdate(sql);
              ps = cn.prepareStatement(sql);
              ps.setInt(1, id);
              cantidad = ps.executeUpdate();
        }catch(Exception ex){
             System.out.println("Error SQL: " + ex.getMessage());
        }
        
        return (cantidad>0);
    }
    

}
