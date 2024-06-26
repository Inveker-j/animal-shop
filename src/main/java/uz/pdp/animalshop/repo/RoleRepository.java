package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.animalshop.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}