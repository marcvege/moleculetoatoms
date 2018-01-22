import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FormulaShould {
    @Test
    void parse_H2() {
        Formula formula = new Formula("H2");
        assertThat(formula.first, is((Atomic)new Atom("H2","H", 2)));
        assertThat(formula.rest, is((Atomic)new Formula("")));
    }

    @Test
    void parse_H2O() {
        Formula formula = new Formula("H2O");
        assertThat(formula.first, is((Atomic)new Atom("H2", "H", 2)));
        assertThat(formula.rest, is((Atomic)new Formula("O")));
    }
}