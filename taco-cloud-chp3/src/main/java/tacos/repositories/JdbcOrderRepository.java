package tacos.repositories;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacos.domain.IngredientRef;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private JdbcOperations jdbcOperations ;

    JdbcOrderRepository(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }
    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco_Order (delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv, placed_at) "
                        + "values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );

        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        tacoOrder.setPlacedAt(new Date());
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(Arrays.asList(
                tacoOrder.getDeliveryName(),
                tacoOrder.getDeliveryStreet(),
                tacoOrder.getDeliveryCity(),
                tacoOrder.getDeliveryState(),
                tacoOrder.getDeliveryZip(),
                tacoOrder.getCcNumber(),
                tacoOrder.getCcExpiration(),
                tacoOrder.getCcCVV(),
                tacoOrder.getPlacedAt()));

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder() ;
        jdbcOperations.update(preparedStatementCreator, generatedKeyHolder);
        long orderId = generatedKeyHolder.getKey().longValue();
        List<Taco>  tacos  = tacoOrder.getTacos();
        int i = 0;
        for(Taco taco : tacos){
            saveTaco(orderId, i++ ,taco) ;
        }
        return tacoOrder;
    }

    private long saveTaco(long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, created_at, taco_order, taco_order_key) values (?, ?, ?, ?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
        );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
                Arrays.asList( taco.getName(), taco.getCreatedAt(), orderId, orderKey));
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder() ;
        jdbcOperations.update(preparedStatementCreator, generatedKeyHolder);
        long tacoId = generatedKeyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId,taco.getIngredients());
        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientsRefs) {
        int key =0 ;
        for (IngredientRef ingredientRef: ingredientsRefs) {
            jdbcOperations.update("insert into Ingredient_Ref (ingredient, taco, taco_key) values (?, ?, ?)",
                    ingredientRef.getIngredient().getId(), tacoId, key++);
        }
    }

}
