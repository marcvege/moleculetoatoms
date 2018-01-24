import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

public class SubFormula extends Atomic {
    private static final Map<String, Pattern> patternMap;
    static
    {
        patternMap = new HashMap<>();
        patternMap.put("(",Pattern.compile("^([(])(.*)([)])(\\d*)(.*)"));
        patternMap.put("{",Pattern.compile("^([{])(.*)([}])(\\d*)(.*)"));
        patternMap.put("[",Pattern.compile("^([\\[])(.*)([\\]])(\\d*)(.*)"));
    }
    protected String formula;
    protected int number;


    public SubFormula(String value, String formula, int number) {
        super(value);
        this.formula = formula;
        this.number = number;
    }

    @Override
    Map<String, Integer> getAtoms(Map<String, Integer> counter) throws Exception {
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

    public static Optional<Atomic> extract(String formula, char open, char close) {
        String subformula = getSubFormula(formula, open, close);
        Matcher m = patternMap.get(valueOf(open)).matcher(subformula);
        if (m.matches()) {
            return Optional.of(parse(m));
        } else {
            return Optional.empty();
        }
    }

    private static String getSubFormula(String formula, char open, char close) {
        int index = 1;
        char firstChar = formula.charAt(0);
        if (open == firstChar) {
            int opens = 1;
            while (index < formula.length() && opens > 0) {
                char c = formula.charAt(index);
                if (c == open) opens++;
                if (c == close) opens--;
                index++;
            }
            while (index < formula.length() && Character.isDigit(formula.charAt(index))) index++;
        }

        return formula.substring(0, index);
    }

    private static SubFormula parse(Matcher m) {
        String value = m.group(1) + m.group(2) + m.group(3) + m.group(4);
        String formula = m.group(2);
        int number = ("".equals(m.group(4))) ? 1 : Integer.parseInt(m.group(4));
        return new SubFormula(value, formula, number);
    }
}
