package com.example.forums_backend.api;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.service.AccountManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forums_backend.config.route.constant.AccountRoute.*;

@RestController
@RequestMapping(PREFIX_ACCOUNT_ROUTE)
@RequiredArgsConstructor
public class AccountController {
    final AccountManagerService accountManagerService;

    @RequestMapping(value = GET_ALL_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(accountManagerService.findAll());
    }

    @RequestMapping(value = UPDATE_PATH, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Account> update(@RequestBody Account account, @PathVariable Long id) {
        return ResponseEntity.ok(accountManagerService.update(account, id));
    }

    @RequestMapping(value = DELETE_PATH, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        accountManagerService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
