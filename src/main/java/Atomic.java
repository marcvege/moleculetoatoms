import java.util.Map;

public abstract class Atomic {
    protected String value;

    public Atomic(String value) {
        this.value = value;
    }

    abstract Map<String, Integer> getAtoms(Map<String, Integer> counter) throws Exception;


}
