package giaodichvien.models;

import xulygiaodien.XuLyGiaoDien;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

public class Customer extends User implements Serializable{
    private static final long serialVersionUID = 1L;
    private Map<String, Account> accounts;
    private List<Transaction> transactions;

    // Constructor
    public Customer(String name, String customerId, String passWord) {
        super(name, customerId, passWord);
        accounts = new HashMap<>();
        transactions = new ArrayList<>();
    }

    //Getter
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account newAccount){
        accounts.put(newAccount.getAccountNumber(),newAccount);
    }

    public void addTransaction (Transaction transaction){
        transactions.add(transaction);
    }

    public double getBalance(){
        // result là tổng balance các tài khoản của 1 khách hàng
        double result = 0.0;
        if(accounts != null){
            for (Account acc: accounts.values())
                result += acc.getBalance();
        }
        return result;
    }

    public void withdraw(Account account, double amount,boolean isWithdraw, boolean isKhachHang){
        SavingsAccount savingsAccount;
        LoanAccount loanAccount;

        if(account != null) {
            // Down-casting để sử dụng các method của SavingsAccount hay LoanAccount
            if (account instanceof SavingsAccount) {
                savingsAccount = (SavingsAccount) account;
                // Thay đổi số dư của tài khoản tương ứng và in biên lai
                if(isKhachHang)
                    savingsAccount.log(amount,isWithdraw);
                else
                    savingsAccount.changeBalance(amount,isWithdraw);
                addAccount(savingsAccount);
            } else {
                loanAccount = (LoanAccount) account;
                // Thay đổi số dư của tài khoản tương ứng
                if(isKhachHang)
                    loanAccount.log(amount,isWithdraw);
                else
                    loanAccount.changeBalance(amount,isWithdraw);
                addAccount(loanAccount);
            }
        } else
            System.out.println("Số tài khoản chưa tồn tại");
    }


    // Nộp tiền vào tài khoản
    public void nopTien (Account account, double amount){
        if(account != null) {
            account.setBalance(account.getBalance() + amount);
            addAccount(account);
        } else
            System.out.println("Số tài khoản chưa tồn tại");
    }

    // Hiển thị danh sách accountNumber của 1 khách hàng
    public void displayAccountNumber(){
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        System.out.println("|       Tổng cộng      |"+ String.format("%31s",en.format(getBalance())+"đ"));
        // 1      123456 |   SAVINGS |                    4,000,000đ    Normal
        // 2      234567 |      LOAN |                    6,890,000đ


        int i = 1;
        for (Account acc: accounts.values()){
            if(acc instanceof SavingsAccount){
                SavingsAccount savingsAccount = (SavingsAccount) acc;
                System.out.println(i+"      "+savingsAccount.toString()) ;
            } else {
                LoanAccount loanAccount = (LoanAccount) acc;
                System.out.println(i+"      "+loanAccount.toString()) ;
            }
            ++i;
        }

    }
    // Hàm hiển thị toàn bộ thông tin của khách hàng
    // Được sử dụng cho chức năng 1

    public void displayInformation() {
        // 079097007211  |                    Thông tin khách hàng
        // Số căn cước công dân:                      079097000000
        // Họ và tên:                                          ABC
        // Nơi sinh:                                   Hồ Chí Minh
        // Giới tính:                                          Nam
        // Năm sinh:                                          1997
        System.out.println(getCustomerId()+  String.format("  |%40s","Thông tin khách hàng"));
        System.out.println("Số căn cước công dân:" + String.format("%34s",getCustomerId()));
        System.out.println("Họ và tên:" + String.format("%45s",getName()));
        XuLyGiaoDien.hienThiThongTin(getCustomerId());
        System.out.println("------------------------------------------------");

        displayAccountNumber();
    }
}
