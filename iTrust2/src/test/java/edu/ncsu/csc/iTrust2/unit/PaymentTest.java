package edu.ncsu.csc.iTrust2.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.iTrust2.TestConfig;
import edu.ncsu.csc.iTrust2.forms.PaymentForm;
import edu.ncsu.csc.iTrust2.models.Payment;
import edu.ncsu.csc.iTrust2.models.enums.PaymentMethod;
import edu.ncsu.csc.iTrust2.services.PaymentService;

/**
 * Tests the Payment form and Payment classes with the database.
 *
 * @author Godsend Cheung
 *
 */
@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class PaymentTest {

    /** Service for payment database operations. */
    @Autowired
    private PaymentService paymentService;

    /**
     * Tests the payment and payment form class.
     */
    @Test
    @Transactional
    public void testPayment () {
        final Payment p = new Payment();
        p.setAmount( 10.52f );
        p.setDate( ZonedDateTime.now() );
        p.setMethod( PaymentMethod.CASH );

        // Check if payment fields are retrieved normally.
        paymentService.save( p );
        final List<Payment> result = paymentService.findAll();
        assertEquals( 1, result.size() );
        final Payment sp = result.get( 0 );
        assertEquals( 10.52f, sp.getAmount(), 0.000001 );
        assertEquals( "CASH", sp.getMethod().getPaymentMethod() );

        // Check if payment form is accurate.
        final PaymentForm pf = new PaymentForm( p );
        assertEquals( p.getDate().toString(), pf.getDate() );
        assertEquals( 10.52f, pf.getAmount(), 0.00001 );
        assertEquals( "CASH", pf.getMethod() );
        assertEquals( p.getId(), pf.getId() );

        // Build payment from payment form.
        Payment buildResult = paymentService.build( pf );
        assertEquals( p.getAmount(), buildResult.getAmount(), 0.000001 );
        assertEquals( p.getDate(), buildResult.getDate() );
        assertEquals( p.getId(), buildResult.getId() );
        assertEquals( p.getMethod(), buildResult.getMethod() );

        // Since this payment is exactly the same as the above payment, there
        // should
        // not be more than 1 payment.
        paymentService.save( buildResult );
        assertEquals( 1, paymentService.findAll().size() );

        // Assert exception throwing causes in build.
        try {
            pf.setAmount( -50.0f );
            buildResult = paymentService.build( pf );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Cannot have a payment less than 0.", e.getMessage() );
        }

        pf.setAmount( 10.52f );
        try {
            pf.setMethod( null );
            buildResult = paymentService.build( pf );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Cannot have an empty payment type.", e.getMessage() );
        }
    }
}
