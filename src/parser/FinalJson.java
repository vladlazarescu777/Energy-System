package parser;

import java.util.List;

public class FinalJson {
    private List<ConsumersJson> consumers;
    private List<DistributorsJson> distributors;

    public FinalJson(final List<ConsumersJson> consumers,
                     final List<DistributorsJson> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

    public final List<ConsumersJson> getConsumers() {
        return consumers;
    }

    public final List<DistributorsJson> getDistributors() {
        return distributors;
    }
}
