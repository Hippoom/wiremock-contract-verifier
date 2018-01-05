package org.restbucks.tdd.web.rest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.restbucks.tdd.domain.ordering.Order;
import org.restbucks.tdd.domain.ordering.OrderRepository;
import org.restbucks.tdd.web.rest.assembler.OrderResourceAssembler;
import org.restbucks.tdd.web.rest.resource.OrderResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginRestController {

    @PostMapping("/login")
    public String handle(@RequestParam String username, @RequestParam String password) {
        return username;
    }

}
