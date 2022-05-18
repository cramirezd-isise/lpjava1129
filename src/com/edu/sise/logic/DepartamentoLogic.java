/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.sise.logic;

import com.edu.sise.dao.DAOManager;
import com.edu.sise.dao.DepartamentoDao;
import com.edu.sise.entidades.Departamento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class DepartamentoLogic {
    
    List<Departamento> lista;
    DepartamentoDao dao = DAOManager.getInstancia().getDepartamentoDao();
    
    public DefaultTableModel getModelo(){
        //cargarLista();
        lista = dao.getAll();
        DefaultTableModel modelo = new DefaultTableModel();
        //crear mis columnas
        modelo.addColumn("ID");
        modelo.addColumn("NOMBRE");
        for(Departamento obj : lista){
            Object data[] ={
                obj.getId_depa(),
                obj.getNombre()
            };
            
            modelo.addRow(data);
        }
        return modelo;
    }
    
    
//    public void cargarLista(){
//        lista = new ArrayList<>();
//        
//        lista.add(new Departamento(100, "LA LIBERTAD"));
//        lista.add(new Departamento(200, "LIMA"));
//        lista.add(new Departamento(300, "TUMBES"));
//        lista.add(new Departamento(400, "LAMBAYEQUE"));
//    }
    
    public void imprimir(JTable tabla){
        tabla.setModel(getModelo());
    }
    
    public boolean agregarDepartamento(Departamento o){
        return dao.agregarDepartamento(o);
    }
    
    public boolean modificarDepartamento(Departamento o){
        return dao.modificarDepartamento(o);
    }
    
    public boolean eliminarDepartamento(Integer id){
        return dao.eliminarDepartamento(id);
    }
    
    //metodo para llenar información del combo departamento
    public void llenarComboDepartamento(JComboBox cbo){
        lista = dao.getAll();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
         for(Departamento obj : lista){
             modelo.addElement(obj);
         }
         
         //actualiza la información en el combo
         cbo.setModel(modelo);
    }
    
    public void seleccionarItemCbo(JComboBox cbo, Integer id){
        DefaultComboBoxModel modelo = (DefaultComboBoxModel)cbo.getModel();
        
        for(int i=0; i<modelo.getSize();i++){
            if(((Departamento)modelo.getElementAt(i)).getId_depa()==id){
                modelo.setSelectedItem(modelo.getElementAt(i));
            }
        }
    }
    
}
