package edu.ncsu.csc.iTrust2.forms;

import java.util.Objects;

import edu.ncsu.csc.iTrust2.models.CPTCode;

public class CPTCodeForm {
    /**
     * The identifier for this CPT code in the database.
     */
    private Long    id;

    /**
     * The string representation of this CPT code.
     */
    private String  code;

    /**
     * Boolean representing whether this CPT Code contains a duration.
     */
    private Boolean hasDuration;

    /**
     * The cost associated with this CPT code.
     */
    private float   cost;

    /**
     * The description associated with this CPT code.
     */
    private String  description;

    /**
     * Boolean description for if this code is disabled.
     */
    private boolean disabled;

    /**
     * Empty Constructor for Hibernate.
     */
    public CPTCodeForm () {

    }

    /**
     * Construct a form off an existing ICDCode object
     *
     * @param code
     *            The object to fill this form with
     */
    public CPTCodeForm ( final CPTCode code ) {
        setCode( code.getCode() );
        setDescription( code.getDescription() );
        setId( code.getId() );
        setCost( code.getCost() );
        setHasDuration( code.getHasDuration() );
        setDisabled( code.getDisabled() );
        // setMinDuration( code.getMinDuration() );
        // setMaxDuration( code.getMaxDuration() );
    }

    /**
     * Returns the id of this CPTCode form.
     *
     * @return the id.
     */
    public Long getId () {
        return id;
    }

    /**
     * Sets the id of this CPTCode form.
     *
     * @param i
     *            the id.
     */
    public void setId ( final Long i ) {
        this.id = i;
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
    //
    // /**
    // * Returns the minimum duration associated with this CPT Code.
    // *
    // * @return the minimum duration associated with this CPT Code
    // */
    // public Integer getMinDuration () {
    // return minDuration;
    // }
    //
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
     * Sets this CPT Code's hasDuration value to the specified Boolean.
     */
    public void setHasDuration ( final Boolean hasDuration ) {
        this.hasDuration = hasDuration;
    }

    /**
     * Returns this CPT Code's hasDuration value.
     *
     * @return this CPT Code's hasDuration value
     */
    public Boolean getHasDuration () {
        return hasDuration;
    }

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
     * Sets the disabled boolean value.
     *
     * @param disabled
     *            value of the cptcode.
     */
    public void setDisabled ( final Boolean disabled ) {
        this.disabled = disabled;
    }

    /**
     * Retrieves the disabled field of this cptcode.
     *
     * @return a boolean value.
     */
    public Boolean getDisabled () {
        return disabled;
    }

    @Override
    public int hashCode () {
        return Objects.hash( code, cost, description, hasDuration );
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
        final CPTCodeForm other = (CPTCodeForm) obj;
        return Objects.equals( code, other.code ) && Float.floatToIntBits( cost ) == Float.floatToIntBits( other.cost )
                && Objects.equals( description, other.description ) && Objects.equals( hasDuration, other.hasDuration );
    }

}
