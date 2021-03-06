import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Atom extends Atomic {
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
    public AtomCounter getAtoms() {
        return new AtomCounter(element, number);
    }
}
