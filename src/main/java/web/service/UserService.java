package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repo;
    private final RoleRepository roleRepo;

    public UserService(UserRepository repo, RoleRepository roleRepo) {
        this.repo = repo;
        this.roleRepo = roleRepo;
    }

    public void registerUser(User user, String adminCode) {
        // Assign role
        Role role;
        if ("SECRET123".equals(adminCode)) {
            role = roleRepo.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
        } else {
            role = roleRepo.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        }
        user.setRoles(Set.of(role));

        // Save to DB
        repo.save(user);
    }

    public List<User> getAll() { return repo.findAll(); }

    public User get(Long id) { return repo.findById(id).orElse(null); }

    public void save(User user) { repo.save(user); }

    public void delete(Long id) { repo.deleteById(id); }

    public User findUserByUsername(String username) throws UsernameNotFoundException{
        return repo.findByUsername(username)
                .orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return repo.findByUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));
    }
}
