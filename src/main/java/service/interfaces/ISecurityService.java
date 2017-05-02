package service.interfaces;

/**
 * Created by Admin on 30.04.2017.
 */
public interface ISecurityService {
    public String findLoggedInUsername();
    public void autologin(String username, String password);
}
