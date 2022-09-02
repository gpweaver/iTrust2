package edu.ncsu.csc.iTrust2.services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.models.CPTCode;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.repositories.CPTCodeRepository;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * Service class for interacting with CPTCode model, performing CRUD tasks with
 * database.
 *
 * @author Godsend Cheung
 * @author Gabe Weaver
 *
 */
@Component
@Transactional
public class CPTCodeService extends Service<CPTCode, Long> {

    /** Repository for CRUD operations */
    @Autowired
    private CPTCodeRepository repository;

    /** Service for looking up Personnel */
    @Autowired
    private PersonnelService  service;

    @Override
    protected JpaRepository<CPTCode, Long> getRepository () {
        return repository;
    }

    /**
     * Finds a CPTCode object for the given Code
     *
     * @param code
     *            Code of the CPTCode desired
     * @return CPTCode found, if any
     */
    public CPTCode findByCode ( final String code ) {
        return repository.findByCode( code );
    }

    @Override
    public List<CPTCode> findAll () {
        final User u = service.findByName( LoggerUtil.currentUser() );
        final Collection<Role> roles = u.getRoles();

        if ( roles.contains( Role.ROLE_BSM ) || roles.contains( Role.ROLE_HCP ) || roles.contains( Role.ROLE_OPH )
                || roles.contains( Role.ROLE_VACCINATOR ) || roles.contains( Role.ROLE_ADMIN ) ) {
            // return repository.findAll();

            return repository.findByDisabledIsFalse();
        }
        return null;
    }
}
