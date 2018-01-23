import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubFormula extends Atomic{
    private static final Pattern p = Pattern.compile("^([(])(.*)([)])(\\d*)(.*)");
    protected String formula;
    protected int number;


    public SubFormula(String value, String formula, int number) {
        super(value);
        this.formula = formula;
        this.number = number;
    }

    @Override
    Map<String, Integer> getAtoms(Map<String, Integer> counter) {
        Map<String, Integer> counterFormula = (new Formula(formula)).getAtoms(new HashMap<>());
        counterFormula.forEach((key, value) -> {
            Integer counterKey = counter.get(key);
            if (counterKey != null) {
                counter.put(key, counterKey + value * number);
            } else {
                counter.put(key, value * number);
            }
        });

        return counter;
    }

    public static Optional<SubFormula> extract(String formula) {
        Matcher m = p.matcher(formula);
        if (m.matches()) {
            return Optional.of(parse(m));
        } else {
            return Optional.empty();
        }
    }

    private static SubFormula parse(Matcher m) {
        String value = m.group(1) + m.group(2) + m.group(3) + m.group(4);
        String formula = m.group(2);
        int number = ("".equals(m.group(4))) ? 1 : Integer.parseInt(m.group(4));
        return new SubFormula(value, formula, number);
    }
}
