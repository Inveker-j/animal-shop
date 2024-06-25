package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.AttachmentContent;
import uz.pdp.animalshop.repo.AttachmentContentRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentContentContentService implements BaseService<AttachmentContent, UUID> {
    private final AttachmentContentRepository attachmentContentRepository;

    @Override
    public List<AttachmentContent> findAll() {
        return attachmentContentRepository.findAll();
    }

    @Override
    public Optional<AttachmentContent> findById(UUID id) {
        return attachmentContentRepository.findById(id);
    }

    @Override
    public AttachmentContent save(AttachmentContent attachment) {
        return attachmentContentRepository.save(attachment);
    }

    @Override
    public void delete(UUID uuid) {
        attachmentContentRepository.deleteById(uuid);
    }
}
