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
        int limitAtom = 1;
        while (limitAtom < value.length() && !Character.isUpperCase(value.charAt(limitAtom))) {
            limitAtom++;
        }
        first = new Atom(value.substring(0, limitAtom));
        rest = new Formula(value.substring(limitAtom));
    }

    public Map<String, Integer> getAtoms(Map<String, Integer> counter) {
        if (this.isEmpty()) return counter;
        return rest.getAtoms(first.getAtoms(counter));
    }

    public String getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value == null || value.length() == 0;
    }
}
