//package uz.pdp.animalshop.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import uz.pdp.animalshop.dto.AnimalDTO;
//import uz.pdp.animalshop.entity.Animal;
//import uz.pdp.animalshop.entity.Attachment;
//import uz.pdp.animalshop.entity.AttachmentContent;
//import uz.pdp.animalshop.entity.Category;
//import uz.pdp.animalshop.entity.enums.Gander;
//import uz.pdp.animalshop.service.AnimalService;
//import uz.pdp.animalshop.service.AttachmentContentService;
//import uz.pdp.animalshop.service.AttachmentService;
//import uz.pdp.animalshop.service.CategoryService;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/animal")
//@RequiredArgsConstructor
//public class AnimalController {
//    private final AnimalService animalService;
//    private final AttachmentService attachmentService;
//    private final AttachmentContentService attachmentContentService;
//    private final CategoryService categoryService;
//
//    @GetMapping("/settings/get-animals")
//    public ResponseEntity<?> findAllAnimals() {
//        List<Animal> availableAnimals = animalService.findAnailableAnimals();
//        List<AnimalDTO> animalDTOS = new ArrayList<>();
//        List<byte[]> bytes = new ArrayList<>();
//
//        for (Animal animal : availableAnimals) {
//            Optional<Attachment> optionalAttachment = attachmentService.findById(animal.getId());
//            if (optionalAttachment.isPresent()) {
//                Attachment attachment = optionalAttachment.get();
//                Optional<AttachmentContent> optionalAttachmentContent = attachmentContentService.findAttachmentContentByAttachmentId(attachment.getId());
//                if (optionalAttachmentContent.isPresent()) {
//                    AttachmentContent attachmentContent = optionalAttachmentContent.get();
//                    bytes.add(attachmentContent.getContent());
//                    AnimalDTO animalDTO = new AnimalDTO();
//                    animalDTO.setId(animal.getId());
//                    animalDTO.setCategoryName(animal.getCategory().getName());
//                    animalDTO.setGander(animal.getGander());
//                    animalDTO.setImage(bytes);
//                    animalDTOS.add(animalDTO);
//                }
//            }
//        }
//        return ResponseEntity.ok().body(animalDTOS);
//    }
//
//    @PostMapping("/settings/save-animal")
//    public ResponseEntity<?> saveAnimal(@RequestBody Animal animal,
//                                        @RequestParam UUID categoryId,
//                                        @RequestParam(name = "images") List<MultipartFile> images,
//                                        @RequestParam String gander) {
//        if (images.isEmpty()) {
//            return ResponseEntity.badRequest().body("Not found any files");
//        }
//
//        List<Attachment> attachments = new ArrayList<>();
//        List<byte[]> bytes = new ArrayList<>();
//
//        Optional<Category> optionalCategory = categoryService.findById(categoryId);
//
//        AnimalDTO animalDTO = new AnimalDTO();
//
//        for (MultipartFile image : images) {
//            Attachment attachment = Attachment.builder()
//                    .name(image.getName())
//                    .size(image.getSize())
//                    .build();
//            attachmentService.save(attachment);
//
//            try {
//                if (optionalCategory.isPresent()) {
//                    Category category = optionalCategory.get();
//                    animalDTO.setCategoryName(category.getName());
//
//                    AttachmentContent attachmentContent = AttachmentContent.builder()
//                            .contentType(image.getContentType())
//                            .content(image.getInputStream().readAllBytes())
//                            .attachment(attachment)
//                            .build();
//                    attachmentContentService.save(attachmentContent);
//
//                    bytes.add(attachmentContent.getContent());
//
//                    attachments.add(attachment);
//
////                    animal.setAttachments(attachments);
//                    animal.setCategory(category);
//                    animal.setGander(Gander.valueOf(gander));
//                    animalDTO.setGander(Gander.valueOf(gander));
//                }
//
//                animalService.save(animal);
//
//
//            } catch (IOException e) {
//                return ResponseEntity.status(RuntimeException.class.getModifiers()).build();
//            }
//        }
//        animalDTO.setName(animal.getName());
//        animalDTO.setId(animal.getId());
//        animalDTO.setImage(bytes);
//
//        return ResponseEntity.ok().body(animalDTO);
//    }
//
//    @PutMapping("/settings/edit-animal")
//    public ResponseEntity<?> editAnimal(@RequestBody Animal animal,
//                                        @RequestParam UUID animalId,
//                                        @RequestParam(name = "images", required = false) List<MultipartFile> images,
//                                        @RequestParam String gander,
//                                        @RequestParam UUID categoryId) {
//
//        Optional<Animal> optionalAnimal = animalService.findById(animalId);
//        Optional<Category> optionalCategory = categoryService.findById(categoryId);
//
//        if (images.isEmpty()) {
//            return ResponseEntity.badRequest().body("Not found any files");
//        }
//        List<Attachment> attachments = new ArrayList<>();
//        List<byte[]> bytes = new ArrayList<>();
//        AnimalDTO animalDTO = new AnimalDTO();
//        animalDTO.setName(animal.getName());
//        animalDTO.setGander(Gander.valueOf(gander));
//
//        for (MultipartFile image : images) {
//            if (optionalAnimal.isPresent()) {
//                if (optionalCategory.isPresent()) {
//                    Category category = optionalCategory.get();
//                    animal.setCategory(category);
//                    animalDTO.setCategoryName(category.getName());
//                    Animal animal1 = optionalAnimal.get();
//                    animal.setId(animal1.getId());
//                    for (Attachment attachment : animal1.getAttachments()) {
//
//                        Optional<Attachment> optionalAttachment = attachmentService.findById(attachment.getId());
//                        if (optionalAttachment.isPresent()) {
//                            Attachment attachment1 = optionalAttachment.get();
//                            attachment1.setName(image.getName());
//                            attachment1.setSize(image.getSize());
//                            attachmentService.save(attachment1);
//                            attachments.add(attachment1);
//                            animal.setAttachments(attachments);
//                            animalService.save(animal);
//                            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentService.findAttachmentContentByAttachmentId(attachment.getId());
//                            if (optionalAttachmentContent.isPresent()) {
//                                AttachmentContent attachmentContent = optionalAttachmentContent.get();
//                                attachmentContent.setAttachment(attachment);
//                                try {
//                                    attachmentContent.setContent(image.getInputStream().readAllBytes());
//                                    bytes.add(image.getInputStream().readAllBytes());
//                                    animalDTO.setImage(bytes);
//                                    attachmentContent.setContentType(image.getContentType());
//                                    attachmentContentService.save(attachmentContent);
//                                } catch (IOException e) {
//                                    return ResponseEntity.status(RuntimeException.class.getModifiers()).build();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//        return ResponseEntity.ok().body(animalDTO);
//    }
//
//}
