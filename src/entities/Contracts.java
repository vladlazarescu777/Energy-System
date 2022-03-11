package entities;

public class Contracts {
    private int length;
    private int price;

    public final int getLength() {
        return length;
    }

    public final void setLength(final int length) {
        this.length = length;
    }

    public final int getPrice() {
        return price;
    }

    public final void setPrice(final int price) {
        this.price = price;
    }

    @Override
    public final String toString() {
        return "Contracts{"
                + "length="
                + length
                + ", price=" + price
                + '}';
    }
}
