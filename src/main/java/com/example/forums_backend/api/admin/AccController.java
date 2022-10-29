package com.example.forums_backend.api.admin;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.service.AccountManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forums_backend.config.constant.route.AdminRoute.*;

@RestController
@RequestMapping(PREFIX_ADMIN_ROUTE)
@RequiredArgsConstructor
public class AccController {
    @Autowired
    AccountManagerService accountManagerService;

    @RequestMapping(value = ACCOUNT_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(accountManagerService.findAll());
    }

    @RequestMapping(value = ACCOUNT_PATH_WITH_ID, produces = "application/json", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<Account> update(@RequestBody Account account, @PathVariable Long id) {
        return ResponseEntity.ok(accountManagerService.update(account, id));
    }

    @RequestMapping(value = ACCOUNT_PATH_WITH_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        accountManagerService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}