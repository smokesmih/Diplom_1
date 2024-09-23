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

import java.util.List;

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
        List<Ingredient> ingredients = Mockito.mock(List.class);
        Ingredient ingredient = Mockito.mock(Ingredient.class);
        burger.ingredients = ingredients;  // Приходится указывать явно,  из-за исходного кода, есть ли возможность менять код?
        burger.addIngredient(ingredient);
        Mockito.verify(ingredients, Mockito.times(1)).add(ingredient);
    }

    @Test
    public void removeIngredientTest() {
        List<Ingredient> ingredients = Mockito.mock(List.class);
        burger.ingredients = ingredients;
        int random = (int) (Math.random() * 10);
        burger.removeIngredient(random);
        Mockito.verify(ingredients, Mockito.times(1)).remove(random);
    }

    @Test
    public void moveIngredientTest() {
        List<Ingredient> ingredients = Mockito.mock(List.class);
        burger.ingredients = ingredients;
        burger.moveIngredient(1, 4);
        Mockito.verify(ingredients, Mockito.times(1)).add(4, ingredients.remove(1));
    }

    @Test
    public void getPriceTest() {
        burger.bun = bun;
        Mockito.when(bun.getPrice()).thenReturn(77.5F);
        Ingredient ingredientSauce = Mockito.mock(Ingredient.class);
        Ingredient ingredientFilling = Mockito.mock(Ingredient.class);
        burger.ingredients.add(ingredientSauce);
        burger.ingredients.add(ingredientFilling);
        Mockito.when(ingredientFilling.getPrice()).thenReturn(180.3F);
        Mockito.when(ingredientSauce.getPrice()).thenReturn(40F);
        Assert.assertEquals("Ошибка в работе метода Burger.getPrice", 77.5F * 2 + 180.3F + 40F, burger.getPrice(), 0.0001F);
    }

    @Test
    public void getReceiptTest() {
        Ingredient ingredientFirst = Mockito.mock(Ingredient.class);
        Ingredient ingredientSecond = Mockito.mock(Ingredient.class);
        Ingredient ingredientThird = Mockito.mock(Ingredient.class);
        // Так как придется вызвать метод getPriceTest() этого же класса
        Burger burgerSpy = Mockito.spy(Burger.class);

        burgerSpy.bun = bun;
        burgerSpy.ingredients.add(ingredientFirst);
        burgerSpy.ingredients.add(ingredientSecond);
        burgerSpy.ingredients.add(ingredientThird);

        when(bun.getName()).thenReturn("Кунжутная булочка");
        when(bun.getPrice()).thenReturn(23F);

        when(ingredientFirst.getName()).thenReturn("Pesto");
        when(ingredientFirst.getPrice()).thenReturn(12F);
        when(ingredientFirst.getType()).thenReturn(IngredientType.SAUCE);

        when(ingredientSecond.getName()).thenReturn("Meat");
        when(ingredientSecond.getPrice()).thenReturn(120.5F);
        when(ingredientSecond.getType()).thenReturn(IngredientType.FILLING);

        when(ingredientThird.getName()).thenReturn("Tabasco");
        when(ingredientThird.getPrice()).thenReturn(17F);
        when(ingredientThird.getType()).thenReturn(IngredientType.SAUCE);

        String expectedReceipt = String.format(
                "(==== %s ====)%n" +
                        "= %s %s =%n" +
                        "= %s %s =%n" +
                        "= %s %s =%n" +
                        "(==== %s ====)%n" +
                        "%nPrice: %f%n",
                bun.getName(),
                ingredientFirst.getType().toString().toLowerCase(), ingredientFirst.getName(),
                ingredientSecond.getType().toString().toLowerCase(), ingredientSecond.getName(),
                ingredientThird.getType().toString().toLowerCase(), ingredientThird.getName(),
                bun.getName(),
                burgerSpy.getPrice());

        Assert.assertEquals("Ошибка в работе метода Burger.getReceipt", expectedReceipt, burgerSpy.getReceipt());
    }

}
