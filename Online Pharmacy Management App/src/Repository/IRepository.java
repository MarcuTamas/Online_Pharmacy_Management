package Repository;

import Domain.Entity;

import java.util.List;

//Interface for repository.
public interface IRepository<T extends Entity> {

    //The methods which repository must have.  | MenuCRUD |

    //Create
    void create(T entity)throws RepositoryException;

    //Read
    List<T> readAll();
    T readOne(int id) throws RepositoryException;

    //Update
    void update(T entity)throws RepositoryException;

    //Delete
    void delete(int id) throws RepositoryException;
}
