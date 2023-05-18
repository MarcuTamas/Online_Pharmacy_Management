package Service;

import Domain.*;
import Repository.IRepository;
import Repository.RepositoryException;
import Test.PopulateWithValidObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionService {

    private IRepository<Transaction> transactionRepository;
    private IRepository<Medicine>  medicineRepository;
    private IRepository<ClientCard> clientCardRepository;
    private TransactionValidator transactionValidator;

    public TransactionService(IRepository<Transaction> transactionRepository, IRepository<Medicine> medicineRepository, IRepository<ClientCard> clientCardRepository, TransactionValidator transactionValidator) {
        this.transactionRepository = transactionRepository;
        this.medicineRepository = medicineRepository;
        this.clientCardRepository = clientCardRepository;
        this.transactionValidator = transactionValidator;
    }

    //Crud

    /**
     * Overloaded add methods, that checks if the client has a client card
     * and proceed as well by calling the needed constructor of Transaction Class.
     */
    public void add(int id, int medicineID, boolean medicineNeedRecipe, int medicinePieces, float medicinePrice, Date transactionDate) throws TransactionValidationException, RepositoryException {
        float medicinePaidPrice = (float) medicinePieces*medicinePrice;

        Transaction transaction= new Transaction(id, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, transactionDate);
        this.transactionValidator.validate(transaction, this.medicineRepository);
        this.transactionRepository.create(transaction);
    }

    public void add(int id, int medicineID, boolean medicineNeedRecipe, int medicinePieces, float medicinePrice, boolean hasClientCard, int clientCardID, Date transactionDate) throws TransactionValidationException, RepositoryException {
        float medicinePaidPrice = (float) medicinePieces*medicinePrice;
        float priceDiscount = 0;

        if(medicineNeedRecipe){
            priceDiscount = (medicinePaidPrice*15)/100;
            medicinePaidPrice -= priceDiscount;
        }else{
            priceDiscount = (medicinePaidPrice*10)/100;
            medicinePaidPrice -= priceDiscount;
        }

        Transaction transaction = new Transaction(id, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, hasClientCard, clientCardID, priceDiscount, transactionDate);
        this.transactionValidator.validate(transaction, this.medicineRepository);
        this.transactionRepository.create(transaction);
    }

    public Transaction getOne(int id) throws RepositoryException {
        return this.transactionRepository.readOne(id);
    }

    //Ibidem, overloaded methods.
    public void updateTransaction(int id, int medicineID, boolean medicineNeedRecipe, int medicinePieces, float medicinePrice, Date transactionDate) throws TransactionValidationException, RepositoryException {
        float medicinePaidPrice = (float) medicinePieces*medicinePrice;

        Transaction transaction= new Transaction(id, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, transactionDate);
        this.transactionValidator.validate(transaction, this.medicineRepository);
        this.transactionRepository.update(transaction);
    }

    public void updateTransaction(int id, int medicineID, boolean medicineNeedRecipe, int medicinePieces, float medicinePrice, boolean hasClientCard, int clientCardID, Date transactionDate) throws TransactionValidationException, RepositoryException {
        float medicinePaidPrice = (float) medicinePieces*medicinePrice;
        float priceDiscount = 0;

        if(medicineNeedRecipe){
            priceDiscount = (medicinePaidPrice*15)/100;
            medicinePaidPrice -= priceDiscount;
        }else{
            priceDiscount = (medicinePaidPrice*10)/100;
            medicinePaidPrice -= priceDiscount;
        }

        Transaction transaction = new Transaction(id, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, hasClientCard, clientCardID, priceDiscount, transactionDate);
        this.transactionValidator.validate(transaction, this.medicineRepository);
        this.transactionRepository.update(transaction);
    }

    public void deleteTransaction(int id) throws RepositoryException {
        this.transactionRepository.delete(id);
    }

    public List<Transaction> getAll(){
        return this.transactionRepository.readAll();
    }

    public List<Transaction> getTransactionByDate(Date startDate, Date endDate) {
        List<Transaction> transactionsBetweenDates = new ArrayList<>();

        for (Transaction transaction : this.getAll()) {
            Date transactionDate = transaction.getTransactionDate();

            if (transactionDate.after(startDate) && transactionDate.before(endDate) ||
                    transactionDate.equals(startDate) ||
                    transactionDate.equals(endDate)) {

                transactionsBetweenDates.add(transaction);
            }
        }

        return transactionsBetweenDates;
    }

    public List<MedicineSoldDTO> medicineSortedBySoldAmount() {

        List<Medicine> allMedicines = medicineRepository.readAll();
        List<Transaction> allTransactions = transactionRepository.readAll();

        List<MedicineSoldDTO> medicinesSoldAmount = new ArrayList<>();

        //Adding the all medicines, avoiding to repeat them.
        for (Medicine medicine : allMedicines){
            if(!medicinesSoldAmount.contains(medicine.getId())){
                medicinesSoldAmount.add(new MedicineSoldDTO(medicine, 0));
            }
        }

        //Adding the total medicine pieces sold.
        for (Transaction transaction : allTransactions){
            for (MedicineSoldDTO medicineSold : medicinesSoldAmount){
                if (transaction.getMedicineID() == medicineSold.medicine.getId()){
                    medicineSold.setTotalSoldMedicine(medicineSold.totalSoldMedicine + transaction.getMedicinePieces());
                }
            }
        }

        //Sort.
        for (int i = 0; i < medicinesSoldAmount.size(); i++){
            for (int j = 0; j < medicinesSoldAmount.size(); j++){
                if(medicinesSoldAmount.get(i).totalSoldMedicine > medicinesSoldAmount.get(j).totalSoldMedicine){
                    MedicineSoldDTO temp = medicinesSoldAmount.get(i);
                    medicinesSoldAmount.set(i, medicinesSoldAmount.get(j));
                    medicinesSoldAmount.set(j, temp);
                }
            }
        }
        return medicinesSoldAmount;
    }

    public List<ClientCardDiscountDTO> clientCardSortedByDiscount() {

        List<ClientCard> allClientCards = clientCardRepository.readAll();
        List<Transaction> allTransactions = transactionRepository.readAll();

        List<ClientCardDiscountDTO> clientCardDiscount = new ArrayList<>();

        //Adding all clients to dto.
        for (ClientCard client : allClientCards) {
            if (!clientCardDiscount.contains(client.getId())){
                clientCardDiscount.add(new ClientCardDiscountDTO(client, 0));
            }
        }

        //Adding clint discount.
        for (Transaction transaction : allTransactions){
            if(transaction.isHasClientCard()){
                for (ClientCardDiscountDTO clientDiscount : clientCardDiscount){
                    if (transaction.getClientCardID() == clientDiscount.clientCard.getId()){
                        clientDiscount.setTotalDiscountGet(clientDiscount.totalDiscountGet + transaction.getPriceDiscount());
                    }
                }
            }

        }


        //Sort
        for (int i = 0; i < clientCardDiscount.size(); i++){
            for (int j = 0; j < clientCardDiscount.size(); j++){
                if(clientCardDiscount.get(i).totalDiscountGet > clientCardDiscount.get(j).totalDiscountGet){
                    ClientCardDiscountDTO temp = clientCardDiscount.get(i);
                    clientCardDiscount.set(i, clientCardDiscount.get(j));
                    clientCardDiscount.set(j, temp);
                }
            }
        }
        return clientCardDiscount;
    }

    public List<Transaction> deleteTransactionByDate(Date startDate, Date endDate) throws RepositoryException {
        List<Transaction> transactionsBetweenDates = new ArrayList<>();

        for (Transaction transaction : this.getAll()) {
            Date transactionDate = transaction.getTransactionDate();

            if (transactionDate.after(startDate) && transactionDate.before(endDate) ||
                    transactionDate.equals(startDate) ||
                    transactionDate.equals(endDate)) {

                transactionsBetweenDates.add(transaction);
                this.transactionRepository.delete(transaction.getId());
            }
        }

        return transactionsBetweenDates;
    }
}
