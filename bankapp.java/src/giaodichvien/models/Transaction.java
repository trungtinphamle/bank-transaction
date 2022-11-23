package giaodichvien.models;

import java.io.Serializable;
import java.util.UUID;

import static xulygiaodien.XuLyGiaoDien.getDateTime;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String accountNumber;
    private double amount;
    private String time;

    // Constructor
    public Transaction(String accountNumber, double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.id = String.valueOf(UUID.randomUUID());
        this.time = getDateTime();
    }
    //Getter
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }
}
