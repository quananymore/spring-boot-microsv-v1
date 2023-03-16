package ntt.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("amountAvailable")
    private int amountAvailable;
    @JsonProperty("amountReserved")
    private int amountReserved;

    public Customer() {
    }

    public Customer(Long id, String name, int amountAvailable, int amountReserved) {
        this.id = id;
        this.name = name;
        this.amountAvailable = amountAvailable;
        this.amountReserved = amountReserved;
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

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public int getAmountReserved() {
        return amountReserved;
    }

    public void setAmountReserved(int amountReserved) {
        this.amountReserved = amountReserved;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountAvailable=" + amountAvailable +
                ", amountReserved=" + amountReserved +
                '}';
    }
}
