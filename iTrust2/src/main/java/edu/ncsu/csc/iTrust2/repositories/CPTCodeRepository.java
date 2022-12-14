package edu.ncsu.csc.iTrust2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.CPTCode;

/**
 * Repository for interacting with ICDCode model. Method implementations
 * generated by Spring
 *
 * @author Godsend Cheung
 * @author Gabe Weaver
 *
 */
public interface CPTCodeRepository extends JpaRepository<CPTCode, Long> {

    /**
     * Finds an CPTCode by the provided code
     *
     * @param code
     *            Code to search by
     * @return Matching code, if any
     */
    public CPTCode findByCode ( String code );

    /**
     * Finds all CPTCodes with a 'disabled' value of false.
     *
     * @return all CPTCodes with a 'disabled' value of false
     */
    public List<CPTCode> findByDisabledIsFalse ();
}
