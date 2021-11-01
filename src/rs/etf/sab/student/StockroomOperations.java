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
public class StockroomOperations implements rs.etf.sab.operations.StockroomOperations {
    private final Connection connection=DB.getInstance().getConnection();
    @Override
    public int insertStockroom(int i) {
        String proveraMagacina = "SELECT COUNT(*) FROM Adresa WHERE IdAdr=?";
        try {
            PreparedStatement psMagacin = connection.prepareStatement(proveraMagacina);
            psMagacin.setInt(1, i);
            ResultSet rs = psMagacin.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0)return -1;
            }
        } catch (SQLException ex) {
            System.out.println("dal ima adresa");
        }
        
        
        String proveraMagacina1 = "SELECT Grad FROM Adresa A WHERE A.IdAdr=?";
        try {
            PreparedStatement psMagacin1 = connection.prepareStatement(proveraMagacina1);
            psMagacin1.setInt(1, i);
            ResultSet rs1 = psMagacin1.executeQuery();
            if(rs1.next()){
                int idg = rs1.getInt(1);
                String sql_checkCity="SELECT COUNT(*) FROM Magacin M,Adresa A WHERE A.IdAdr=M.Adresa AND A.Grad=?";
                    PreparedStatement pstmt_checkCity=connection.prepareStatement(sql_checkCity);
                    pstmt_checkCity.setInt(1,idg);
                    ResultSet rs_checkCity=pstmt_checkCity.executeQuery();
                    if(rs_checkCity.next()){
                       
                        if(rs_checkCity.getInt(1)>0){
                            //System.out.println("Greska,vec postoji formiran magacin za taj grad!");
                            return -1;
                        }
                    }
                
                
            }
        } catch (SQLException ex) {
            System.out.println("u drugom delu");
        }
        String ubaciMagacin = "INSERT INTO Magacin(Adresa) VALUES(?)";
        try {
            PreparedStatement psUbaciAdresu = connection.prepareStatement(ubaciMagacin,PreparedStatement.RETURN_GENERATED_KEYS);
            psUbaciAdresu.setInt(1, i);
            psUbaciAdresu.executeUpdate();
            ResultSet rs2 = psUbaciAdresu.getGeneratedKeys();
            if(rs2.next()){
                   return rs2.getInt(1);
                }
        } catch (SQLException ex) {
            System.out.println("u trecem delu");
        }
        return -1;
    }

    @Override
    public boolean deleteStockroom(int i) {
        String sql="SELECT COUNT(*) FROM uMagacinu WHERE IdMag=? and inStock=1";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1, i);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)>0)return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StockroomOperations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("u trecem delu");
        }
        String sql2 ="DELETE FROM Magacin WHERE IdMag=?";
        try {
            PreparedStatement ps2=connection.prepareStatement(sql2);
            ps2.setInt(1, i);
            int rez = 0;
            rez=ps2.executeUpdate();
            return rez>0;
        } catch (SQLException ex) {
            Logger.getLogger(StockroomOperations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("u trecem delu1");
        }
        return false;
        
    }

    @Override
    public int deleteStockroomFromCity(int i) {
        
        String proveraGrada = "SELECT COUNT(*) FROM Grad WHERE IdGra=?";
        try {
            PreparedStatement psGrad = connection.prepareStatement(proveraGrada);
            psGrad.setInt(1, i);
            ResultSet rs = psGrad.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0)return -1;
            }
        } catch (SQLException ex) {
            System.out.println("u prvom delu");
            return -1;
        }
        String sql = "SELECT IdMag FROM Adresa A,Magacin M WHERE M.Adresa=A.IdAdr AND A.Grad=?";
        int idm=0;
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1, i);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                idm=rs.getInt(1);
            }else return -1;
        } catch (SQLException ex) {
            Logger.getLogger(StockroomOperations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("u trecem delu2");
        }
        String sql2 = "SELECT COUNT(*) FROM uMagacinu where IdMag=?";
        
        try {
            PreparedStatement ps2=connection.prepareStatement(sql2);
            ps2.setInt(1, idm);
            ResultSet rs2 =ps2.executeQuery();
            
            if(rs2.next()){
                if(rs2.getInt(1)>0)return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockroomOperations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("u trecem delu3");
        }
        
        String del = "DELETE FROM Magacin WHERE IdMag=?";
        try {
            PreparedStatement ps3 = connection.prepareStatement(del);
            ps3.setInt(1, idm);
            ps3.executeUpdate();
            return idm;
        } catch (SQLException ex) {
            Logger.getLogger(StockroomOperations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("u trecem delu4");
        }
        return -1;
        
        
    }

    @Override
    public List<Integer> getAllStockrooms() {
                
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdMag FROM Magacin";
        
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 lista.add(rs.getInt(1));
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(CityOperations.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("u trecem delu6");
         }
        
        
     return lista; 
    }
    
}
