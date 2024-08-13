package miwaworks.banking_api.service;

import miwaworks.banking_api.mapper.AccountMapper;
import miwaworks.banking_api.model.Account;
import miwaworks.banking_api.modeldto.AccountDTO;
import miwaworks.banking_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        return accountMapper.toAccountDTO(
                accountRepository.save(accountMapper.toAccount(accountDTO)));
    }

    public AccountDTO getAccount(Long id) {
        return accountMapper.toAccountDTO(accountRepository.getReferenceById(id));

    }

    public boolean hasAccount(Long id) {
        return accountRepository.existsById(id);
    }

    public AccountDTO depositAccount(Long id, Integer amount) {
        Account account = accountRepository.getReferenceById(id);
        account.setBalance(account.getBalance() + amount);
        return accountMapper.toAccountDTO(accountRepository.save(account));

    }

    public AccountDTO withdrawAccount(Long id, Integer amount) {
        Account account = accountRepository.getReferenceById(id);
        account.setBalance(account.getBalance() - amount);
        return accountMapper.toAccountDTO(accountRepository.save(account));
    }

    public boolean checkBalanceAccount(Long id, Integer amount) {
        return accountRepository.getReferenceById(id).getBalance() >= amount;
    }

    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> accountMapper.toAccountDTO(account))
                .collect(Collectors.toList());

    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
