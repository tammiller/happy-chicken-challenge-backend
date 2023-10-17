package happy.chicken.backend.service;

import happy.chicken.backend.data.VoyagerRepository;
import happy.chicken.backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VoyagerService implements UserService {

    @Autowired
    private VoyagerRepository voyagerRepo;

    @Override
    public void createUserAccount(User user) {
        log.info("saving user {}", user);
        voyagerRepo.saveCustomer(user);
    }
}