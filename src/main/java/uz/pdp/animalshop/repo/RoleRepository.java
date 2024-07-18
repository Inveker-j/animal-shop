package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select * from roles where name = ?1", nativeQuery = true)
    List<Role> findRolesByName(String string);

    @Query(value = "select * from roles where name = ?1", nativeQuery = true)
    Role findRoleByName(String roleName);
}