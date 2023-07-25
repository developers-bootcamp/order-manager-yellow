package com.yellow.ordermanageryellow.service;

import com.yellow.ordermanageryellow.DTO.UserDTO;
import com.yellow.ordermanageryellow.DTO.UserMapper;
import com.yellow.ordermanageryellow.dao.RolesRepository;
import com.yellow.ordermanageryellow.dao.UserRepository;
import com.yellow.ordermanageryellow.exception.NotFoundException;
import com.yellow.ordermanageryellow.exception.ObjectExistException;
import com.yellow.ordermanageryellow.exception.WrongPasswordException;
import com.yellow.ordermanageryellow.exceptions.NoPermissionException;
import com.yellow.ordermanageryellow.model.ProductCategory;
import com.yellow.ordermanageryellow.model.RoleNames;
import com.yellow.ordermanageryellow.model.Roles;
import com.yellow.ordermanageryellow.model.Users;
import com.yellow.ordermanageryellow.security.EncryptedData;
import com.yellow.ordermanageryellow.security.JwtToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UsersService  {
    private final UserRepository UserRepository;
    private final UserMapper userMapper;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private RolesRepository rolesRepository;
    @Value("${pageSize}")
    private int pageSize;

    @Autowired
    public UsersService(UserRepository UserRepository, UserMapper userMapper) {
        this.UserRepository = UserRepository;
        this.userMapper = userMapper;
    }


    @SneakyThrows
    public String login(String email, String password) {
        Users user = UserRepository.findUserByEmail(email);
        if (user == null)
            throw new NotFoundException("user not exist");
        else if (!user.getPassword().equals(password))
            throw new WrongPasswordException("invalid password");
        else return this.jwtToken.generateToken(user);
    }


    @SneakyThrows
    public Users createNewUser(Users newUser,String token) {
        if (!findUser(newUser)) {
            return UserRepository.save(newUser);
        } else
            throw new ObjectExistException("user is already exist");
    }

//    public String generateToken(Users user) {
//        return user.getCompanyId() + "&" + user.getId() + "&" + user.getRoleId();
//    }

//    public String[] getToken(String token) {
//        String[] tokenS = token.split("&");
//        return tokenS;
//    }

    public boolean findUser(Users user) {
        Users foundUser = UserRepository.findUserByEmail(user.getAddress().getEmail());
        if (foundUser == null)
            return false;
        return true;
    }

    @SneakyThrows
    public void deleteUser(String id,String token) {
        String role= this.jwtToken.decryptToken(token, EncryptedData.ROLE);
        String company= this.jwtToken.decryptToken(token, EncryptedData.COMPANY);
        Users userFromDB = this.UserRepository.findById(id).orElse(null);
        if (userFromDB == null) {
            throw new NotFoundException("user is not found");
        }
        String companyOfCategory = userFromDB.getCompanyId().getId();
        Roles wholeRole = rolesRepository.findById(role).orElse(null);
        if(!wholeRole.getName().equals(RoleNames.ADMIN)|| !company.equals(companyOfCategory)){
            throw new NoPermissionException("You do not have permission to update user");
        }
        UserRepository.deleteById(id);
    }

    @SneakyThrows
    public Users updateUser(Users user, String token) throws NoPermissionException {
        String role= this.jwtToken.decryptToken(token, EncryptedData.ROLE);
        String company= this.jwtToken.decryptToken(token, EncryptedData.COMPANY);
        Users userFromDB = this.UserRepository.findById(user.getId()).orElse(null);
        if (userFromDB == null) {
            throw new NotFoundException("user is not found");
        }
        String companyOfCategory = userFromDB.getCompanyId().getId();
        Roles wholeRole = rolesRepository.findById(role).orElse(null);
        if( !wholeRole.getName().equals(RoleNames.ADMIN)|| !company.equals(companyOfCategory)){
            throw new NoPermissionException("You do not have permission to update user");
        }
        return UserRepository.save(user);
    }

    @SneakyThrows
    public HashMap<String, String> getCustomerByNames(String prefix,String token) {
        String roleId= this.jwtToken.decryptToken(token, EncryptedData.ROLE);
        String companyId= this.jwtToken.decryptToken(token, EncryptedData.COMPANY);
        List<Users> users = UserRepository.findByFullNameContainingAndCompanyIdAndRoleId(prefix, companyId, roleId);
        HashMap<String, String> userMap = new HashMap<>();
        for (Users user : users) {
            userMap.put(user.getId(), user.getFullName());
        }
        return userMap;
    }

    @SneakyThrows
    public List<UserDTO> getUsers(int pageNumber,String token) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String roleId= this.jwtToken.decryptToken(token, EncryptedData.ROLE);
        String companyId= this.jwtToken.decryptToken(token, EncryptedData.COMPANY);
        Page<Users> users = UserRepository.findAllByCompanyIdAndRoleId(companyId, roleId, pageable);
        return users.map(userMapper::usersToUserDTO).getContent();
    }
}
