package cn.hao.cloud.service.impl;

import cn.hao.cloud.dao.PaymentDao;
import cn.hao.cloud.service.PaymentService;
import cn.hao.common.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public void create(Payment payment) {
        paymentDao.insertSelective(payment);
    }

    @Override
    public Payment get(Long id) {
        return paymentDao.selectByPrimaryKey(id);
    }
}
