package ra.edu.business.service.register;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;

public interface RegisterService {
    Boolean register(Candidate candidate, Account account);
}
