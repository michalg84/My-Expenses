package dev.galka.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    @NotNull(message = "Please insert name")
    @Size(min = 1, max = 25, message = "Category name has to be 3-25 signs")
    private String name;


}
