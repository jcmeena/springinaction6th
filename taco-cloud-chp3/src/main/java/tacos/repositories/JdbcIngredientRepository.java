package tacos.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.domain.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcIngredientRepository implements  IngredientRepository{

    private JdbcTemplate jdbcTemplate;
    // note here @Autowired is not necessary because there is only one constructor.
    @Autowired
    JdbcIngredientRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        log.info("in findALL " );
        return jdbcTemplate.query("select id , name , type from ingredient" , this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        log.info("in findById " );
        List<Ingredient> results = jdbcTemplate.query("select id,name, type from ingredient where id  =?" ,
                this::mapRowToIngredient,id);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        log.info("in findById " );
        jdbcTemplate.update("inset into ingredient (id , name, type) values (? , ? ,?) ",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row , int rowNum) throws SQLException {
        return new Ingredient( row.getString("id") ,
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type"))
        ) ;
    }
}
