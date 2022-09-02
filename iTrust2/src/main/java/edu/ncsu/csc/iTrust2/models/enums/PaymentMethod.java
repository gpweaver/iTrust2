package edu.ncsu.csc.iTrust2.models.enums;

/**
 * Lists the types of payment methods there are when a patient is paying off
 * their bill
 *
 * @author Godsend Cheung
 * @author Bryan Huynh
 */
public enum PaymentMethod {

    /** The payment is made in cash */
    CASH ( "CASH" ),

    /** The payment is made via a check */
    CHECK ( "CHECK" ),

    /** The payment is made via credit card */
    CREDITCARD ( "CREDITCARD" ),

    /** The payment is made via a patient's insurance */
    INSURANCE ( "INSURANCE" );

    /** Field representing type of payment */
    private String paymentMethod;

    /**
     * Constructs an enum with the following method string.
     *
     * @param paymentMethod
     *            the string representation of the payment method.
     */
    private PaymentMethod ( final String paymentMethod ) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Retrieves the payment method as a string.
     *
     * @return the string representation of a payment.
     */
    public String getPaymentMethod () {
        return paymentMethod;
    }

}
