/**
 *
 */
package edu.ncsu.csc.iTrust2.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.iTrust2.TestConfig;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.BasicHealthMetrics;
import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.Hospital;
import edu.ncsu.csc.iTrust2.models.OfficeVisit;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.Payment;
import edu.ncsu.csc.iTrust2.models.Personnel;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.AppointmentType;
import edu.ncsu.csc.iTrust2.models.enums.BillStatus;
import edu.ncsu.csc.iTrust2.models.enums.HouseholdSmokingStatus;
import edu.ncsu.csc.iTrust2.models.enums.PaymentMethod;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.services.BasicHealthMetricsService;
import edu.ncsu.csc.iTrust2.services.BillService;
import edu.ncsu.csc.iTrust2.services.HospitalService;
import edu.ncsu.csc.iTrust2.services.OfficeVisitService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.PaymentService;
import edu.ncsu.csc.iTrust2.services.UserService;

/**
 * @author Gabe Weaver
 *
 */
@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class BillTest {

    @Autowired
    private OfficeVisitService        officeVisitService;

    @Autowired
    private BasicHealthMetricsService basicHealthMetricsService;

    @Autowired
    private HospitalService           hospitalService;

    @Autowired
    private UserService<User>         userService;

    @Autowired
    private PaymentService            paymentService;

    @Autowired
    private BillService               service;

    @Autowired
    private PatientService            patientService;

    @Before
    public void setup () {
        officeVisitService.deleteAll();

        final User hcp = new Personnel( new UserForm( "hcp", "123456", Role.ROLE_HCP, 1 ) );

        final User alice = new Patient( new UserForm( "patient", "123456", Role.ROLE_PATIENT, 1 ) );

        userService.saveAll( List.of( hcp, alice ) );
    }

    @Test
    @Transactional
    public void testOfficeVisit () {
        final Hospital hosp = new Hospital( "Hospital", "123 Main St", "12345", "NC" );
        hospitalService.save( hosp );

        final OfficeVisit visit = new OfficeVisit();

        final BasicHealthMetrics bhm = new BasicHealthMetrics();
        bhm.setDiastolic( 100 );
        bhm.setHcp( userService.findByName( "hcp" ) );
        bhm.setPatient( userService.findByName( "patient" ) );
        bhm.setHdl( 75 );
        bhm.setHeight( 75f );
        bhm.setHouseSmokingStatus( HouseholdSmokingStatus.NONSMOKING );

        basicHealthMetricsService.save( bhm );

        final Payment payment = new Payment();
        payment.setAmount( 0 );
        payment.setDate( ZonedDateTime.now() );
        payment.setMethod( PaymentMethod.CASH );
        final List<Payment> payments = new ArrayList<Payment>();
        paymentService.save( payment );

        visit.setBasicHealthMetrics( bhm );
        visit.setType( AppointmentType.GENERAL_CHECKUP );
        visit.setHospital( hosp );
        visit.setPatient( userService.findByName( "patient" ) );
        visit.setHcp( userService.findByName( "patient" ) );
        visit.setDate( ZonedDateTime.now() );
        officeVisitService.save( visit );

        Assert.assertEquals( 1, officeVisitService.count() );

        final Bill b = new Bill();
        b.setOfficeVisit( visit );
        b.setPayments( payments );
        b.setStatus( BillStatus.BILL_PAID );
        b.setTotal( 0 );
        service.save( b );

        Bill retrieved = service.findAll().get( 0 );

        assertNotNull( retrieved.getOfficeVisit() );
        assertEquals( 0, retrieved.getPayments().size() );
        assertEquals( BillStatus.BILL_PAID, retrieved.getStatus() );
        assertEquals( 0, retrieved.getTotal() );

        payments.add( payment );
        b.setPayments( payments );
        service.save( b );

        retrieved = service.findAll().get( 0 );
        assertEquals( 1, retrieved.getPayments().size() );

        assertNotNull( service.findByPatient( (Patient) patientService.findByName( "patient" ) ) );
    }
}
