/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.utils;

/**
 *
 * @author Carlos
 */
public class Utils {
    
    public static boolean _esVacio(String valor){
        return (valor.isEmpty() || valor==null || valor.trim().length()==0);
    }
}
