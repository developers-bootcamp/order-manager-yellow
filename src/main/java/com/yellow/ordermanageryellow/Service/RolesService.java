package com.yellow.ordermanageryellow.service;
package com.yellow.ordermanageryellow.service;


import com.yellow.ordermanageryellow.model.Roles;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yellow.ordermanageryellow.dao.RolesRepository;

        import com.yellow.ordermanageryellow.dao.RolesRepository;
        import com.yellow.ordermanageryellow.model.Roles;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.CommandLineRunner;
        import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    private  RolesRepository RolesRepository;
}
