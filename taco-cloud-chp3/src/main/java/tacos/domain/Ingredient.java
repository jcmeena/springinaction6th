package tacos.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Ingredient implements Serializable {

    public enum Type{
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }
    private final String id;
    private final String name;
    private final Type type;

}
