package parser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "isBankrupt", "budget"})
public class ConsumersJson {
    private int id;
    private boolean isBankrupt;
    private int budget;

    public ConsumersJson(final int id, final boolean isBankrupt, final int budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }

    public final int getBudget() {
        return budget;
    }
    /**
     * is Bankrupt
     */
    public final boolean isisBankrupt() {
        return isBankrupt;
    }

    public final int getId() {
        return id;
    }
}
