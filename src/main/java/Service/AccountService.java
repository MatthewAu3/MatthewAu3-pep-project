package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account processNewUser(Account account) {
        if (account.getUsername().length() > 0 &&
         account.getPassword().length() >= 4 &&
         accountDAO.searchByUsername(account.getUsername()) == null)
            return accountDAO.processNewUser(account);
        return null;
    }

    public Account processUserLogin(Account account) {
        return accountDAO.processUserLogins(account);
    }
}
