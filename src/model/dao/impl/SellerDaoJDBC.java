
package model.dao.impl;

import db.DB;
import db.DbException;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Seller;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entities.Department;
public class SellerDaoJDBC implements SellerDao {
    private Connection conn = null;
    
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void insert(Seller obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Seller obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
           "SELECT seller.*,department.Name as DepName"
          +" FROM seller INNER JOIN department"
          +" ON seller.DepartmentId = department.Id"
          +" WHERE seller.Id = ?"                          
           );
           st.setInt(1,id);
           rs = st.executeQuery();
           
           if(rs.next()){
               Department dep = instantiateDepartment(rs);
               Seller seller = instantiateSeller(rs, dep);
               
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
}
