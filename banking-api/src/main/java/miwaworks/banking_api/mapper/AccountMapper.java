package miwaworks.banking_api.mapper;

import miwaworks.banking_api.model.Account;
import miwaworks.banking_api.modeldto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toAccount(AccountDTO accountDTO) {
        return new Account(accountDTO.getId(),
                accountDTO.getHolderName(), accountDTO.getBalance());

    }

    public AccountDTO toAccountDTO(Account account) {
        return new AccountDTO(account.getId(),
                account.getHolderName(), account.getBalance());
    }

}
