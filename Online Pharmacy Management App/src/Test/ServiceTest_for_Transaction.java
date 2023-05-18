package Test;

import Domain.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.ClientCardService;
import Service.MedicineService;
import Service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
class ServiceTest_for_Transaction {


    @Test
    void validAdd_should_PassThroughAllServiceMethods() throws Exception {
        //Arrange

        //Repository
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        IRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();

        //Validator
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        TransactionValidator transactionValidator = new TransactionValidator();

        //Service
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        MedicineService medicineService = new MedicineService(medicineRepository,medicineValidator);
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository, clientCardRepository, transactionValidator);

        //Populate with needed object.
        for (Medicine medicine: PopulateWithValidObjects.generateMedicineList()) {
            medicineService.add(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe());
        }

        for (ClientCard client: PopulateWithValidObjects.generateClintCardList()) {
            clientCardService.add(client.getId(), client.getSurname(), client.getName(), client.getCnp(), client.getBirthday(), client.getRegistrationDate());
        }


        //Act and assert

        //Create and read all.
        for (Transaction transaction : PopulateWithValidObjects.generateTransactionList()) {
            if (!transaction.isHasClientCard()){
                transactionService.add(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getTransactionDate());
            }else {
                transactionService.add(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.isHasClientCard(), transaction.getClientCardID(), transaction.getTransactionDate());
            }
        }
        Assertions.assertEquals(12, transactionService.getAll().size());



        //Read one.
            //We simulate that the id are valid and are input from another list.
        for (Transaction transaction : PopulateWithValidObjects.generateTransactionList()) {
            Assertions.assertNotNull(transactionService.getOne(transaction.getId()));
        }



        //getTransactionByDate.
        List<Date> dateList = new ArrayList<>();
        for (Transaction transaction : PopulateWithValidObjects.generateTransactionList()){
            dateList.add(transaction.getTransactionDate());
        }
        Assertions.assertEquals(12, dateList.size());



        //medicineSortedBySoldAmount.
        List<MedicineSoldDTO> allMedicineSold = transactionService.medicineSortedBySoldAmount();
        Assertions.assertEquals(7, allMedicineSold.size());



        //clientCardSortedByDiscount.
        List<ClientCardDiscountDTO> allClientCards = transactionService.clientCardSortedByDiscount();
        Assertions.assertEquals(7, allClientCards.size());



        //Update first three transactions.
        List<Transaction> updatedTransaction = new ArrayList<>();
        for (int i = 1; i < 4; i++){
            int medicineID = i;
            boolean medicineNeedRecipe = (i%2 != 0) ? true : false;
            int medicinePieces = i;
            float medicinePrice = i;
            boolean hasClientCard = (i%2 != 0) ? true : false;
            int clientCardID = i;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String date = "02.09.1996 17:07";
            Date transactionDate = dateFormat.parse(date);

            //Test both overload update method and both discount amounts.
            if(hasClientCard){
                if(i < 3){
                    medicineNeedRecipe = false;
                }
                transactionService.updateTransaction(i,medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, hasClientCard,clientCardID, transactionDate);
                updatedTransaction.add(transactionService.getOne(i));
            }else{
                transactionService.updateTransaction(i,medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, transactionDate);
                updatedTransaction.add(transactionService.getOne(i));
            }
            Assertions.assertNotNull(transactionService.getOne(i));
        }
        Assertions.assertEquals(3, updatedTransaction.size());



        //Delete four transaction.
        for (int i = 7; i > 3; i--){
            transactionService.deleteTransaction(i);
        }
        Assertions.assertEquals(8, transactionService.getAll().size());



        //deleteTransactionByDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String date = "02.09.1996 17:07";
        Date startDate = dateFormat.parse(date);
        Date endDate = startDate;
        List<Transaction> deleteTransactionByDateList = transactionService.deleteTransactionByDate(startDate, endDate);
        Assertions.assertEquals(3, deleteTransactionByDateList.size());



        //getTransactionByDate
        Date startUpdateDate = dateFormat.parse("01.01.2023 01:03");
        Date endUpdateDate = dateFormat.parse("12.12.2023 07:03");
        List<Transaction> getTransactionByDateList = transactionService.getTransactionByDate(startUpdateDate, endUpdateDate);
        Assertions.assertEquals(3, getTransactionByDateList.size());
    }
}