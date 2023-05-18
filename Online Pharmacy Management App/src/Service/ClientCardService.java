package Service;

import Domain.*;
import Repository.IRepository;
import Repository.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class ClientCardService {
    private IRepository<ClientCard> clientCardRepository;
    private ClientCardValidator clientCardValidator;

    public ClientCardService(IRepository<ClientCard> clientCardRepository, ClientCardValidator clientCardValidator) {
        this.clientCardRepository = clientCardRepository;
        this.clientCardValidator = clientCardValidator;
    }

    //Crud
    public void add(int id, String surname, String name, String cnp, String birthday, String registrationDate) throws ClientCardValidationException, RepositoryException {
        ClientCard clientCard = new ClientCard(id, surname, name, cnp, birthday, registrationDate);
        this.clientCardValidator.validate(clientCard, this.clientCardRepository);
        this.clientCardRepository.create(clientCard);
    }

    public ClientCard getOne(int id) throws RepositoryException {
        return this.clientCardRepository.readOne(id);
    }

    public void updateClientCard(int id, String surname, String name, String cnp, String birthday, String registrationDate) throws ClientCardValidationException, RepositoryException {
        ClientCard clientCard = new ClientCard(id, surname, name, cnp, birthday, registrationDate);
        this.clientCardValidator.validate(clientCard, this.clientCardRepository);
        this.clientCardRepository.update(clientCard);
    }

    public void deleteClientCard(int id) throws RepositoryException {
        this.clientCardRepository.delete(id);
    }

    public List<ClientCard> getAll(){
        return this.clientCardRepository.readAll();

    }

    public List<ClientCard> searchClient(String clientDetail) {
        List<ClientCard> allClients = this.clientCardRepository.readAll();
        List<ClientCard> foundedClients = new ArrayList<>();

        for (ClientCard client : allClients) {

            if (Integer.toString(client.getId()).equalsIgnoreCase(clientDetail) ||
                    client.getSurname().equalsIgnoreCase(clientDetail) ||
                    client.getName().equalsIgnoreCase(clientDetail) ||
                    client.getCnp().equalsIgnoreCase(clientDetail) ||
                    client.getBirthday().contains(clientDetail) ||
                    client.getRegistrationDate().contains(clientDetail)) {

                foundedClients.add(client);
            }
        }

        return foundedClients;
    }

}


