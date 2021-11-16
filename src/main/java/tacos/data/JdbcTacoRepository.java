package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbc;

	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);

		/*
		By the book
		(Ingredient ingredient : taco.getIngredients())
		 */
		for (String ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}

		return taco;
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());

		/*
		By the book:

		PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)",
				Types.VARCHAR, Types.TIMESTAMP)
				.newPreparedStatementCreator(
						Arrays.asList(
								taco.getName(),
								new Timestamp(taco.getCreatedAt().getTime())));

		But, it throws out the NullPointerException.
		 */

		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)",
				Types.VARCHAR, Types.TIMESTAMP);

		// There are two options for kkk to return id,
		// or you can apply them together
		// the first one:
		pscf.setReturnGeneratedKeys(true);
		// the second one:
		pscf.setGeneratedKeysColumnNames("id");

		PreparedStatementCreator psc = pscf
				.newPreparedStatementCreator(
						Arrays.asList(
								taco.getName(),
								new Timestamp(taco.getCreatedAt().getTime())));

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);

		return keyHolder.getKey().longValue();
	}

	/*
	By the book:
	private void saveIngredientToTaco(Ingredient ingredient, long tacoId)
	 */
	private void saveIngredientToTaco(String ingredient, long tacoId) {

		/*
		By the book
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)", tacoId, ingredient.getId())
		 */
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
				tacoId, ingredient);
	}
}
