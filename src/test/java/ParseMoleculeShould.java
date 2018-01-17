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
}