import java.util.HashMap;
import java.util.Map;

public class ParseMolecule {

    public static Map<String,Integer> getAtoms(String formula) {
        return getAtoms(new Formula(formula), new HashMap<String, Integer> ());
    }

    private static Map<String,Integer> getAtoms(Formula formula, HashMap<String, Integer> counter) {
        if(formula.isEmpty()) return counter;
        return null;
    }

}