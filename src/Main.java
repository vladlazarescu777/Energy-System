import entities.Costs;
import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.EntityFactory;
import parser.Reader;
import simulation.Month;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Main {
    private Main() {

    }

    /**
     * main function
     */
    public static void main(final String[] args) throws Exception {

        Reader reader = Reader.getInstance();
        List<Entity> consumersList = reader.readConsumer(args[0]);
        List<Entity> distributorsList = reader.readDistributors(args[0]);
        List<Month> monthList = reader.readMonths(args[0]);

        for (int i = 0; i < monthList.size(); i++) {
            AtomicBoolean closeGame = new AtomicBoolean(true);
            consumersList.forEach(c -> {
                if (!c.isBankrupt()) {
                    closeGame.set(false);
                }
            });
            Month currentMonth;
            List<Costs> currentCosts = null;
            currentMonth = monthList.get(i);
            currentCosts = currentMonth.getCosts();

            List<Costs> finalCurrentCosts = currentCosts;
            Distributor finalDistributor = (Distributor) distributorsList.stream().peek(d -> {
                if (finalCurrentCosts != null) {
                    finalCurrentCosts.stream().filter(c -> c.getId() == d.getId()).forEach(
                            c -> {
                                ((Distributor) d).setInfrastructureCost(c.getInfrastructureCost());
                                ((Distributor) d).setProductionCost(c.getProductionCost());
                            });
                }
                ((Distributor) d).setPrice();
            }
            ).min(Comparator.comparing(Entity::getPrice)).orElseThrow(
                    NoSuchElementException::new);


            Consumer consumer = monthList.get(i).getConsumer();
            if (consumer != null) {
                consumersList.add(EntityFactory.getInstance()
                        .getEntity(consumer.getId(), consumer.getBudget(),
                                consumer.getMonthlyIncome()));
            }

            consumersList.stream().filter(c -> ((Consumer) c).getRemainingMonths() == 0
                    && !c.isBankrupt()
            ).forEach(c -> {
                if (((Consumer) c).getDistributor() != null) {
                    ((Consumer) c).getDistributor().changeConsumers((Consumer) c);
                }
                ((Consumer) c).setDistributor(finalDistributor);
                ((Consumer) c).setRemainingMonths(finalDistributor.getContract().getLength());
                c.setContract(finalDistributor.getContract());
                finalDistributor.changeConsumers(((Consumer) c));
            });

            distributorsList.forEach(c -> {
                        ((Distributor) c).deleteConsumer();
                        ((Distributor) c).changeBudget();
                    }
            );
            if (closeGame.get()) {
                break;
            }
            consumersList.forEach(c -> {
                        if (!c.isBankrupt()) {
                            c.addBudget(((Consumer) c).getMonthlyIncome());
                            ((Consumer) c).payContract();
                        }
                    }
            );
        }
        reader.writeToFile(distributorsList, consumersList, args[1]);
    }
}
