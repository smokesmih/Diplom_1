import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private Burger burger;

    @Mock
    Bun bun;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunTest() {
        burger.setBuns(bun);
        Assert.assertEquals("Метод setBuns() отработал неверно", bun, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        Ingredient ingredient = Mockito.mock(Ingredient.class);
        burger.addIngredient(ingredient);
        Assert.assertEquals("Ошибка в работе метода addIngredient",1,burger.ingredients.size());
    }

    @Test
    public void removeIngredientTest() {
        Ingredient ingredient = Mockito.mock(Ingredient.class);
        burger.addIngredient(ingredient);
        burger.removeIngredient(0);
        Assert.assertEquals("Ошибка в работе метода removeIngredient",0,burger.ingredients.size());
    }

    @Test
    public void moveIngredientTest() {
        Ingredient ingredient0 = Mockito.mock(Ingredient.class);
        Ingredient ingredient1 = Mockito.mock(Ingredient.class);
        burger.addIngredient(ingredient0);
        burger.addIngredient(ingredient1);
        burger.moveIngredient(0, 1);
        Assert.assertEquals("Ошибка в работе метода moveIngredient",ingredient0,burger.ingredients.get(1));
    }

    @Test
    public void getPriceTest() {
        burger.bun = bun;
        Mockito.when(bun.getPrice()).thenReturn(77.5F);
        Ingredient ingredientSauce = Mockito.mock(Ingredient.class);
        Ingredient ingredientFilling = Mockito.mock(Ingredient.class);
        burger.addIngredient(ingredientSauce);
        burger.addIngredient(ingredientFilling);
        Mockito.when(ingredientFilling.getPrice()).thenReturn(180.3F);
        Mockito.when(ingredientSauce.getPrice()).thenReturn(40F);
        Assert.assertEquals("Ошибка в работе метода Burger.getPrice", 77.5F * 2 + 180.3F + 40F, burger.getPrice(), 0.0001F);
    }

    @Test
    public void getReceiptTest() {
        Ingredient ingredientSecond = Mockito.mock(Ingredient.class);
        Burger burger = new Burger();
        Burger burgerSpy = Mockito.spy(burger);
        burgerSpy.bun = bun;
        burgerSpy.ingredients.add(ingredientSecond);
        when(bun.getName()).thenReturn("Кунжутная булочка");
        when(bun.getPrice()).thenReturn(23F);
        when(ingredientSecond.getName()).thenReturn("Meat");
        when(ingredientSecond.getPrice()).thenReturn(120.5F);
        when(ingredientSecond.getType()).thenReturn(IngredientType.FILLING);

        String expectedReceipt = String.format(
                "(==== %s ====)%n" +
                        "= %s %s =%n" +
                        "(==== %s ====)%n" +
                        "%nPrice: %f%n",
                bun.getName(),
                ingredientSecond.getType().toString().toLowerCase(), ingredientSecond.getName(),
                bun.getName(),
                burgerSpy.getPrice());

        Assert.assertEquals("Ошибка в работе метода Burger.getReceipt", expectedReceipt, burgerSpy.getReceipt());
    }

}
