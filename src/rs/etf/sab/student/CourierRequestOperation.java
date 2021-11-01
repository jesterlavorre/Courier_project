/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import rs.etf.sab.student.*;
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
public class CourierRequestOperation implements rs.etf.sab.operations.CourierRequestOperation{
private final Connection connection=DB.getInstance().getConnection();
    @Override
    public boolean insertCourierRequest(String user, String vozacka) {
        if(user==null || vozacka==null)return false;
         String proveraKor = "SELECT COUNT(*) FROM Korisnik WHERE KorIme=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraKor);
                ps1.setString(1, user);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return false;
                }  
            } catch (SQLException ex) {
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("glusdapan");
                 return false;
            }
        String proveraKor1 = "SELECT COUNT(*) FROM Kurir WHERE KorIme=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraKor1);
                ps1.setString(1, user);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)!=0)return false;
                }  
            } catch (SQLException ex) {
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("glusdasdpan");
                 return false;
            }
            
       String zahtevprov="SELECT COUNT(*) FROM ZAHTEVKURIR WHERE VozackaDozvola=?";     
    try {
        PreparedStatement pszahtev=connection.prepareStatement(zahtevprov);
        
        pszahtev.setString(1, vozacka);
        ResultSet rszahtev = pszahtev.executeQuery();
        if(rszahtev.next()){
            if(rszahtev.getInt(1)>0)return false;
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
    }
            
            
            
            String insertQ = "INSERT INTO ZAHTEVKURIR (Korisnik,VozackaDozvola) VALUES(?,?)";
             PreparedStatement psInsert;
     
         try {
             psInsert = connection.prepareStatement(insertQ);
              psInsert.setString(1, user);
              psInsert.setString(2, vozacka);
               if(psInsert.executeUpdate()>0)return true;
              
         } catch (SQLException ex) {
             System.out.println("ovde");
             return false;
         }
    return false;
    }

    @Override
    public boolean deleteCourierRequest(String user) {
        if(user==null)return false;
        String deleteQ = "DELETE FROM ZAHTEVKURIR WHERE Korisnik=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setString(1, user);
            return psDelete.executeUpdate()>0;
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        return true;
    }

    @Override
    public boolean changeDriverLicenceNumberInCourierRequest(String user, String vozacka) {
        if(user==null||vozacka==null)return false;
        String proveraKor1 = "SELECT COUNT(*) FROM ZAHTEVKURIR WHERE Korisnik=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraKor1);
                ps1.setString(1, user);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return false;
                }  
            } catch (SQLException ex) {
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("glupan");
                 return false;
            }
            
            String insertQ = "UPDATE ZAHTEVKURIR SET VozackaDozvola=? WHERE Korisnik=?";
             PreparedStatement psUpdate;
     
         try {
             psUpdate = connection.prepareStatement(insertQ);
              psUpdate.setString(1, vozacka);
              psUpdate.setString(2, user);
               if(psUpdate.executeUpdate()>0)return true;
              
         } catch (SQLException ex) {
             System.out.println("glupan");
             return false;
         }
    return false;
            
    }

    @Override
    public List<String> getAllCourierRequests() {
               
        List<String> lista = new LinkedList<>();
        String listQ = "SELECT Korisnik FROM ZAHTEVKURIR";
        
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 lista.add(rs.getString(1));
             }
             return lista; 
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
     return lista;   
    
    }

    @Override
    public boolean grantRequest(String user) {
        if(user==null)return false;
        String proveraKor1 = "SELECT COUNT(*) FROM ZAHTEVKURIR WHERE Korisnik=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraKor1);
                ps1.setString(1, user);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return false;
                }  
            } catch (SQLException ex) {
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("asfsafa");
                 return false;
            }
            String vozacka = "SELECT VozackaDozvola FROM ZAHTEVKURIR WHERE Korisnik=?";
            String izvadjenavozacka=null;
            try {
                PreparedStatement ps1 = connection.prepareStatement(vozacka);
                ps1.setString(1, user);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getString(1)==null)return false;
                        izvadjenavozacka = rs1.getString(1);
                        CourierOperations CourierOperations = new CourierOperations();
                        if(CourierOperations.insertCourier(user, izvadjenavozacka))
                         if(this.deleteCourierRequest(user))
                         return true;
                         else System.out.println(user);
                        else System.out.println(user+"ja"+izvadjenavozacka);
                }  
            } catch (SQLException ex) {
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("kurasafsf");
                 return false;
            }
    return false;
           
          
    }
    
}
