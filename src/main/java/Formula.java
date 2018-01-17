import java.util.HashMap;
import java.util.Map;

public class Formula {
    private String formula;

    public Formula(String formula) {
        this.formula = formula;
    }

    public Map<String,Integer> getAtoms(HashMap<String, Integer> counter) {
        if(this.isEmpty()) return counter;
//        return formula.rest().getAtoms(counter.add(formula.first().getAtoms()))
        return null;
    }

    public boolean isEmpty() {
        return formula == null || formula.length() == 0;
    }
}
