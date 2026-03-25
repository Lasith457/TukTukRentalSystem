package model;

public class TukTuk {

    private int tuktukId;
    private String plateNo;
    private String model;
    private double pricePerDay;
    private String status;

    public TukTuk() {
    }

    public TukTuk(int tuktukId, String plateNo, String model, double pricePerDay, String status) {
        this.tuktukId = tuktukId;
        this.plateNo = plateNo;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public int getTuktukId() {
        return tuktukId;
    }

    public void setTuktukId(int tuktukId) {
        this.tuktukId = tuktukId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}