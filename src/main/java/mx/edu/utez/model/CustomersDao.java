package mx.edu.utez.model;
import mx.edu.utez.Service.ConnectionMySQL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CustomersDao {
    private Connection con;
    private CallableStatement cstm;
    private ResultSet rs;
    public List<Customers> findAll(){
        List<Customers> listCustomers = new ArrayList<>();
        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers;");
            rs = cstm.executeQuery();

            while(rs.next()){
                Customers employee = new Customers();

                employee. setCustomerNumber(rs.getInt("employeeNumber"));
                employee.setContactLastName(rs.getString("lastname"));
                employee.setCustomerName(rs.getString("firstname"));

                listCustomers.add(employee);
            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return listCustomers;
    }
    public Customers findByCustomersNumber(int CustomersNumber){
        Customers customers = null;

        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers WHERE CustomersNumber = ?;");
            cstm.setInt(1, CustomersNumber);
            rs = cstm.executeQuery();

            if(rs.next()){
                customers = new Customers();

                customers.setCustomerNumber(rs.getInt("customerNumber"));
                customers.setContactLastName(rs.getString("LastName"));
                customers.setCustomerName(rs.getString("customerName"));
            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return customers;
    }
    public boolean save(Customers customers, boolean isCreate){
        boolean flag = false;

        try{
            con = ConnectionMySQL.getConnection();
            if(isCreate){
                cstm = con.prepareCall("INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country,salesRepEmployeeNumber, creditLimit) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

                cstm.setInt(1, customers.getCustomerNumber());
                cstm.setString(2, customers.getCustomerName());
                cstm.setString(3, customers.getContactLastName());
                cstm.setString(4, customers.getContactFirstName());
                cstm.setString(5, customers.getPhone());
                cstm.setString(6, customers.getAddressLine1());
                cstm.setString(7, customers.getAddressLine2());
                cstm.setString(8, customers.getCity());
                cstm.setString(9, customers.getState());
                cstm.setString(10, customers.getPostalCode());
                cstm.setString(11, customers.getCountry());
                cstm.setInt(12, customers.getSalesRepEmployeeNumber());
                cstm.setDouble(13, customers.getCreditLimit());
            } else {
                cstm = con.prepareCall("UPDATE customers SET customerNumber = ?, customerName = ?, contactLastName = ?, contactFirstName = ?, phone = ?, addressLine1 = ?, addressLine2 = ?, city = ?, state = ?, postalCode = ?, country = ?, salesRepEmployeeNumber = ?, creditLimit = ? WHERE customers.customerNumber = ?;");

                cstm.setString(1, customers.getCustomerName());
                cstm.setString(2, customers.getContactLastName());
                cstm.setString(3, customers.getContactFirstName());
                cstm.setString(4, customers.getPhone());
                cstm.setString(5, customers.getAddressLine1());
                cstm.setString(6, customers.getAddressLine2());
                cstm.setString(7, customers.getCity());
                cstm.setString(8, customers.getState());
                cstm.setString(9, customers.getPostalCode());
                cstm.setString(10, customers.getCountry());
                cstm.setInt(11, customers.getSalesRepEmployeeNumber());
                cstm.setDouble(12, customers.getCreditLimit());
                cstm.setInt(13, customers.getCustomerNumber());
            }

            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return flag;
    }

    public boolean delete(int employeeNumber){
        boolean flag = false;

        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("DELETE FROM customer WHERE customerNumber = ?;");
            cstm.setInt(1, employeeNumber);
            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return flag;
    }
}


