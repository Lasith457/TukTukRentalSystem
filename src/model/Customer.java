package model;

public class Customer {

    private int customerId;
    private String name;
    private String nic;
    private String phone;
    private String licenseNo;

    public Customer() {
    }

    public Customer(int customerId, String name, String nic, String phone, String licenseNo) {
        this.customerId = customerId;
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.licenseNo = licenseNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }
}