import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class AtomShould {
    @Test
    void create_an_empty_element() {
        Atom atom = (Atom) Atom.extract("O").get();
        assertThat(atom.element, is("O"));
        assertThat(atom.number, is(1));
    }

    @Test
    void create_an_numered_element() {
        Atom atom = (Atom) Atom.extract("Li2").get();
        assertThat(atom.element, is("Li"));
        assertThat(atom.number, is(2));
    }

    @Test
    void extract_atom_when_formula_start_with_atom() {
        Atom atom = (Atom) Atom.extract("H2O").get();
        assertThat(atom, is(new Atom("H2", "H", 2)));
    }
}