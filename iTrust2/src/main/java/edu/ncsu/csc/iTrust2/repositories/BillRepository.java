package edu.ncsu.csc.iTrust2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
