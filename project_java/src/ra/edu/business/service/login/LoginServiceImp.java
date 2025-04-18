package ra.edu.business.service.login;

import ra.edu.business.dao.login.LoginDao;
import ra.edu.business.dao.login.LoginDaoImp;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;

public class LoginServiceImp implements LoginService {
    private final LoginDao loginDao;
    public LoginServiceImp() {
        loginDao = new LoginDaoImp();
    }
    @Override
    public Account login(String email, String password) {
        return loginDao.login(email, password);
    }
}
