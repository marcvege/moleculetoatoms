public class Formula {
    private String formula;

    public Formula(String formula) {
        this.formula = formula;
    }

    public boolean isEmpty() {
        return formula == null || formula.length() == 0;
    }
}
