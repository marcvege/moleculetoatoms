import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    @DisplayName("Positives cases")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @CsvSource({"H2O, H:2|O:1",
                "H2OCl3, H:2|O:1|Cl:3",
                "H2(O3Cl)3, H:2|O:9|Cl:3",
                "H2(O3(Li2Hg)4Cl)3, H:2|O:9|Cl:3|Li:24|Hg:12",
                "H2(O3[Li2Hg]4Cl)3, H:2|O:9|Cl:3|Li:24|Hg:12",
                "H2(O3Cl)3(Li2Hg)4, H:2|O:9|Cl:3|Li:8|Hg:4",
                "H2[O3Cl]3(Li2Hg)4, H:2|O:9|Cl:3|Li:8|Hg:4",
                "B2H6, B:2|H:6",
                "C6H12O6, C:6|H:12|O:6",
                "Mo(CO)6, Mo:1|C:6|O:6",
                "Mg(OH)2, Mg:1|O:2|H:2",
                "Fe(C5H5)2, Fe:1|C:10|H:10",
                "(C5H5)Fe(CO)2CH3, C:8|H:8|Fe:1|O:2",
                "Pd[P(C6H5)3]4, P:4|C:72|Pd:1|H:60",
                "K4[ON(SO3)2]2, S:4|K:4|N:2|O:14",
                "As2{Be4C5[BCo3(CO2)3]2}4Cu5, As:2|B:8|Cu:5|Be:16|C:44|Co:24|O:48",
                "{[Co(NH3)4(OH)2]3Co}(SO4)3, S:3|H:42|Co:4|N:12|O:18",
                "{((H)2)[O]}, H:2|O:1"})
    void return_atoms_for_valid_formulas(String formula, String expectedCount) throws Exception {
        assertThat(ParseMolecule.getAtoms(formula), is(getAtoms(expectedCount)));
    }

    private Map<String, Integer> getAtoms(String atomsCount) {
        return Arrays.stream(atomsCount.split("\\|"))
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(s -> s[0], s -> Integer.parseInt(s[1])));
    }

    @DisplayName("Negative cases")
    @ParameterizedTest(name = "\"{0}\" should throw an exception")
    @CsvSource({"pie",
                "cl",
                "Cl[H2O)",
                "Mg(OH",
                "MgOH)2",
                "Mg(OH]2",
                "Au5(C2H5[OH)3Li]3"})
    void throw_exception_if_no_contains_a_formula(String badFormula) throws Exception {
        assertThrows(IllegalArgumentException.class, () -> ParseMolecule.getAtoms(badFormula));
    }
}