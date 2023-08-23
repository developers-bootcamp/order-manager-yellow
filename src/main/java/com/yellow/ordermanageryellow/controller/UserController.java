package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.exceptions.NotValidStatusExeption;
import com.yellow.ordermanageryellow.exceptions.ObjectAlreadyExistException;
import com.yellow.ordermanageryellow.model.Users;
import com.yellow.ordermanageryellow.Dto.UserDTO;
import com.yellow.ordermanageryellow.Dto.UserMapper;
import com.yellow.ordermanageryellow.exceptions.NoPermissionException;
import com.yellow.ordermanageryellow.service.UsersService;
import com.yellow.ordermanageryellow.model.Users;
import com.yellow.ordermanageryellow.exception.NotFoundException;
import com.yellow.ordermanageryellow.exception.ObjectExistException;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yellow.ordermanageryellow.exception.WrongPasswordException;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/User")
public class UserController {

    private final UsersService usersService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UsersService usersService, UserMapper userMapper) {
        this.usersService = usersService;
        this.userMapper = userMapper;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String password, @RequestParam String email) {
        try {
            return ResponseEntity.ok().body(usersService.login(email, password));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (WrongPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("The password provided is incorrect");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping()
    @RequestMapping("/signUp")
    public ResponseEntity<String> signUP(@RequestParam("fullName") String fullName, @RequestParam("companyName") String companyName, @RequestParam("email") String email,
                                         @RequestParam("password") String password) {
        try {
            Users user =usersService.signUp(fullName,companyName,email,password);
            return ResponseEntity.ok(user.getFullName());

        }catch (NotValidStatusExeption ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        }catch (ObjectAlreadyExistException ex) {
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (Exception ex) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong please try later  "+ex.getMessage());
        }

    }
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
    public ResponseEntity<String> createNewUser(@RequestBody Users newUser, @RequestHeader("Authorization") String token) {
        try {
            usersService.createNewUser(newUser, token);
        }catch (NoPermissionException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ObjectExistException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok().body("success");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id,@RequestHeader("Authorization") String token) {
        try {
            usersService.deleteUser(id,token);
        }catch (NoPermissionException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping()
    public ResponseEntity updateUser(@RequestBody Users user, @RequestHeader("Authorization") String token) {
        try {
            usersService.updateUser(user,token);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("update success " + user.getFullName());

    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity getAllUsers(@PathVariable int pageNumber, @RequestHeader("Authorization") String token) {
        List<UserDTO> customers;
        try {
            customers = usersService.getUsers(pageNumber,token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping("/customersNames")
    public ResponseEntity<HashMap<String, String>> getCustomersByPrefix(@RequestParam String prefix, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usersService.getCustomerByNames(prefix,token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


}