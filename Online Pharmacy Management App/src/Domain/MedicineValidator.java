package Domain;

public class MedicineValidator {
    public void validate(Medicine medicine) throws MedicineValidationException {
        if(medicine.getPrice() <= 0){
            throw new MedicineValidationException("The medicine price cannot be less or equal to 0.");
        }
    }
}
