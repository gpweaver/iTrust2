package edu.ncsu.csc.iTrust2.services;

import java.time.ZonedDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.forms.PaymentForm;
import edu.ncsu.csc.iTrust2.models.Payment;
import edu.ncsu.csc.iTrust2.models.enums.PaymentMethod;
import edu.ncsu.csc.iTrust2.repositories.PaymentRepository;

/**
 * Service class for posting the Payment persistence class into a database.
 *
 * @author Godsend Cheung
 *
 */
@Component
@Transactional
public class PaymentService extends Service<Payment, Long> {

    /** Repository for CRUD operations */
    @Autowired
    private PaymentRepository repository;

    @Override
    protected JpaRepository<Payment, Long> getRepository () {
        return repository;
    }

    /**
     * Builds a Payment Object with the provided payment form.
     *
     * @param pf
     *            PaymentForm object.
     * @return a Payment object.
     */
    public Payment build ( final PaymentForm pf ) {
        final Payment p = new Payment();
        if ( pf.getId() != null ) {
            p.setId( pf.getId() );
        }
        final ZonedDateTime date = ZonedDateTime.parse( pf.getDate() );
        p.setDate( date );

        if ( pf.getAmount() <= 0.0f ) {
            throw new IllegalArgumentException( "Cannot have a payment less than 0." );
        }
        p.setAmount( pf.getAmount() );

        PaymentMethod pm = null;
        try {
            pm = PaymentMethod.valueOf( pf.getMethod() );
        }
        catch ( final NullPointerException e ) {
            throw new IllegalArgumentException( "Cannot have an empty payment type." );
        }
        p.setMethod( pm );
        return p;
    }
}
