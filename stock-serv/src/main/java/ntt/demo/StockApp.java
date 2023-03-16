package ntt.demo;

import ntt.demo.base.service.OrderUtils;
import ntt.demo.model.Product;
import ntt.demo.repository.ProductRepository;
import ntt.demo.service.OrderManageService;
import ntt.demo.base.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootApplication
@EnableKafka
public class StockApp {

    private static final Logger LOG = LoggerFactory.getLogger(StockApp.class);

    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }

    @Autowired
    OrderManageService orderManageService;

    @KafkaListener(id = "orders", topics = "orders", groupId = "stock")
    public void onEvent(String str) {
        LOG.info("Received: {}" , str);
        Order o = OrderUtils.stringToOrder(str);

        if (o.getStatus().equals("NEW"))
            orderManageService.reserve(o);
        else
            orderManageService.confirm(o);
        LOG.info("Result: {}",o);
    }

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    public void generateData() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int count = r.nextInt(1000);
            Product p = new Product(Long.valueOf(i), "Product" + i, count, 0);
            repository.save(p);
        }
    }

}
