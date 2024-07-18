package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.repo.PostRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getPagination(int page, int size) {
        return postRepository.findByIsDeletedFalse(PageRequest.of(page,size));
    }

    public Optional<Post> findById(UUID id) {
        return postRepository.findById(id);
    }

    public ResponseEntity<?> save(Post post) {
        return ResponseEntity.ok( postRepository.save(post));
    }

    public void delete(UUID uuid) {
        postRepository.deleteById(uuid);
    }
}
