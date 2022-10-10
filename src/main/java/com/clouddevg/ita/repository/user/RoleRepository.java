package com.clouddevg.ita.repository.user;

import com.clouddevg.ita.entity.user.Role;
import com.clouddevg.ita.entity.user.UserRoles;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(UserRoles role);

}
