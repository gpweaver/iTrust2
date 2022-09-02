package edu.ncsu.csc.iTrust2.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.forms.CPTCodeForm;
import edu.ncsu.csc.iTrust2.models.CPTCode;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.CPTCodeService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * Class that provides the REST endpoints for handling CPT Codes.
 *
 * @author Gabe Weaver
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APICPTCodeController extends APIController {

    /** LoggerUtil */
    @Autowired
    private LoggerUtil     loggerUtil;

    /** CPTCode service */
    @Autowired
    private CPTCodeService service;

    /**
     * Returns a list of all CPT Codes in the system.
     *
     * @return the CPT codes in the system
     */
    @GetMapping ( BASE_PATH + "/cptcodes" )
    public List<CPTCode> getCodes () {
        return service.findAll();
    }

    /**
     * Adds a CPT Code to the database.
     *
     * @param form
     *            the CPT Code to create the new CPT Code from
     * @return the result of the action
     */
    @PostMapping ( BASE_PATH + "/cptcodes" )
    @PreAuthorize ( "hasAnyRole('ROLE_BSM', 'ROLE_ADMIN')" )
    public ResponseEntity createCPTCode ( @RequestBody final CPTCodeForm form ) {
        if ( service.findByCode( form.getCode() ) != null ) {
            return new ResponseEntity( errorResponse( "CPTCode " + form.getCode() + " already exists" ),
                    HttpStatus.CONFLICT );
        }

        CPTCode code = null;

        try {
            code = new CPTCode( form );
            service.save( code );

            loggerUtil.log( TransactionType.CPTCODE_CREATE, LoggerUtil.currentUser(),
                    LoggerUtil.currentUser() + " created a CPT Code" );

            return new ResponseEntity( code, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not create " + form.getCode() + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }

    }

    /**
     * Deletes the specified CPT Code from the database.
     *
     * @param code
     *            the CPT Code to delete from the database
     */
    @DeleteMapping ( BASE_PATH + "/cptcodes/{code}" )
    @PreAuthorize ( "hasAnyRole('ROLE_BSM', 'ROLE_ADMIN')" )
    public ResponseEntity deleteCPTCode ( @PathVariable ( "code" ) final String code ) {
        try {
            final CPTCode c = service.findByCode( code );
            c.setDisabled( true );
            service.save( c );

            loggerUtil.log( TransactionType.CPTCODE_DELETE, LoggerUtil.currentUser(),
                    LoggerUtil.currentUser() + " deleted a CPT Code" );

            return new ResponseEntity( HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not delete CPT Code " + code + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Updates the provided CPT Code.
     *
     * @param form
     *            the form containing the new parameters for the code
     * @return the result of the action
     */
    @PutMapping ( BASE_PATH + "/cptcodes" )
    @PreAuthorize ( "hasAnyRole('ROLE_BSM', 'ROLE_ADMIN')" )
    public ResponseEntity updateCPTCode ( @RequestBody final CPTCodeForm form ) {
        try {
            if ( !service.existsById( form.getId() ) ) {
                return new ResponseEntity( "CPTCode " + form.getCode() + "does not exist", HttpStatus.NOT_FOUND );
            }

            final CPTCode code = service.findById( form.getId() );
            code.setCode( form.getCode() );
            code.setCost( form.getCost() );
            code.setDescription( form.getDescription() );
            code.setHasDuration( form.getHasDuration() );
            service.save( code );

            loggerUtil.log( TransactionType.CPTCODE_EDIT, LoggerUtil.currentUser(),
                    LoggerUtil.currentUser() + " edited a CPT Code" );

            return new ResponseEntity( code, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not update CPT Code " + form.getCode() + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Generates a set of default CPT codes for the iTrust2 system.
     *
     * @return ResponseEntity indicating that everything is OK
     */
    @PostMapping ( BASE_PATH + "generateCodes" )
    @PreAuthorize ( "hasAnyRole('ROLE_BSM', 'ROLE_ADMIN', 'ROLE_HCP')" )
    public ResponseEntity generateCodes () {
        
        CPTCode code = new CPTCode();


        // Generates a list of codes and saves them, with the following information
        // code, description, cost, hasDuration, disabled

        // 99202,15-29 minutes,$75, true, false
        code.setCode("99202");
        code.setDescription("15-29 minutes");
        code.setCost(75.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99202" );
        if ( service.findByCode( "99202" ) == null ) {
            service.save( code );
        }

        // 99203,30-44 minutes,$150, true, false
        code = new CPTCode();
        code.setCode("99203");
        code.setDescription("30-44 minutes");
        code.setCost(150.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99203" );
        if ( service.findByCode( "99203" ) == null ) {
            service.save( code );
        }

        // 99204,45-59 minutes,$200, true, false
        code = new CPTCode();
        code.setCode("99204");
        code.setDescription("45-59 minutes");
        code.setCost(200.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        if ( service.findByCode( "99204" ) == null ) {
            service.save( code );
        }
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99204" );

        // 99205,60-74 minutes,$250, true, false
        code = new CPTCode();
        code.setCode("99205");
        code.setDescription("60-74 minutes");
        code.setCost(250.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        if ( service.findByCode( "99205" ) == null ) {
            service.save( code );
        }
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99205" );

        // 99212,10-19 minutes,$50, true, false
        code = new CPTCode();
        code.setCode("99212");
        code.setDescription("10-19 minutes");
        code.setCost(50.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        if ( service.findByCode( "99212" ) == null ) {
            service.save( code );
        }
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99212" );

        // 99213,20-29 minutes,$100, true, false
        code = new CPTCode();
        code.setCode("99213");
        code.setDescription("20-29 minutes");
        code.setCost(100.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        if ( service.findByCode( "99213" ) == null ) {
            service.save( code );
        }
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99213" );

        // 99214,30-39 minutes,$125, true, false
        code = new CPTCode();
        code.setCode("99214");
        code.setDescription("30-39 minutes");
        code.setCost(125.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        if ( service.findByCode( "99214" ) == null ) {
            service.save( code );
        }
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99214" );

        // 99215,40-54 minutes,$175, true, false
        code = new CPTCode();
        code.setCode("99215");
        code.setDescription("40-54 minutes");
        code.setCost(175.0f);
        code.setHasDuration(true);
        code.setDisabled(false);
        if ( service.findByCode( "99215" ) == null ) {
            service.save( code );
        }
        loggerUtil.log( TransactionType.CPTCODE_CREATE, "99215" );

        return new ResponseEntity( HttpStatus.OK );
    }

}
