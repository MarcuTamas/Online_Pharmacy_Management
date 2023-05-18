package UserInterface;

import Domain.*;
import Repository.RepositoryException;
import Service.ClientCardService;
import Service.MedicineService;
import Service.TransactionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Console {
    private MedicineService medicineService;
    private ClientCardService clientCardService;
    private TransactionService transactionService;
    private Scanner scanner;

    //Coloring the error messege.
    private static final String ANSI_PURPLE = "\033[1;35m";
    private static final String ANSI_GREEN = "\033[4;32m" ;
    private static final String ANSI_RESET = "\u001B[0m";

    public Console(MedicineService medicineService, ClientCardService clientCardService, TransactionService transactionService) {
        this.medicineService = medicineService;
        this.clientCardService = clientCardService;
        this.transactionService = transactionService;

        this.scanner = new Scanner(System.in);
    }

    //Main menu.
    public void showMainMenu(){
        System.out.println("1. MenuCRUD medicines.");
        System.out.println("2. MenuCRUD client cards.");
        System.out.println("3. MenuCRUD transactions.");
        System.out.println("4. Find medicine or client.");
        System.out.println("5. Show all transactions in a given range of time.");
        System.out.println("6. Show all medicine in descending order by amount sold.");
        System.out.println("7. Show all client cards in descending order by the obtained discount.");
        System.out.println("8. Delete all transaction in a given range of time.");
        System.out.println("9. Price increase with a given percent of all medicines " +
                "which have the price smaller than a given value.");
        System.out.println("0. Exit.");
    }

    private void showAll(ArrayList objects){
        for (Object obj : objects){
            System.out.println(obj);
        }
    }

    public void runConsole() throws Exception {
        while (true) {
            this.showMainMenu();
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        this.runSubmenuCRUDMedicines();
                        break;
                    case 2:
                        this.runSubmenuCRUDClientCards();
                        break;
                    case 3:
                        this.runSubmenuCRUDTransactions();
                        break;
                    case 4:
                        this.runSubMenuForFullTexSearch();
                        break;
                    case 5:
                        this.runSubmenuForTransactionSearch();
                        break;
                    case 6:
                        this.runSubmenuForMedicinesSoldAmount();
                        break;
                    case 7:
                        this.runSubmenuForClientDiscount();
                        break;
                    case 8:
                        this.runSubmenuForTransactionDeleteByDates();
                        break;
                    case 9:
                        this.runSubmenuMedicinePriceIncrease();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            }catch (InputMismatchException wrongInput) {
                System.out.println(ANSI_PURPLE + "InputMismatchException. Wrong data type entered." + ANSI_RESET);
                scanner.next();
            }
        }
    }


    //Submenu

    //Medicine runSubmenu and handle.
    private void runSubmenuCRUDMedicines() throws Exception {
        MenuCRUD medicine = new MenuCRUD("medicine");

        while (true) {
            medicine.showCRUD();
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        this.handleAddMedicine();
                        break;
                    case 2:
                        this.handleUpdateMedicine();
                        break;
                    case 3:
                        this.handleDeleteMedicine();
                        break;
                    case 4:
                        this.handleReadOneMedicine();
                        break;
                    case 5:
                        this.showAll((ArrayList) this.medicineService.getAll());
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            }catch (InputMismatchException wrongInput) {
                System.out.println(ANSI_PURPLE + "InputMismatchException error: wrong data type entered." + ANSI_RESET);
                scanner.next();
            }
        }
    }

    //Create
    private void handleAddMedicine(){
        try{
            System.out.println("Medicine id: ");
            int medicineID = this.scanner.nextInt();

            System.out.println("Name: ");
            String name = this.scanner.next();

            System.out.println("Producer: ");
            String producer = this.scanner.next();

            System.out.println("Price: ");
            float price = this.scanner.nextFloat();

            System.out.println("It dose need recipe (Y/N): ");
            String response = this.scanner.next().trim().toLowerCase();

            boolean needRecipe = response.equals("y") ? true : false;

            this.medicineService.add(medicineID, name, producer, price, needRecipe);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        } catch (MedicineValidationException medicineValidationException) {
            System.out.println(ANSI_PURPLE + "Medicine validation error: " + medicineValidationException.getMessage() + ANSI_RESET);
        }
    }

    //Update
    private void handleUpdateMedicine(){
        try{
            System.out.println("Medicine id: ");
            int medicineID = this.scanner.nextInt();

            System.out.println("Name: ");
            String name = this.scanner.next();

            System.out.println("Producer: ");
            String producer = this.scanner.next();

            System.out.println("Price: ");
            float price = this.scanner.nextFloat();

            System.out.println("It dose need recipe (Y/N): ");
            String response = this.scanner.next().trim().toLowerCase();

            boolean needRecipe = response.equals("y");

            this.medicineService.updateMedicine(medicineID, name, producer, price, needRecipe);
        }catch (MedicineValidationException medicineValidationException){
            System.out.println("Medicine validation error: " + medicineValidationException.getMessage());
        }catch (RepositoryException repositoryException){
            System.out.println("Repository error: " + repositoryException.getMessage());
        }
    }

    //Delete
    private void handleDeleteMedicine(){
        try {
            System.out.println("Enter the id: ");
            int id = scanner.nextInt();
            this.medicineService.deletMedicine(id);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }
    }

    //Read one.
    private void handleReadOneMedicine() {
        try {
            System.out.println("Enter the id: ");
            int id = scanner.nextInt();
            System.out.println(this.medicineService.getOne(id));
        }
        catch (RepositoryException repositoryException){
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }
    }

    //Read all, look up for showAll method.


    //Client cards run and h.
    private void runSubmenuCRUDClientCards() {
        MenuCRUD clientCard = new MenuCRUD("client card");

        while (true) {
            clientCard.showCRUD();
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        this.handleAddClientCard();
                        break;
                    case 2:
                        this.handleUpdateClientCard();
                        break;
                    case 3:
                        this.handleDeleteClientCard();
                        break;
                    case 4:
                        this.handleReadOneClientCard();
                        break;
                    case 5:
                        this.showAll((ArrayList) this.clientCardService.getAll());
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            } catch (InputMismatchException wrongInput) {
                System.out.println(ANSI_PURPLE + "InputMismatchException error: wrong data type entered." + ANSI_RESET);
                scanner.next();
            }
        }
    }

    private void handleAddClientCard(){
        try{
            System.out.println("Client card id: ");
            int cardID = this.scanner.nextInt();

            System.out.println("Client surname: ");
            String surname = this.scanner.next();

            System.out.println("Client name: ");
            String name = this.scanner.next();

            System.out.println("Client CNP: ");
            String cnp = this.scanner.next();

            System.out.println("Client birthday: ");
            String birthday = this.scanner.next();

            System.out.println("Registration date: ");
            String registrationDate = this.scanner.next();

            this.clientCardService.add(cardID, surname, name, cnp, birthday, registrationDate);
        } catch (ClientCardValidationException clientCardValidationException) {
            System.out.println(ANSI_PURPLE + "Client card validation error: " + clientCardValidationException.getMessage() + ANSI_RESET);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }
    }

    private void handleUpdateClientCard(){
        try{
            System.out.println("Client card id: ");
            int cardID = this.scanner.nextInt();

            System.out.println("Client surname: ");
            String surname = this.scanner.next();

            System.out.println("Client name: ");
            String name = this.scanner.next();

            System.out.println("Client CNP: ");
            String cnp = this.scanner.next();

            System.out.println("Client birthday: ");
            String birthday = this.scanner.next();

            System.out.println("Registration date: ");
            String registrationDate = this.scanner.next();

            this.clientCardService.updateClientCard(cardID, surname, name, cnp, birthday, registrationDate);
        } catch (ClientCardValidationException clientCardValidationException) {
            System.out.println(ANSI_PURPLE + "Client card validation error: " + clientCardValidationException.getMessage() + ANSI_RESET);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }
    }

    private void handleDeleteClientCard(){
        try {
            System.out.println("Enter the id: ");
            int id = scanner.nextInt();
            this.clientCardService.deleteClientCard(id);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }

    }

    private void handleReadOneClientCard() {
        try {
            System.out.println("Enter the id: ");
            int id = scanner.nextInt();
            System.out.println(this.clientCardService.getOne(id));
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }

    }


    //Transactions run and h.
    private void runSubmenuCRUDTransactions() {
        MenuCRUD transactions = new MenuCRUD("transaction");

        while (true){
            transactions.showCRUD();

            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        this.handleAddTransaction();
                        break;
                    case 2:
                        this.handleUpdateTransaction();
                        break;
                    case 3:
                        this.handleDeleteTransaction();
                        break;
                    case 4:
                        handleReadOneTransaction();
                        break;
                    case 5:
                        this.showAll((ArrayList) this.transactionService.getAll());
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            }catch (InputMismatchException wrongInput) {
                System.out.println(ANSI_PURPLE + "InputMismatchException error: wrong data type entered." + ANSI_RESET);
                scanner.next();
            }
        }
    }

    private void handleAddTransaction(){
        try{
            System.out.println("Transaction id: ");
            int transactionID = this.scanner.nextInt();

            System.out.println("Date (dd.MM.yyyy HH:mm): ");

            //We need a new scanner, with nextLine, to make it work and read all the input.
            //Tried to reuseble scaner and next method, but I does not work.
            String introducedDate = new Scanner(System.in).nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date transactionDate = dateFormat.parse(introducedDate);

            System.out.println("Medicine id: ");
            int medicineID = this.scanner.nextInt();

            System.out.println("Dose the medicine need a recipe ?");
            boolean medicineNeedRecipe = scanner.next().equalsIgnoreCase("y");

            System.out.println("Medicine pieces: ");
            int medicinePieces = this.scanner.nextInt();

            System.out.println("Medicine price: ");
            float medicinePrice = scanner.nextFloat();

            System.out.println("Dose the client have a Client Card ?");
            boolean hasClientCard = scanner.next().equalsIgnoreCase("y");

            /**
             * Depending on having or not a client card, we will call the correct add method for the transaction.
             */
            if(hasClientCard){
                System.out.println("Client card id: ");
                int clientCardID = this.scanner.nextInt();
                this.transactionService.add(transactionID, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, hasClientCard, clientCardID, transactionDate);
            }else{
                this.transactionService.add(transactionID, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, transactionDate);
            }
        } catch (TransactionValidationException transactionValidationException) {
            System.out.println(ANSI_PURPLE + "Transaction validation error: " + transactionValidationException.getMessage() + ANSI_RESET);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        } catch (ParseException invalidDateFormat) {
            System.out.println(ANSI_PURPLE + "ParseException error: invalid date format." + ANSI_RESET);
        }
    }

    private void handleUpdateTransaction() {
        try {
            System.out.println("Transaction id: ");
            int transactionID = this.scanner.nextInt();

            System.out.println("Date (dd.MM.yyyy HH:mm): ");

            //We need a new scanner, with nextLine, to make it work and read all the input.
            //Tried to reuseble scaner and next method, but I dose not work.
            String introducedDate = new Scanner(System.in).nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date transactionDate = dateFormat.parse(introducedDate);

            System.out.println("Medicine id: ");
            int medicineID = this.scanner.nextInt();

            System.out.println("Dose the medicine need a recipe ?");
            boolean medicineNeedRecipe = scanner.next().equalsIgnoreCase("y");

            System.out.println("Medicine pieces: ");
            int medicinePieces = this.scanner.nextInt();

            System.out.println("Medicine price: ");
            float medicinePrice = scanner.nextFloat();

            System.out.println("Dose the client have a Client Card ?");
            boolean hasClientCard = scanner.next().equalsIgnoreCase("y");

            if (hasClientCard) {
                System.out.println("Client card id: ");
                int clientCardID = this.scanner.nextInt();
                this.transactionService.updateTransaction(transactionID, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, hasClientCard, clientCardID, transactionDate);
            } else {
                this.transactionService.updateTransaction(transactionID, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, transactionDate);
            }
        } catch (TransactionValidationException transactionValidationException) {
            System.out.println(ANSI_PURPLE + "Transaction validation error: " + transactionValidationException.getMessage() + ANSI_RESET);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        } catch (ParseException invalidDateFormat) {
            System.out.println(ANSI_PURPLE + "ParseException error: invalid date format." + ANSI_RESET);
        }
    }

    private void handleDeleteTransaction(){
        try {
            System.out.println("Enter the id: ");
            int id = scanner.nextInt();
            this.transactionService.deleteTransaction(id);
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }

    }

    private void handleReadOneTransaction() {
        try {
            System.out.println("Enter the id: ");
            int id = scanner.nextInt();
            System.out.println(this.transactionService.getOne(id));
        } catch (RepositoryException repositoryException) {
            System.out.println(ANSI_PURPLE + "Repository error: " + repositoryException.getMessage() + ANSI_RESET);
        }

    }

    //Other functionalities

    //1. Find medicine or client.
    private void runSubMenuForFullTexSearch(){
        while (true){
            System.out.println();
            System.out.println("What do you want to search for ?" + "\n" +
                    "Choose M for Medicine and C for Client or B for both and 0 to go back to main menu.");
            try {
                String option = scanner.next().toLowerCase();
                switch (option) {
                    case "c":
                        this.handleClientSearch();
                        break;
                    case "m":
                        this.handleMedicineSearch();
                        break;
                    case "b":
                        this.handleSerachForMedicineAndClient();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            }catch (InputMismatchException wrongInput) {
                System.out.println(ANSI_PURPLE + "InputMismatchException error: wrong data type entered." + ANSI_RESET);
                scanner.next();
            }
        }
    }

    private void handleSerachForMedicineAndClient (){
        System.out.println("Enter a detail: ");
        String detail = this.scanner.next();

        //Calling the needed methods for searching through elements.
        List<ClientCard> foundedClients = this.clientCardService.searchClient(detail);
        List<Medicine> foundedMedicines = this.medicineService.searchMedicine(detail);

        if (!foundedClients.isEmpty()){
            System.out.println(ANSI_GREEN + "There are clients with given detail " + "\"" + detail + "\"" + ".");
            System.out.println("The all saved details about them are: " + ANSI_RESET);

            for (ClientCard client : foundedClients) {
                System.out.println(client);
            }
            System.out.println();
        }

        if (!foundedMedicines.isEmpty()){
            System.out.println(ANSI_GREEN + "There are medicines with given detail " + "\"" + detail + "\"" + ".");
            System.out.println("The all saved details about them are: " + ANSI_RESET);

            for (Medicine medicine : foundedMedicines) {
                System.out.println(medicine);
            }
        }

        if (foundedClients.isEmpty() && foundedMedicines.isEmpty()){
            System.out.println(ANSI_PURPLE + "There is not any object with given detail." + ANSI_RESET);
        }

    }

    private void handleClientSearch(){
        System.out.println("Enter a detail about the client which you want to be found: ");
        String clientDetail = this.scanner.next();
        List<ClientCard> foundedClients = this.clientCardService.searchClient(clientDetail);

        if (!foundedClients.isEmpty()){
            System.out.println(ANSI_GREEN + "There are clients with given detail " + "\"" + clientDetail + "\"" + ".");
            System.out.println("The all saved details about them are: " + ANSI_RESET);

            for (ClientCard client : foundedClients) {
                System.out.println(client);
            }
        }else {
            System.out.println(ANSI_PURPLE + "There is not any client with given detail." + ANSI_RESET);
        }
    }


    private void handleMedicineSearch(){

        System.out.println("Enter a detail about the medicine which you want to be found: ");
        String medicineDetail = this.scanner.next();
        List<Medicine> foundedMedicines = this.medicineService.searchMedicine(medicineDetail);

        if (!foundedMedicines.isEmpty()){
            System.out.println(ANSI_GREEN + "There are medicines with given detail " + "\"" + medicineDetail + "\"" + ".");
            System.out.println("The all saved details about them are: " + ANSI_RESET);

            for (Medicine medicine : foundedMedicines) {
                System.out.println(medicine);
            }
        }else {
            System.out.println(ANSI_PURPLE + "There is not any medicine with given detail." + ANSI_RESET);
        }
    }

    //2. Show all transactions in a given range of time.
    private void runSubmenuForTransactionSearch() throws ParseException {
        while (true){
            System.out.println();
            System.out.println("Enter 1 for searching all transaction between two given dates and 0 for return.");

            int option = scanner.nextInt();

            try {
                switch (option) {
                    case 1:
                        this.handleTransactionShow();
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            }catch (ParseException invalidDateFormat){
                System.out.println(ANSI_PURPLE + "Invalid date format." + ANSI_RESET);
            }
        }
    }

    public void handleTransactionShow() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        System.out.println("Enter start date (dd.MM.yyyy HH:mm): ");
        String introducedStartDate = new Scanner(System.in).nextLine();
        Date startDate = dateFormat.parse(introducedStartDate);

        System.out.println("Enter end date (dd.MM.yyyy HH:mm): ");
        String introducedEndDate = new Scanner(System.in).nextLine();
        Date endDate = dateFormat.parse(introducedEndDate);

        List<Transaction> foundedTransactions = this.transactionService.getTransactionByDate(startDate, endDate);

        if (!foundedTransactions.isEmpty()) {
            System.out.println(ANSI_GREEN + "There are transactions between given dates." + ANSI_RESET);

            for (Transaction transaction : foundedTransactions) {
                System.out.println(transaction);
            }
        } else {
            System.out.println(ANSI_PURPLE + "There is not any transaction between the given time." + ANSI_RESET);
        }
    }

    //3. Show all medicine in descending order by amount sold.
    private void runSubmenuForMedicinesSoldAmount() {
        while (true){
            System.out.println("Enter 1 for showing all medicines sorted by sold amount and 0 for return.");

            int option = scanner.nextInt();

            try {
                switch (option) {
                    case 1:
                        this.handleMedicineSoldAmount();
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            }catch (Exception exception){
                System.out.println(ANSI_PURPLE + exception.getMessage() + ANSI_RESET);
            }
        }
    }

    public void handleMedicineSoldAmount() {
        List<MedicineSoldDTO> allMedicines = this.transactionService.medicineSortedBySoldAmount();

        for (MedicineSoldDTO medicine : allMedicines){
            System.out.println(medicine);
        }
    }

    //4. Show all client cards in descending order by the obtained discount.
    private void runSubmenuForClientDiscount() {
        while (true){
            System.out.println("Enter 1 for showing all clients sorted by sold amount and 0 for return.");

            int option = scanner.nextInt();

            try {
                switch (option) {
                    case 1:
                        this.handleClientDiscountAmount();
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            }catch (Exception exception){
                System.out.println(ANSI_PURPLE + exception.getMessage() + ANSI_RESET);
            }
        }
    }

    public void handleClientDiscountAmount() {
        List<ClientCardDiscountDTO> allClients = this.transactionService.clientCardSortedByDiscount();

        for (ClientCardDiscountDTO client : allClients){
            if (client.totalDiscountGet > 0){
                System.out.println(client);
            }
        }

    }

    //5. Delete all transactions in a given range of time.
    private void runSubmenuForTransactionDeleteByDates() throws ParseException {
        while (true){
            System.out.println();
            System.out.println("Enter 1 for deleting all transaction between two given dates and 0 for return.");

            int option = scanner.nextInt();

            try {
                switch (option) {
                    case 1:
                        this.handleTransactionDeleteByDates();
                    case 0:
                        return;
                    default:
                        System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                }
            } catch (Exception e) {
                System.out.println(ANSI_PURPLE + e.getMessage() + ANSI_RESET);
            }
        }
    }

    public void handleTransactionDeleteByDates() throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try{
            System.out.println("Enter start date (dd.MM.yyyy HH:mm): ");
            String introducedStartDate = new Scanner(System.in).nextLine();
            Date startDate = dateFormat.parse(introducedStartDate);

            System.out.println("Enter end date (dd.MM.yyyy HH:mm): ");
            String introducedEndDate = new Scanner(System.in).nextLine();
            Date endDate = dateFormat.parse(introducedEndDate);


            List<Transaction> foundedTransactions = this.transactionService.deleteTransactionByDate(startDate, endDate);

            if (!foundedTransactions.isEmpty()) {
                System.out.println(ANSI_GREEN + "The following transaction will be deleted: " + ANSI_RESET);

                for (Transaction transaction : foundedTransactions) {
                    System.out.println(transaction);
                }
            } else {
                System.out.println(ANSI_PURPLE + "There is not any transaction between the given time." + ANSI_RESET);
            }
        }catch (ParseException invalidDateFormat){
            System.out.println(ANSI_PURPLE + "ParseException error: invalid date format." + ANSI_RESET);
        }
    }

        // Medicine price increase with a given percent.
        private void runSubmenuMedicinePriceIncrease() {
            while (true){
                System.out.println("Enter 1 for increasing the medicine price with a given percent and 0 for return.");

                int option = scanner.nextInt();

                try {
                    switch (option) {
                        case 1:
                            this.handleMedicinePriceIncrease();
                        case 0:
                            return;
                        default:
                            System.out.println(ANSI_PURPLE + "Invalid option." + ANSI_RESET);
                    }
                } catch (Exception e) {
                    System.out.println(ANSI_PURPLE + e.getMessage() + ANSI_RESET);
                }
            }
        }

        public void handleMedicinePriceIncrease() {
            System.out.println("Enter the medicine price: ");
            float medicinePrice = this.scanner.nextFloat();

            System.out.println("Enter the increasing percentage: ");
            float increasePercent = this.scanner.nextFloat();

            this.medicineService.medicinePriceIncrease(medicinePrice, increasePercent);
        }

    }
