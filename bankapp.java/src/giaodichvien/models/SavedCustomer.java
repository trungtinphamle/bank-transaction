package giaodichvien.models;

import java.io.Serializable;
import java.util.Map;

public class SavedCustomer implements Serializable {
    private Map<String, Customer> map;
    private static final long serialVersionUID = 1L;
    public SavedCustomer(Map<String, Customer> map) {
        this.map = map;
    } // Dùng Hashmap

    public void updateCustomer(Customer customer){
        map.put(customer.getCustomerId(), customer);
    }

    public Map<String, Customer> getMap() {
        return map;
    }
}
