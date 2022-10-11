package com.clouddevg.ita.repository.user;

import com.clouddevg.ita.entity.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
