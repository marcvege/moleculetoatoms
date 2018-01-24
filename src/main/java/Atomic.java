import java.util.Map;
import java.util.Objects;

public abstract class Atomic {
    protected String value;

    public Atomic(String value) {
        this.value = value;
    }

    abstract Map<String, Integer> getAtoms(Map<String, Integer> counter) throws Exception;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atomic atomic = (Atomic) o;
        return Objects.equals(value, atomic.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
