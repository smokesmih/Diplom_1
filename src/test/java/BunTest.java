import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.Bun;

public class BunTest {

    private Bun bunCredential;

    @Before
    public void setup() {
        bunCredential = new Bun("black bun", 127);
    }

    @Test
    public void getNameTest() {
        setup();
        Assert.assertEquals("Возвращено неверное название Bun", "black bun", bunCredential.getName());
    }

    @Test
    public void getPriceTest() {
        setup();
        Assert.assertEquals("Возвращена неверная цена Bun", 127, bunCredential.getPrice(), 0.0001F);
    }

}
