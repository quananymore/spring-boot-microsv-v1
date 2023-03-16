package ntt.demo.service;

import ntt.demo.model.Product;
import ntt.demo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ntt.demo.base.domain.Order;

@Service
public class OrderManageService {

    private static final String SOURCE = "stock";

    private static final Logger log = LoggerFactory.getLogger(OrderManageService.class);

    private ProductRepository repository;

    private KafkaTemplate<Long, Order> template;


    public OrderManageService(ProductRepository repository, KafkaTemplate<Long, Order> template) {
        this.repository = repository;
        this.template = template;
    }

    //reserve function be call if is new order
    public void reserve(Order order) {
        try {
            Product product = repository.findById(order.getProductId()).orElseThrow();
            log.info("Found: {}", product);
            if (order.getStatus().equals("NEW")) {
                if (order.getProductCount() < product.getAvailableItems()) {
                    product.setReservedItems(product.getReservedItems() + order.getProductCount());
                    product.setAvailableItems(product.getAvailableItems() - order.getProductCount());
                    order.setStatus("ACCEPT");
                    repository.save(product);
                } else {
                    order.setStatus("REJECT");
                }
                template.send("stock-orders", order.getId(), order);
                log.info("Sent: {}", order);
            }
        } catch (Exception e) {
            log.info("Not found product with id: {}", order.getProductId());
        }

    }

    //confirm function
    public void confirm(Order order) {
        try {
            Product product = repository.findById(order.getProductId()).orElseThrow();
            log.info("Found: {}", product);
            if (order.getStatus().equals("CONFIRMED")) {
                product.setReservedItems(product.getReservedItems() - order.getProductCount());
                repository.save(product);
            } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
                product.setReservedItems(product.getReservedItems() - order.getProductCount());
                product.setAvailableItems(product.getAvailableItems() + order.getProductCount());
                repository.save(product);
            }
        } catch (Exception e) {
            log.info("Not found product with id: {}", order.getProductId());
        }


    }
}
