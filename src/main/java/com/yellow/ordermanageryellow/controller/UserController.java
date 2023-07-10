package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.DTO.MapStructMapper;
import com.yellow.ordermanageryellow.DTO.UserDTO;
import com.yellow.ordermanageryellow.Service.UsersService;
import com.yellow.ordermanageryellow.exception.NotFoundException;
import com.yellow.ordermanageryellow.exception.ObjectExistException;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    private final UsersService usersService;
    private final MapStructMapper mapStructMapper;

    @Autowired
    public UserController(UsersService usersService, MapStructMapper mapStructMapper) {
        this.usersService = usersService;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String password, @RequestParam String email, @RequestHeader String token) {
        try {
            return ResponseEntity.ok().body(usersService.login(email, password));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<String> createNewUser(@RequestBody Users newUser, @RequestHeader String token) {
        try {

            usersService.createNewUser(newUser);
        } catch (ObjectExistException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok().body("success");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id, @RequestHeader String token) {
        try {
            usersService.deleteUser(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping()
    public ResponseEntity updateCustomer(@RequestBody Users user, @RequestHeader String token) {
        try {
            usersService.updateUser(user);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("update success " + user.getFullName());

    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<UserDTO>> getAllCustomers(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestHeader String token) {
        List<UserDTO> customers;
        try {
            customers = usersService.getCustomers(pageNumber, pageSize);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping("/customersNames")
    public ResponseEntity<HashMap<String, String>> getCustomersByPrefix(@RequestParam String prefix, @RequestHeader String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usersService.getCustomerByNames(prefix));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


}
