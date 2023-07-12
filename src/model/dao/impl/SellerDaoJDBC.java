
package model.dao.impl;

import db.DB;
import db.DbException;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Seller;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import model.entities.Department;
public class SellerDaoJDBC implements SellerDao {
    private Connection conn = null;
    
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void insert(Seller seller) {
       PreparedStatement st = null;
       try{
           st = conn.prepareStatement(
                   """
                   INSERT INTO seller
                   (Name, Email, BirthDate, BaseSalary, DepartmentId)
                   VALUES
                   (?, ?, ?, ?, ?)
                   """,        
                   Statement.RETURN_GENERATED_KEYS);
           
           st.setString(1,seller.getName());
           st.setString(2,seller.getEmail());
           st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
           st.setDouble(4,seller.getBaseSalary());
           st.setInt(5, seller.getDepartment().getId());
           
           int rowsAffected = st.executeUpdate();
           
           if (rowsAffected > 0){
               ResultSet rs = st.getGeneratedKeys();
               if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
               }
               DB.closeResultSet(rs);
           }
           else{
               throw new DbException("Unexpected error! No rows affected!");
           }
       }
       catch(SQLException e){
           throw new DbException(e.getMessage());
       }
       finally{
           DB.closeStatement(st);
       }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
       try{
           st = conn.prepareStatement("""
                UPDATE seller
                SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?
                WHERE Id = ?""");
           
           st.setString(1,seller.getName());
           st.setString(2,seller.getEmail());
           st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
           st.setDouble(4,seller.getBaseSalary());
           st.setInt(5, seller.getDepartment().getId());
           st.setInt(6,seller.getId());
           
           st.executeUpdate();
           
       }
       catch(SQLException e){
           throw new DbException(e.getMessage());
       }
       finally{
           DB.closeStatement(st);
           
       }
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Seller findById(Integer id) {
       PreparedStatement st = null;
       ResultSet rs = null;
       
       try{
           st = conn.prepareStatement(
           """
           SELECT seller.*,department.Name as DepName
            FROM seller INNER JOIN department
            ON seller.DepartmentId = department.Id
            WHERE seller.Id = ? 
           """);
                                                       
           st.setInt(1,id);
           rs = st.executeQuery();
           
           if(rs.next()){
               Department dep = instantiateDepartment(rs);
               Seller seller = instantiateSeller(rs, dep);
               return seller;
           }
           return null;
       }
       catch(SQLException e){
           throw new DbException(e.getMessage());
       }
       finally{
           DB.closeStatement(st);
           DB.closeResultSet(rs);
       }
       
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
       ResultSet rs = null;
       
       try{
           st = conn.prepareStatement("""
                SELECT seller.*,department.Name as DepName
                FROM seller INNER JOIN department
                ON seller.DepartmentId = department.Id
                ORDER BY Name""");
           rs = st.executeQuery();
           
           List<Seller> listSeller = new ArrayList<>();
           Map<Integer,Department> mapDep = new HashMap<>();
           
           while(rs.next()){
               Department dep = mapDep.get(rs.getInt("DepartmentId"));
               
               if(dep == null){
                   dep = instantiateDepartment(rs);
                   mapDep.put(rs.getInt("DepartmentId"),dep);
               }
               
               Seller seller = instantiateSeller(rs, dep);
               listSeller.add(seller);
           }
           
           return listSeller;
           
           
       } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
               Department dep = new Department();
               dep.setId(rs.getInt("DepartmentId"));
               dep.setName(rs.getString("DepName"));
               return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(dep);
        
        return seller;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
       PreparedStatement st = null;
       ResultSet rs = null;
       
       try{
           st = conn.prepareStatement(
           "SELECT seller.*,department.Name as DepName"
           +" FROM seller INNER JOIN department"
           +" ON seller.DepartmentId = department.Id"
           +" WHERE DepartmentId = ?"
           +" ORDER BY Name"                       
           );
           st.setInt(1,department.getId());
           rs = st.executeQuery();
           
           List<Seller> listSeller = new ArrayList<>();
           Map<Integer,Department> mapDep = new HashMap<>();
           
           while(rs.next()){
               Department dep = mapDep.get(rs.getInt("DepartmentId"));
               
               if(dep == null){
                   dep = instantiateDepartment(rs);
                   mapDep.put(rs.getInt("DepartmentId"),dep);
               }
               
               Seller seller = instantiateSeller(rs, dep);
               listSeller.add(seller);
           }
           return listSeller;
       }
       catch(SQLException e){
           throw new DbException(e.getMessage());
       }
       finally{
           DB.closeStatement(st);
           DB.closeResultSet(rs);
       }
    }
    
}
