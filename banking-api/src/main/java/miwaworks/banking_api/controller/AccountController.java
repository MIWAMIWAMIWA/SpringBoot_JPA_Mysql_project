package miwaworks.banking_api.controller;

import miwaworks.banking_api.modeldto.AccountDTO;
import miwaworks.banking_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(
                accountService.createAccount(accountDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(final @PathVariable Long id) {
        if (accountService.hasAccount(id)) {
            return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<Object> deposit(
            @PathVariable Long id, @RequestBody Map<String, Integer> request) {

        if (accountService.hasAccount(id)) {
            if (request.get("amount") < 0) {
                return ResponseEntity.ok(accountService.depositAccount(id, request.get("amount")));
            } else {
                String errorMessage = "Invalid deposit amount";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } else {
            String errorMessage = "Account not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Object> withdraw(
            @PathVariable Long id, @RequestBody Map<String, Integer> request) {
        if (accountService.hasAccount(id)) {
            if (request.get("amount") <= 0) {
                String errorMessage = "Invalid withdraw amount";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            } else {
                if (accountService.checkBalanceAccount(id, request.get("amount"))) {
                    return ResponseEntity.ok(
                            accountService.withdrawAccount(id, request.get("amount")));

                } else {
                    String errorMessage = "Not enough money on balance to withdraw";
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
                }
            }
        } else {
            String errorMessage = "Account not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAccount(final @PathVariable Long id) {
        if (accountService.hasAccount(id)) {
            accountService.deleteAccount(id);
            return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }

}
