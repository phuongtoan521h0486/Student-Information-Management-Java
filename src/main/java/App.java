import org.thd.Controllers.AccountController;
import org.thd.Views.FormUserSystem;
import org.thd.Views.FormCreateAccount;
import org.thd.Views.FormLogin;
import org.thd.Views.FormMain;

public class App {
    public static void main(String[] args) {
        FormLogin f = new FormLogin();
        f.setVisible(true);

        FormCreateAccount f1 = new FormCreateAccount();
        f1.setVisible(true);

        FormUserSystem f2 = new FormUserSystem();
        f2.setVisible(true);
    }
}
