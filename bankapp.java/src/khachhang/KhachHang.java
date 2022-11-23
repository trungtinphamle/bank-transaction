package khachhang;

import giaodichvien.models.*;
import xulygiaodien.XuLyGiaoDien;

import java.io.IOException;
import java.util.Scanner;

/*
Giao diện dành cho khách hàng
 */
public class KhachHang {
    private static final Bank BANK = new Bank("000000000000","000000");
    private static final boolean IS_KHACH_HANG = true;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String [] args) throws IOException, ClassNotFoundException{
        giaoDienBatDau();
    }

    // Tạo giao diện bắt đầu
    public static void giaoDienBatDau() throws IOException, ClassNotFoundException{
        System.out.println("+----------+--------------------+----------+");
        System.out.println("| NGÂN HÀNG VŨ TRỤ | KHÁCH HÀNG            |");
        System.out.println("+----------+--------------------+----------+");
        System.out.println(" 1. Đăng nhập                               ");
        System.out.println(" 2. Đăng ký                                 ");
        System.out.println(" 0. Thoát                                   ");
        System.out.println("+----------+--------------------+----------+");
        chucNangBatDau();
    }

    public static void chucNangBatDau() throws IOException, ClassNotFoundException {
        System.out.print("Chức năng: ");
        int chucNang = XuLyGiaoDien.nhapChucNang();// Nhập chức năng trong hàm tạo chức năng
        switch (chucNang){
            case 1: // Đăng nhập bằng CCCD và mật khẩu
                System.out.println("Nhập CCCD khách hàng:");
                String customerId  = XuLyGiaoDien.nhapCCCD();
                System.out.println("Nhập mật khẩu:");
                String passWord = sc.nextLine();
                Customer customer = BANK.getCustomerById(customerId);

                // Kiểm tra CCCD đã tồn tại và đúng mật khẩu thì đăng nhập
                if(customer != null && customer.getPassWord().equals(passWord)){
                    BANK.setCurrentCustomerId(customerId);
                    System.out.println("Đăng nhập thành công");
                    System.out.println("\nNhấn ENTER để tiếp tục");
                    sc.nextLine();
                    trangChu();
                } else {
                    System.out.println("CCCD hoặc mật khẩu chưa tồn tại");
                    System.out.println("\nNhấn ENTER để tiếp tục");
                    sc.nextLine();
                    giaoDienBatDau();
                }
                break;
            case 2: // Đăng ký mới khách hàng
                System.out.println("Nhập CCCD khách hàng:");
                customerId  = XuLyGiaoDien.nhapCCCD();

                // Nếu CCCD chưa tồn tại thì tiến hành đăng ký mới
                if(BANK.isCustomerExisted(customerId)){
                    System.out.println("CCCD đã tồn tại");
                } else {
                    System.out.println("CCCD hợp lệ");
                    System.out.println("Nhập tên đầy đủ của khách hàng:");
                    String name = sc.nextLine().trim().toUpperCase();
                    System.out.println("Nhập mật khẩu:");
                    passWord = sc.nextLine();
                    BANK.addCustomer(new Customer(name,customerId,passWord));
                }

                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                giaoDienBatDau();
                break;

            case 0:
                System.out.println("Thoát chương trình");
                break;
            default:
                System.out.println("Nhập chưa chính xác");
                chucNangBatDau();
        }
    }

    public static void trangChu() throws IOException, ClassNotFoundException{
        System.out.println("+----------+--------------------+----------+");
        System.out.println("| NGÂN HÀNG VŨ TRỤ | GIAO DỊCH VIÊN        |");
        System.out.println("+----------+--------------------+----------+");
        System.out.println(" 1. Thông tin khách hàng                    ");
        System.out.println(" 2. Thêm tài khoản thanh toán               ");
        System.out.println(" 3. Thêm tài khoản tín dụng                 ");
        System.out.println(" 4. Đổi mật khẩu                            ");
        System.out.println(" 5. Rút tiền                                ");
        System.out.println(" 6. Chuyển khoản                            ");
        System.out.println(" 7. Lịch sử giao dịch                       ");
        System.out.println(" 0. Đăng xuất                               ");
        System.out.println("+----------+--------------------+----------+");
        chucNangTrangChu();
    }

    // Hàm chọn chức năng
    public static void chucNangTrangChu() throws IOException, ClassNotFoundException{
        System.out.print("Chức năng: ");
        int chucNang = XuLyGiaoDien.nhapChucNang();// Nhập chức năng trong hàm tạo chức năng
        boolean isSavings = (chucNang == 2)? true: false;
        switch (chucNang) {
            case 1: // Hiển thị thông tin khách hàng
                String id = BANK.getCurrentCustomerId();
                BANK.getCustomerById(id).displayInformation();
                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                trangChu();
                break;
            case 2: // Thêm tài khoản savings
            case 3: // thêm tài khoản loan

                //Nhập Số tài khoản
                String accountNumber = "a";
                boolean checkChuSo = false;
                boolean checkDoDai = false;
                boolean checkTonTai = true;
                while (!checkChuSo || !checkDoDai || checkTonTai) {
                    System.out.println("Nhập mã STK gồm 6 chữ số");
                    accountNumber = sc.next().trim();
                    sc.nextLine();

                    // check chuỗi chỉ chứa số
                    for (int i = 0; i < 6; ++i) {
                        if (accountNumber.charAt(i) < '0' || accountNumber.charAt(i) > '9') {
                            checkChuSo = false;
                            System.out.println("STK chỉ gồm chữ số");
                        }
                    }
                    checkChuSo = true;

                    // check độ dài 6 ký tự
                    if (accountNumber.length() != 6) {
                        checkDoDai = false;
                        System.out.println("STK chỉ gồm 6 ký tự");
                    } else
                        checkDoDai = true;

                    // check tài khoản đã tồn tại
                    checkTonTai = BANK.isAccountExisted(accountNumber);
                }

                BANK.addAccount(accountNumber, 0, isSavings);

                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                trangChu();
                break;
            case 4: // Đổi mật khẩu
                String customerId = BANK.getCurrentCustomerId();
                Customer customer = BANK.getCustomerById(customerId);

                System.out.println("Nhập mật khẩu hiện tại");
                String passWord = sc.nextLine();
                if(customer.getPassWord().equals(passWord)){
                    System.out.println("Nhập mật khẩu mới");
                    String newPassWord = sc.nextLine();
                    System.out.println("Nhập lại mật khẩu mới:");
                    String reNewPassWord = sc.nextLine();

                    while (!reNewPassWord.equals(newPassWord)){
                        System.out.println("Nhập lại không khớp. Nhập lại mật khẩu mới:");
                        reNewPassWord = sc.nextLine();
                    }
                    // Cập nhật mật khẩu mới
                    BANK.changePassWord(reNewPassWord);
                    System.out.println("Đổi mật khẩu thành công");
                } else
                    System.out.println("Mật khẩu không chính xác");

                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                trangChu();
                break;
            case 5: // Rút tiền mặt
                id = BANK.getCurrentCustomerId();
                Customer currentCustomer = BANK.getCustomerById(id);
                currentCustomer.displayAccountNumber();

                System.out.println("Nhập số tài khoản:");
                String stk = sc.next();
                sc.nextLine();
                while (currentCustomer.getAccounts().get(stk) == null) {
                    System.out.println("STK không tồn tại. Nhập lại:");
                    stk = sc.next();
                    sc.nextLine();
                }
                Account currentAccount = currentCustomer.getAccounts().get(stk);

                if (currentAccount instanceof SavingsAccount) {
                    System.out.println("Tài khoản Savings");
                    System.out.println("- Số tiền rút phải lớn hơn hoặc bằng 50_000đ");
                    System.out.println("- Số tiền 1 lần rút không được quá 5_000_000đ đối với tài khoản thường, và không giới hạn đối với tài khoản Premium");
                    System.out.println("- Số dư còn lại sau khi rút phải lớn hơn hoặc bằng 50_000đ");
                    System.out.println("- Số tiền rút phải là bội số của 10_000đ");
                    isSavings = true;
                } else {
                    System.out.println("Tài khoản Loan");
                    System.out.println("- Hạn mức không được qúa giới hạn 100_000_000đ");
                    System.out.println("- Phí cho mỗi lần giao dịch là 0.05 trên số tiền giao dịch đối với tài khoản thường và 0.01 trên số tiền giao dịch đối với tài khoản Premium");
                    System.out.println("- Hạn mức còn lại sau khi rút tiền không được nhỏ hơn 50_000đ");
                    isSavings = false;
                }

                System.out.print("\nNhập số tiền: ");
                double money;
                while (true) {
                    try {
                        money = sc.nextDouble();
                        sc.nextLine();
                        break;
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.print("Nhập lại số tiền là một số thực: ");
                    }
                }

                // Kiểm tra số tiền hợp lệ trước khi cho rút
                // Kiểm tra số tiền hợp lệ theo Savings
                if (isSavings) {
                    SavingsAccount savingsAccount = (SavingsAccount) currentAccount;
                    if (!savingsAccount.isAccepted(money, true)) {
                        // Thông báo không hợp lệ lưu lịch sử giao dịch
                        System.out.println("Số tiền rút không hợp lệ. Kết thúc");
                    } else {
                        BANK.withdraw(id, stk, money, IS_KHACH_HANG);
                        System.out.println("Rút tiền thành công");
                    }
                }
                // Kiểm tra số tiền hợp lệ theo Loan
                else {
                    LoanAccount loanAccount = (LoanAccount) currentAccount;
                    if (!loanAccount.isAccepted(money, true)) {
                        // Thông báo không hợp lệ và lưu lịch sử giao dịch
                        System.out.println("Số tiền rút không hợp lệ. Kết thúc");
                    } else {
                        BANK.withdraw(id, stk, money, IS_KHACH_HANG);
                        System.out.println("Rút tiền thành công");
                    }
                }

                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                trangChu();
                break;
            case 6:// chuyển khoản
                id = BANK.getCurrentCustomerId();
                currentCustomer = BANK.getCustomerById(id);
                currentCustomer.displayAccountNumber();

                System.out.println("Nhập số tài khoản người chuyển:");
                stk = sc.next();
                sc.nextLine();
                while (currentCustomer.getAccounts().get(stk) == null) {
                    System.out.println("STK không tồn tại. Nhập lại:");
                    stk = sc.next();
                    sc.nextLine();
                }
                currentAccount = currentCustomer.getAccounts().get(stk);

                // Xác định tài khoản chuyển tiền là Savings hay Loan
                if (currentAccount instanceof SavingsAccount)
                    isSavings = true;
                else
                    isSavings = false;


                System.out.println("\nNhập thông tin người nhận:\n");
                System.out.println("------------------------------");
                System.out.println("Ngân hàng nhận:");
                System.out.println("------------------------------");
                System.out.println("1. Ngân hàng Vũ Trụ");
                System.out.println("2. Ngân hàng khác");
                System.out.println("------------------------------");

                System.out.print("Chọn chức năng 1 hoặc 2: ");
                int choice;
                while (true) {
                    try {
                        choice = sc.nextInt();
                        if (choice != 1 && choice != 2)
                            throw new Exception();
                        sc.nextLine();
                        break;
                    } catch (Exception ex) {
                        sc.nextLine();
                        System.out.print("Chọn chức năng 1 hoặc 2: ");
                    }
                }

                System.out.println("Nhập số tài khoản người nhận:");
                String accNumber = sc.next();
                sc.nextLine();
                while (choice == 1 && !BANK.isAccountExisted(accNumber)) {
                    System.out.println("STK không tồn tại. Nhập lại:");
                    accNumber = sc.next();
                    sc.nextLine();
                }

                // Nếu chuyển tiền trong cùng ngân hàng
                //   thì cho hiện tên người nhận để kiểm tra đã nhập đúng stk người nhận chưa?
                String receiveId = "";
                if (choice == 1) {
                    receiveId = BANK.getCustomerByAccountNumber(accNumber);
                    Customer receiveCustomer = BANK.getCustomerById(receiveId);
                    System.out.println(receiveCustomer.getName());
                }

                // chưa đặt điều kiện số tiền chuyển
                System.out.print("\nNhập số tiền cần chuyển: ");
                while (true){
                    try {
                        money = sc.nextDouble();
                        sc.nextLine();
                        break;
                    } catch (Exception e){
                        sc.nextLine();
                        System.out.print("Nhập lại số tiền là một số thực: ");
                    }
                }

                // Kiểm tra số tiền hợp lệ theo Savings
                if (isSavings) {
                    SavingsAccount savingsAccount = (SavingsAccount) currentAccount;
                    if (!savingsAccount.isAccepted(money, false)) {
                        // Thông báo không hợp lệ lưu lịch sử giao dịch
                        System.out.println("Số tiền chuyển không hợp lệ. Kết thúc");
                    } else {
                        if (choice != 1) {
                            receiveId = BANK.getBankCustomerId();
                            accNumber = BANK.getBankAccountNumber();
                        }
                        BANK.transfer(id, stk, receiveId, accNumber, money, IS_KHACH_HANG);
                        System.out.println("Chuyển tiền thành công");
                    }
                }
                // Kiểm tra số tiền hợp lệ theo Loan
                else {
                    LoanAccount loanAccount = (LoanAccount) currentAccount;
                    if (!loanAccount.isAccepted(money, false)) {
                        // Thông báo không hợp lệ và lưu lịch sử giao dịch
                        System.out.println("Số tiền chuyển không hợp lệ. Kết thúc");
                    } else {
                        if (choice != 1) {
                            receiveId = BANK.getBankCustomerId();
                            accNumber = BANK.getBankAccountNumber();
                        }
                        BANK.transfer(id, stk, receiveId, accNumber, money, IS_KHACH_HANG);
                        System.out.println("Chuyển tiền thành công");
                    }
                }

                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                trangChu();
                break;
            case 7: // Hiển thị lịch sử giao dịch
                System.out.println("10 giao dịch gần nhất:");
                BANK.displayTransaction(10);

                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                trangChu();
                break;
            case 0: // Đăng xuất khách hàng
                BANK.exitCurrentCustomer();
                System.out.println("Đăng xuất thành công");

                System.out.println("\nNhấn ENTER để tiếp tục");
                sc.nextLine();
                giaoDienBatDau(); // Quay về giao diện bắt đầu để đăng nhập tài khoản khác
                break;
            default:
                System.out.println("Nhập chưa chính xác");
                chucNangTrangChu();
        }
    }
}

