package application;

import java.util.Date;
import model.entities.Department;
import model.entities.Seller;

public class Progam {
    public static void main(String[] args){
        Department department = new Department(1,"Games");
        Seller seller = new Seller(7, "Rodrigo", "rodrigo@outlook.com", new Date(), 3000.0, department);
        System.out.println(seller);
    }
}
