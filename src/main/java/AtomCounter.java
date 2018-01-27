import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AtomCounter {
    Map<String, Integer> counterMap;

    public AtomCounter(String element, int number) {
        counterMap = new HashMap<>();
        counterMap.put(element, number);
    }

    public AtomCounter() {
        counterMap = new HashMap<>();
    }

    public AtomCounter add(AtomCounter atomCounter) {
        atomCounter.counterMap.forEach((key, value) -> {
            Integer counterKey = counterMap.get(key);
            if (counterKey != null) {
                counterMap.put(key, counterKey + value);
            } else {
                counterMap.put(key, value);
            }
        });
        return this;
    }

    public AtomCounter multiplyBy(int number) {
        counterMap.forEach((key, value) -> counterMap.put(key, value * number));
        return this;
    }

    public Map<String, Integer> toMap() {
        return Collections.unmodifiableMap(counterMap);
    }
}
