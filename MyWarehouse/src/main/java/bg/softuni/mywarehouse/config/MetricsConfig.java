package bg.softuni.mywarehouse.config;

import bg.softuni.mywarehouse.repositories.OrderRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    public MetricsConfig(MeterRegistry meterRegistry,
                         OrderRepository orderRepository) {

        Gauge.builder("order.count", orderRepository::count).
                register(meterRegistry);

    }
}
