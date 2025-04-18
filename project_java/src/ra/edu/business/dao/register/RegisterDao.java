package ra.edu.business.dao.register;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;

public interface RegisterDao {
    Boolean register(Candidate candidate, Account account);
}
