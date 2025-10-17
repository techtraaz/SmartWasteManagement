package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Payment;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {

    List<Payment> findByUserId(Integer userId);

    Payment findByPaymentId(Integer paymentId);



}
