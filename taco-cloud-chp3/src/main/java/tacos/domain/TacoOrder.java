package tacos.domain;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrder {

    private Long id;
    private Date placedAt = new Date();

    @NotBlank(message = "delivery name is required")
    private String deliveryName;

    @NotBlank(message = "delivery street is required")
    private String deliveryStreet;

    @NotBlank(message = "delivery city is required")
    private String deliveryCity;

    @NotBlank(message = "delivery state is required")
    private String deliveryState;

    @NotBlank(message = "delivery zip is required")
    private String deliveryZip;

    //@CreditCardNumber(message = "not a valid credit card number")
    @NotBlank(message = "not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([2-9][0-9])$")
    private String ccExpiration;

    @Digits(fraction = 0 ,integer = 3 , message = "Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }
}
