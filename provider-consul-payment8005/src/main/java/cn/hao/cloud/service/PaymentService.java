package cn.hao.cloud.service;


import cn.hao.common.entity.Payment;

public interface PaymentService {
    void create(Payment payment);

    Payment get(Long id);
}
