import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Atom extends Atomic {
    private static final Pattern p = Pattern.compile("^([a-zA-Z]+)(\\d*)");
    protected String element;
    protected int number = 1;

    public Atom(String value) {
        super(value);
        parse(value);
    }

    private void parse(String value) {
        Matcher m = p.matcher(value);
        if (m.matches()) {
            element = m.group(1);
            if (!"".equals(m.group(2))) number = Integer.parseInt(m.group(2));
        } else {
            throw new RuntimeException("Atom doesn't match");
        }
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
