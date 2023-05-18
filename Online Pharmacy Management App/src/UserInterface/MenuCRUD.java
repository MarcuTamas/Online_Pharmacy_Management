package UserInterface;

public class MenuCRUD {
    private String elementCRUD;

    public MenuCRUD(String elementCRUD) {
        this.elementCRUD = elementCRUD;
    }

    public void showCRUD(){

        System.out.println("1. Add " + elementCRUD + ".");
        System.out.println("2. Update " + elementCRUD + ".");
        System.out.println("3. Delete " + elementCRUD + " by id.");
        System.out.println("4. Show one " + elementCRUD + " by id.");
        System.out.println("5. Show all " + elementCRUD + ".");
        System.out.println("0. Back.");
    }

}
