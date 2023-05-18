package Domain;

public abstract class Entity{
    //Fields
    private final int id;

    //Constructor
    public Entity(int id){
        this.id = id;
    }

    //Accessors
    public int getId(){
       return id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
