package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.Dao.UserRepository;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private UserRepository UserRepository;

}