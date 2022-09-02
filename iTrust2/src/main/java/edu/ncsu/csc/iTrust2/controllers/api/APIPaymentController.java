package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.ncsu.csc.iTrust2.forms.PaymentForm;
import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.Payment;
import edu.ncsu.csc.iTrust2.models.enums.BillStatus;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.BillService;
import edu.ncsu.csc.iTrust2.services.PaymentService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * API controller for interacting with the Payment Model. Provides standard CRUD
 * routes as appropriate for different user types.
 *
 * @author Godsend Cheung
 *
 */
@Controller
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APIPaymentController extends APIController {

    /** Service for CRUD Operations */
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private LoggerUtil     loggerUtil;

    @Autowired
    private BillService    billService;

    /**
     * Posts a payment entry into the database.
     *
     * @param paymentForm
     *            form to be validated and saved.
     *
     * @return A ResponseEntity and HTTPStatus.
     */
    @PostMapping ( BASE_PATH + "payments" )
    @PreAuthorize ( "hasAnyRole('ROLE_BSM')" )
    public ResponseEntity createPayment ( @RequestBody final PaymentForm pf ) {
        try {
            final Bill b = billService.findById( pf.getId() );
            pf.setId( null );
            final Payment p = paymentService.build( pf );
            if ( null != p.getId() && paymentService.existsById( p.getId() ) ) {
                return new ResponseEntity( errorResponse( "Payment with the id " + p.getId() + " already exists." ),
                        HttpStatus.CONFLICT );
            }

            if ( b == null ) {
                return new ResponseEntity( errorResponse( "Could not find bill to post a payment." ),
                        HttpStatus.NOT_FOUND );
            }

            b.addPayment( p );
            float total = 0.0f;
            for ( final Payment payment : b.getPayments() ) {
                total += payment.getAmount();
            }
            if ( total >= b.getTotal() ) {
                b.setStatus( BillStatus.BILL_PAID );
                if ( b.getPayments().size() == 1 ) {
                    loggerUtil.log( TransactionType.BILL_FULLPAYMENT_SINGLE, LoggerUtil.currentUser(),
                            b.getOfficeVisit().getPatient().getUsername() );
                }
                else {
                    loggerUtil.log( TransactionType.BILL_FULLPAYMENT_MULTIPLE, LoggerUtil.currentUser(),
                            b.getOfficeVisit().getPatient().getUsername() );
                }
            }
            else {
                loggerUtil.log( TransactionType.BILL_PARTPAYMENT_SINGLE, LoggerUtil.currentUser() );
            }

            paymentService.save( p );
            billService.save( b );
            // paymentService.save( p );

            return new ResponseEntity( p, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return new ResponseEntity( errorResponse( "Could not complete the payment due to " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }
}
