package server;

import giaodichvien.models.*;

import java.io.*;
import java.util.HashMap;

public class Test {
    public static void main (String [] arg) throws IOException{
        // Dùng để khởi tạo Customer ID và Account Number cho Ngân hàng
        try(ObjectOutputStream out1 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("bankapp.java/src/data/customers.log")));
            ObjectOutputStream out2 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("bankapp.java/src/data/accounts.log")));)
        {
            SavedCustomer savedCustomer = new SavedCustomer(new HashMap<>());
            SavedAccount savedAccount = new SavedAccount(new HashMap<>());
            Customer customer = new Customer("Ngân hàng Vũ Trụ","000000000000","admin");
            customer.addAccount(new Account("000000",0));
            savedCustomer.updateCustomer(customer);
            savedAccount.updateAccountId("000000","000000000000");
            out1.writeObject(savedCustomer);
            out2.writeObject(savedAccount);
            out1.close();
            out2.close();
            System.out.println("Done");
        }
    }
}
