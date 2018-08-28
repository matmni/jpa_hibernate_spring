package service;

import entity.spring.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SpringDataService {

    private UserRepository userRepository;

    @Autowired
    public SpringDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//
//
//    public void optimistLockingFalseTest() {
//        try {
//            User user = getUser(27L);
//
//            user.setPortalName("newName2");
//            user = userRepository.save(user);
//            user = userRepository.save(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void addUser() {
        User user = new User();
        user.setPortalName("onet2.pl");
        userRepository.save(user);
    }
    @Transactional
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    public Set<User> getNonDuplicates() {
        Set<User> users = new HashSet<>();
        users.addAll(userRepository.findAll());
        System.out.println(users.size());
        return users;
    }
}
