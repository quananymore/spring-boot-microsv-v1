package ntt.demo;

import net.datafaker.Faker;
import ntt.demo.base.service.OrderUtils;
import ntt.demo.model.Customer;
import ntt.demo.repository.CustomerRepository;
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
import java.util.Objects;
import java.util.Random;

@SpringBootApplication
@EnableKafka
public class PaymentApp {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentApp.class);

    public static void main(String[] args) {
        SpringApplication.run(PaymentApp.class, args);
    }

    @Autowired
    OrderManageService orderManageService;

    @KafkaListener(id = "orders", topics = "orders", groupId = "payment")
    public void onEvent(String str) {
        Order o = OrderUtils.stringToOrder(str);
        LOG.info("Received: {}" , o);
        if (o.getStatus().equals("NEW"))
            orderManageService.reserve(o);
        else
            orderManageService.confirm(o);
        LOG.info("Result: {}",o);
    }

    @Autowired
    private CustomerRepository repository;

//
//    @PostConstruct
//    public void generateData() {
//        Random r = new Random();
//        Faker faker = new Faker();
//        for (int i = 0; i < 10; i++) {
//            int count = r.nextInt(1000);
//            Customer c = new Customer(Long.valueOf(i), faker.name().fullName(), count, 0);
//            repository.save(c);
//        }
//    }
}