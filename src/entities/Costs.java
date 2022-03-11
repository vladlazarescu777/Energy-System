package entities;

public class Costs {
    private int id;
    private int infrastructureCost;
    private int productionCost;

    public Costs(final int id, final int infrastructureCost, final int productionCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
    }

    public final int getId() {
        return id;
    }

    public final int getInfrastructureCost() {
        return infrastructureCost;
    }

    public final int getProductionCost() {
        return productionCost;
    }
}
