package edu.ncsu.csc.iTrust2.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.repositories.BillRepository;

@Component
@Transactional
public class BillService extends Service<Bill, Long> {

    /** Repository for CRUD operations */
    @Autowired
    private BillRepository repository;

    @Override
    protected JpaRepository<Bill, Long> getRepository () {
        return repository;
    }

    /**
     * Gets all bills for the specified patient from the database.
     *
     * @param patient
     *            the patient whose bills will be retrieved
     * @return all bills for the specified patient from the database
     */
    public List<Bill> findByPatient ( final Patient patient ) {
        final List<Bill> dbBills = findAll();
        final List<Bill> bills = new ArrayList<Bill>();

        for ( final Bill b : dbBills ) {
            if ( b.getOfficeVisit().getPatient().equals( patient ) ) {
                bills.add( b );
            }
        }

        return bills;
    }

}
