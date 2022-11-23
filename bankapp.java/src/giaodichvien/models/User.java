package giaodichvien.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String customerId;

    private String passWord;

    // Constructor
    public User(String name, String customerId, String passWord) {
        this.name = name;
        this.customerId = customerId;
        this.passWord = passWord;
    }

    // Setter
    public void setPassWord (String passWord) {this.passWord = passWord;}

    // Getter
    public String getName() {
        return name;
    }
    public String getCustomerId() {
        return customerId;
    }
    public String getPassWord(){ return passWord; }
}
