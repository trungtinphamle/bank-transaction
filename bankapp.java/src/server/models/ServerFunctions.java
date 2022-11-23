package server.models;

import java.io.*;
import java.net.Socket;

public class ServerFunctions extends Thread{
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    public ServerFunctions(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            String message = input.readUTF();
            // Get Customers
            if(message.equals("getcustomers")){
                inputStream = new ObjectInputStream (new BufferedInputStream(new FileInputStream("bankapp.java/src/data/customers.log")));
                // Đọc dữ liệu từ file và xuất cho client
                Object object = inputStream.readObject();
                output.writeObject(object);
                output.flush();
            }

            // update Customers
             if(message.equals("updatecustomers")){
                inputStream = new ObjectInputStream (new BufferedInputStream (new FileInputStream("bankapp.java/src/data/customers.log")));
                 // Đọc dữ liệu từ file và xuất cho client
                output.writeObject(inputStream.readObject());
                output.flush();

                outputStream = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream("bankapp.java/src/data/customers.log")));
                // Nhận lại dữ liệu từ client và lưu vào file
                outputStream.writeObject(input.readObject());
                // Phản hồi kết quả cho client
                output.writeUTF("Cập nhật khách hàng thành công");
                output.flush();
            }

            // Lấy danh sách Accounts
            if(message.equals("getaccounts")){
                inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("bankapp.java/src/data/accounts.log")));
                // Đọc dữ liệu từ file và xuất cho client
                output.writeObject(inputStream.readObject());
                output.flush();
            }

            // Cập nhật danh sách accounts
            if(message.equals("updateaccounts")){
                inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("bankapp.java/src/data/accounts.log")));
                // Đọc dữ liệu từ file và xuất cho client
                output.writeObject(inputStream.readObject());
                output.flush();

                outputStream = new ObjectOutputStream (new BufferedOutputStream (new FileOutputStream("bankapp.java/src/data/accounts.log")));
                // Nhận lại dữ liệu từ client và lưu vào file
                outputStream.writeObject(input.readObject());
                // Phản hồi kết quả cho client
                output.writeUTF("Cập nhật danh sách Account - ID thành công");
                output.flush();
            }
        } catch (IOException ex){
            System.out.println("OOPS " + ex.getMessage());
        } catch (ClassNotFoundException ex){
            System.out.println("Không tìm thấy dữ liệu. " + ex.getMessage());
        }finally {
            try {
                socket.close();
                outputStream.close();
                inputStream.close();
            } catch (IOException ex){
                System.out.println("oops " + ex.getMessage());
            } catch (NullPointerException ex){
            }
        }
    }
}
