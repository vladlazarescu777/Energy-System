package simulation;

import entities.Costs;
import entities.Consumer;

import java.util.List;

public class Month {
    private int monthNo;
    private Consumer consumer;
    private List<Costs> costs;

    public Month(final int monthNo, final Consumer consumer, final List<Costs> costs) {
        this.monthNo = monthNo;
        this.consumer = consumer;
        this.costs = costs;
    }

    public final int getMonthNo() {
        return monthNo;
    }

    public final Consumer getConsumer() {
        return consumer;
    }

    public final List<Costs> getCosts() {
        return costs;
    }

}
