package edu.ncsu.csc.iTrust2.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import edu.ncsu.csc.iTrust2.forms.CPTCodeForm;

/**
 * Class to represent a CPT Code for office visits.
 *
 * @author Gabe Weaver
 * @author Godsend Cheung
 */
@Entity

public class CPTCode extends DomainObject {

    /**
     * The identifier for this CPT code in the database.
     */
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long    id;

    /**
     * The string representation of this CPT code.
     */
    private String  code;

    // /**
    // * The maximum duration associated with this CPT code.
    // */
    // private Integer maxDuration;
    //
    // /**
    // * The minimum duration associated with this CPT code.
    // */
    // private Integer minDuration;

    /**
     * The cost associated with this CPT code.
     */
    private float   cost;

    /**
     * The description associated with this CPT code.
     */
    @NotNull
    private String  description;

    /**
     * Boolean value if this CPTCode is related to duration.
     */
    private Boolean hasDuration;

    /**
     * Boolean value if this CPTCode is disabled.
     */
    private Boolean disabled = false;

    /**
     * CPTCode empty constructor for hibernate.
     */
    public CPTCode () {

    }

    /**
     * CPTCode constructor that builds from its associated form.
     *
     * @param form
     *            The form that validates and sanitizes input
     */
    public CPTCode ( final CPTCodeForm form ) {
        setId( form.getId() );
        setCode( form.getCode() );
        setHasDuration( form.getHasDuration() );
        setCost( form.getCost() );
        setDescription( form.getDescription() );
        setDisabled( form.getDisabled() );

        // Check description length.
        if ( description == null || description.isBlank() ) {
            throw new IllegalArgumentException( "Description cannot be empty" );
        }
        else if ( description.length() > 500 ) {
            throw new IllegalArgumentException( "Description too long (500 characters max)" );
        }

        // Check code length.
        final char[] c = code.toCharArray();
        if ( c.length != 5 ) {
            throw new IllegalArgumentException( "Code must be five characters: " + code );
        }
        else {
            for ( int i = 0; i < c.length; i++ ) {
                if ( !Character.isDigit( c[i] ) ) {
                    throw new IllegalArgumentException( "Code must be numeric." );
                }
            }
        }

        // Check cost value.
        if ( cost <= 0.0f ) {
            throw new IllegalArgumentException( "Cost must be a positive number." );
        }

        // Set null to default false.
        if ( hasDuration == null ) {
            hasDuration = false;
        }

        // // Check duration values . (DEPRECIATED)
        // if ( minDuration == null || minDuration < 0 ) {
        // throw new IllegalArgumentException( "Min duration must be a positive
        // integer." );
        // }
        // else if ( maxDuration == null || maxDuration < 0 ) {
        // throw new IllegalArgumentException( "Max duration must be a positive
        // integer." );
        // }
        // else if ( minDuration > maxDuration ) {
        // throw new IllegalArgumentException( "Max duration must be greater
        // than min duration" );
        // }
    }

    /**
     * Sets the ID of the Code
     *
     * @param id
     *            The new ID of the Code. For Hibernate.
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    @Override
    public Long getId () {
        return id;
    }

    /**
     * Returns the code associated with this CPT code.
     *
     * @return this CPT Code's code
     */
    public String getCode () {
        return code;
    }

    /**
     * Sets this CPT Code's code to the provided String.
     *
     * @param code
     *            the code to set
     */
    public void setCode ( final String code ) {
        this.code = code;
    }

    // /**
    // * Returns the maximum duration associated with this CPT Code.
    // *
    // * @return the maximum duration associated with this CPT Code
    // */
    // public Integer getMaxDuration () {
    // return maxDuration;
    // }
    //
    // /**
    // * Sets the maximum duration associated with this CPT Code.
    // *
    // * @param maxDuration
    // * the maximum duration associated with this CPT Code
    // */
    // public void setMaxDuration ( final Integer maxDuration ) {
    // this.maxDuration = maxDuration;
    // }

    // /**
    // * Returns the minimum duration associated with this CPT Code.
    // *
    // * @return the minimum duration associated with this CPT Code
    // */
    // public Integer getMinDuration () {
    // return minDuration;
    // }

    @Override
    public int hashCode () {
        return Objects.hash( code, cost, description, id, hasDuration );
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final CPTCode other = (CPTCode) obj;
        return Objects.equals( code, other.code ) && Float.floatToIntBits( cost ) == Float.floatToIntBits( other.cost )
                && Objects.equals( description, other.description ) && Objects.equals( hasDuration, other.hasDuration );
    }

    // /**
    // * Sets the minimum duration associated with this CPT Code.
    // *
    // * @param minDuration
    // * the minimum duration associated with this CPT Code
    // */
    // public void setMinDuration ( final Integer minDuration ) {
    // this.minDuration = minDuration;
    // }

    /**
     * Returns the cost associated with this CPT code.
     *
     * @return the cost associated with this CPT code
     */
    public float getCost () {
        return cost;
    }

    /**
     * Sets the cost associated with this CPT Code.
     *
     * @param cost
     *            the cost associated with this CPT code
     */
    public void setCost ( final float cost ) {
        this.cost = cost;
    }

    /**
     * Returns the description associated with this CPT Code.
     *
     * @return the description associated with this CPT Code
     */
    public String getDescription () {
        return description;
    }

    /**
     * Sets the description associated with this CPT Code.
     *
     * @param description
     *            the description associated with this CPT Code
     */
    public void setDescription ( final String description ) {
        this.description = description;
    }

    /**
     * Sets the duration boolean value of the CPTCode
     *
     * @param isDuration
     *            boolean value of the CPTCode.
     */
    public void setHasDuration ( final Boolean hasDuration ) {
        this.hasDuration = hasDuration;
    }

    /**
     * Retrieves the duration boolean value of the CPTCode
     *
     * @return the duration boolean value of the CPTCode.
     */
    public Boolean getHasDuration () {
        return hasDuration;
    }

    public void setDisabled ( final Boolean disabled ) {
        this.disabled = disabled;
    }

    public boolean getDisabled () {
        return disabled;
    }
}
