package parser;

public class ContractsJson {
    private int consumerId;
    private int price;
    private int remainedContractMonths;

    public  ContractsJson(final int consumerId, final int price, final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public final int getConsumerId() {
        return consumerId;
    }

    public final int getPrice() {
        return price;
    }

    public final int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    @Override
    public final String toString() {
        return "{" + "consumerId=" + consumerId
                + "price=" + price
                + "remainedContractMonths=" + remainedContractMonths + '}';
    }
}
