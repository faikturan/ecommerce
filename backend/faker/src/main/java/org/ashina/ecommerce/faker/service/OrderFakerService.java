package org.ashina.ecommerce.faker.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.ashina.ecommerce.faker.model.customer.Customer;
import org.ashina.ecommerce.faker.model.order.Order;
import org.ashina.ecommerce.faker.model.order.OrderStatus;
import org.ashina.ecommerce.faker.model.product.Product;
import org.ashina.ecommerce.faker.repository.customer.CustomerRepository;
import org.ashina.ecommerce.faker.repository.order.OrderRepository;
import org.ashina.ecommerce.faker.repository.product.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OrderFakerService implements FakerService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private Faker faker;
    private List<Customer> customers;
    private List<Product> products;

    private static final int CUSTOMER_SIZE = 1000;
    private static final int PRODUCT_SIZE = 100;
    private static final int LINE_SIZE = 5;
    private static final int MAX_QUANTITY = 3;

    @PostConstruct
    public void init() {
        this.faker = faker();
        this.customers = customerRepository.findAll(PageRequest.of(0, CUSTOMER_SIZE)).getContent();
        this.products = productRepository.findAll(PageRequest.of(0, PRODUCT_SIZE)).getContent();
    }

    private Faker faker() {
        return new Faker();
    }

    @Override
    public void fake() {
        for (Customer customer : customers) {
            if (customer.getOrders() > 0) {
                for (int orderCount = 0; orderCount < customer.getOrders(); orderCount++) {
                    Order order = order(customer);
                    orderRepository.save(order);
                }
            }
        }
    }

    private Order order(Customer customer) {
        Order order = new Order();
        int total = 0;
        order.setCustomerId(customer.getId());
        List<Product> randomizedProducts = randomizedProducts();
        for (Product product : randomizedProducts) {
            Order.Line line = line(product);
            order.addLine(line);
            total += line.getProductPrice() * line.getQuantity();
        }
        order.setTotal(total);
        order.setRecipient(recipient(customer));
        order.setStatus(status());
        return order;
    }

    private List<Product> randomizedProducts() {
        List<Integer> productIndexes = IntStream.range(0, PRODUCT_SIZE).boxed().collect(Collectors.toList());
        Collections.shuffle(productIndexes);
        List<Product> randomizedProducts = new ArrayList<>();
        int randomizedLineSize = RandomUtils.nextInt(1, LINE_SIZE);
        for (int i = 0; i < randomizedLineSize; i++) {
            randomizedProducts.add(products.get(i));
        }
        return randomizedProducts;
    }

    private Order.Line line(Product product) {
        Order.Line line = new Order.Line();
        line.setProductId(product.getId());
        line.setProductPrice(product.getPrice());
        line.setQuantity(RandomUtils.nextInt(1, MAX_QUANTITY + 1));
        return line;
    }

    private Order.Recipient recipient(Customer customer) {
        Order.Recipient recipient = new Order.Recipient();
        recipient.setName(customer.getLastName() + " " + customer.getFirstName());
        recipient.setPhoneNumber(faker.phoneNumber().phoneNumber());
        recipient.setAddress(faker.address().fullAddress());
        return recipient;
    }

    private OrderStatus status() {
        OrderStatus[] statuses = OrderStatus.values();
        return statuses[RandomUtils.nextInt(0, statuses.length)];
    }
}
