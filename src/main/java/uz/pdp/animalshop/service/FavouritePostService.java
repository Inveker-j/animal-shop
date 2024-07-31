package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.FavouritePost;
import uz.pdp.animalshop.repo.FavouritePostRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouritePostService implements BaseService<FavouritePost, UUID> {
    private final FavouritePostRepository favouritePostRepository;

    @Override
    public List<FavouritePost> findAll() {
        return favouritePostRepository.findAll();
    }

    @Override
    public Optional<FavouritePost> findById(UUID id) {
        return favouritePostRepository.findById(id);
    }


    @Override
    public ResponseEntity<?> save(FavouritePost user) {
        return ResponseEntity.ok(favouritePostRepository.save(user));
    }

    @Override
    public void delete(UUID uuid) {
        favouritePostRepository.deleteById(uuid);
    }

    public Optional<FavouritePost> findByUserId(UUID userId) {
        return favouritePostRepository.findByUser_Id(userId);
    }


}
