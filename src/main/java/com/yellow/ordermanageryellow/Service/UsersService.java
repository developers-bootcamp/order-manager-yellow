package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.DTO.MapStructMapper;
import com.yellow.ordermanageryellow.DTO.UserDTO;
import com.yellow.ordermanageryellow.Dao.UserRepository;
import com.yellow.ordermanageryellow.exception.NotFoundException;
import com.yellow.ordermanageryellow.exception.ObjectExistException;
import com.yellow.ordermanageryellow.exception.WrongPasswordException;
import com.yellow.ordermanageryellow.model.Users;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class UsersService implements CommandLineRunner {
    private final UserRepository UserRepository;
    private final MapStructMapper mapStructMapper;

    @Autowired
    public UsersService(UserRepository UserRepository, MapStructMapper mapStructMapper) {
        this.UserRepository = UserRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Override
    public void run(String... args) {
        Users myModel = new Users("12");
        UserRepository.save(myModel);
    }

    @SneakyThrows
    public String login(String email, String password) {
        Users user = UserRepository.findUserByEmail(email);
        if (user == null)
            throw new NotFoundException("user not exist");
        else if (!user.getPassword().equals(password))
            throw new WrongPasswordException("invalid password");
        else return generateToken(user);

    }


    @SneakyThrows
    public Users createNewUser(Users newUser) {
        if (!findUser(newUser)) {
            return UserRepository.save(newUser);
        } else
            throw new ObjectExistException("user is already exist");
    }

    public String generateToken(Users user) {
        return user.getCompanyId() + user.getId() + user.getRoleId();
    }

    public boolean findUser(Users user) {

        Users foundUser = UserRepository.findUserByEmail(user.getAddress().getEmail());
        if (foundUser == null)
            return false;
        return true;

    }

    @SneakyThrows
    public void deleteUser(String id) {
        if (UserRepository.existsById(id))
            UserRepository.deleteById(id);
        else
            throw new NotFoundException("user not found");
    }

    @SneakyThrows
    public Users updateUser(Users user) {
        if (UserRepository.existsById(user.getId()))
            return UserRepository.save(user);
        else
            throw new NotFoundException("user not found");
    }

    @SneakyThrows
    public HashMap<String, String> getCustomerByNames(String prefix) {

        //company and role will be taken from token
        List<Users> users = UserRepository.findByFullNameContainingAndCompanyIdAndRoleId(prefix, "1", "1");
        HashMap<String, String> userMap = new HashMap<>();
        for (Users user : users) {
            userMap.put(user.getId(), user.getFullName());
        }
        return userMap;
    }

    @SneakyThrows
    public List<UserDTO> getCustomers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //company and role will be taken from token
        Page<Users> users = UserRepository.findAllByCompanyIdAndRoleId("1", "1", pageable);
        return users.map(mapStructMapper::usersToUserDTO).getContent();
    }


}
