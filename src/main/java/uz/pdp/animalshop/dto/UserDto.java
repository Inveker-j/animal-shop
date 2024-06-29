package uz.pdp.animalshop.dto;

import java.util.UUID;

public interface UserDto {
    UUID id();

    String firstName();

    String lastName();

    String email();

    String phone();

}
