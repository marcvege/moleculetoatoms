import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AtomShould {
    @Test
    void create_an_empty_element() {
        Atom atom = (Atom) Atom.extract("O").get();
        assertThat(atom).isEqualToComparingFieldByField(new Atom("O","O", 1));
    }

    @Test
    void create_an_numeral_element() {
        Atom atom = (Atom) Atom.extract("Li2").get();
        assertThat(atom).isEqualToComparingFieldByField(new Atom("Li2","Li", 2));
    }

    @Test
    void extract_atom_when_formula_start_with_atom() {
        Atom atom = (Atom) Atom.extract("H2O").get();
        assertThat(atom).isEqualToComparingFieldByField(new Atom("H2", "H", 2));
    }
}