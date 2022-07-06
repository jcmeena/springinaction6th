package tacos.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table
public class Ingredient implements Persistable<String> {

    @Override
    public boolean isNew() {
        return true;
    }

    public enum Type{
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }
    @Id
    private  String id;
    private  String name;
    private  Type type;

}
