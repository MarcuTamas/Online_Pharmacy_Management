package Domain;

public class Medicine extends Entity{

    //Fields
   private String name;
   private String producer;
   private float price;
   private boolean needRecipe;

   //Constructor


    public Medicine(int id, String name, String producer, float price, boolean needRecipe) {
        super(id);
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.needRecipe = needRecipe;
    }

    //Accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public boolean isNeedRecipe() {
        return needRecipe;
    }

    public void setNeedRecipe(boolean needRecipe) {
        this.needRecipe = needRecipe;
    }


    @Override
    public String toString() {
        return "Medicine{" +
                "id='" + this.getId() +
                ", name='" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", needRecipe=" + needRecipe +
                '}';
    }
}
