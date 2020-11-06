package cat.alkaid.projects.intrastat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.alkaid.projects.intrastat.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserName(String userName);

	void changePassword(String password);

	Account findByActivationCode(String activationCode);
}
