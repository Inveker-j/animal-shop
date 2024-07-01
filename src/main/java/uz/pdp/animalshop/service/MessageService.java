package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.Message;
import uz.pdp.animalshop.repo.MessageRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService implements BaseService<Message, UUID> {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(UUID uuid) {
        messageRepository.deleteById(uuid);
    }

    public List<Message> getHistory(UUID fromId, UUID toId) {
        return messageRepository.getHistory(fromId, toId);
    }

    public void deleteChat(UUID toId) {
        messageRepository.deleteChat(toId);
    }
}
