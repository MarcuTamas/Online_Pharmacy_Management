package Service;

import Domain.Medicine;
import Domain.MedicineValidationException;
import Domain.MedicineValidator;
import Repository.IRepository;
import Repository.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class MedicineService {
    private IRepository<Medicine> medicineRepository;
    private MedicineValidator medicineValidator;

    public MedicineService(IRepository<Medicine> medicineRepository, MedicineValidator medicineValidator) {
        this.medicineRepository = medicineRepository;
        this.medicineValidator = medicineValidator;
    }


    //Crud
    public void add(int id, String name, String producer, float price, boolean needRecipe) throws MedicineValidationException, RepositoryException {
        Medicine medicine = new Medicine(id, name, producer, price, needRecipe);
        this.medicineValidator.validate(medicine);
        this.medicineRepository.create(medicine);
    }

    public Medicine getOne(int id) throws RepositoryException {
        return this.medicineRepository.readOne(id);
    }

    public void updateMedicine(int id, String name, String producer, float price, boolean needRecipe) throws MedicineValidationException, RepositoryException {
        Medicine medicine = new Medicine(id, name, producer, price, needRecipe);
        this.medicineValidator.validate(medicine);
        this.medicineRepository.update(medicine);
    }

    public void deletMedicine(int id) throws RepositoryException {
        this.medicineRepository.delete(id);
    }


    public List<Medicine> getAll() {
        return this.medicineRepository.readAll();
    }


    public List<Medicine> searchMedicine(String medicineDetail) {
        List<Medicine> allMedicines = medicineRepository.readAll();
        List<Medicine> foundedMedicines = new ArrayList<>();

        for (Medicine medicine : allMedicines) {

            if (Integer.toString(medicine.getId()).equalsIgnoreCase(medicineDetail) ||
                    medicine.getName().equalsIgnoreCase(medicineDetail) ||
                    medicine.getProducer().equalsIgnoreCase(medicineDetail) ||

                    //The strig valueOf method convert a float and a boolean type to string.
                    String.valueOf(medicine.getPrice()).equalsIgnoreCase(medicineDetail) ||
                    String.valueOf(medicine.isNeedRecipe()).equalsIgnoreCase(medicineDetail)) {

                foundedMedicines.add(medicine);
            }
        }
        return foundedMedicines;
    }

    public void medicinePriceIncrease(float price, float increasePercent) {
        for (Medicine medicine : this.getAll()){
            if (medicine.getPrice() < price){
                float medicinePrice = medicine.getPrice();
                float medicineUpdatedPrice = medicinePrice + (medicinePrice * (increasePercent/100f));
                medicine.setPrice(medicineUpdatedPrice);
            }
        }
    }

}



