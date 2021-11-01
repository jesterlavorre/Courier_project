/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

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
public class AddressOperations implements rs.etf.sab.operations.AddressOperations{
    
    private final Connection connection = DB.getInstance().getConnection();
    
    @Override
    public int insertAddress(String street, int number, int cityId, int x, int y) {
         
        if(street == null)return -1;
        
        String proveraGrada = "SELECT COUNT(*) FROM Grad WHERE IdGra=?";
        try {
            PreparedStatement psGrad = connection.prepareStatement(proveraGrada);
            psGrad.setInt(1, cityId);
            ResultSet rs = psGrad.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0)return -1;
            }
        } catch (SQLException ex) {
            System.out.println("u prvom delu");
            return -1;
        }
      /*  String proveraAdrese = "SELECT * FROM Adresa WHERE Ulica=? and Broj=? and Grad=?";
        try {
            PreparedStatement psAdresa = connection.prepareStatement(proveraAdrese);
            psAdresa.setString(1, street);
            psAdresa.setInt(2, number);
            psAdresa.setInt(3, cityId);
            ResultSet rs1 = psAdresa.executeQuery();
            if(rs1.next()){
                if(rs1.getInt(1)!=0)return -1;
            }
        } catch (SQLException ex) {
            System.out.println("u drugom delu");
            return -1;
        }*/
        String ubaciAdresu = "INSERT INTO Adresa(Ulica,Broj,Grad,Xkor,Ykor) VALUES(?,?,?,?,?)";
        PreparedStatement psUbaciAdresu;
        
        try {
            psUbaciAdresu = connection.prepareStatement(ubaciAdresu,PreparedStatement.RETURN_GENERATED_KEYS);
            psUbaciAdresu.setString(1, street);
            psUbaciAdresu.setInt(2, number);
            psUbaciAdresu.setInt(3, cityId);
            psUbaciAdresu.setInt(4, x);
            psUbaciAdresu.setInt(5, y);
            psUbaciAdresu.executeUpdate();
            ResultSet rs2 = psUbaciAdresu.getGeneratedKeys();
            if(rs2.next()){
                   return rs2.getInt(1);
                }
        } catch (SQLException ex) {
            System.out.println("u trecem delu");
            return -1;
        }
        return -1;

    }

    @Override
    public int deleteAddresses(String name, int number) {

            if(name == null)return -1;
            
            String deleteQ = "DELETE FROM Adresa WHERE Ulica=? and Broj=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setString(1, name);
             psDelete.setInt(2, number);
            return psDelete.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
             
         }
         return -1;
    }

    @Override
    public boolean deleteAdress(int i) {
            String deleteQ = "DELETE FROM Adresa WHERE IdAdr=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setInt(1, i);
             if(psDelete.executeUpdate() > 0)return true;
             else return false;
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
             
         }
         return false;
    }
    @Override
    public int deleteAllAddressesFromCity(int i) {
        
          String deleteQ = "DELETE FROM Adresa WHERE Grad=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setInt(1, i);
             return psDelete.executeUpdate();

         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
         return -1;
    }

    @Override
    public List<Integer> getAllAddresses() {
           
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdAdr FROM Adresa";
        
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 lista.add(rs.getInt(1));
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
     return lista;   
    }

    @Override
    public List<Integer> getAllAddressesFromCity(int i) {
          String proveraGrada = "SELECT COUNT(*) FROM Grad WHERE IdGra=?";
        try {
            PreparedStatement psGrad = connection.prepareStatement(proveraGrada);
            psGrad.setInt(1, i);
            ResultSet rs = psGrad.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0)return null;
            }
        } catch (SQLException ex) {
            System.out.println("u prvom delu");
            return null;
        }
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdAdr FROM Adresa WHERE Grad=? ";
        
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ps.setInt(1, i);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 lista.add(rs.getInt(1));
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
     return lista;   
    }

   
    
}
