package OOPSprint2;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RepanTest {

    Repan repan = new Repan();

    @Test
    void kontolleraFilExistens() {
        assertTrue(Files.exists(Path.of("src/OOPSprint2/customers.txt")));
        assertFalse(!Files.exists(Path.of("src/OOPSprint2/customers.txt")));
    }

    @Test
    void kontrolleraBetaldatum() {
        assertTrue(repan.inpasseringTidpunkt.equals(LocalDate.now()));
        assertFalse(!repan.inpasseringTidpunkt.equals(LocalDate.now()));
    }
}
