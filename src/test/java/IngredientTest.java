import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Ingredient;
import praktikum.IngredientType;

@RunWith(Parameterized.class)
public class IngredientTest {

    private IngredientType type;
    private String name;
    private float price;
    private Ingredient ingredient;

    public IngredientTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }


    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][]{
                {IngredientType.SAUCE, "Pesto", 12},
                {IngredientType.FILLING, "Meat", 120.5F},
        };
    }

    @Before
    public void setup() {
        ingredient = new Ingredient(type, name, price);
    }

    @Test
    public void getTypeTest() {
        Assert.assertEquals("Ошибка в возврате IngredientType", type, ingredient.getType());
    }

    @Test
    public void getNameTest() {
        Assert.assertEquals("Ошибка в возврате названия ингридиета", name, ingredient.getName());
    }

    @Test
    public void getPriceTest() {
        Assert.assertEquals("Ошибка в возврате цены ингридиета", price, ingredient.getPrice(), 0.0001F);

    }


}
