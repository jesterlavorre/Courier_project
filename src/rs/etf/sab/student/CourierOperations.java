/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
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
public class CourierOperations implements rs.etf.sab.operations.CourierOperations {
    private final Connection connection=DB.getInstance().getConnection();
    @Override
    public boolean insertCourier(String user, String vozacka) {
         String proveraKor = "SELECT COUNT(*) FROM Korisnik WHERE KorIme=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraKor);
                ps1.setString(1, user);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return false;
                }  
            } catch (SQLException ex) {
                System.out.println("1");
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            String proveraKor1 = "SELECT COUNT(*) FROM Kurir WHERE VozackaDozvola=? OR KorIme=?";
            try {
                PreparedStatement ps2 = connection.prepareStatement(proveraKor1);
                ps2.setString(1, vozacka);
                ps2.setString(2, user);
                ResultSet rs2=ps2.executeQuery();
                if(rs2.next()){
                    if(rs2.getInt(1)!=0)return false;
                }  
            } catch (SQLException ex) {
                System.out.println("2");
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            int idadr=0;
            String gdeje = "SELECT Adresa FROM Korisnik WHERE KorIme=?";
            try {
                PreparedStatement ps3 = connection.prepareStatement(gdeje);
                ps3.setString(1, user);
                ResultSet rs3=ps3.executeQuery();
                rs3.next();
                idadr=rs3.getInt(1);
            } catch (SQLException ex) {
                System.out.println("3");
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
          String insertQ = "INSERT INTO Kurir (KorIme,VozackaDozvola,BrIsporucenihPaketa,Profit,Status,trenutnaLokacija) VALUES (?,?,0,0.00,0,?)";
          //System.out.println(idadr+"adresa");

        try(PreparedStatement psInsert = connection.prepareStatement(insertQ)){
            psInsert.setString(1, user);
            psInsert.setString(2, vozacka);
            psInsert.setInt(3, idadr);
            if(psInsert.executeUpdate()>0) return true;
        }catch(SQLException ex){
            System.out.println("4");
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteCourier(String string) {
       String deleteQ = "DELETE FROM Kurir WHERE KorIme=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setString(1, string);
            if( psDelete.executeUpdate()>0)return true;
            else return false;
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        return true;
    }

    @Override
    public List<String> getCouriersWithStatus(int i) {
        List<String> lista = new LinkedList<>();
        String listQ = "SELECT KorIme FROM Kurir WHERE Status=?";
        
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ps.setInt(1, i);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 lista.add(rs.getString(1));
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
     return lista; 
    }

    @Override
    public List<String> getAllCouriers() {
        List<String> lista = new LinkedList<>();
        String listQ = "SELECT KorIme FROM Kurir";
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 lista.add(rs.getString(1));
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
     return lista; 
    }

    @Override
    public BigDecimal getAverageCourierProfit(int i) {
        
        if(i==-1){
            String svih = "SELECT AVG(Profit) FROM Kurir";
            try {
                PreparedStatement ps=connection.prepareStatement(svih);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getBigDecimal(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
             String svih = "SELECT AVG(Profit) FROM Kurir BrIsporucenihPaketa=?";
            try {
                PreparedStatement ps=connection.prepareStatement(svih);
                ps.setInt(1, i);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getBigDecimal(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return null;
    }


    
}
