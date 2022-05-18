/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Carlos
 */
public class Conexion {
    
    //declarar nuestra url, user (login) y password
    //apuntan a su misma máquina existe dos formas:
    //localhost o 127.0.0.1
    private static String url="jdbc:mysql://localhost:3306/lpjava1129";
    private static String user="root";
    private static String password="123456";
    
    //declarar el Driver
    
    private static String driver="com.mysql.cj.jdbc.Driver";
    
    //declarar el tipo Conecction
    
    Connection conn = null;
    
    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error en Driver: " + ex.getMessage());
        }
    }
    
    public Connection getConn(){
        
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("Error en DriverManager: " + ex.getMessage());
        }
        
        return conn;
    }
}
