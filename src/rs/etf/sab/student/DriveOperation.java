/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;
import rs.etf.sab.student.*;
import java.beans.Statement;
import java.math.BigDecimal;
import rs.etf.sab.student.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Nikola
 */
public class DriveOperation implements rs.etf.sab.operations.DriveOperation{
private final Connection connection=DB.getInstance().getConnection();
    @Override
    public boolean planingDrive(String kurirUser) {
       
    
    return false;
        
    }

    @Override
    public int nextStop(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getPackagesInVehicle(String string) {
        
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdPak FROM Utovar join Vozilo on(Utovar.RegBr=Vozilo.RegBr)join trenutnoVozi on(Vozilo.RegBr=trenutnoVozi.RegBr) where trenutnoVozi.KorIme=?";
        
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ps.setString(1, string);
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
