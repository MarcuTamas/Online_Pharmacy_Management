package Domain;


public class ClientCard extends Entity{

    //Fields
    private String surname;
    private String name;
    private String cnp;
    private String birthday;
    private String registrationDate;

    //Constructor
    public ClientCard(int id, String surname, String name, String cnp, String birthday, String registrationDate) {
        super(id);
        this.surname = surname;
        this.name = name;
        this.cnp = cnp;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
    }

    //Accessors
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "id='" + this.getId() +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", cnp='" + cnp + '\'' +
                ", birthday='" + birthday + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}
