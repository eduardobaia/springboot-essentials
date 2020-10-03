package pt.com.devdojo.awesome.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pt.com.devdojo.awesome.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
