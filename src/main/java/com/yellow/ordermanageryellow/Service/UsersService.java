package com.yellow.ordermanageryellow.Service;
import com.yellow.ordermanageryellow.Dao.UserRepository;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements CommandLineRunner {
    private final UserRepository UserRepository;
    @Autowired
    public UsersService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }
    @Override
    public void run(String... args) {
        Users myModel = new Users("12");
        UserRepository.save(myModel);
    }
    public  ResponseEntity<String> login(String email,String password){
        Users user=UserRepository.findUserByEmail(email);
    if(user==null){
    return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);}
    if (user.getPassword()!=null)
        if(user.getPassword().equals(password))
           return new ResponseEntity<>(generateToken(user), HttpStatus.OK);
        else
            return new ResponseEntity<>("wrong password",HttpStatus.UNAUTHORIZED);
    else
        return new ResponseEntity<>("unexpected error",HttpStatus.INTERNAL_SERVER_ERROR);
}
    public String generateToken(Users user){
        return user.getCompanyId() + user.getId()+ user.getRoleId();
    }
}