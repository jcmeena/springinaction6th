package tacos.repositories;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.Ingredient;

import java.util.Optional;

public interface IngredientRepository  extends CrudRepository<Ingredient, String> {


}
