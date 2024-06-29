package uz.pdp.animalshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SaveUserDTO implements UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String rePassword;

    @Override
    public UUID id() {
        return this.id;
    }

    @Override
    public String firstName() {
        return this.firstName;
    }

    @Override
    public String lastName() {
        return this.lastName;
    }

    @Override
    public String email() {
        return this.email;
    }

    @Override
    public String phone() {
        return this.password;
    }

}
