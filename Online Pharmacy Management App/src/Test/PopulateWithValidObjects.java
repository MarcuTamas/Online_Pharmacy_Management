package Test;

import Domain.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.ClientCardService;
import Service.MedicineService;
import Service.TransactionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PopulateWithValidObjects {

    //Populate with ClientCard.
    public static List<ClientCard> generateClintCardList () throws Exception {
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        clientCardService.add(1, "clientCard1", "clientCard1", "1960902123121", "02.09.1996", "21.03.2023");
        clientCardService.add(2, "clientCard2", "clientCard2", "1940102123131", "03.10.1991", "22.04.2022");
        clientCardService.add(3, "clientCard3", "clientCard3", "1940201132141", "04.02.1995", "23.05.2021");
        clientCardService.add(4, "clientCard4", "clientCard4", "1940201132151", "05.03.1996", "01.03.2007");
        clientCardService.add(5, "clientCard5", "clientCard5", "1940201132161", "07.02.1997", "02.04.1996");
        clientCardService.add(6, "clientCard2", "clientCard2", "1940201132171", "02.09.2000", "03.03.2003");
        clientCardService.add(7, "clientCard7", "clientCard7", "1940201132181", "02.09.1996", "21.03.2023");

        return clientCardService.getAll();
    }

    //Populate with Medicines.
    public static List<Medicine> generateMedicineList () throws Exception {
        IRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);

        medicineService.add(1, "Lipitor", "Pfizer", 0.59327f, false);
        medicineService.add(2, "Humira", "AbbVie", 3.14159f, true);
        medicineService.add(3, "Crestor", "AstraZeneca", 2.71828f, false);
        medicineService.add(4, "Advair Diskus ", "GlaxoSmithKline", 4.56789f, false);
        medicineService.add(5, "Plavix", "Sanofi", 1.23456f, false);
        medicineService.add(6, "Nexium", "AstraZeneca", 7.13f, true);
        medicineService.add(7, "Abilify", "Otsuka", 3.14159f, false);

        return medicineService.getAll();
    }

    public static List<Transaction> generateTransactionList() throws Exception {
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


        //Populate with objects in memory

        for (Medicine medicine: PopulateWithValidObjects.generateMedicineList()) {
            medicineService.add(medicine.getId(), medicine.getName(), medicine.getProducer(), medicine.getPrice(), medicine.isNeedRecipe());
        }

        for (ClientCard client: PopulateWithValidObjects.generateClintCardList()) {
            clientCardService.add(client.getId(), client.getSurname(), client.getName(), client.getCnp(), client.getBirthday(), client.getRegistrationDate());
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String date1 = "02.09.2022 10:07";
        Date transactionDate1 = dateFormat.parse(date1);

        String date2 = "22.09.2022 11:01";
        Date transactionDate2 = dateFormat.parse(date2);

        String date3 = "03.10.2022 12:03";
        Date transactionDate3 = dateFormat.parse(date3);

        String date4 = "10.05.2023 14:20";
        Date transactionDate4 = dateFormat.parse(date4);

        String date5 = "03.09.2023 19:30";
        Date transactionDate5 = dateFormat.parse(date5);

        String date6 = "18.11.2022 11:55";
        Date transactionDate6 = dateFormat.parse(date6);

        String date7 = "21.07.2023 11:10";
        Date transactionDate7 = dateFormat.parse(date7);

        String date8 = "22.02.2023 01:03";
        Date transactionDate8 = dateFormat.parse(date8);

        String date9 = "07.07.2022 24:01";
        Date transactionDate9 = dateFormat.parse(date9);

        String date10 = "28.02.1996 07:10";
        Date transactionDate10 = dateFormat.parse(date10);

        String date11 = "21.07.2023 07:01";
        Date transactionDate11 = dateFormat.parse(date11);


        transactionService.add(1,1, true, 10,10, transactionDate1);
        transactionService.add(2,2, false, 10,10, true, 1, transactionDate2);
        transactionService.add(3,3, true,10,10, true, 2,  transactionDate3);
        transactionService.add(4,4, true, 3,23.99f, transactionDate4);
        transactionService.add(5,5, false, 7,7f, transactionDate5);
        transactionService.add(6,6, true, 8,49.87f, transactionDate6);
        transactionService.add(7,7, false, 1,17f, true, 3, transactionDate7);
        transactionService.add(8,7, false, 7,32.40f, true, 4, transactionDate8);
        transactionService.add(9,1, true, 8,7f, true, 5, transactionDate9);
        transactionService.add(10,1, false, 3,12f, true, 6, transactionDate10);
        transactionService.add(11,1, false, 3,9f, false, 7, transactionDate11);
        transactionService.add(12,1, true, 7,7.2f, true, 7, transactionDate11);

        return transactionService.getAll();
    }

}
