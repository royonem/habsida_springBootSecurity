package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.Table;

@Repository
@Table(name = "users")
public interface UserRepository extends JpaRepository<User, Long> {

}
