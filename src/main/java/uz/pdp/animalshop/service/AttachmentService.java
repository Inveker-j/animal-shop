package uz.pdp.attachmentshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.Attachment;
import uz.pdp.animalshop.repo.AttachmentRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService implements BaseService<Attachment, UUID> {
    private final AttachmentRepository attachmentRepository;

    @Override
    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    @Override
    public Optional<Attachment> findById(UUID id) {
        return attachmentRepository.findById(id);
    }

    @Override
    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Override
    public void delete(UUID uuid) {
        attachmentRepository.deleteById(uuid);
    }
}
