package Repository;

import Domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends Entity> implements IRepository<T> {
    //Fields
    private Map<Integer, T> storage;

    //Constructor
    public InMemoryRepository(){
        this.storage = new HashMap<>();
    }

    //Implement (override) the repository interface methods.
    @Override
    public void create(T entity) throws RepositoryException {
        if (this.storage.containsKey(entity.getId())) {
            throw new RepositoryException("There is already an entity with the id " + entity.getId() + ".");
        }
        this.storage.put(entity.getId(), entity);
    }

    @Override
    public List<T> readAll() {
        return new ArrayList<>(this.storage.values());
    }

    @Override
    public T readOne(int id) throws RepositoryException {
        if (!this.storage.containsKey(id)) {
            throw new RepositoryException("There is no entity with the id: " + id + ".");
        }
        return this.storage.get(id);
    }

    @Override
    public void update(T entity) throws RepositoryException {
        if (!this.storage.containsKey(entity.getId())) {
            throw new RepositoryException("There is no entity with the id: " + entity.getId() + " to update.");
        }
        this.storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(int id) throws RepositoryException {
        if (!this.storage.containsKey(id)) {
            throw new RepositoryException("There is no entity with the id " + id + " to be deleted.");
        }
        this.storage.remove(id);
    }
}
