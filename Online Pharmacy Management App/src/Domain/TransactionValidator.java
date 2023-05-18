package Domain;

import Repository.IRepository;

public class TransactionValidator {
    public void validate(Transaction transaction, IRepository<Medicine> medicineRepository) throws TransactionValidationException{
        try {
            medicineRepository.readOne(transaction.getMedicineID());
        }catch (Exception readOneException){
            throw new TransactionValidationException("The medicine with the id " + transaction.getMedicineID() + " does not exist.");
        }
    }
}
