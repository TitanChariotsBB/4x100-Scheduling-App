import org.junit.jupiter.api.Test;

class loadCatalogTest {

    @Test
    void loadCatalog() {
        Main.loadCatalog();
        System.out.println(Main.getCatalog());
    }
}