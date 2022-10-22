package com.example.forums_backend;

import com.example.forums_backend.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForumsBackendApplicationTests {
@Autowired
    VoteService voteService;
    @Test
    void contextLoads() {
        Long id = Long.valueOf(1);
        voteService.delete(id);
    }

}
