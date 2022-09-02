package edu.ncsu.csc.iTrust2.models;

import java.time.ZonedDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.JsonAdapter;

import edu.ncsu.csc.iTrust2.adapters.ZonedDateTimeAdapter;
import edu.ncsu.csc.iTrust2.adapters.ZonedDateTimeAttributeConverter;
import edu.ncsu.csc.iTrust2.models.enums.PaymentMethod;

/**
 * Represents a Payment Object which is attached to a bill, reducing its total
 * amount due.
 *
 * @author Godsend Cheung
 * @author Brian Huynh
 *
 */
@Entity
public class Payment extends DomainObject {

    /** Unique identifier for a payment. */
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long          id;

    /** Amount that this payment satisfies towards a bill. */
    private float         amount;

    /** Date that this payment occurs on. */
    @NotNull
    @Convert ( converter = ZonedDateTimeAttributeConverter.class )
    @JsonAdapter ( ZonedDateTimeAdapter.class )
    private ZonedDateTime date;

    /** Payment method. */
    @Enumerated ( EnumType.STRING )
    private PaymentMethod method;

    /**
     * Empty Constructor.
     */
    public Payment () {

    }

    @Override
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
    public ZonedDateTime getDate () {
        return date;
    }

    /**
     * Sets the date as a String.
     *
     * @param date
     *            the date to set
     */
    public void setDate ( final ZonedDateTime date ) {
        this.date = date;
    }

    /**
     * Retrieves the payment method used for this payment.
     *
     * @return the method
     */
    public PaymentMethod getMethod () {
        return method;
    }

    /**
     * Sets the payment method.
     *
     * @param method
     *            the method to set
     */
    public void setMethod ( final PaymentMethod method ) {
        this.method = method;
    }
}
