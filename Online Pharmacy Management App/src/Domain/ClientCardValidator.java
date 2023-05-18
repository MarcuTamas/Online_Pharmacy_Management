package Domain;

import Repository.IRepository;

public class ClientCardValidator {
    /**
     * This method checks if the CNP has a valid format and if it is checks if it unique in the system.
     * @param clientCardToBeChecked is the client card already saved in the repo.
     * @param clientCardRepository  is the repo which we need to pass through in order to check the clients CNP.
     * @throws ClientCardValidationException if the CNP format is not valid or event it is, throws an exception if it already exists in the database.
     */
    public void validate(ClientCard clientCardToBeChecked, IRepository<ClientCard> clientCardRepository) throws ClientCardValidationException {
        if (!clientCardToBeChecked.getCnp().matches("^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$")){
            throw new ClientCardValidationException("The CNP dose not have a valid format.");
        } else {
            for(ClientCard clientCard : clientCardRepository.readAll()){
                if (clientCardToBeChecked.getCnp().equals(clientCard.getCnp())){
                    throw new ClientCardValidationException("The CNP already exist and it must to be unique.");
                }
            }
        }
    }
}
