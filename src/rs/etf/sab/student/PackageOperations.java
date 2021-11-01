/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

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
public class PackageOperations implements rs.etf.sab.operations.PackageOperations {
    
    private final Connection connection=(Connection) DB.getInstance().getConnection();

    @Override
    public int insertPackage(int adresaod, int adresado, String user, int i2, BigDecimal bd) {
        String proveraKor = "SELECT COUNT(*) FROM Korisnik WHERE KorIme=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraKor);
                ps1.setString(1, user);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return -1;
                }  
            } catch (SQLException ex) {
                System.out.println("1");
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            String proveraAdr = "SELECT COUNT(*) FROM Adresa WHERE IdAdr=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraAdr);
                ps1.setInt(1, adresado);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return -1;
                }  
            } catch (SQLException ex) {
                System.out.println("1");
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            String proveraAdr1 = "SELECT COUNT(*) FROM Adresa WHERE IdAdr=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraAdr1);
                ps1.setInt(1, adresaod);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return -1;
                }  
            } catch (SQLException ex) {
                System.out.println("1");
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String insert ="INSERT INTO Paket(Tip,Tezina,Status,Cena,VremeKreiranja,KorIme,AdrPreuzimanja,AdrDostavljanja) VALUES(?,?,0,0,?,?,?,?)";
        try {
            PreparedStatement psinsert = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            psinsert.setInt(1,i2);
            psinsert.setBigDecimal(2, bd);
            
            PreparedStatement time =connection.prepareStatement("SELECT getDate()");
            ResultSet rs = time.executeQuery();
            psinsert.setTimestamp(3,rs.getTimestamp(1));
            psinsert.setString(4, user);
            psinsert.setInt(5, adresaod);
            psinsert.setInt(6, adresado);
            psinsert.executeUpdate();
            ResultSet rsinsert = psinsert.getGeneratedKeys();
            if(rsinsert.next()){
                return rsinsert.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
            
    }

    @Override
    public boolean acceptAnOffer(int i) {
        String prov ="SELECT COUNT(*) FROM Paket WHERE IdPak=? and Status=0";
        try {
            PreparedStatement psprov = connection.prepareStatement(prov);
            psprov.setInt(1, i);
            ResultSet rs= psprov.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0)return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String update ="UPDATE Paket SET Satus=1, VremePrihvatanja=? WHERE IdPak=? and Status=0";
        try {
            PreparedStatement psupdate = connection.prepareStatement(update);
            PreparedStatement time =connection.prepareStatement("SELECT getDate()");
            ResultSet rs = time.executeQuery(); 
            psupdate.setTimestamp(1, rs.getTimestamp(1));
            psupdate.setInt(2, i);
            if( psupdate.executeUpdate()>0)return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }

    @Override
    public boolean rejectAnOffer(int i) {
        String prov ="SELECT COUNT(*) FROM Paket WHERE IdPak=? and Status=0";
        try {
            PreparedStatement psprov = connection.prepareStatement(prov);
            psprov.setInt(1, i);
            ResultSet rs= psprov.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0)return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String update ="UPDATE Paket SET Satus=4 WHERE IdPak=? and Status=0";
        try {
            PreparedStatement psupdate = connection.prepareStatement(update);
            psupdate.setInt(1, i);
            if( psupdate.executeUpdate()>0)return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllPackages() {
         List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdPak FROM Paket";
        
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
    public List<Integer> getAllPackagesWithSpecificType(int i) {
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdPak FROM Paket WHERE TipPaketa=?";
        
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

    @Override
    public List<Integer> getAllUndeliveredPackages() {
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdPak FROM Paket WHERE WHERE Status=1 OR Status=2";
        
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
    public List<Integer> getAllUndeliveredPackagesFromCity(int i) {
       List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdPak FROM Paket join Adresa on(Paket.AdrPreuzimanja) where (Paket.Status=1 OR Paket.Status=2) AND  Adresa.Grad=?";
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

    @Override
    public List<Integer> getAllPackagesCurrentlyAtCity(int i) {
        List<Integer> lista = new LinkedList<>();
        String listQ = "SELECT IdPak FROM Paket WHERE (Paket.Status=2 and TrenutnaLok=?) or (Paket.Status = 3 and Paket.AdrDostavljanja in (select IdAdr from Adresa where Grad=?))or ((Status=1 OR Status=0 OR Status=4) and Paket.AdrPreuzimanja in select from in (select IdAdr from Adresa where Grad=?)) ";
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ps.setInt(1, i);
             ps.setInt(2, i);
             ps.setInt(3, i);
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
    public boolean deletePackage(int i) {
        String deleteQ = "DELETE FROM Paket WHERE IdPak=? AND (Status=0 OR Status=4)";
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
    public boolean changeWeight(int i, BigDecimal bd) {
        String provera = "SELECT COUNT(*) FROM Paket WHERE IdPak=? and Status=0";
    try {
        PreparedStatement psprov = connection.prepareStatement(provera);
        psprov.setInt(1, i);
        ResultSet rsprov=psprov.executeQuery();
        if(rsprov.next()){
            if(rsprov.getInt(1)==0)return false;
        }
    } catch (SQLException ex) {
        System.out.println("ovde");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
        String insert = "UPDATE Paket SET Tezina=? WHERE IdPak=?AND Status=0";
    try {
        PreparedStatement psinsert = connection.prepareStatement(insert);
        psinsert.setBigDecimal(1, bd);
        psinsert.setInt(2, i);
        if(psinsert.executeUpdate()>0)return true;
    } catch (SQLException ex) {
        System.out.println("ovde1");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
    }

    @Override
    public boolean changeType(int i, int tip) {
        String provera = "SELECT COUNT(*) FROM Paket WHERE IdPak=? and Status=0";
    try {
        PreparedStatement psprov = connection.prepareStatement(provera);
        psprov.setInt(1, i);
        ResultSet rsprov=psprov.executeQuery();
        if(rsprov.next()){
            if(rsprov.getInt(1)==0)return false;
        }
    } catch (SQLException ex) {
        System.out.println("ovde");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    
        String insert = "UPDATE Paket SET Tip=? WHERE IdPak=?AND Status=0";
    try {
        PreparedStatement psinsert = connection.prepareStatement(insert);
        psinsert.setInt(1, tip);
        psinsert.setInt(2, i);
        if(psinsert.executeUpdate()>0)return true;
    } catch (SQLException ex) {
        System.out.println("ovde1");
        Logger.getLogger(VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
    }

    @Override
    public int getDeliveryStatus(int i) {
        String status ="select Status from Paket where IdPak=?";
        try {
            PreparedStatement ps =connection.prepareStatement(status);
            ps.setInt(1, i);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public BigDecimal getPriceOfDelivery(int i) {
        String status ="select Cena from Paket where IdPak=?";
        try {
            PreparedStatement ps =connection.prepareStatement(status);
            ps.setInt(1, i);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getBigDecimal(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
  
    }

    @Override
    public int getCurrentLocationOfPackage(int i) {
       String status ="select TrenutnaLok from Paket where IdPak=? AND Status=2";
        try {
            PreparedStatement ps =connection.prepareStatement(status);
            ps.setInt(1, i);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                 if( rs.getInt(1)==0)return -1;
                else return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         String sql_Do="SELECT A.Grad FROM Paket P,Adresa A WHERE IdPak=? AND Status=3 AND P.AdrDostavljanja=A.IdAdr";
           
           try{
               PreparedStatement pstmt_Do=connection.prepareStatement(sql_Do);
               pstmt_Do.setInt(1, i);
               ResultSet rs_Do=pstmt_Do.executeQuery();
               if(rs_Do.next()){
                   return rs_Do.getInt(1);
               
               }
           } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
           
            String sql_Od="SELECT A.Grad FROM Paket P,Adresa A WHERE IdPak=? AND (Status=0 OR Status=1 OR Status=4) AND P.AdrPreuzimanja=A.IdAdr";
           
           try{
           PreparedStatement pstmt_Od=connection.prepareStatement(sql_Od);
               pstmt_Od.setInt(1, i);
               ResultSet rs_Od=pstmt_Od.executeQuery();
               if(rs_Od.next()){
                   return rs_Od.getInt(1);
               
               }
           } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    @Override
    public java.sql.Date getAcceptanceTime(int i) {
       String status ="select VremePrihvatanja from Paket where Status!=0 AND Status!=4 AND IdPak=?";
        try {
            PreparedStatement ps =connection.prepareStatement(status);
            ps.setInt(1, i);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getDate(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
