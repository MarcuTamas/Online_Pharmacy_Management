package Domain;

public class MedicineSoldDTO {
    public Medicine medicine;
    public float totalSoldMedicine;

    public MedicineSoldDTO(Medicine medicine, float totalSoldMedicine) {
        this.medicine = medicine;
        this.totalSoldMedicine = totalSoldMedicine;
    }

    public void setTotalSoldMedicine(float totalSoldMedicine) {
        this.totalSoldMedicine = totalSoldMedicine;
    }

    @Override
    public String toString() {
        return "MedicineSoldDTO: " +
                medicine +
                ", totalSoldMedicine=" + totalSoldMedicine +
                '}';
    }
}
