package util;

import dao.CustomerDAO;
import model.Customer;

import java.util.List;

public class TestCustomer {

    public static void main(String[] args) {

        CustomerDAO dao = new CustomerDAO();

        List<Customer> customers = dao.getAllCustomers();

        for (Customer c : customers) {

            System.out.println(
                    c.getCustomerId() + " "
                            + c.getName() + " "
                            + c.getNic() + " "
                            + c.getPhone()
            );

        }

    }
}