package giaodichvien.models;


import xulygiaodien.XuLyGiaoDien;

import java.text.NumberFormat;
import java.util.Locale;

/*
 * Class SavingsAcount kế thừa từ Account
 * Quản lý việc in biên lai giao dịch tài khoản savings
 * Quản lý thông tin tài khoản savings
 * Đối tượng của SavingsAccount được lưu vào DigitalBank thông qua hàm addAccount
 */
public class SavingsAccount extends Account implements ReportService, WithdrawOrTransfer {
    public final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5_000_000;
    public final double SAVINGS_ACCOUNT_MAX_TRANSFER = 100_000_000;

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public boolean isPremium(){return getBalance() > 20_000_000;}

    // Hiển thị Biên lai giao dịch tài khoản Savings
    @Override
    public void log(double amount, boolean isWithdraw) {
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        System.out.println("+------------+---------------------+------------+");
        System.out.printf("%30s\n", "BIÊN LAI GIAO DỊCH SAVINGS");
        System.out.printf("NGÀY G/D: %28s\n", XuLyGiaoDien.getDateTime());
        System.out.printf("ATM ID: %30s\n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SỐ TK: %31s\n", getAccountNumber());
        if(isWithdraw)
            System.out.printf("SỐ TIỀN RÚT: %25s\n", en.format(amount) + "đ");
        else {
            System.out.printf("SỐ TIỀN CHUYỂN: %22s\n", en.format(amount) + "đ");
        }
        System.out.printf("SỐ DƯ: %31s\n", en.format(getBalance() - amount) + "đ");
        System.out.printf("PHÍ + VAT: %27s\n", en.format(getTransactionFee(isWithdraw)*amount) + "đ");
        System.out.println("+------------+---------------------+------------+");

        // Cập nhật số dư balance sau khi in để tránh hàm getTransactionFee() tính sai
        changeBalance(amount, isWithdraw);
    }

    // Thay đổi số dư tài khoản
    @Override
    public void changeBalance(double amount, boolean isWithdraw) {
        double balance = getBalance() - amount;
        setBalance(balance);
    }

    // Sử dụng để check lượng tiền rút có hợp lệ không
    @Override
    public boolean isAccepted(double amount, boolean isWithdraw) {
        if(isWithdraw){
            if(amount < 50_000 ||
                    (!isPremium() &&
                            amount > SAVINGS_ACCOUNT_MAX_WITHDRAW) ||
                    amount % 10_000 != 0)
                return false;

            else if(getBalance() - amount < 50_000)
                return false;

        } else {
            if(amount < 50_000 ||
                    (!isPremium() &&
                            amount > SAVINGS_ACCOUNT_MAX_TRANSFER) ||
                    amount % 10_000 != 0)
                return false;

            else if(getBalance() - amount < 50_000)
                return false;

        }

        return true;
    }

    // Hiển thị thông tin tài khoản Savings
    @Override
    public String toString() {
        // 123456 |   SAVINGS |                  1,600,000đ     Normal
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        String acc = String.format(" |   SAVINGS |          %18s",en.format(getBalance())+"đ");
        String premium = isPremium()? "     Premium" : "     Normal";
        return(getAccountNumber()+ acc + premium) ;
    }

    // Tài khoản Savings không tốn phí khi rút tiền
    public double getTransactionFee(boolean isWithdraw){
        return 0d;
    }
}
