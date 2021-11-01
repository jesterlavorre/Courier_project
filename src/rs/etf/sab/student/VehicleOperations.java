/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
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
public class VehicleOperations implements rs.etf.sab.operations.VehicleOperations{
private final Connection connection=DB.getInstance().getConnection();
    @Override
    public boolean insertVehicle(String tablica, int tipgoriva, BigDecimal potrosnja, BigDecimal kapacitet) {
        if(tablica==null)return false;
        
        String prov = "SELECT COUNT(*) FROM Vozilo WHERE RegBr=?";
        
    try {
        PreparedStatement psprov = connection.prepareStatement(prov);
        psprov.setString(1, tablica);
       ResultSet rsprov = psprov.executeQuery();
        if(rsprov.next()){
            if(rsprov.getInt(1)>0)return false;
        } 
    } catch (SQLException ex) {
        System.out.println("ovde");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    String insert = "INSERT INTO Vozilo(RegBr,TipGoriva,Potrosnja,Nosivost)VALUES(?,?,?,?)";
    
    try {
        PreparedStatement psinsert = connection.prepareStatement(insert);
        psinsert.setString(1, tablica);
        psinsert.setInt(2, tipgoriva);
        psinsert.setBigDecimal(3, potrosnja);
        psinsert.setBigDecimal(4, kapacitet);
        if(psinsert.executeUpdate()>0)return true;
                } catch (SQLException ex) {
                     System.out.println("ovde123");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
        
        
    }

    @Override
    public int deleteVehicles(String... vozila) {
        int izbrisani = 0; 
        for(int i = 0; i < vozila.length; i++){
            if(vozila[i] == null)continue;
            
            String deleteQ = "DELETE FROM Vozilo WHERE RegBr=?";
                PreparedStatement psDelete;
         try {
             psDelete = connection.prepareStatement(deleteQ);
             psDelete.setString(1, vozila[i]);
             izbrisani+=psDelete.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        }
        return izbrisani;
    }

    @Override
    public List<String> getAllVehichles() {
        List<String> lista = new LinkedList<>();
        String listQ = "SELECT RegBr FROM Vozilo";
        
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
    public boolean changeFuelType(String tablica, int i) {
        String provera = "SELECT COUNT(*) FROM uMagacinu WHERE RegBr=? and inStock=1";
    try {
        PreparedStatement psprov = connection.prepareStatement(provera);
        psprov.setString(1, tablica);
        ResultSet rsprov=psprov.executeQuery();
        if(rsprov.next()){
            if(rsprov.getInt(1)==0)return false;
        }
    } catch (SQLException ex) {
        System.out.println("ovde");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
        String insert = "UPDATE Vozilo SET TipGoriva=? WHERE RegBr=?";
    try {
        PreparedStatement psinsert = connection.prepareStatement(insert);
        psinsert.setInt(1, i);
        psinsert.setString(2, tablica);
        if(psinsert.executeUpdate()>0)return true;
    } catch (SQLException ex) {
        System.out.println("ovde1");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
        
    }

    @Override
    public boolean changeConsumption(String tablica, BigDecimal bd) {
         String provera = "SELECT COUNT(*) FROM uMagacinu WHERE RegBr=? and inStock=1";
    try {
        PreparedStatement psprov = connection.prepareStatement(provera);
        psprov.setString(1, tablica);
        ResultSet rsprov=psprov.executeQuery();
        if(rsprov.next()){
            if(rsprov.getInt(1)==0)return false;
        }
    } catch (SQLException ex) {
        System.out.println("ovde");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
        String insert = "UPDATE Vozilo SET Potrosnja=? WHERE RegBr=?";
    try {
        PreparedStatement psinsert = connection.prepareStatement(insert);
        psinsert.setBigDecimal(1, bd);
        psinsert.setString(2, tablica);
        if(psinsert.executeUpdate()>0)return true;
    } catch (SQLException ex) {
        System.out.println("ovde1");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
    }

    @Override
    public boolean changeCapacity(String tablica, BigDecimal bd) {
        String provera = "SELECT COUNT(*) FROM uMagacinu WHERE RegBr=? and inStock=1";
    try {
        PreparedStatement psprov = connection.prepareStatement(provera);
        psprov.setString(1, tablica);
        ResultSet rsprov=psprov.executeQuery();
        if(rsprov.next()){
            if(rsprov.getInt(1)==0)return false;
        }
    } catch (SQLException ex) {
        System.out.println("ovde");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
        String insert = "UPDATE Vozilo SET Nosivost=? WHERE RegBr=?";
    try {
        PreparedStatement psinsert = connection.prepareStatement(insert);
        psinsert.setBigDecimal(1, bd);
        psinsert.setString(2, tablica);
        if(psinsert.executeUpdate()>0)return true;
    } catch (SQLException ex) {
        System.out.println("ovde1");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
    }

    @Override
    public boolean parkVehicle(String tablica, int magacin) {
       String provvozila="SELECT COUNT(*) FROM Vozilo WHERE RegBr=?";
    try {
        PreparedStatement psprovvoz=connection.prepareStatement(provvozila);
        psprovvoz.setString(1, tablica);
        ResultSet rsprovoz = psprovvoz.executeQuery();
        if(rsprovoz.next()){
            if(rsprovoz.getInt(1)==0)return false;
        }
    } catch (SQLException ex) {
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    String provmagacina="SELECT COUNT(*) FROM Magacin WHERE IdMag=?";
    try {
        PreparedStatement psprovmag=connection.prepareStatement(provmagacina);
        psprovmag.setInt(1, magacin);
        ResultSet rspromag = psprovmag.executeQuery();
        if(rspromag.next()){
            if(rspromag.getInt(1)==0)return false;
        }
    } catch (SQLException ex) {
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    String utoku="SELECT COUNT(*) FROM trenutnoVozi WHERE RegBr=?";
    try {
        PreparedStatement psutoku=connection.prepareStatement(utoku);
        psutoku.setString(1, tablica);
        ResultSet rsproutoku = psutoku.executeQuery();
        if(rsproutoku.next()){
            if(rsproutoku.getInt(1)>0)return false;
        }
    } catch (SQLException ex) {
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    String parkiraj ="INSERT INTO uMagacinu(IdMag,RegBr,inStock) VALUES(?,?,1)";
    try {
        PreparedStatement pspark=connection.prepareStatement(parkiraj);
        pspark.setInt(1, magacin);
        pspark.setString(2, tablica);
       if(pspark.executeUpdate()>0)return true;
        
    } catch (SQLException ex) {
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
    
    
    }
    
}
