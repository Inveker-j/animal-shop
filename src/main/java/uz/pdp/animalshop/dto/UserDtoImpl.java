package uz.pdp.animalshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDtoImpl implements UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
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
        return null;
    }

}
