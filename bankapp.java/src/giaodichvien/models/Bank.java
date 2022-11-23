package giaodichvien.models;

import java.io.*;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.*;

public class Bank {

    private String bankCustomerId;
    private String bankAccountNumber;
    private String currentCustomerId;

    public Bank(String bankCustomerId, String bankAccountNumber) {
        this.bankCustomerId = bankCustomerId;
        this.bankAccountNumber = bankAccountNumber;
    }

    // Getter
    public String getBankCustomerId() {
        return bankCustomerId;
    }
    public String getBankAccountNumber() {return bankAccountNumber;}

    public Customer getCustomerById (String customerId) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Gửi yêu cầu để server xuất tập dữ liệu Customers
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("getcustomers");
            output.flush();

            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedCustomer savedCustomer = (SavedCustomer) input.readObject();
            Map map = savedCustomer.getMap();
            return (Customer) map.get(customerId); // Chỉ return customer có id tương ứng
        } catch (EOFException e){
            System.out.println("Oops " + e.getMessage());
            return null;
        }
    }

    public boolean isCustomerExisted (String customerId) throws IOException, ClassNotFoundException{
        if(getCustomerById(customerId) != null)
            return true;
        else
            return false;
    }

    // Setter
    public void setCurrentCustomerId(String customerId){
        currentCustomerId = customerId;
    }

    // Getter
    public String getCurrentCustomerId() {
        return currentCustomerId;
    }

    // Đăng xuất khách hàng
    public void exitCurrentCustomer(){
        currentCustomerId = null;
    }

    public void addCustomer(Customer customer) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Gửi yêu cầu Server xuất tập dữ liệu Customers
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("updatecustomers");
            output.flush();

            // Thêm khách hàng vào tập dữ liệu
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedCustomer savedCustomer = (SavedCustomer) input.readObject();
            savedCustomer.updateCustomer(customer);

            // Gửi lại tập dữ liệu cho server lưu trữ vào database
            output.writeObject(savedCustomer);
            output.flush();

            input.readUTF();
        } catch (EOFException ex){
            System.out.println("oops " + ex.getMessage());
        }
    }

    public boolean isAccountExisted (String accountNumber) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Yêu cầu server gửi tập dữ liệu Accounts
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("getaccounts");
            output.flush();

            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedAccount savedAccount = (SavedAccount) input.readObject();
            Map<String, String> map = savedAccount.getMap();

            // Kiểm tra trong tập dữ liệu trả về có Account Number tương ứng chưa rồi return kết quả
            if(map.get(accountNumber) != null) {
                System.out.println("STK đã tồn tại");
                return true;
            }else
                return false;
        } catch (EOFException ex){
            System.out.println("oops " + ex.getMessage());
            return false;
        }
    }

    public void addAccount(String accountNumber, double balance, boolean isSavings) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Yêu cầu server xuất tập dữ liệu  Customers
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("updatecustomers");
            output.flush();

            // Thêm tài khoản mới vào customer đang giao dịch
            Customer currentCustomer = getCustomerById(currentCustomerId);
            Account newAccount = isSavings? new SavingsAccount(accountNumber, balance): new LoanAccount(accountNumber, balance);
            currentCustomer.addAccount(newAccount);
            currentCustomer.addTransaction(new Transaction(accountNumber, balance));

            // Thêm customer đã thêm tài khoản này vào tập dữ liệu customers
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedCustomer savedCustomer = (SavedCustomer) input.readObject();
            savedCustomer.updateCustomer(currentCustomer);

            // Gửi dữ liệu customers đến server lưu trữ
            output.writeObject(savedCustomer);
            output.flush();

            input.readUTF();
        } catch (NullPointerException ex){
            System.out.println("Oops " +ex.getMessage());
        } catch (EOFException ex){
            System.out.println("oef exception");
        }

        try(Socket socket = new Socket("localhost",7777)){
            // Yêu cầu server tiếp tục xuất tập dữ liệu Accounts
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("updateaccounts");
            output.flush();

            // Cập nhật account number vào dữ liệu
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedAccount savedAccount = (SavedAccount) input.readObject();
            savedAccount.updateAccountId(accountNumber, currentCustomerId);

            // Xuất dữ liệu cho server lưu trữ
            output.writeObject(savedAccount);
            output.flush();

            input.readUTF();
            System.out.println("Thêm tài khoản thành công");
        } catch (NullPointerException ex){
            System.out.println("Oops " +ex.getMessage());
        } catch (EOFException ex){
            System.out.println("oef exception");
        }
    }

    public void changePassWord(String newPassWord) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Gửi yêu cầu Server xuất tập dữ liệu Customers
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("updatecustomers");
            output.flush();

            // Đọc dữ liệu từ server gửi đến
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedCustomer savedCustomer = (SavedCustomer) input.readObject();
            // Lấy ra current Customer
            Customer currentCustomer = savedCustomer.getMap().get(currentCustomerId);
            // Thay đổi passWord cho current Customer
            currentCustomer.setPassWord(newPassWord);
            savedCustomer.updateCustomer(currentCustomer);

            // Gửi lại tập dữ liệu cho server lưu trữ vào database
            output.writeObject(savedCustomer);
            output.flush();

            input.readUTF();
        } catch (EOFException ex){
            System.out.println("oops " + ex.getMessage());
        }
    }
    public String getCustomerByAccountNumber(String accountNumber) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Yêu cầu server xuất dữ liệu Accounts
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("getaccounts");
            output.flush();

            // Lấy ra customer id của số tài khoản tương ứng
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedAccount savedAccount = (SavedAccount) input.readObject();
            Map<String, String> map = savedAccount.getMap();
            return map.get(accountNumber); // return customer id
        }
    }

    public void withdraw(String customerId, String accountNumber, double amount, boolean isKhachHang) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Gửi yêu cầu Server xuất tập dữ liệu Customers
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("updatecustomers");
            output.flush();

            // Thêm khách hàng vào tập dữ liệu
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedCustomer savedCustomer = (SavedCustomer) input.readObject();
            // Lấy ra customer có customer id tương ứng
            Customer currentCustomer = savedCustomer.getMap().get(customerId);
            // Lấy ra account có account number tương ứng
            Account currentAccount = currentCustomer.getAccounts().get(accountNumber);
            // Thay đổi số dư tài khoản của currentCustomer
            currentCustomer.withdraw(currentAccount, amount, true, isKhachHang);
            // Thêm lịch sử giao dịch
            currentCustomer.addTransaction(new Transaction(accountNumber, -amount));
            savedCustomer.updateCustomer(currentCustomer);

            // Gửi lại tập dữ liệu cho server lưu trữ vào database
            output.writeObject(savedCustomer);
            output.flush();

            input.readUTF();
        } catch (EOFException ex){
            System.out.println("oops " + ex.getMessage());
        }
    }

    public void nopTien(String customerId, String accountNumber, double amount) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Gửi yêu cầu Server xuất tập dữ liệu Customers
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("updatecustomers");
            output.flush();

            // Thêm khách hàng vào tập dữ liệu
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedCustomer savedCustomer = (SavedCustomer) input.readObject();
            // Lấy ra customer có customer id tương ứng
            Customer currentCustomer = savedCustomer.getMap().get(customerId);
            // Lấy ra account có account number tương ứng
            Account currentAccount = currentCustomer.getAccounts().get(accountNumber);
            // Thay đổi số dư tài khoản của currentCustomer
            currentCustomer.nopTien(currentAccount, amount);
            currentCustomer.addTransaction(new Transaction(accountNumber, amount));
            savedCustomer.updateCustomer(currentCustomer);

            // Gửi lại tập dữ liệu cho server lưu trữ vào database
            output.writeObject(savedCustomer);
            output.flush();

            input.readUTF();
        } catch (EOFException ex){
            System.out.println("oops " + ex.getMessage());
        }
    }

    public void transfer(String customerId, String accountNumber, String receiveId,String receiveAccNumber, double amount, boolean isKhachHang) throws IOException, ClassNotFoundException{
        try(Socket socket = new Socket("localhost",7777)){
            // Gửi yêu cầu Server xuất tập dữ liệu Customers
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF("updatecustomers");
            output.flush();

            // Thêm khách hàng vào tập dữ liệu
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            SavedCustomer savedCustomer = (SavedCustomer) input.readObject();
            // Lấy ra customer có customer id tương ứng
            Customer currentCustomer = savedCustomer.getMap().get(customerId);
            Customer receiveCustomer = savedCustomer.getMap().get(receiveId);
            // Lấy ra account có account number tương ứng
            Account currentAccount = currentCustomer.getAccounts().get(accountNumber);
            Account receiveAccount = currentCustomer.getAccounts().get(receiveAccNumber);
            // Thay đổi số dư tài khoản của currentCustomer
            currentCustomer.withdraw(currentAccount, amount, false, isKhachHang);
            receiveCustomer.nopTien(receiveAccount, amount);
            // Thêm lịch sử giao dịch
            currentCustomer.addTransaction(new Transaction(accountNumber, -amount));
            receiveCustomer.addTransaction(new Transaction(receiveAccNumber, amount));

            savedCustomer.updateCustomer(currentCustomer);
            savedCustomer.updateCustomer(receiveCustomer);

            // Gửi lại tập dữ liệu cho server lưu trữ vào database
            output.writeObject(savedCustomer);
            output.flush();

            input.readUTF();
        } catch (EOFException ex){
            System.out.println("oops " + ex.getMessage());
        }
    }

    // Hiển thị lịch sử giao dịch của 1 khách hàng
    public void displayTransaction(int max) throws IOException, ClassNotFoundException{
        try {
            Locale localeEN = new Locale("en", "EN");
            NumberFormat en = NumberFormat.getInstance(localeEN);

            Customer currentCustomer = getCustomerById(currentCustomerId);

            List<Transaction> transactions = currentCustomer.getTransactions();
            int length = transactions.size();

            // Hiển thị tối đa max lần lịch sử giao dịch của currentCustomer
            for (int i = length -1; i>= length - max; --i){
                System.out.printf("%s |", transactions.get(i).getId());
                System.out.printf("%7s|", transactions.get(i).getAccountNumber());
                System.out.printf("%16sđ |", en.format(transactions.get(i).getAmount()));
                System.out.printf("%20s", transactions.get(i).getTime());
                System.out.println();
            }
        } catch (IndexOutOfBoundsException ex){}

    }

}
