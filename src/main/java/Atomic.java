public abstract class Atomic {
    protected String value;

    public Atomic(String value) {
        this.value = value;
    }

    abstract AtomCounter getAtoms();


}
