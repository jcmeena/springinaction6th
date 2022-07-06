package tacos.domain.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.domain.IngredientRef;

//@Component
public class IngredientRefByIdConverter{
//        implements Converter<String, IngredientRef> {

    private IngredientByIdConverter ingredientByIdConverter;
    IngredientRefByIdConverter(IngredientByIdConverter ingredientByIdConverter){
      //  this.ingredientByIdConverter =ingredientByIdConverter;
    }
//    @Override
//    public IngredientRef convert(String source) {
//      IngredientRef ref = new IngredientRef();
//     //   ref.setIngredient(ingredientByIdConverter.convert(source));
//        return ref;
//    }
}
