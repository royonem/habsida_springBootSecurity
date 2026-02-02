package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.User;
import web.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> getAll() { return repo.findAll(); }

    public User get(Long id) { return repo.findById(id).orElse(null); }

    public void save(User user) { repo.save(user); }

    public void delete(Long id) { repo.deleteById(id); }
}
