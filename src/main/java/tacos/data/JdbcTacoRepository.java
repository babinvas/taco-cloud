package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbc;

	// @Autowired
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		// TODO by the book
		//  (Ingredient ingredient : taco.getIngredients())
		for (String ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}

		return null;
	}

	private long saveTacoInfo(Taco taco) {
		return 0;
	}

	// TODO by the book
	//  private void saveIngredientToTaco(Ingredient ingredient, long tacoId)
	private void saveIngredientToTaco(String ingredient, long tacoId) {
	}
}
