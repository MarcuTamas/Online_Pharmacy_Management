package Domain;

public class ClientCardDiscountDTO {

    public ClientCard clientCard;
    public float totalDiscountGet;

    public ClientCardDiscountDTO(ClientCard clientCard, float totalDiscountGet) {
        this.clientCard = clientCard;
        this.totalDiscountGet = totalDiscountGet;
    }

    public void setTotalDiscountGet(float totalDiscountGet) {
        this.totalDiscountGet = totalDiscountGet;
    }

    @Override
    public String toString() {
        return "ClientCardDiscountDTO: " +
                clientCard +
                ", totalDiscountGet=" + totalDiscountGet +
                '}';
    }
}
