package ra.edu.business.service.login;

import ra.edu.business.model.account.Account;


public interface LoginService {
    Account login(String email, String password );
}
