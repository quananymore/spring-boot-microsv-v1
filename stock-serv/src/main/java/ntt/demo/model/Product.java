package ntt.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("availableItems")
    private int availableItems;
    @JsonProperty("reservedItems")
    private int reservedItems;

    public Product() {
    }

    public Product(Long id, String name, int availableItems, int reservedItems) {
        this.id = id;
        this.name = name;
        this.availableItems = availableItems;
        this.reservedItems = reservedItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public int getReservedItems() {
        return reservedItems;
    }

    public void setReservedItems(int reservedItems) {
        this.reservedItems = reservedItems;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availableItems=" + availableItems +
                ", reservedItems=" + reservedItems +
                '}';
    }
}
