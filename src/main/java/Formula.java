public class Formula extends Atomic {

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


    public AtomCounter getAtoms()  {
        if (this.isEmpty()) return new AtomCounter();
        return rest.getAtoms().add(first.getAtoms());
    }

    public boolean isEmpty() {
        return value == null || value.length() == 0;
    }
}
