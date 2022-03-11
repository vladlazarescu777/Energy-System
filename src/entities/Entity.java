package entities;

public abstract class Entity {
    private int id;
    private int initialBudget;
    private int budget;
    private boolean bankrupt;
    private Contracts contract = new Contracts();

    public final int getId() {
        return id;
    }

    public final int getInitialBudget() {
        return initialBudget;
    }

    public final void setId(final int id) {
        this.id = id;
    }
    /**
     * set initial budget
     */
    public final void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
        setBudget(initialBudget);
    }

    public final boolean isBankrupt() {
        return bankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public final int getBudget() {
        return budget;
    }

    public final void setBudget(final int budget) {
        this.budget = budget;
    }

    public final Contracts getContract() {
        return contract;
    }

    public final void setContract(final Contracts contract) {
        this.contract = contract;
    }

    public final int getPrice() {
        return contract.getPrice();
    }
    /**
     * add budget money
     */
    public final void addBudget(final int toAdd) {
        budget += toAdd;
    }
    /**
     * pay utilities
     */
    public final void payUtilities(final int pay) {
        budget -= pay;
    }
}
