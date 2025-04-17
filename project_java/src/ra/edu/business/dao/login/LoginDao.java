package ra.edu.business.dao.login;

import ra.edu.business.model.candidate.Candidate;

public interface LoginDao {
    Candidate login(String email,String password );
}
