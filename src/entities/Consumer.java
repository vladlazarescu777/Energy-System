package entities;

import constant.Constants;

public class Consumer extends Entity {
    private int monthlyIncome;
    private Distributor distributor;
    private int delay;
    private int contractPrice;
    private int remainingMonths;

    public Consumer(final int id, final int initialBudget, final int monthlyIncome) {
        setId(id);
        setMonthlyIncome(monthlyIncome);
        setInitialBudget(initialBudget);
        setBankrupt(false);
        delay = 0;
    }

    public final int getDelay() {
        return delay;
    }

    public final void setDelay(final int cost) {
        delay = cost;
    }

    public final int getMonthlyIncome() {
        return monthlyIncome;
    }

    public final void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public final Distributor getDistributor() {
        return distributor;
    }

    /**
     * sets the distributor and the contract price
     */
    public final void setDistributor(final Distributor distributor) {
        this.distributor = distributor;
        this.contractPrice = distributor.getContract().getPrice();
    }

    public final int getContractPrice() {
        return contractPrice;
    }

    public final void setRemainingMonths(final int remainingMonths) {
        this.remainingMonths = remainingMonths;
    }

    public final int getRemainingMonths() {
        return remainingMonths;
    }

    /**
     * function for the payment of the contract
     * checking if it went bankrupt
     * set the delay price
     * pay the bill dealay
     */
    public final void payContract() {
        if (distributor.isBankrupt()) {
            remainingMonths = 0;
            return;
        }

        if (!isBankrupt()) {
            if (delay == 0) {
                if (getBudget() >= contractPrice) {
                    setBudget(getBudget() - contractPrice);
                    distributor.addBudget(contractPrice);
                } else {
                    delay = contractPrice;
                }
            } else {
                int costs = (int) Math.round(Math.floor(Constants.DIVPAY * delay))
                        + contractPrice;
                if (getBudget() >= costs) {
                    setBudget(getBudget() - costs);
                    delay = 0;
                    distributor.addBudget(costs);
                } else {
                    setBankrupt(true);
                    distributor.deleteConsumer();
                }
            }
            remainingMonths--;
        }
    }
}
