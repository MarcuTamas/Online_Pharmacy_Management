package Test;

import Domain.ClientCard;
import Repository.IRepository;
import Repository.InMemoryRepository;
import org.junit.jupiter.api.Assertions;

import java.util.List;

class InMemoryRepositoryTest_for_ClientCard {

    @org.junit.jupiter.api.Test
    void validAdd_should_saveToRepository() throws Exception {

        // AAA = Arrange, Act, Assert.

        // Arrange
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();

        ClientCard clientCard1 = new ClientCard(1, "clientCard1", "clientCard1", "1960902123121", "02.09.1996", "21.03.2023");
        ClientCard clientCard2 = new ClientCard(2, "clientCard2", "clientCard2", "1940102123131", "03.10.1991", "22.04.2022");
        ClientCard clientCard3 = new ClientCard(3, "clientCard3", "clientCard3", "1940201132141", "04.02.1995", "23.05.2021");
        ClientCard clientCard4 = new ClientCard(4, "clientCard4", "clientCard4", "1940201132151", "05.03.1996", "01.03.2007");
        ClientCard clientCard5 = new ClientCard(5, "clientCard5", "clientCard5", "1940201132161", "07.02.1997", "02.04.1996");
        ClientCard clientCard6 = new ClientCard(6, "clientCard2", "clientCard2", "1940201132171", "02.09.2000", "03.03.2003");
        ClientCard clientCard7 = new ClientCard(7, "clientCard7", "clientCard7", "1940201132181", "02.09.1996", "21.03.2023");

        // Act
        clientCardRepository.create(clientCard1);
        clientCardRepository.create(clientCard2);
        clientCardRepository.create(clientCard3);
        clientCardRepository.create(clientCard4);
        clientCardRepository.create(clientCard5);
        clientCardRepository.create(clientCard6);
        clientCardRepository.create(clientCard7);

        List<ClientCard> allClientCards = clientCardRepository.readAll();

        // Assert
        Assertions.assertEquals(7, allClientCards.size());
        Assertions.assertNotNull(clientCardRepository.readOne(1));
    }

    @org.junit.jupiter.api.Test
    void addWithDuplicateID_should_raiseExceptionInRepository() throws Exception {
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();

        ClientCard clientCard1 = new ClientCard(1, "clientCard1", "clientCard1", "1960902123121", "02.09.1996", "21.03.2023");
        ClientCard clientCard2 = new ClientCard(2, "clientCard2", "clientCard2", "1940102123131", "03.10.1991", "22.04.2022");
        ClientCard clientCard3 = new ClientCard(3, "clientCard3", "clientCard3", "1940201132141", "04.02.1995", "23.05.2021");
        ClientCard clientCard4 = new ClientCard(4, "clientCard4", "clientCard4", "1940201132151", "05.03.1996", "01.03.2007");
        ClientCard clientCard5 = new ClientCard(5, "clientCard5", "clientCard5", "1940201132161", "07.02.1997", "02.04.1996");
        ClientCard clientCard6 = new ClientCard(6, "clientCard2", "clientCard2", "1940201132171", "02.09.2000", "03.03.2003");
        ClientCard clientCard7 = new ClientCard(7, "clientCard7", "clientCard7", "1940201132181", "02.09.1996", "21.03.2023");
        ClientCard clientCard8 = new ClientCard(7, "clientCard7", "clientCard7", "1940201132181", "02.09.1996", "21.03.2023");

        clientCardRepository.create(clientCard1);
        clientCardRepository.create(clientCard2);
        clientCardRepository.create(clientCard3);
        clientCardRepository.create(clientCard4);
        clientCardRepository.create(clientCard5);
        clientCardRepository.create(clientCard6);
        clientCardRepository.create(clientCard7);



        try{
            clientCardRepository.create(clientCard8);
            Assertions.fail("Exception not raised in repository for duplicates id.");
        }catch (Exception ex){

        }

//        Lambda expression.
//        Assertions.assertThrows(Exception.class, () -> clientCardRepository.create(clientCard8));


    }


}