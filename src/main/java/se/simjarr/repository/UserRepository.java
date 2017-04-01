package se.simjarr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.simjarr.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

}
