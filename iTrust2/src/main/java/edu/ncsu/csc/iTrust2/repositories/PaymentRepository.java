package edu.ncsu.csc.iTrust2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.Payment;

/**
 * Repository for interacting with the Payment model.
 *
 * @author Godsend Cheung
 *
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
