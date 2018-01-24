import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParseMoleculeShould {
    @Test
    void return_empty_map_if_formula_is_empty() throws Exception {
        Map<String, Integer> value = ParseMolecule.getAtoms("");
        assertThat(value.size(), is(0));
    }

    @Test
    void return_empty_map_if_formula_is_null() throws Exception {
        Map<String, Integer> value = ParseMolecule.getAtoms(null);
        assertThat(value.size(), is(0));
    }

    @Test
    void parse_H2O() throws Exception {
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("H", 2);
                put("O", 1);
            }
        };
        Map<String, Integer> value = ParseMolecule.getAtoms("H2O");
        assertThat(value, is(expected));
    }

    @Test
    void parse_H2OCl3() throws Exception {
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("H", 2);
                put("O", 1);
                put("Cl", 3);
            }
        };
        Map<String, Integer> value = ParseMolecule.getAtoms("H2OCl3");
        assertThat(value, is(expected));
    }


    @Test
    void parse_with_sub_formula() throws Exception {
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("H", 2);
                put("O", 9);
                put("Cl", 3);
            }
        };
        Map<String, Integer> value = ParseMolecule.getAtoms("H2(O3Cl)3");
        assertThat(value, is(expected));
    }

    @Test
    void parse_with_nested_subformulas() throws Exception {
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("H", 2);
                put("O", 9);
                put("Cl", 3);
                put("Li", 24);
                put("Hg", 12);
            }
        };
        Map<String, Integer> value = ParseMolecule.getAtoms("H2(O3(Li2Hg)4Cl)3");
        assertThat(value, is(expected));
    }

    @Test
    void parse_with_nested_subformulas_mixed() throws Exception {
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("H", 2);
                put("O", 9);
                put("Cl", 3);
                put("Li", 24);
                put("Hg", 12);
            }
        };
        Map<String, Integer> value = ParseMolecule.getAtoms("H2(O3[Li2Hg]4Cl)3");
        assertThat(value, is(expected));
    }

    @Test
    void parse_with_two_subformulas() throws Exception {
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("H", 2);
                put("O", 9);
                put("Cl", 3);
                put("Li", 8);
                put("Hg", 4);
            }
        };
        Map<String, Integer> value = ParseMolecule.getAtoms("H2(O3Cl)3(Li2Hg)4");
        assertThat(value, is(expected));
    }

    @Test
    void parse_with_two_subformulas_brackets() throws Exception {
        Map<String, Integer> expected = new HashMap<String, Integer>() {
            {
                put("H", 2);
                put("O", 9);
                put("Cl", 3);
                put("Li", 8);
                put("Hg", 4);
            }
        };
        Map<String, Integer> value = ParseMolecule.getAtoms("H2[O3Cl]3(Li2Hg)4");
        assertThat(value, is(expected));
    }

    @Test
    void throw_exception_if_no_formula() throws Exception {
        Executable parsing = () -> ParseMolecule.getAtoms("pie");
        assertThrows(IllegalArgumentException.class, parsing);

         parsing = () -> ParseMolecule.getAtoms("cl");
        assertThrows(IllegalArgumentException.class, parsing);

         parsing = () -> ParseMolecule.getAtoms("Cl[H2O)");
        assertThrows(IllegalArgumentException.class, parsing);
    }
}