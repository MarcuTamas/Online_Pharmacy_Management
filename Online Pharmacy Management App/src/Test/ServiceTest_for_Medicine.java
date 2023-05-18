package Test;

import Domain.Medicine;
import Domain.MedicineValidator;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.MedicineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ServiceTest_for_Medicine {
    @Test
    void validAdd_should_PassThroughAllServiceMethods() throws Exception {
        //Arange
        IRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);



        //Act and assert

        //Create and read all.
        for (Medicine medicine: PopulateWithValidObjects.generateMedicineList()) {
            medicineService.add(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe());
        }
        Assertions.assertEquals(7, medicineService.getAll().size());

        //Read one.
        //We simulate that the id are valid and are input from another list.
        for (Medicine medicine : PopulateWithValidObjects.generateMedicineList()) {
            Assertions.assertNotNull(medicineService.getOne(medicine.getId()));
        }

        //Search.
        List<Medicine> searchedClient = medicineService.searchMedicine("true");
        Assertions.assertEquals(2, searchedClient.size());

        //Medicine Price Incrase.
        medicineService.medicinePriceIncrease(3, 100000);
        List<Medicine> medicinesWithIncreasedPrice = new ArrayList<>();
        for (Medicine medicine : medicineService.getAll()){
            if(medicine.getPrice() > 100){
                medicinesWithIncreasedPrice.add(medicine);
            }
        }
        Assertions.assertEquals(3, medicinesWithIncreasedPrice.size());

        //Update
        for (int i = 1; i < 4; i++){
            String name = "Updated name " + i;
            String producer = "Updated producer " + i;
            float price = (float) i;
            boolean needRecipe = (i%2 != 0) ? true : false;
            medicineService.updateMedicine(i, name, producer, price, needRecipe);
            Assertions.assertNotNull(medicineService.getOne(i));
        }

        //Delete.
        for (int i = 7; i > 3; i--){
            medicineService.deletMedicine(i);
        }
        Assertions.assertEquals(3, medicineService.getAll().size());
    }
}