package uz.pdp.animalshop.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import uz.pdp.animalshop.entity.Post;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void deleteById() {
        Post post = new Post();
        post.setPhone("+998903231604");
        post.setTitle("Animal Shop");

        Post save = postRepository.save(post);
        assertNotNull(save);

        postRepository.deleteById(save.getId());

        Optional<Post> optional = postRepository.findById(save.getId());
        assertTrue(optional.isPresent());
    }

    @Test
    void findByIsDeletedFalse() {
        Post post = new Post();
        post.setPhone("+998903231604");
        post.setTitle("Animal Shop");
        post.setIsDeleted(false);

        Post save = postRepository.save(post);
        assertNotNull(save);

        Post post1 = new Post();
        post1.setPhone("+998903231604");
        post1.setTitle("Animal Shop");
        post1.setIsDeleted(true);

        Post save1 = postRepository.save(post1);
        assertNotNull(save1);

        Post post2 = new Post();
        post2.setPhone("+998903231604");
        post2.setTitle("Animal Shop");
        post2.setIsDeleted(false);

        Post save2 = postRepository.save(post2);
        assertNotNull(save2);

        Pageable pageable =  PageRequest.of(0,10);

        Page<Post> page = postRepository.findByIsDeletedFalse(pageable);

        assertNotNull(page);
        assertEquals(2, page.getContent().size());
        assertTrue(page.getContent().contains(save));
        assertTrue(page.getContent().contains(save2));
        assertFalse(page.getContent().contains(save1));
    }
}