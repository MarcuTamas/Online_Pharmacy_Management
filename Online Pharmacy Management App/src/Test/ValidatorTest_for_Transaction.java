package Test;

import Domain.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class ValidatorTest_for_Transaction {
    IRepository<Medicine> medicineRepository = new InMemoryRepository<>();
    TransactionValidator transactionValidator = new TransactionValidator();

    void populateRepositoryWithMedicine () throws Exception {
        List<Medicine> allMedicines = PopulateWithValidObjects.generateMedicineList();

        for (Medicine medicine : allMedicines){
            this.medicineRepository.create(new Medicine(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe()));
        }
    }


    @Test
    void addInvalidTransaction_should_RaiseException() throws Exception {

        //Arange.
        populateRepositoryWithMedicine();


        //Act and assert.
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String date = "02.09.1996 17:07";
            Date transactionDate = dateFormat.parse(date);
            Transaction transactionWithInvalidFormat = new Transaction(1, 8, false, 10, 10, 100, transactionDate);
            this.transactionValidator.validate(transactionWithInvalidFormat, medicineRepository);
            Assertions.fail("Exception not raised in transaction validator for invalid medicine id.");
        } catch (TransactionValidationException transactionValidationException) {
            System.out.println("Transaction validation error: " + transactionValidationException.getMessage());
        } catch (ParseException parseException) {
            System.out.println("ParseException error: invalid date format.");
        }
    }
}