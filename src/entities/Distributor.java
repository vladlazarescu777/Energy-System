package entities;


import constant.Constants;

import java.util.ArrayList;
import java.util.List;

public class Distributor extends Entity {
    private int initialInfrastructureCost;
    private int initialProductionCost;
    private List<Consumer> consumerList = new ArrayList<>();
    private int profit;
    private int productionCost;
    private int infrastructureCost;
    private int monthlyCosts;


    public Distributor(final int id, final int initialBudget, final int initialInfrastructureCost,
                       final int initialProductionCost, final int contractLength) {
        setId(id);
        setBankrupt(false);
        setInitialBudget(initialBudget);
        setInitialInfrastructureCost(initialInfrastructureCost);
        setInitialProductionCost(initialProductionCost);
        getContract().setLength(contractLength);
    }

    public final int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    /**
     * set initial infrastructure cost and infrastructure cost
     */
    public final void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
        infrastructureCost = initialInfrastructureCost;
    }

    public final int getInitialProductionCost() {
        return initialProductionCost;
    }

    /**
     * set initial production cost and production cost
     */
    public final void setInitialProductionCost(final int initialProductionCost) {
        this.initialProductionCost = initialProductionCost;
        productionCost = initialProductionCost;

    }

    public final List<Consumer> getConsumerList() {
        return consumerList;
    }

    /**
     * set production cost price and Monthly Costs
     */
    public final void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
        setPrice();
        setMonthlyCosts();
    }

    public final int getProductionCost() {
        return productionCost;
    }

    public final int getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     * set infrastructure cost price and Monthly Costs
     */
    public final void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
        setProfit();
        setMonthlyCosts();
    }

    /**
     * set profit
     */
    public final void setProfit() {
        profit = (int) Math.round(Math.floor(Constants.DIVPROFIT * productionCost));
    }

    /**
     * set the price of the contract
     */
    public final void setPrice() {
        setProfit();
        if (consumerList.size() == 0) {
            getContract().setPrice(infrastructureCost + productionCost + profit);
        } else {
            getContract().setPrice((int) Math.round(Math.floor(
                    infrastructureCost / consumerList.size()) + productionCost + profit));
        }
    }

    public final int getMonthlyCosts() {
        return monthlyCosts;
    }

    /**
     * set monthly cost
     */
    public final void setMonthlyCosts() {
        this.monthlyCosts = infrastructureCost + (productionCost * consumerList.size());
    }

    /**
     * change consumers of distributors
     * check if consumers have given bankruptcy
     * check if consumers they have completed the contractual period
     * set monthly costs
     */
    public final void changeConsumers(final Consumer consumer) {
        if (!consumerList.removeIf(c ->
                c.getId() == consumer.getId()
                        && (consumer.getRemainingMonths() == 0 || consumer.isBankrupt())
        )) {
            if (!consumerList.contains(consumer)) {
                consumerList.add(consumer);
            }
        }
        setMonthlyCosts();
    }

    /**
     * delete Customer if he went bankrupt or they have completed the contractual period
     * set monthly costs
     */
    public final void deleteConsumer() {
        consumerList.removeIf(c ->
                c.isBankrupt()
                        || c.getRemainingMonths() == 0);
        setMonthlyCosts();
    }

    /**
     * change budget after set monthly costs and verify if he went bankrupt
     */
    public final void changeBudget() {
        this.setMonthlyCosts();
        if (getBudget() - monthlyCosts < 0) {
            setBankrupt(true);
        }
        if (!isBankrupt()) {
            setBudget(getBudget() - monthlyCosts);
        }
    }
}
