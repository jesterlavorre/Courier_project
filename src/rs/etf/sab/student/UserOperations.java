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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Nikola
 */
public class UserOperations implements rs.etf.sab.operations.UserOperations{
    
    private final Connection connection=DB.getInstance().getConnection();
    @Override
    public boolean insertUser(String username, String firstname, String lastname, String pass, int i) {
        int pom=0;
        if(username == null || firstname == null || pass == null) return false;
        String usernameCheckQ = "SELECT COUNT(*) FROM Korisnik WHERE KorIme = ?";
        try(PreparedStatement psUsernameCheck = connection.prepareStatement(usernameCheckQ);){
            psUsernameCheck.setString(1, username);
            ResultSet rsUsernameCheck = psUsernameCheck.executeQuery();
            if(rsUsernameCheck.next()){
                if(rsUsernameCheck.getInt(1)>0) return false;
            }     
        }catch (SQLException ex) {
           System.out.println("Greska pri proveri jedinstvenosti korisnika");
           return false;
        }
        Pattern p3 = Pattern.compile("[A-Z]+");
        Matcher m3 = p3.matcher(pass);
        if (!m3.find()) {
            return false;
        }
        Pattern p4 = Pattern.compile("[a-z]+");
        Matcher m4 = p4.matcher(pass);
        if (!m4.find()) {
            return false;
        }
        Pattern p5 = Pattern.compile("[0-9]+");
        Matcher m5 = p5.matcher(pass);
        if (!m5.find()) {
            return false;
        }
        Pattern p6 = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");
        Matcher m6 = p6.matcher(pass);
        if (!m6.find()) {
            return false;
        }
        if (pass.length() < 8) {
            return false;
        }
        if(!Character.isUpperCase(firstname.charAt(0))) return false;
        if(!Character.isUpperCase(lastname.charAt(0))) return false;
        
        String prov1 = "SELECT COUNT(*) FROM Adresa WHERE IdAdr=?";
        try {
            PreparedStatement psprov1 = connection.prepareStatement(prov1);
            psprov1.setInt(1, i);
            ResultSet rsprov=psprov1.executeQuery();
            if(rsprov.next()){
                if(rsprov.getInt(1)==0)return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("user");
        }  
        String insertQ = " INSERT INTO Korisnik(KorIme,Ime,Prezime,Sifra,Adresa) VALUES(?,?,?,?,?)";
       // String insertKupac = " INSERT INTO Kupac(KorIme) VALUES(?)";
            PreparedStatement ps;
            //PreparedStatement psKupac;
        try {
            ps = connection.prepareStatement(insertQ);
            ps.setString(1, username);
            ps.setString(2, firstname);
            ps.setString(3, lastname);
            ps.setString(4, pass);
            ps.setInt(5, i);
            pom = ps.executeUpdate();
           // psKupac= connection.prepareStatement(insertKupac);      
          //  psKupac.setString(1, string);    
        } catch (SQLException ex) {
            Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("jebackevi");
            return false;
        }
        if(pom!=0)return true;
        else return false; 
    }

    @Override
    public boolean declareAdmin(String string) {
        if(string == null) return false;
        String userCheck = "SELECT COUNT(*) FROM Korisnik WHERE KorIme = ?";
        try{
        PreparedStatement psUserCheck = connection.prepareStatement(userCheck);
            psUserCheck.setString(1, string);
            ResultSet rsUserCheck = psUserCheck.executeQuery();
            if(rsUserCheck.next()){
                if(rsUserCheck.getInt(1) == 0) return false;
            }
        }catch(SQLException ex){
            System.out.println("declareAdmin(String username) - Greska pri proveri postojanja korisnika!");
            return false;
        }
        
        String adminCheckQ = "SELECT COUNT(*) FROM Administrator WHERE KorIme = ?";
        try{
            PreparedStatement psAdminCheck = connection.prepareStatement(adminCheckQ);
            psAdminCheck.setString(1, string);
            ResultSet rsAdminCheck = psAdminCheck.executeQuery();
            if(rsAdminCheck.next()){
                if(rsAdminCheck.getInt(1) > 0) {
                    //System.out.println("declareAdmin - korisnik je vec admin!");
                    return false;
                }
            }
        }catch(SQLException ex){
            return false;
        }
        
        String insertQ = "INSERT INTO Administrator (KorIme) VALUES (?)";
        try(PreparedStatement psInsert = connection.prepareStatement(insertQ)){
            psInsert.setString(1, string);
            if(psInsert.executeUpdate()>0) return true;
            
        }catch(SQLException ex){
            System.out.println("declareAdmin(String username) - Greska pri proveri da li je ovaj user vec postao admin!");
            return false;
        }
        return false;
    }

    @Override
    public int getSentPackages(String... strings) {
        int pom=0;
        if(strings==null)return -1;
        for(int i=0;i<strings.length;i++){
            if(strings[i]==null)continue;
            String proveraKor = "SELECT COUNT(*) FROM Korisnik WHERE KorIme=?";
            try {
                PreparedStatement ps1 = connection.prepareStatement(proveraKor);
                ps1.setString(1, strings[i]);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){
                    if(rs1.getInt(1)==0)return -1;
                }  
            } catch (SQLException ex) {
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            String paketi = "SELECT COUNT(*) FROM Paket WHERE KorIme=?";
            try {
                PreparedStatement ps2 = connection.prepareStatement(paketi);
                ps2.setString(1, strings[i]);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    if(rs2.getInt(1)>0)pom+=rs2.getInt(1);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        return pom;
    }

    @Override
    public int deleteUsers(String... strings) {
         int pom=0;
         int i=0;
        String insertQ = " DELETE FROM Korisnik WHERE KorIme=?";
            PreparedStatement ps;
        try {
            ps = connection.prepareStatement(insertQ);
            for(i = 0; i<strings.length;i++){
                if(strings[i]==null)continue;
                ps.setString(1, strings[i]);
                 pom+=ps.executeUpdate();
                 
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        return pom;
        
        
    }

    @Override
    public List<String> getAllUsers() {
        List<String> lista = new LinkedList<>();
        String listQ = "SELECT KorIme FROM Korisnik";
        
         try {
             PreparedStatement ps = connection.prepareStatement(listQ);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 lista.add(rs.getString(1));
             }
         } catch (SQLException ex) {
            return null;
         }
        return lista;
    }
    
}
