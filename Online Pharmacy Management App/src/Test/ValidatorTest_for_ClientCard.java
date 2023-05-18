package Test;

import Domain.ClientCard;
import Domain.ClientCardValidationException;
import Domain.ClientCardValidator;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Repository.RepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ValidatorTest_for_ClientCard {
    IRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
    ClientCardValidator clientCardValidator = new ClientCardValidator();

    void populateRepositoryWithClient () throws Exception {
        List<ClientCard> allClient = PopulateWithValidObjects.generateClintCardList();

        for (ClientCard client : allClient){
            this.clientCardRepository.create(new ClientCard(client.getId(), client.getSurname(), client.getName(), client.getCnp(), client.getBirthday(), client.getRegistrationDate()));
        }
    }


    @Test
    void addInvalidClient_should_RaiseException() throws Exception {

        //Arange.
        populateRepositoryWithClient();


        //Act and assert.
        try{
            ClientCard clientWithInvalidFormat = new ClientCard(8, "test", "test", "test", "test", "test");
            clientCardRepository.create(clientWithInvalidFormat);
            clientCardValidator.validate(clientWithInvalidFormat, clientCardRepository);
            Assertions.fail("Exception not raised in card validator for invalid CNP format.");
        } catch (ClientCardValidationException clientCardValidationException) {
            System.out.println("Client card validation error: " + clientCardValidationException.getMessage());
        } catch (RepositoryException repositoryException) {
            System.out.println("Repository error: " + repositoryException.getMessage());
        }

        try{
            ClientCard oldClientCard = clientCardRepository.readOne(7);
            ClientCard clientDuplicate = new ClientCard(9, "test", "test", oldClientCard.getCnp(), "test", "test");
            this.clientCardRepository.create(clientDuplicate);
            this.clientCardValidator.validate(clientDuplicate, clientCardRepository);
            Assertions.fail("Exception not raised in card validator for duplicated CNP.");
        } catch (ClientCardValidationException clientCardValidationException) {
            System.out.println("Client card validation error: " + clientCardValidationException.getMessage());
        } catch (RepositoryException repositoryException) {
            System.out.println("Repository error: " + repositoryException.getMessage());
        }
    }
}