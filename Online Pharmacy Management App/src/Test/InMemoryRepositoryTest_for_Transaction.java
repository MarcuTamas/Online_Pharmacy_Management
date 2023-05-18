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

class InMemoryRepositoryTest_for_Transaction {
    @Test
    void validAdd_should_PassThroughAllInMemoryRepositoryMethods() throws Exception {
        //Arrange
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        List<Transaction> allTransactions = PopulateWithValidObjects.generateTransactionList();



        //Act and assert

        //Create
        for (Transaction transaction : allTransactions){
            if(transaction.isHasClientCard()){
                transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.isHasClientCard(), transaction.getClientCardID(), transaction.getPriceDiscount(), transaction.getTransactionDate()));
            }else{
                transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.getTransactionDate()));
            }
        }
        Assertions.assertEquals(12, transactionRepository.readAll().size());



        //Read all
        List<Transaction> readAll_Transact = transactionRepository.readAll();
        Assertions.assertEquals(12, readAll_Transact.size());



        //Read one
        for (Transaction transaction : allTransactions) {
            Assertions.assertNotNull(transactionRepository.readOne(transaction.getId()));
        }


        //Update
        List<Transaction> updatedTransaction = new ArrayList<>();
        for (int i = 1; i < 4; i++){
            int medicineID = i;
            boolean medicineNeedRecipe = (i%2 != 0) ? true : false;
            int medicinePieces = i;
            float medicinePrice = i;
            float medicinePaidPrice = i;
            float priceDiscount = i;
            boolean hasClientCard = (i%2 != 0) ? true : false;
            int clientCardID = i;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String date = "02.09.1996 17:07";
            Date transactionDate = dateFormat.parse(date);

            Transaction newTransaction;

            //Test both overload update method and both discount amounts.
            if(hasClientCard){
                if(i < 3){
                    medicineNeedRecipe = false;
                }
                newTransaction = new Transaction(i, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, hasClientCard, clientCardID, priceDiscount, transactionDate);
                transactionRepository.update(newTransaction);
                updatedTransaction.add(transactionRepository.readOne(i));
            }else{
                newTransaction = new Transaction(i, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, transactionDate);
                transactionRepository.update(newTransaction);
                updatedTransaction.add(transactionRepository.readOne(i));
            }
            Assertions.assertNotNull(transactionRepository.readOne(i));
        }
        Assertions.assertEquals(3, updatedTransaction.size());



        //Delete
        for (int i = 7; i > 3; i--){
            transactionRepository.delete(i);
        }
        Assertions.assertEquals(8, transactionRepository.readAll().size());
    }
}