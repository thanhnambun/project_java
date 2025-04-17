package ra.edu.business.service.login;

import ra.edu.business.model.candidate.Candidate;

public interface LoginService {
    Candidate login(String email, String password );
}
