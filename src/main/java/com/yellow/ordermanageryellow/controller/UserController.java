package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.exceptions.NotValidStatusExeption;
import com.yellow.ordermanageryellow.exceptions.ObjectAlreadyExistException;
import com.yellow.ordermanageryellow.model.Users;
import com.yellow.ordermanageryellow.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")

@RestController
@RequestMapping("/User")
public class UserController {

    private final UsersService usersService;
    @Autowired
    public UserController(UsersService usersService){
        this.usersService=usersService;
    }
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam  String password, @RequestParam String email){
        return usersService.login(email,password);
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



}
