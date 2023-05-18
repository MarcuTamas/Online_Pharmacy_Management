package Domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction extends Entity{

    //Fields
    private int medicineID;
    private boolean medicineNeedRecipe;
    private int medicinePieces;
    private float medicinePrice;
    private  float medicinePaidPrice;
    private boolean hasClientCard;
    private Integer clientCardID;
    private float priceDiscount;
    private Date transactionDate;
    private static final String ANSI_PURPLE = "\033[0;35m";
    private static final String ANSI_GREEN = "\033[0;32m" ;
    private static final String ANSI_RESET = "\u001B[0m";

    //Constructors. We need at least 2 constructors, because sometimes we don't have client card.

    //First constructor called when the client doesn't have a Client Card.
    public Transaction(int id, int medicineID, boolean medicineNeedRecipe, int medicinePieces, float medicinePrice, float medicinePaidPrice, Date transactionDate) {
        this(id, medicineID, medicineNeedRecipe, medicinePieces, medicinePrice, medicinePaidPrice, false, null, 0, transactionDate);
    }

    //The main constructor (second in the code) called when the client has a Client Card.
    public Transaction(int id, int medicineID, boolean medicineNeedRecipe, int medicinePieces, float medicinePrice, float medicinePaidPrice, boolean hasClientCard, Integer clientCardID, float priceDiscount, Date transactionDate) {
        super(id);
        this.medicineID = medicineID;
        this.medicineNeedRecipe = medicineNeedRecipe;
        this.medicinePieces = medicinePieces;
        this.medicinePrice = medicinePrice;
        this.medicinePaidPrice = medicinePaidPrice;
        this.hasClientCard = hasClientCard;
        this.clientCardID = clientCardID;
        this.priceDiscount = priceDiscount;
        this.transactionDate = transactionDate;
    }

    //Accessors
    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    public boolean isMedicineNeedRecipe() {
        return medicineNeedRecipe;
    }

    public void setMedicineNeedRecipe(boolean medicineNeedRecipe) {
        this.medicineNeedRecipe = medicineNeedRecipe;
    }

    public int getMedicinePieces() {
        return medicinePieces;
    }

    public void setMedicinePieces(int medicinePieces) {
        this.medicinePieces = medicinePieces;
    }

    public float getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(float medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    public float getMedicinePaidPrice() {
        return medicinePaidPrice;
    }

    public void setMedicinePaidPrice(float medicinePaidPrice) {
        this.medicinePaidPrice = medicinePaidPrice;
    }

    public boolean isHasClientCard() {
        return hasClientCard;
    }

    public void setHasClientCard(boolean hasClientCard) {
        this.hasClientCard = hasClientCard;
    }

    public int getClientCardID() {
        return clientCardID;
    }

    public void setClientCardID(int clientCardID) {
        this.clientCardID = clientCardID;
    }

    public float getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(float priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Transaction{" +
                "id='" + this.getId() +
                ", medicineID=" + medicineID +
                ", medicineNeedRecipe=" + medicineNeedRecipe +
                ", medicinePieces=" + medicinePieces +
                ", medicinePrice=" + medicinePrice +
                ", medicinePaidPrice=" + medicinePaidPrice + ", ");

        if (!hasClientCard){
            sb.append(ANSI_PURPLE + "hasClientCard=❌" +
                    ", priceDiscount=" + "No discount was applied" + ANSI_RESET +
                    ", transactionDate='" + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(transactionDate)  + '\'' +
                    '}');
        }else {
            sb.append(ANSI_GREEN + "hasClientCard=✅" +
                    ", clientCardID=" + clientCardID +
                    ", priceDiscount=" + priceDiscount + ANSI_RESET +
                    ", transactionDate='" + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(transactionDate) + '\'' +
                    '}');
        }

        return sb.toString();
    }

}
