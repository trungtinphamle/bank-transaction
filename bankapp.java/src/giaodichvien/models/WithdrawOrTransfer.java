package giaodichvien.models;

/*
 interface nghiệp vụ rút tiền
 */
public interface WithdrawOrTransfer {
    // Xác định rút tiền hay không
    void changeBalance (double amount, boolean isWithdraw);
    // Xac định giá trị có thoải điều kiện rút tiền hay không
    boolean isAccepted (double amount, boolean isWithdraw);

}
