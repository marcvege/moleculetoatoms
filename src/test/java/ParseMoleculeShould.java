import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParseMoleculeShould {
    @Test
    void return_empty_map_if_formula_is_empty() {
        Map<String, Integer> value = ParseMolecule.getAtoms("");
        assertThat(value.size(), is(0));
    }

    @Test
    void return_empty_map_if_formula_is_null() {
        Map<String, Integer> value = ParseMolecule.getAtoms(null);
        assertThat(value.size(), is(0));
    }

    @Test
    void parse_H2O() {
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
    void parse_H2OCl3() {
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
    void parse_with_sub_formula() {
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
    void parse_with_nested_subformulas() {
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
    void parse_with_two_subformulas() {
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
}