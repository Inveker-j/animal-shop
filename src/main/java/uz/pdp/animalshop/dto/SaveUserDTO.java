package uz.pdp.animalshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.animalshop.entity.Role;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SaveUserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String rePassword;
}
