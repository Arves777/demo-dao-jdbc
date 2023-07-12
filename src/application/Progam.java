package application;

import java.util.Date;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Progam {
    public static void main(String[] args){
        
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("### TEST 1: seller findByID ###");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        
        System.out.println("\n### TEST 2: seller findByDepartment ###");
        Department department = new Department(2,null);
        List<Seller> listSeller = sellerDao.findByDepartment(department);
        for(Seller s: listSeller){
            System.out.println(s);
        }
        
        System.out.println("\n### TEST 3: seller findAll ###");
        listSeller = sellerDao.findAll();
        for(Seller s: listSeller){
            System.out.println(s);
        }
        
        System.out.println("\n### TEST 4: seller insert ###");
        Seller seller2 = new Seller(null,"Antonio","Antonio@outlook.com",new Date(),6000.00,department);
        sellerDao.insert(seller2);
        System.out.println("Inserted! New id = " + seller2.getId());
                
    }
}
