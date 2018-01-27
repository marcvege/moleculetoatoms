import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormulaShould {
    @Test
    void parse_H2() throws Exception {
        Formula formula = new Formula("H2");
        assertThat(formula.first).isEqualToComparingFieldByField(new Atom("H2","H", 2));
        assertThat(formula.rest).isEqualToComparingFieldByField(new Formula(""));
    }

    @Test
    void parse_H2O() throws Exception {
        Formula formula = new Formula("H2O");
        assertThat(formula.first).isEqualToComparingFieldByField(new Atom("H2", "H", 2));
        assertThat(formula.rest).isEqualToComparingFieldByFieldRecursively(new Formula("O"));
    }

    @Test
    void with_formula() throws Exception {
        Formula formula = new Formula("H2(O2(Cl3H2O))3");
        assertThat(formula.first).isEqualToComparingFieldByField(new Atom("H2", "H", 2));
        assertThat(formula.rest).isEqualToComparingFieldByFieldRecursively(new Formula("(O2(Cl3H2O))3"));
    }

    @Test
    void with_formula_with_brackets() throws Exception {
        Formula formula = new Formula("H2[O2[Cl3H2O]]3");
        assertThat(formula.first).isEqualToComparingFieldByField(new Atom("H2", "H", 2));
        assertThat(formula.rest).isEqualToComparingFieldByFieldRecursively(new Formula("[O2[Cl3H2O]]3"));
    }
}