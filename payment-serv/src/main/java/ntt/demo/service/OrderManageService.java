package ntt.demo.service;

import ntt.demo.model.Customer;
import ntt.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ntt.demo.base.domain.Order;

@Service
public class OrderManageService {

    private static final String SOURCE = "payment";

    private static final Logger log = LoggerFactory.getLogger(OrderManageService.class);

    private CustomerRepository repository;

    private KafkaTemplate<Long, Order> template;


    public OrderManageService(CustomerRepository repository, KafkaTemplate<Long, Order> template) {
        this.repository = repository;
        this.template = template;
    }

    //reserve function
    public void reserve(Order order) {
        try {
            Customer customer = repository.findById(order.getCustomerId()).orElseThrow();
            log.info("Found: {}", customer);
            if (order.getPrice() < customer.getAmountAvailable()) {
                order.setStatus("ACCEPT");
                customer.setAmountReserved(customer.getAmountReserved() + order.getPrice());
                customer.setAmountAvailable(customer.getAmountAvailable() - order.getPrice());
            } else {
                order.setStatus("REJECT");
            }
            order.setSource(SOURCE);
            repository.save(customer);
            template.send("payment-orders", order.getId(), order);
            log.info("Sent: {}", order);
        } catch (Exception ex) {
            log.info("Not Found customer with id: {}", order.getCustomerId().toString());
        }

    }

    //confirm function
    public void confirm(Order order) {
        try {
            Customer customer = repository.findById(order.getCustomerId()).orElseThrow();
            log.info("Found: {}", customer);
            if (order.getStatus().equals("CONFIRMED")) {
                customer.setAmountReserved(customer.getAmountReserved() - order.getPrice());
                repository.save(customer);
            } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
                customer.setAmountReserved(customer.getAmountReserved() - order.getPrice());
                customer.setAmountAvailable(customer.getAmountAvailable() + order.getPrice());
                repository.save(customer);
            }
        } catch (Exception ex) {
            log.info("Not Found customer with id: {}", order.getCustomerId().toString());
        }


    }
}
