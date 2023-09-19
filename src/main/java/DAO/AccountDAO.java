package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.SQLException;

public class AccountDAO {

     public Account processNewUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            String username = account.getUsername();
            String password2 = account.getPassword();
            return searchForId(username, password2);
        
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
     }

     public Account processUserLogins(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Account account2 = new Account(rs.getInt("account_id"),
                        rs.getString("username"), 
                        rs.getString("password"));
                return account2;
             }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }

     public Account searchForId(String username, String password) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"), 
                        rs.getString("password"));
                return account;
             }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
        }
     }
