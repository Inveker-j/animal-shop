package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.animalshop.entity.FavouritePost;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavouritePostRepository extends JpaRepository<FavouritePost, UUID> {
    Optional<FavouritePost> findByUser_Id(UUID id);
}