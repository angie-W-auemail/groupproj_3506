package login;

public class LogInModel {
    private String userName;
    private String password;
 
    public LogInModel(){
 
    }
 
    public LogInModel(String username, String password){
        this.userName = username;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
