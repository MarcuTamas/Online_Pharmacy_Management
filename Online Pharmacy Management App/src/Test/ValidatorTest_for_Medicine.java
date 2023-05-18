package Test;

import Domain.Medicine;
import Domain.MedicineValidationException;
import Domain.MedicineValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ValidatorTest_for_Medicine {
    MedicineValidator medicineValidator = new MedicineValidator();


    @Test
    void addInvalidMedicine_should_RaiseException() {
        //Arrange act and assert.

        //Test if the price is 0.
        try{
            Medicine medicineWithInvalidPrice = new Medicine(8, "test", "test", 0, true);
            medicineValidator.validate(medicineWithInvalidPrice);
            Assertions.fail("Exception not raised in medicine validator for invalid price.");
        } catch (MedicineValidationException medicineValidationException) {
            System.out.println("Medicine validation exception: " + medicineValidationException.getMessage());
        }

        //Test if the price is less than 0.
        try{
            Medicine medicineWithInvalidPrice = new Medicine(8, "test", "test", -1, true);
            medicineValidator.validate(medicineWithInvalidPrice);
            Assertions.fail("Exception not raised in medicine validator for invalid price.");
        } catch (MedicineValidationException medicineValidationException) {
            System.out.println("Medicine validation exception: " + medicineValidationException.getMessage());
        }
    }

}