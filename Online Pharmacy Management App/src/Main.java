import Domain.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.ClientCardService;
import Service.MedicineService;
import Service.TransactionService;
import UserInterface.Console;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws Exception {
        //Repository
        IRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();

        //Validator
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionValidator transactionValidator = new TransactionValidator();

        //Service
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository, clientCardRepository, transactionValidator);

        //Populate with objects in memory
        medicineService.add(1, "Paracetamol", "Farmec", 1.37f, false);
        medicineService.add(2, "Advantan", "ThePharma", 3.71f, true);
        medicineService.add(3, "Sudocream", "Tosara", 7.13f, false);

        clientCardService.add(1, "clientCard1", "clientCard1", "1960902123121", "02.09.1996", "21.03.2023");
        clientCardService.add(2, "clientCard2", "clientCard2", "1940102123131", "03.10.1991", "22.04.2022");
        clientCardService.add(3, "clientCard3", "clientCard3", "1940201132141", "04.02.1995", "23.05.2021");
        clientCardService.add(4, "clientCard4", "clientCard4", "1940201132151", "05.03.1996", "01.03.2007");
        clientCardService.add(5, "clientCard5", "clientCard5", "1940201132161", "07.02.1997", "02.04.1996");
        clientCardService.add(6, "clientCard2", "clientCard2", "1940201132171", "02.09.2000", "03.03.2003");
        clientCardService.add(7, "clientCard7", "clientCard7", "1940201132181", "02.09.1996", "21.03.2023");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String date1 = "02.09.2022 10:07";
        Date transactionDate1 = dateFormat.parse(date1);
        String date2 = "22.09.2022 11:01";
        Date transactionDate2 = dateFormat.parse(date2);
        String date3 = "03.10.2022 12:03";
        Date transactionDate3 = dateFormat.parse(date3);

        transactionService.add(1,1, true, 10,10, transactionDate1);
        transactionService.add(2,2, false, 10,10, true, 1, transactionDate2);
        transactionService.add(3,3, true,10,10, true, 2,  transactionDate3);
        transactionService.add(4,1, true, 3,23.99f, transactionDate1);
        transactionService.add(5,2, false, 7,7f, transactionDate1);
        transactionService.add(6,3, true, 8,49.87f, transactionDate1);
        transactionService.add(7,1, false, 1,17f, true, 2, transactionDate3);
        transactionService.add(8,2, false, 7,32.40f, true, 2, transactionDate3);
        transactionService.add(9,1, true, 3,7f, true, 3, transactionDate3);

        Console console = new Console(medicineService, clientCardService, transactionService);
        console.runConsole();

    }

}