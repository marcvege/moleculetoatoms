import java.util.HashMap;
import java.util.Map;

public class ParseMolecule {

    public static Map<String,Integer> getAtoms(String formula) {
        return new Formula(formula).getAtoms(new HashMap<String, Integer> ());
    }

}