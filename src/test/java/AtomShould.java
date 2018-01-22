import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class AtomShould {
    @Test
    void create_an_empty_element() {
        Atom atom = new Atom("O");
        assertThat(atom.element, is("O"));
        assertThat(atom.number, is(1));
    }
    @Test
    void create_an_numered_element() {
        Atom atom = new Atom("Li2");
        assertThat(atom.element, is("Li"));
        assertThat(atom.number, is(2));
    }
}