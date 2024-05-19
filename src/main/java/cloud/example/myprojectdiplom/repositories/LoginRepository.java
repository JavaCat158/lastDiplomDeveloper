package cloud.example.myprojectdiplom.repositories;

import cloud.example.myprojectdiplom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LoginRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
