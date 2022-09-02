package edu.ncsu.csc.iTrust2.forms;

import edu.ncsu.csc.iTrust2.models.Payment;

public class PaymentForm {

    /** Unique identifier for a payment. */
    private Long   id;

    /** Amount that this payment satisfies towards a bill. */
    private float  amount;

    /** Date that this payment occurs on. */
    private String date;

    /** Payment method. */
    private String method;

    /**
     * Empty Constructor.
     */
    public PaymentForm () {

    }

    /**
     * Constructs a PaymentForm from a Payment Object.
     *
     * @param p
     *            Payment to construct the form from.
     */
    public PaymentForm ( final Payment p ) {
        setId( p.getId() );
        setAmount( p.getAmount() );
        setDate( p.getDate().toString() );
        setMethod( p.getMethod().getPaymentMethod() );
    }

    /**
     * Returns the id of the payment.
     *
     * @return the id
     */
    public Long getId () {
        return id;
    }

    /**
     * Sets the id of the payment.
     *
     * @param id
     *            the id to set
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns the amount this payment satisfies
     *
     * @return the amount
     */
    public float getAmount () {
        return amount;
    }

    /**
     * Sets the amount this payment satisfies.
     *
     * @param amount
     *            the amount to set
     */
    public void setAmount ( final float amount ) {
        this.amount = amount;
    }

    /**
     * Retrieves the date in a string format.
     *
     * @return the date
     */
    public String getDate () {
        return date;
    }

    /**
     * Sets the date as a String.
     *
     * @param date
     *            the date to set
     */
    public void setDate ( final String date ) {
        this.date = date;
    }

    /**
     * Retrieves the payment method used for this payment.
     *
     * @return the method
     */
    public String getMethod () {
        return method;
    }

    /**
     * Sets the payment method.
     *
     * @param method
     *            the method to set
     */
    public void setMethod ( final String method ) {
        this.method = method;
    }

}
