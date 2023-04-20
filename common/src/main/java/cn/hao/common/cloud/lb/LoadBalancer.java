package cn.hao.common.cloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

public interface LoadBalancer {

    ServiceInstance instance (List<ServiceInstance> serviceInstances);

    ServiceInstance instance (String serviceId, DiscoveryClient discoveryClient);

    ServiceInstance instance (String serviceId);
}
