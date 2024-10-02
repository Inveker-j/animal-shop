package uz.pdp.animalshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
    private UUID messageId;
    private String message;
    private Long latitude;
    private Long longitude;
    private UUID toId;
}
