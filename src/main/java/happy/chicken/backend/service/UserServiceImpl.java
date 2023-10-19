package happy.chicken.backend.service;

import happy.chicken.backend.data.UserRepository;
import happy.chicken.backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public void createUserAccount(User user) {
        userRepo.saveCustomer(user);
    }
}
