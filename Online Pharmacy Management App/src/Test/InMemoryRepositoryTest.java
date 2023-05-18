package Test;

import Domain.ClientCard;
import Domain.Medicine;
import Domain.Transaction;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Repository.RepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class InMemoryRepositoryTest {
    IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
    IRepository<Medicine> medicineRepository = new InMemoryRepository<>();
    IRepository<Transaction> transactionRepository = new InMemoryRepository<>();

    @Test
    void validAdd_should_PassThroughAllInMemoryRepositoryMethods() throws Exception {
        //Arrange
        List<ClientCard> allClientCards = PopulateWithValidObjects.generateClintCardList();
        List<Medicine> allMedicines = PopulateWithValidObjects.generateMedicineList();
        List<Transaction> allTransactions = PopulateWithValidObjects.generateTransactionList();



        //Act and assert

        //Create
        for (ClientCard client : allClientCards){
            this.clientCardRepository.create(new ClientCard(client.getId(), client.getSurname(), client.getName(), client.getCnp(), client.getBirthday(), client.getRegistrationDate()));
        }
        for (Medicine medicine: allMedicines){
            this.medicineRepository.create(new Medicine(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe()));
        }
        for (Transaction transaction : allTransactions){
            if(transaction.isHasClientCard()){
                this.transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.isHasClientCard(), transaction.getClientCardID(), transaction.getPriceDiscount(), transaction.getTransactionDate()));
            }else{
                this.transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.getTransactionDate()));
            }
        }
        Assertions.assertEquals(7, this.clientCardRepository.readAll().size());
        Assertions.assertEquals(7, this.medicineRepository.readAll().size());
        Assertions.assertEquals(12, this.transactionRepository.readAll().size());



        //Read all
        List<ClientCard> readAll_Client = this.clientCardRepository.readAll();
        List<Medicine> readAll_Medicine = this.medicineRepository.readAll();
        List<Transaction> readAll_Transact = this.transactionRepository.readAll();

        Assertions.assertEquals(7, readAll_Client.size());
        Assertions.assertEquals(7, readAll_Medicine.size());
        Assertions.assertEquals(12, readAll_Transact.size());



        //Read one
        for (ClientCard client : allClientCards) {
            Assertions.assertNotNull(this.clientCardRepository.readOne(client.getId()));
        }
        for (Medicine medicine : allMedicines) {
            Assertions.assertNotNull(this.medicineRepository.readOne(medicine.getId()));
        }
        for (Transaction transaction : allTransactions) {
            Assertions.assertNotNull(this.transactionRepository.readOne(transaction.getId()));
        }


        //Update
        List<ClientCard> updatedClient = new ArrayList<>();
        List<Medicine> updatedMedicine = new ArrayList<>();
        List<Transaction> updatedTransaction = new ArrayList<>();

        for (int i = 1; i < 4; i++){
            String surName = "Updated surname " + i;
            String name = "Updated name " + i;
            String cnp = "1960902" + i + "23123";
            String birthday = "Updated birthday " + i;
            String registrationDate = "Updated registration date " + i;
            this.clientCardRepository.update(new ClientCard(i, surName, name, cnp, birthday, registrationDate));
            updatedClient.add(this.clientCardRepository.readOne(i));
            Assertions.assertNotNull(this.clientCardRepository.readOne(i));
        }

        for (int i = 1; i < 4; i++){
            String name = "Updated name " + i;
            String producer = "Updated producer " + i;
            float price = (float) i;
            boolean needRecipe = (i%2 != 0) ? true : false;
            this.medicineRepository.update(new Medicine(i, name, producer, price, needRecipe));
            updatedMedicine.add(this.medicineRepository.readOne(i));
            Assertions.assertNotNull(this.medicineRepository.readOne(i));
        }

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
                this.transactionRepository.update(newTransaction);
                updatedTransaction.add(this.transactionRepository.readOne(i));
            }else{
                newTransaction = new Transaction(i, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, transactionDate);
                this.transactionRepository.update(newTransaction);
                updatedTransaction.add(this.transactionRepository.readOne(i));
            }
            Assertions.assertNotNull(this.transactionRepository.readOne(i));
        }

        Assertions.assertEquals(3, updatedClient.size());
        Assertions.assertEquals(3, updatedMedicine.size());
        Assertions.assertEquals(3, updatedTransaction.size());



        //Delete
        for (int i = 7; i > 3; i--){
            this.clientCardRepository.delete(i);
            this.medicineRepository.delete(i);
            this.transactionRepository.delete(i);
        }

        Assertions.assertEquals(3, this.clientCardRepository.readAll().size());
        Assertions.assertEquals(3, this.medicineRepository.readAll().size());
        Assertions.assertEquals(8, this.transactionRepository.readAll().size());
    }


    @Test
    void invalidAdd_should_RiseExceptionForCRUD() throws Exception {
        //Arrange
        List<ClientCard> allClientCards = PopulateWithValidObjects.generateClintCardList();
        List<Medicine> allMedicines = PopulateWithValidObjects.generateMedicineList();
        List<Transaction> allTransactions = PopulateWithValidObjects.generateTransactionList();



        //Act and assert

        //Create
        try {
            for (ClientCard client : allClientCards) {
                this.clientCardRepository.create(new ClientCard(client.getId(), client.getSurname(), client.getName(), client.getCnp(), client.getBirthday(), client.getRegistrationDate()));
                this.clientCardRepository.create(new ClientCard(client.getId(), client.getSurname(), client.getName(), client.getCnp(), client.getBirthday(), client.getRegistrationDate()));
            }
            for (Medicine medicine : allMedicines) {
                this.medicineRepository.create(new Medicine(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe()));
                this.medicineRepository.create(new Medicine(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe()));
            }
            for (Transaction transaction : allTransactions) {
                if (transaction.isHasClientCard()) {
                    this.transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.isHasClientCard(), transaction.getClientCardID(), transaction.getPriceDiscount(), transaction.getTransactionDate()));
                    this.transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.isHasClientCard(), transaction.getClientCardID(), transaction.getPriceDiscount(), transaction.getTransactionDate()));
                } else {
                    this.transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.getTransactionDate()));
                    this.transactionRepository.create(new Transaction(transaction.getId(), transaction.getMedicineID(), transaction.isMedicineNeedRecipe(), transaction.getMedicinePieces(), transaction.getMedicinePrice(), transaction.getMedicinePaidPrice(), transaction.getTransactionDate()));
                }
            }
            Assertions.fail("Exception not raised in repository for duplicates id.");
        }catch (RepositoryException createRaisedException){
            System.out.println("Repository error: " + createRaisedException.getMessage());
        }



        //Read one
        try {
            this.clientCardRepository.readOne(26);
            Assertions.fail("Exception not raised in repository for no entity with given id.");
        }catch (RepositoryException readOneRaisedException){
            System.out.println("Repository error: " + readOneRaisedException.getMessage());
        }
        try {
            this.medicineRepository.readOne(26);
            Assertions.fail("Exception not raised in repository for no entity with given id.");
        }catch (RepositoryException readOneRaisedException){
            System.out.println("Repository error: " + readOneRaisedException.getMessage());
        }
        try {
            this.transactionRepository.readOne(26);
            Assertions.fail("Exception not raised in repository for no entity with given id.");
        }catch (RepositoryException readOneRaisedException){
            System.out.println("Repository error: " + readOneRaisedException.getMessage());
        }


        //Update
        try {
            int i = 77;
            String surName = "Updated surname " + i;
            String name = "Updated name " + i;
            String cnp = "1960902" + i + "23123";
            String birthday = "Updated birthday " + i;
            String registrationDate = "Updated registration date " + i;
            this.clientCardRepository.update(new ClientCard(i, surName, name, cnp, birthday, registrationDate));
            Assertions.fail("Exception not raised in repository for no entity with given id to be updated.");
        }catch (RepositoryException updateRaisedException){
            System.out.println("Repository error: " + updateRaisedException.getMessage());
        }

        try {
            int i = 77;
            String name = "Updated name " + i;
            String producer = "Updated producer " + i;
            float price = (float) i;
            boolean needRecipe = (i%2 != 0) ? true : false;
            this.medicineRepository.update(new Medicine(i, name, producer, price, needRecipe));
            Assertions.fail("Exception not raised in repository for no entity with given id to be updated.");
        }catch (RepositoryException updateRaisedException){
            System.out.println("Repository error: " + updateRaisedException.getMessage());
        }

        try{
            int i = 77;
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
                this.transactionRepository.update(newTransaction);
            }else{
                newTransaction = new Transaction(i, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, transactionDate);
                this.transactionRepository.update(newTransaction);
            }
            Assertions.fail("Exception not raised in repository for no entity with given id to be updated.");
        }catch (RepositoryException updateRaisedException){
            System.out.println("Repository error: " + updateRaisedException.getMessage());
        }



        //Delete
        try {
            this.clientCardRepository.delete(77);
            Assertions.fail("Exception not raised in repository for no entity with given id to be deleted.");
        }catch (RepositoryException deleteRaisedException) {
            System.out.println("Repository error: " + deleteRaisedException.getMessage());
        }
        try {
            this.medicineRepository.delete(77);
            Assertions.fail("Exception not raised in repository for no entity with given id to be deleted.");
        }catch (RepositoryException deleteRaisedException) {
            System.out.println("Repository error: " + deleteRaisedException.getMessage());
        }
        try {
            this.transactionRepository.delete(77);
            Assertions.fail("Exception not raised in repository for no entity with given id to be deleted.");
        }catch (RepositoryException deleteRaisedException) {
            System.out.println("Repository error: " + deleteRaisedException.getMessage());
        }
    }
}