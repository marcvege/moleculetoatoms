import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubFormulaShould {
    @Test
    void parse_an_empty_element() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O)", '(', ')').get();
        assertThat(subformula).isEqualToComparingFieldByField(new SubFormula("(O)", "O", 1));
    }

    @Test
    void parse_a_valued_element() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O2)3", '(', ')').get();
        assertThat(subformula).isEqualToComparingFieldByField(new SubFormula("(O2)3", "O2", 3));
    }

    @Test
    void parse_an_element_with_parentesis() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O2(Cl3H2O))3", '(', ')').get();
        assertThat(subformula).isEqualToComparingFieldByField(new SubFormula("(O2(Cl3H2O))3", "O2(Cl3H2O)", 3));
    }

    @Test
    void parse_two_subformulas() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O2(Cl3H2O))3(H2O", '(', ')').get();
        assertThat(subformula).isEqualToComparingFieldByField(new SubFormula("(O2(Cl3H2O))3", "O2(Cl3H2O)", 3));
    }

    @Test
    void parse_an_element_with_brackets() {
        SubFormula subformula = (SubFormula) SubFormula.extract("[O2[Cl3H2O]]3", '[', ']').get();
        assertThat(subformula).isEqualToComparingFieldByField(new SubFormula("[O2[Cl3H2O]]3", "O2[Cl3H2O]", 3));
    }

    @Test
    void parse_an_element_with_claudators() {
        SubFormula subformula = (SubFormula) SubFormula.extract("{O2{Cl3H2O}}3", '{', '}').get();
        assertThat(subformula).isEqualToComparingFieldByField(new SubFormula("{O2{Cl3H2O}}3", "O2{Cl3H2O}", 3));
    }
}