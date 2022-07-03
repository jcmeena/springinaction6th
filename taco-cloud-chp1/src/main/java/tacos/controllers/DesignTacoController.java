package tacos.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredientList = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type: types) {
            // here you have created request attributes
            // wrap -- list of ingredients of type wrap
            // protein -- list of ingredients of type protein
            // veggies -- list of ingredients of type veggies
            // cheese -- list of ingredients of type cheese
            // sauce -- list of ingredients of type sauce
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientList,type));
        }
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(x-> x.getType().equals(type)).collect(Collectors.toList());
    }

    @ModelAttribute(name="tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name="taco")
    public Taco taco(){
        return new Taco();
    }

    @PostMapping
    public String processTaco(@Valid Taco taco , Errors errors, @ModelAttribute TacoOrder tacoOrder){
        if(errors.hasErrors()){
            log.info("Errors in processTaco :{} ", errors);
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco :{} ", taco);
        log.info("Processing tacoOrder :{} ", tacoOrder);
        return "redirect:/orders/current";

    }
}
