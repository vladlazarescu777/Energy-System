package entities;

public final class EntityFactory {
    private EntityFactory() {

    }

    private static EntityFactory entityFactory = null;
    /**
     * entity factory using singleton
     */
    public static EntityFactory getInstance() {
        if (entityFactory == null) {
            entityFactory = new EntityFactory();
        }
        return entityFactory;
    }
    /**
     * get  distributor entity
     */
    public Entity getEntity(final int id, final int initialBudget,
                            final int contractsLength, final int initialInfrastructureCost,
                            final int initialProductionCost) {
        return new Distributor(id, initialBudget, initialInfrastructureCost, initialProductionCost,
                contractsLength);
    }
    /**
     * get consumer entity
     */
    public Entity getEntity(final int id, final int initialBudget, final int monthlyIncome) {
        return new Consumer(id, initialBudget, monthlyIncome);
    }
}
