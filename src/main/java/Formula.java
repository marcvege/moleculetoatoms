import java.util.Map;

public class Formula extends Atomic {

    protected Atomic first;
    protected Atomic rest;

    public Formula(String value) {
        super(value);
        parse(value);
    }

    private void parse(String value) {
        if (this.isEmpty()) return;
        first = Atom.extract(value).
                orElseGet(() -> SubFormula.extract(value).get());
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
