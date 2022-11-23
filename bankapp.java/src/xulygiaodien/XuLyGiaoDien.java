package xulygiaodien;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class XuLyGiaoDien {
    public static final Scanner sc = new Scanner(System.in);

    // Hàm tạo chức năng
    public static int nhapChucNang(){
        while (true){
            try{
                int chucNang = sc.nextInt(); // Nếu nhập chữ sẽ báo lỗi và nhập lại
                return chucNang;
            } catch (Exception ex){
                sc.nextLine();
                System.out.println("Vui lòng nhập một chữ số");
                System.out.print("Chức năng: ");
            }
        }
    }

    // Hàm checkMaXacThucHard
    public static boolean checkMaXacThuc(){
        while (true) {
            String chuoiKyTu = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String maXacThuc = "";
            for (int i = 0; i<6; ++i) {
                int viTri = (int) (Math.random()* chuoiKyTu.length());
                maXacThuc += chuoiKyTu.charAt(viTri);
            }
            System.out.println("Nhập mã xác thực: " + maXacThuc);
            Scanner sc = new Scanner(System.in);
            String matMa = sc.nextLine();
            if(matMa.equals(maXacThuc)) return true;
            else System.out.println("Mã xác thực không đúng, vui lòng thử lại");
        }
    }

    public static String matKhauNgauNhien(){
        String chuoiKyTu = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String matKhau = "";
        // Mật khẩu ngẫu nhiên 8 chữ số
        for (int i = 0; i<8; ++i) {
            int viTri = (int) (Math.random()* chuoiKyTu.length());
            matKhau += chuoiKyTu.charAt(viTri);
        }
        return matKhau;
    }
    
    // Hàm nhập CCCD
    public static String nhapCCCD(){
        // Nhập CCCD
        String customerId  = sc.next().trim();
        sc.nextLine();
        boolean checkCCCD = checkCCCD(customerId); // Check CCCD hợp lệ
        while (!checkCCCD){
            System.out.println("Số CCCD không hợp lệ hoặc chưa tồn tại");
            System.out.println("Vui lòng nhập lại số CCCD:");
            customerId = sc.next().trim();
            sc.nextLine();
            checkCCCD = checkCCCD(customerId);
        }
        return customerId;
    }

    // Hàm checkCCCD
    public static boolean checkCCCD(String cccd){
        // check độ dài đúng 12 ký tự
        if(cccd.length()!= 12) return false;

        // check chuỗi chỉ chứa số
        for (int i = 0; i<12; ++i){
            if(cccd.charAt(i)<'0'|| cccd.charAt(i)>'9'){
                return false;
            }
        }
        // check 3 số đầu có thuoc danh sách tỉnh thành
        Map <String, String> map = danhSachThanhPho();
        boolean check = false;
        for (String item : map.keySet()){
            if(item.equals(cccd.substring(0,3))) {
                check = true;
                break;
            }
        }
        if(!check) System.out.print("3 số đầu không hợp lệ. ");
        return check;
    }
    // Hàm tạo danh sách 63 tỉnh, thành phố
    public static Map<String,String> danhSachThanhPho(){
        Map<String, String> map = new HashMap<>();
        map.put("001","Hà Nội");
        map.put("002","Hà Giang");
        map.put("004","Cao Bằng");
        map.put("006","Bắc Kạn");
        map.put("008","Tuyên Quang");
        map.put("010","Lào Cai");
        map.put("011","Điện Biên");
        map.put("012","Lai Châu");
        map.put("014","Sơn La");
        map.put("015","Yên Bái");
        map.put("017","Hòa Bình");
        map.put("019","Thái Nguyên");
        map.put("020","Lạng Sơn");
        map.put("022","Quảng Ninh");
        map.put("024","Bắc Giang");
        map.put("025","Phú Thọ");
        map.put("026","Vĩnh Phúc");
        map.put("027","Bắc Ninh");
        map.put("030","Hải Dương");
        map.put("031","Hải Phòng");
        map.put("033","Hưng Yên");
        map.put("034","Thái Bình");
        map.put("035","Hà Nam");
        map.put("036","Nam Định");
        map.put("037","Ninh Bình");
        map.put("038","Thanh Hóa");
        map.put("040","Nghệ An");
        map.put("042","Hà Tĩnh");
        map.put("044","Quảng Bình");
        map.put("045","Quảng Trị");
        map.put("046","Thừa Thiên Huế");
        map.put("048","Đà Nẵng");
        map.put("049","Quảng Nam");
        map.put("051","Quảng Ngãi");
        map.put("052","Bình Định");
        map.put("054","Phú Yên");
        map.put("056","Khánh Hòa");
        map.put("058","Ninh Thuận");
        map.put("060","Bình Thuận");
        map.put("062","Kon Tum");
        map.put("064","Gia Lai");
        map.put("066","Đắk Lắk");
        map.put("067","Đắk Nông");
        map.put("068","Lâm Đồng");
        map.put("070","Bình Phước");
        map.put("072","Tây Ninh");
        map.put("074","Bình Dương");
        map.put("075","Đồng Nai");
        map.put("077","Bà Rịa - Vũng Tàu");
        map.put("079","Hồ Chí Minh");
        map.put("080","Long An");
        map.put("082","Tiền Giang");
        map.put("083","Bến Tre");
        map.put("084","Trà Vinh");
        map.put("086","Vĩnh Long");
        map.put("087","Đồng Tháp");
        map.put("089","An Giang");
        map.put("091","Kiên Giang");
        map.put("092","Cần Thơ");
        map.put("093","Hậu Giang");
        map.put("094","Sóc Trăng");
        map.put("095","Bạc Liêu");
        map.put("096","Cà Mau");
        return map;
    }
    // Hàm hiển thị thông tin nơi sinh, giới tính,tuổi...
    public static void hienThiThongTin(String cccd){

        Map <String, String> map = danhSachThanhPho();
        for (String item : map.keySet()){
            if(item.equals(cccd.substring(0,3))) {
                System.out.println("Nơi sinh:" + String.format("%46s",map.get(item)));
                break;
            }
        }

        // Xác định gioi tính
        String gioiTinh;
        if((int)(cccd.charAt(3))%2==0)gioiTinh = "Nam";
        else gioiTinh = "Nữ";
        System.out.println("Giới tính:"+ String.format("%45s",gioiTinh));

        // Xác định năm sinh
        String namSinh;
        if(cccd.charAt(3)=='0'||cccd.charAt(3)=='1')namSinh = "19"+ cccd.substring(4,6);
        else if(cccd.charAt(3)=='2'||cccd.charAt(3)=='3')namSinh = "20"+ cccd.substring(4,6);
        else if(cccd.charAt(3)=='4'||cccd.charAt(3)=='5')namSinh = "21"+ cccd.substring(4,6);
        else if(cccd.charAt(3)=='6'||cccd.charAt(3)=='7')namSinh = "22"+ cccd.substring(4,6);
        else namSinh = "23"+ cccd.substring(4,6);

        // Hiển thị giới tính và năm sinh
        System.out.println("Năm sinh: "+ String.format("%45s",namSinh));
    }

    // Hàm getDateTime trả về kế quả thời điểm gọi hàm theo format sẵn có
    public static String getDateTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }
}
