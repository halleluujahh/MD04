package org.com.session06.controller.admin;

import lombok.AllArgsConstructor;
import org.com.session06.dto.request.AccountRequestDTO;
import org.com.session06.entity.User;
import org.com.session06.exception.NotFoundException;
import org.com.session06.service.AuthService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/admin/account")
@RestController
@AllArgsConstructor
public class AccountController {
    private final AuthService authService;
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(authService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAllPagination(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @RequestParam(name = "status", defaultValue = "1") Boolean status,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sort,
            @RequestParam(name = "keyword", required = false) String keyword
    ){
        return new ResponseEntity<>(authService.findAll(page, limit, status, sort, keyword), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccountRequestDTO accountRequestDTO){
        return new ResponseEntity<>(authService.createAccount(accountRequestDTO),HttpStatus.CREATED);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long userId) throws NotFoundException {
        return new ResponseEntity<>(authService.changeStatus(userId), HttpStatus.OK);
    }
}