package generator;

public class PasswordEntry {

    private  String password;
    private  String websiteName;
    private  String login;

    public PasswordEntry (String websiteName, String login, String password){
        this.websiteName = websiteName;
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}