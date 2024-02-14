package data;

public class CourierData {
    private String login = "Slonopat" + 1000 + (int)(Math.random() *99999);
    private String password = "Abracadabra" + 1000 + (int)(Math.random() *999999);
    private String name = "" + 1000 + (int)(Math.random() *999999);

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CourierData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
