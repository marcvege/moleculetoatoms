import java.util.Map;

public class ParseMolecule {

    public static Map<String,Integer> getAtoms(String formula) throws Exception {
        return new Formula(formula).getAtoms().toMap();
    }

}