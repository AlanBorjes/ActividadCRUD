package mx.edu.utez.controller;
import mx.edu.utez.model.Customers;
import mx.edu.utez.model.CustomersDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
@Path("/Customers")
public class Service {
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customers> getCustomers(){
        return new CustomersDao().findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customers getCustomers(@PathParam("id") int customersNumber){
        return new CustomersDao().findByCustomersNumber(customersNumber);
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Customers save(MultivaluedMap<String, String> formParams){
        int customersNumber = Integer.parseInt(formParams.get("customersNumber").get(0));
        if(new CustomersDao().save(getParams(customersNumber, formParams), true))
            return new CustomersDao().findByCustomersNumber(customersNumber);
        return null;
    }

    @POST
    @Path("/save/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Customers save(@PathParam("id") int customersNumber, MultivaluedMap<String, String> formParams){
        if(new CustomersDao().save(getParams(customersNumber, formParams), false))
            return new CustomersDao().findByCustomersNumber(customersNumber);
        return null;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteCustomers(@PathParam("id") int customerNumber){
        return new CustomersDao().delete(customerNumber);
    }

    private Customers getParams(int customerNumber, MultivaluedMap<String, String> formParams){
        String customerName = formParams.get("customerName").get(0);
        String contactLastName = formParams.get("contactLastName").get(0);
        String contactFirstName = formParams.get("contactFirstName").get(0);
        String phone = formParams.get("phone").get(0);
        String addressLine1 = formParams.get("addressLine1").get(0);
        String addressLine2 = formParams.get("addressLine2").get(0);
        String city = formParams.get("city").get(0);
        String state = formParams.get("state").get(0);
        String postalCode = formParams.get("postalCode").get(0);
        String country = formParams.get("country").get(0);
        int salesRepEmployeeNumber = Integer.parseInt(formParams.get("salesRepEmployeeNumber").get(0));
        int creditLimit = Integer.parseInt(formParams.get("creditLimit").get(0));

        Customers customers = new Customers(customerNumber,  customerName,  contactLastName,  contactFirstName,  phone,  addressLine1,  addressLine2,  city,  state,  postalCode,  country,  salesRepEmployeeNumber,  creditLimit);
        System.out.println(customers);
        return customers;
    }
}
