package giaodichvien.models;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Account implements Serializable {
    private static final long serialVersionUID = 2L;
    private String accountNumber;
    private double balance;

    // Constructor
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Setter
    public void setBalance(double balance) {
        this.balance = balance;
    }
    //Getter
    public String getAccountNumber() {
        return accountNumber;
    }
    //Getter
    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        // 1      123456 |   SAVINGS |                  1,600,000đ     Normal
        // 2      234567 |      LOAN |                  2,100,000đ
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        return(accountNumber + String.format(" |                      %20s",en.format(balance)+"đ")) ;
    }
}
