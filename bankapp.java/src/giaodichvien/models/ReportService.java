package giaodichvien.models;

/*
 interface quản lý nghiệp vụ báo cáo
*/
public interface ReportService {
    // In biên lai tài khoản khi rút tiền
    void log(double amount, boolean isWithdraw);
}
