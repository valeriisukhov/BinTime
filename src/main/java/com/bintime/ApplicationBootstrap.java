package com.bintime;

import com.bintime.dto.bintime.Offer;
import com.bintime.dto.bintime.Product;
import com.bintime.entity.*;
import com.bintime.services.*;
import com.bintime.services.impl.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Initial bean for app entity</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    InitializingBean
 * @see    User
 * @see    UserRole
 * @see    UserRoleService
 * @see    UserService
 */
public class ApplicationBootstrap implements InitializingBean {
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Value("#{properties['db.hbm2ddl.auto']}")
    private String db;

    @Value("#{properties['cloud.login']}")
    private String login;
    @Value("#{properties['cloud.pass']}")
    private String pass;

    @Transactional
    public void afterPropertiesSet() throws Exception {
        if (db.contains("create")){
            initUsers();
            //initProducts();
        }
    }

    /**
     * Init App Users
     */
    private void initUsers() {
        List<User> users = userService.list();
        if (users.size()>0){
            return;
        }
        UserRole roleAdmin = new UserRole();
        roleAdmin.setName("ROLE_ADMIN");
        userRoleService.add(roleAdmin);

        UserRole roleUser = new UserRole();
        roleUser.setName("ROLE_USER");
        userRoleService.add(roleUser);

        ArrayList<UserRole> adminRoles = new ArrayList<UserRole>();
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword(new ShaPasswordEncoder().encodePassword("admin", null));
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setCreated(new Date());
        admin.setRoles(adminRoles);
        userService.add(admin);
    }

    public void initProducts(){
        redisService.flush();
        Offer offer1 = new Offer();
        offer1.setPrice(100.0);
        offer1.setAvailable(1);
        offer1 = redisService.storeOffer(offer1);

        Offer offer2 = new Offer();
        offer2.setPrice(66.0);
        offer2.setAvailable(2);
        offer2 = redisService.storeOffer(offer2);

        Offer offer3 = new Offer();
        offer3.setPrice(90.0);
        offer3.setAvailable(0);
        offer3 = redisService.storeOffer(offer3);

        Product product1 = new Product();
        product1.setMpn("SA-1KP");
        ArrayList<Long> offersId = new ArrayList<Long>();
        offersId.add(offer1.getId());
        offersId.add(offer2.getId());
        product1.setOffers(offersId);
        product1 = redisService.storeProduct(product1);

        Product product2 = new Product();
        product2.setMpn("SA-2KP");
        offersId = new ArrayList<Long>();
        offersId.add(offer3.getId());
        product2.setOffers(offersId);
        product2 = redisService.storeProduct(product2);
    }
}
