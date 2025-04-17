package ra.edu.validate;

public class StringRule {
    private int min;
    private int max;


    public StringRule() {
    }

    public StringRule(int max, int min) {
        this.max = max;
        this.min = min;
    }
    public boolean isValidString(String value) {
        if (value == null) {
            return false;
        }
        return value.length() >= this.min && value.length() <= this.max;
    }
}

