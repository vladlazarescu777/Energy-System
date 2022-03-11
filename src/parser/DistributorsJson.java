package parser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "id", "budget", "isBankrupt", "contracts"})
public class DistributorsJson {
    private int id;
    private int budget;
    private boolean isBankrupt;
    private List<ContractsJson> contracts;

    public DistributorsJson(final int id, final int budget, final boolean isBankrupt,
                            final List<ContractsJson> contracts) {
        this.id = id;
        this.budget = budget;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    public final int getId() {
        return id;
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

    public final List<ContractsJson> getContracts() {
        return contracts;
    }
}
