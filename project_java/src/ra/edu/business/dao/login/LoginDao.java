package ra.edu.business.dao.login;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;

public interface LoginDao {
    Account login(String email, String password );
}
