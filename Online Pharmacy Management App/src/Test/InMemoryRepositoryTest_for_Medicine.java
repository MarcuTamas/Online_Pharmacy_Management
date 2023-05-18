package Test;

import Domain.ClientCard;
import Domain.Medicine;
import Domain.Transaction;
import Repository.IRepository;
import Repository.InMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class InMemoryRepositoryTest_for_Medicine {

    @Test
    void validAdd_should_PassThroughAllInMemoryRepositoryMethods() throws Exception {
        //Arrange
        IRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        List<Medicine> allMedicines = PopulateWithValidObjects.generateMedicineList();



        //Act and assert

        //Create
        for (Medicine medicine: allMedicines){
            medicineRepository.create(new Medicine(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe()));
        }
        Assertions.assertEquals(7, medicineRepository.readAll().size());



        //Read all
        List<Medicine> readAll_Medicine = medicineRepository.readAll();
        Assertions.assertEquals(7, readAll_Medicine.size());



        //Read one
        for (Medicine medicine : allMedicines) {
            Assertions.assertNotNull(medicineRepository.readOne(medicine.getId()));
        }


        //Update
        List<Medicine> updatedMedicine = new ArrayList<>();
        for (int i = 1; i < 4; i++){
            String name = "Updated name " + i;
            String producer = "Updated producer " + i;
            float price = (float) i;
            boolean needRecipe = (i%2 != 0) ? true : false;
            medicineRepository.update(new Medicine(i, name, producer, price, needRecipe));
            updatedMedicine.add(medicineRepository.readOne(i));
            Assertions.assertNotNull(medicineRepository.readOne(i));
        }
        Assertions.assertEquals(3, updatedMedicine.size());



        //Delete
        for (int i = 7; i > 3; i--){
            medicineRepository.delete(i);
        }
        Assertions.assertEquals(3, medicineRepository.readAll().size());
    }
}