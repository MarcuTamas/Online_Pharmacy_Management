package Test;

import Domain.ClientCard;
import Domain.ClientCardValidator;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.ClientCardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ServiceTest_for_ClientCard {

    @Test
    void validAdd_should_PassThroughAllServiceMethods() throws Exception {

        //Arange
        IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);



        //Act and assert

        //Create and read all.
        for (ClientCard client: PopulateWithValidObjects.generateClintCardList()) {
            clientCardService.add(client.getId(), client.getSurname(), client.getName(), client.getCnp(), client.getBirthday(), client.getRegistrationDate());
        }
        Assertions.assertEquals(7, clientCardService.getAll().size());

        //Read one.
        for (ClientCard client : clientCardService.getAll()) {
            Assertions.assertNotNull(clientCardService.getOne(client.getId()));
        }

        //Search.
        List<ClientCard> searchedClient = clientCardService.searchClient("1996");
        Assertions.assertEquals(4, searchedClient.size());

        //Update
        for (int i = 1; i < 4; i++){
            String surName = "Updated surname " + i;
            String name = "Updated name " + i;
            String cnp = "1960902" + i + "23123";
            String birthday = "Updated birthday " + i;
            String registrationDate = "Updated registration date " + i;

            clientCardService.updateClientCard(i, surName, name, cnp, birthday, registrationDate);

            Assertions.assertNotNull(clientCardService.getOne(i));
        }

        //Delete.
        for (int i = 7; i > 3; i--){
            clientCardService.deleteClientCard(i);
        }
        Assertions.assertEquals(3, clientCardService.getAll().size());
    }
}