package ntt.demo.order.service;

import ntt.demo.base.domain.Order;
import ntt.demo.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderManageService {


    private OrderRepository repository;

    public Order confirm(Order orderPayment, Order orderStock){
        Order o = new Order(orderPayment.getId(),
                orderPayment.getCustomerId(),
                orderPayment.getProductId(),
                orderPayment.getProductCount(),
                orderPayment.getPrice());
        if (orderPayment.getStatus().equals("ACCEPT") &&
                orderStock.getStatus().equals("ACCEPT")) {
            o.setStatus("CONFIRMED");
        } else if (orderPayment.getStatus().equals("REJECT") &&
                orderStock.getStatus().equals("REJECT")) {
            o.setStatus("REJECTED");
        } else if (orderPayment.getStatus().equals("REJECT") ||
                orderStock.getStatus().equals("REJECT")) {
            String source = orderPayment.getStatus().equals("REJECT")
                    ? "PAYMENT" : "STOCK";
            o.setStatus("ROLLBACK");
            o.setSource(source);
        }

        return o;
    }
    public void saveOrder(Order o){
        repository.save(o);
    }
}
