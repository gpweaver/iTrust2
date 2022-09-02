package edu.ncsu.csc.iTrust2.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.services.BillService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * @author Gabe Weaver
 *
 */
@RestController
public class APIBillController extends APIController {

    @Autowired
    BillService          service;

    @Autowired
    PatientService<User> patientService;

    @PreAuthorize ( "hasAnyRole('ROLE_BSM', 'ROLE_ADMIN')" )
    @GetMapping ( BASE_PATH + "/bills" )
    public List<Bill> getBills () {
        return service.findAll();
    }

    @GetMapping ( BASE_PATH + "/getMyBills" )
    @PreAuthorize ( "hasAnyRole('ROLE_PATIENT')" )
    public List<Bill> getMyBills () {

        final String username = LoggerUtil.currentUser();
        final Patient p = (Patient) patientService.findByName( username );

        final List<Bill> bills = service.findByPatient( p );
        return bills;
    }

}
