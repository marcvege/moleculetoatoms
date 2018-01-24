import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class SubFormulaShould {
    @Test
    void parse_an_empty_element() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O)",'(', ')').get();
        assertThat(subformula.formula, is("O"));
        assertThat(subformula.number, is(1));
        assertThat(subformula.value, is("(O)"));
    }

    @Test
    void parse_a_valued_element() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O2)3",'(', ')').get();
        assertThat(subformula.formula, is("O2"));
        assertThat(subformula.number, is(3));
        assertThat(subformula.value, is("(O2)3"));
    }

    @Test
    void parse_an_element_with_parentesis() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O2(Cl3H2O))3",'(', ')').get();
        assertThat(subformula.formula, is("O2(Cl3H2O)"));
        assertThat(subformula.number, is(3));
        assertThat(subformula.value, is("(O2(Cl3H2O))3"));
    }

    @Test
    void parse_two_subformulas() {
        SubFormula subformula = (SubFormula) SubFormula.extract("(O2(Cl3H2O))3(H2O",'(', ')').get();
        assertThat(subformula.formula, is("O2(Cl3H2O)"));
        assertThat(subformula.number, is(3));
        assertThat(subformula.value, is("(O2(Cl3H2O))3"));
    }

    @Test
    void parse_an_element_with_brackets() {
        SubFormula subformula = (SubFormula) SubFormula.extract("[O2[Cl3H2O]]3",'[', ']').get();
        assertThat(subformula.formula, is("O2[Cl3H2O]"));
        assertThat(subformula.number, is(3));
        assertThat(subformula.value, is("[O2[Cl3H2O]]3"));
    }

    @Test
    void parse_an_element_with_claudators() {
        SubFormula subformula = (SubFormula) SubFormula.extract("{O2{Cl3H2O}}3",'{', '}').get();
        assertThat(subformula.formula, is("O2{Cl3H2O}"));
        assertThat(subformula.number, is(3));
        assertThat(subformula.value, is("{O2{Cl3H2O}}3"));
    }
}