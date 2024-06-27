package uz.pdp.animalshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentException {
    private Integer status;
    private String message;
    private String url;
}
