package giaodichvien.models;

import java.io.Serializable;
import java.util.Map;

public class SavedAccount implements Serializable {
    private static final long serialVersionUID = 2L;
    private Map<String, String> map;
    public SavedAccount(Map<String, String> map) {
        this.map = map;
    } // Dùng Hashmap

    public void updateAccountId(String accountNumber, String customerId){
        map.put(accountNumber,customerId);
    }

    public Map<String, String> getMap() {
        return map;
    }
}
