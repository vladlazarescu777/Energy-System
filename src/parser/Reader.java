package parser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Costs;
import entities.Consumer;
import entities.Entity;
import entities.EntityFactory;
import entities.Distributor;
import simulation.Month;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({"consumers", "distributors"})
public final class Reader {
    private Reader() {

    }

    private static Reader reader = null;
    private static EntityFactory entityFactory = EntityFactory.getInstance();

    /**
     * read Consumer
     */
    public List<Entity> readConsumer(final String filename) {
        List<Entity> consumersList = new ArrayList<Entity>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode obj = mapper.readValue(new File(filename), JsonNode.class);
            JsonNode data = obj.get("initialData").get("consumers");
            data.forEach(e -> consumersList
                    .add(entityFactory
                            .getEntity(e.get("id").asInt(), e.get("initialBudget").asInt(),
                                    e.get("monthlyIncome").asInt())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return consumersList;
    }

    /**
     * read Distributors
     */
    public List<Entity> readDistributors(final String filename) {
        List<Entity> distributorsList = new ArrayList<Entity>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode obj = mapper.readValue(new File(filename), JsonNode.class);
            JsonNode data = obj.get("initialData").get("distributors");
            for (int i = 0; i < data.size(); i++) {
                distributorsList.add(entityFactory.getEntity(data.get(i).get("id").asInt(),
                        data.get(i).get("initialBudget").asInt(),
                        data.get(i).get("contractLength").asInt(),
                        data.get(i).get("initialInfrastructureCost").asInt(),
                        data.get(i).get("initialProductionCost").asInt()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return distributorsList;
    }

    /**
     * read Months
     */
    public List<Month> readMonths(final String filename) {
        List<Month> monthList = new ArrayList<Month>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode obj = mapper.readValue(new File(filename), JsonNode.class);
            JsonNode data = obj.get("monthlyUpdates");
            monthList.add(new Month(0, null, new ArrayList<>()));

            for (int i = 0; i < data.size(); i++) {
                JsonNode consumers = data.get(i).get("newConsumers");
                JsonNode costsChanges = data.get(i).get("costsChanges");
                Consumer consumer = null;

                if (consumers.size() > 0) {
                    consumer = new Consumer(consumers.get(0).get("id").asInt(),
                            consumers.get(0).get("initialBudget").asInt(),
                            consumers.get(0).get("monthlyIncome").asInt());
                }
                List<Costs> costsList = new ArrayList<>();
                for (int j = 0; j < costsChanges.size(); j++) {
                    costsList.add(new Costs(costsChanges.get(j).get("id").asInt(),
                            costsChanges.get(j).get("infrastructureCost").asInt(),
                            costsChanges.get(j).get("productionCost").asInt()));
                }
                monthList.add(new Month(monthList.size(), consumer, costsList));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monthList;
    }

    /**
     * write To File
     */
    public void writeToFile(final List<Entity> distributorList,
                            final List<Entity> consumerList, final String name) {
        ObjectMapper mapper = new ObjectMapper();

        List<ConsumersJson> consumers = new ArrayList<>();
        List<DistributorsJson> distributorsJsons = new ArrayList<>();

        for (Entity entity : consumerList) {
            consumers.add(new ConsumersJson(entity.getId(),
                    entity.isBankrupt(),
                    entity.getBudget()));
        }

        for (Entity entity : distributorList) {
            List<ContractsJson> contractsJsons;
            contractsJsons = ((Distributor) entity).getConsumerList().stream().map(
                    e -> new ContractsJson(e.getId(), e.getContractPrice(), e.getRemainingMonths()))
                    .collect(
                            Collectors.toList());
            Comparator<ContractsJson> comparator = Comparator.comparing(ContractsJson::getPrice)
                    .reversed();
            comparator.thenComparing(ContractsJson::getConsumerId);
            contractsJsons = contractsJsons.stream().sorted(comparator)
                    .collect(Collectors.toList());
            distributorsJsons.add(new DistributorsJson(entity.getId(),
                    entity.getBudget(), entity.isBankrupt(),
                    contractsJsons));
        }
        FinalJson finalJson = new FinalJson(consumers, distributorsJsons);

        try {
            mapper.writeValue(Paths.get(name).toFile(), finalJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * singleton reader instance
     */
    public static Reader getInstance() {
        if (reader == null) {
            reader = new Reader();
        }
        return reader;
    }
}
