package model;

import java.sql.Date;

public class Rental {

    private int rentalId;
    private int customerId;
    private int tuktukId;
    private Date startDate;
    private Date endDate;
    private double totalAmount;
    private String customerName;
    private String plateNo;

    public Rental() {
    }

    public Rental(int rentalId, int customerId, int tuktukId, Date startDate, Date endDate, double totalAmount) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.tuktukId = tuktukId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTuktukId() {
        return tuktukId;
    }

    public void setTuktukId(int tuktukId) {
        this.tuktukId = tuktukId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }
}