import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

public class ParseMolecule {

    public static Map<String, Integer> getAtoms(String formula)  {
        return new Formula(formula).getAtoms(new HashMap<>());
    }

    public static abstract class Atomic {
        protected String value;

        public Atomic(String value) {
            this.value = value;
        }

        abstract Map<String, Integer> getAtoms(Map<String, Integer> counter) ;
    }

    public static class Formula extends Atomic {
        protected Atomic first;
        protected Atomic rest;

        public Formula(String value) {
            super(value);
            parse(value);
        }

        private void parse(String value) {
            if (this.isEmpty()) return;
            first = Atom.extract(value)
                    .orElseGet(() -> SubFormula.extract(value, '(', ')')
                            .orElseGet(() -> SubFormula.extract(value, '[', ']')
                                    .orElseGet(() -> SubFormula.extract(value, '{', '}')
                                            .orElseThrow(() -> new IllegalArgumentException()))));
            rest = new Formula(value.substring(first.value.length()));
        }

        public Map<String, Integer> getAtoms(Map<String, Integer> counter) {
            if (this.isEmpty()) return counter;
            return rest.getAtoms(first.getAtoms(counter));
        }

        public boolean isEmpty() {
            return value == null || value.length() == 0;
        }
    }

    public static class Atom extends Atomic {
        private static final Pattern p = Pattern.compile("^([A-Z][a-z]*)(\\d*)(.*)");
        protected String element;
        protected int number;

        public Atom(String value, String element, int number) {
            super(value);

            this.element = element;
            this.number = number;
        }

        public static Optional<Atomic> extract(String formula) {
            Matcher m = p.matcher(formula);
            if (m.matches()) {
                return Optional.of(parse(m));
            } else {
                return Optional.empty();
            }
        }

        private static Atom parse(Matcher m) {
            String value = m.group(1) + m.group(2);
            String element = m.group(1);
            int number = ("".equals(m.group(2))) ? 1 : Integer.parseInt(m.group(2));
            return new Atom(value, element, number);
        }

        @Override
        public Map<String, Integer> getAtoms(Map<String, Integer> counter) {
            Integer counterElement = counter.get(element);
            if (counterElement != null) {
                counter.put(element, counterElement + number);
            } else {
                counter.put(element, number);
            }
            return counter;
        }
    }

    private static final Map<String, Pattern> patternMap;

    static {
        patternMap = new HashMap<>();
        patternMap.put("(", Pattern.compile("^([(])(.*)([)])(\\d*)(.*)"));
        patternMap.put("{", Pattern.compile("^([{])(.*)([}])(\\d*)(.*)"));
        patternMap.put("[", Pattern.compile("^([\\[])(.*)([\\]])(\\d*)(.*)"));
    }

    public static class SubFormula extends Atomic {
        protected Atomic formula;
        protected int number;


        public SubFormula(String value, String formula, int number) {
            super(value);
            this.formula = new Formula(formula);
            this.number = number;
        }

        @Override
        Map<String, Integer> getAtoms(Map<String, Integer> counter)  {
            Map<String, Integer> counterFormula = formula.getAtoms(new HashMap<>());
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

}