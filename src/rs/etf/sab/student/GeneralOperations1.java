/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikola
 */
public class GeneralOperations1 implements rs.etf.sab.operations.GeneralOperations {
    
    private final Connection connection=DB.getInstance().getConnection();
    
    @Override
    public void eraseAll() {
        String s1 = "DELETE FROM Adresa";
        String s = "DELETE FROM Grad";
        String s2 = "DELETE FROM Magacin";
        String s3 = "DELETE FROM Korisnik";
        String s4 = "DELETE FROM ZAHTEVKURIR";
        String s5 = "DELETE FROM Kurir";
        String s6 = "DELETE FROM Vozilo";
        String s7 = "DELETE FROM uMagacinu";
        String s8 = "DELETE FROM Vozio";
        String s9 = "DELETE FROM trenutnoVozi";
        String s10 = "DELETE FROM Paket";
        
        PreparedStatement ps10;
        try {
            ps10 = connection.prepareStatement(s10);
             ps10.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        PreparedStatement ps9;
        try {
            ps9 = connection.prepareStatement(s9);
             ps9.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        PreparedStatement ps8;
        try {
            ps8 = connection.prepareStatement(s8);
             ps8.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        } 
        PreparedStatement ps7;
        try {
            ps7 = connection.prepareStatement(s7);
             ps7.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
         PreparedStatement ps6;
        try {
            ps6 = connection.prepareStatement(s6);
             ps6.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
         PreparedStatement ps5;
        try {
            ps5 = connection.prepareStatement(s5);
             ps5.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
         PreparedStatement ps4;
        try {
            ps4 = connection.prepareStatement(s4);
             ps4.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        }   
           PreparedStatement ps3;
        try {
            ps3 = connection.prepareStatement(s3);
             ps3.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            
            PreparedStatement ps2 = connection.prepareStatement(s2);
            ps2.executeUpdate();
   
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         PreparedStatement ps1;
        try {
            ps1 = connection.prepareStatement(s1);
            ps1.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        }
            
          
            
            
          PreparedStatement ps;
        try {
            ps = connection.prepareStatement(s);
             ps.executeUpdate();  
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneralOperations1.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
    }
    
}
