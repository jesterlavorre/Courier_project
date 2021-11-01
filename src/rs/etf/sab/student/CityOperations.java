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
public class CityOperations implements rs.etf.sab.operations.CityOperations {
    
    private final Connection connection=DB.getInstance().getConnection();

    @Override
    public int insertCity(String name, String postalCode) {
   
             if(name == null || postalCode == null) return -1;
             
             String check = "SELECT IdGra FROM Grad WHERE PostanskiBroj=?";
             
             
             PreparedStatement psCheck;
         try {
             psCheck = connection.prepareStatement(check);
            // psCheck.setString(1, name);
             psCheck.setString(1, postalCode);
             ResultSet rsCheck = psCheck.executeQuery();
             if(rsCheck.next()){
                 if(rsCheck.getInt(1) > 0) return -1;
              }
         } catch (SQLException ex) {
             System.out.println("glupan");
             return -1;
         }
            String insertQ = "INSERT INTO Grad (Naziv,PostanskiBroj) VALUES(?,?)";
             PreparedStatement psInsert;
     
         try {
             psInsert = connection.prepareStatement(insertQ,PreparedStatement.RETURN_GENERATED_KEYS);
              psInsert.setString(1, name);
              psInsert.setString(2, postalCode);
               psInsert.executeUpdate();
               ResultSet rsInsert = psInsert.getGeneratedKeys();
               if(rsInsert.next()){
                   return rsInsert.getInt(1);
                }
         } catch (SQLException ex) {
             System.out.println("glupan");
             return -1;
         }
             

                
         return -1;
    }

    @Override
    public int deleteCity(String... cities) {
        int izbrisani = 0;
       
        
        for(int i = 0; i < cities.length; i++){
            if(cities[i] == null)continue;
            
            String deleteQ = "DELETE FROM Grad WHERE Naziv=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setString(1, cities[i]);
             izbrisani+=psDelete.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        }
        return izbrisani;
    }

    @Override
    public boolean deleteCity(int i) {
        
            String deleteQ = "DELETE FROM Grad WHERE IdGra=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setInt(1, i);
            if( psDelete.executeUpdate()>0)return true;
            else return false;
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        return true;
    }

    @Override
    public List<Integer> getAllCities() {
        
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdGra FROM Grad";
        
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
        public int getCityForAddress(int idA){
        String sql="SELECT IdGra FROM Adresa WHERE IdAdr=?";
        try(PreparedStatement pstmt=connection.prepareStatement(sql)){
            pstmt.setInt(1,idA);
           ResultSet rs=pstmt.executeQuery();
           if(rs.next()){
               return rs.getInt(1);
               
           }else return -1;
           
        }catch(SQLException e){
            return -1;
        
        }  
        
    }
}
