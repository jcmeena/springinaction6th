package tacos.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Taco {
    @NotNull
    @Size(min=5 , message = "the name must be at least 5 characters long")
    private String name;
    @NotNull
    @Size(min=1, message = "at least one ingredient must be selected")
    private List<Ingredient> ingredients;
}
