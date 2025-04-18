package ra.edu.business.service.register;

import ra.edu.business.dao.register.RegisterDao;
import ra.edu.business.dao.register.RegisterDaoImp;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;

public class RegisterServiceImp implements RegisterService {
    private final RegisterDao registerDao;
    public RegisterServiceImp() {
        registerDao = new RegisterDaoImp();
    }
    @Override
    public Boolean register(Candidate candidate , Account account) {
        return registerDao.register(candidate,account);
    }
}
