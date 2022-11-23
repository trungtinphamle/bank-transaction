package giaodichvien.models;

import xulygiaodien.XuLyGiaoDien;

import java.text.NumberFormat;
import java.util.Locale;


/*
 * Class LoanAcount kế thừa từ Account
 * Quản lý việc in biên lai giao dịch tài khoản Loan
 * Quản lý thông tin tài khoản Loan
 * Đối tượng của LoanAccount được lưu vào DigitalBank thông qua hàm addAccount
 */
public class LoanAccount extends Account implements ReportService, WithdrawOrTransfer {
    // Hằng số
    public final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    public final double LOAN_ACCOUNT_TRANSFER_FEE = 0.00;

    public LoanAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    // Hiển thị Biên lai giao dịch Loan
    @Override
    public void log(double amount, boolean isWithdraw) {
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        System.out.println("+------------+---------------------+------------+");
        System.out.printf("%30s\n", "BIÊN LAI GIAO DỊCH LOAN");
        System.out.printf("NGÀY G/D: %28s\n", XuLyGiaoDien.getDateTime());
        System.out.printf("ATM ID: %30s\n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SỐ TK: %31s\n", getAccountNumber());
        if(isWithdraw)
            System.out.printf("SỐ TIỀN RÚT: %25s\n", en.format(amount) + "đ");
        else {
            System.out.printf("SỐ TIỀN CHUYỂN: %22s\n", en.format(amount) + "đ");
        }
        System.out.printf("SỐ DƯ: %31s\n", en.format(getBalance()-amount - getTransactionFee(isWithdraw)*amount) + "đ");
        System.out.printf("PHÍ + VAT: %27s\n", en.format(getTransactionFee(isWithdraw)*amount) + "đ");
        System.out.println("+------------+---------------------+------------+");

        // Cập nhật số dư balance sau khi in để tránh hàm getTransactionFee() tính sai
        changeBalance(amount, isWithdraw);
    }

    // Thay đổi số dư tài khoản
    @Override
    public void changeBalance(double amount, boolean isWithdraw) {
        double balance = getBalance() - amount - getTransactionFee(isWithdraw)*amount;
        setBalance(balance);
    }

    // Sử dụng để check lượng tiền rút có hợp lệ không
    @Override
    public boolean isAccepted(double amount, boolean isWithdraw) {
        if(amount<= 0 ||
                getBalance() - amount - getTransactionFee(isWithdraw) * amount < 50_000)
            return false;
        else
            return true;
    }

    // Hiển thị thông tin tài khoản Loan
    @Override
    public String toString() {
        // 234567 |      LOAN |                  2,100,000đ
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        String acc = String.format(" |      LOAN |          %18s",en.format(getBalance())+"đ");
        return(getAccountNumber()+ acc) ;
    }

    // Tính phí rút tiền cho tài khoản Loan
    public double getTransactionFee(boolean isWithdraw){
        if(isWithdraw)
            return LOAN_ACCOUNT_WITHDRAW_FEE;
        else
            return LOAN_ACCOUNT_TRANSFER_FEE;
    }


}
