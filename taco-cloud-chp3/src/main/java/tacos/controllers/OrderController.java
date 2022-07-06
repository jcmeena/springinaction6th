package tacos.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.domain.TacoOrder;
import tacos.repositories.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderRepository orderRepository;
    @Autowired
    OrderController(OrderRepository orderRepository){
        this.orderRepository =orderRepository;
    }
    @GetMapping("current")
    public String orderForm(){
        log.info("orderForm method called ");
        return "orderForm";
    }
    @PostMapping()
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus){
        if(errors.hasErrors()){
            log.info("Errors in process order : {}" , errors);
            return "orderForm";
        }
        log.info("order submitted: {} ", tacoOrder);
        orderRepository.save(tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
