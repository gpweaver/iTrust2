package edu.ncsu.csc.iTrust2.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.iTrust2.TestConfig;
import edu.ncsu.csc.iTrust2.forms.CPTCodeForm;
import edu.ncsu.csc.iTrust2.models.CPTCode;
import edu.ncsu.csc.iTrust2.services.CPTCodeService;

/**
 * Class to test that CPTCode and CPTCodeForm objects are created correctly.
 *
 * @author Godsend Cheung
 * @author Gabe Weaver
 *
 */
@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class CPTCodeTest {

    @Autowired
    /** CPTCode Service class for database access. */
    private CPTCodeService service;

    @SuppressWarnings ( "unlikely-arg-type" )
    @Test
    @Transactional
    public void testCodes () {
        final CPTCodeForm form = new CPTCodeForm();
        form.setId( 1L );
        form.setCode( "29481" );
        form.setDescription( "Testing" );
        form.setCost( 250.50f );
        form.setHasDuration( true );

        final CPTCode base = new CPTCode();
        base.setCode( "29481" );
        base.setDescription( "Testing" );
        base.setId( 1L );
        base.setCost( 250.50f );
        base.setHasDuration( true );

        // Check that code is created the same from given CPTCodeForm object.
        final CPTCode code = new CPTCode( form );
        assertEquals( code, base );
        assertEquals( base.hashCode(), code.hashCode() );

        // Check that form is created the same from given CPTCode object.
        final CPTCodeForm f2 = new CPTCodeForm( code );
        assertEquals( form, f2 );
        assertEquals( form.hashCode(), f2.hashCode() );

        // Check that CPTCode fields have been set as appropriate.
        assertEquals( "29481", code.getCode() );
        assertTrue( code.getId().equals( 1L ) );
        assertEquals( "Testing", code.getDescription() );
        assertTrue( code.getHasDuration() );
        assertEquals( 250.50f, code.getCost(), 0.001 );

        // Secondary code and form.
        final CPTCodeForm compareForm = new CPTCodeForm();
        final CPTCode compareCode = new CPTCode();

        // Check Code equals() method.
        assertFalse( code.equals( null ) );
        assertTrue( code.equals( code ) );
        assertFalse( code.equals( form ) );

        compareCode.setCode( "29481" );
        assertFalse( code.equals( compareCode ) );
        compareCode.setCost( 250.50f );
        assertFalse( code.equals( compareCode ) );
        compareCode.setDescription( "Testing" );
        assertFalse( code.equals( compareCode ) );
        compareCode.setId( 1L );
        assertFalse( code.equals( compareCode ) );
        compareCode.setHasDuration( true );
        assertTrue( code.equals( compareCode ) );

        // Check Form equals() method.
        assertFalse( form.equals( null ) );
        assertFalse( form.equals( code ) );
        assertTrue( form.equals( form ) );

        compareForm.setCode( "29481" );
        assertFalse( form.equals( compareForm ) );
        compareForm.setCost( 250.50f );
        assertFalse( form.equals( compareForm ) );
        compareForm.setDescription( "Testing" );
        assertFalse( form.equals( compareForm ) );
        compareForm.setId( 1L );
        assertFalse( form.equals( compareForm ) );
        compareForm.setHasDuration( true );
        assertTrue( form.equals( compareForm ) );
    }

    @Test
    @Transactional
    public void testInvalidCodes () {
        final CPTCodeForm form = new CPTCodeForm();
        form.setCode( "20" );

        CPTCode code;
        try {
            code = new CPTCode( form );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Description cannot be empty", e.getMessage() );
        }

        form.setDescription( "     " );
        try {
            code = new CPTCode( form );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Description cannot be empty", e.getMessage() );
        }

        // Making a long description for validation.
        final StringBuilder sb = new StringBuilder( "description" );
        for ( int i = 0; i < 50; i++ ) {
            sb.append( "description" );
        }
        form.setDescription( sb.toString() );
        try {
            code = new CPTCode( form );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Description too long (500 characters max)", e.getMessage() );
        }

        form.setDescription( "Office Visit" );
        form.setCode( "201" );
        try {
            code = new CPTCode( form );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Code must be five characters: 201", e.getMessage() );
        }

        form.setCode( "29N20" );
        try {
            code = new CPTCode( form );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Code must be numeric.", e.getMessage() );
        }

        form.setCode( "30281" );
        form.setCost( -3f );
        try {
            code = new CPTCode( form );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( "Cost must be a positive number.", e.getMessage() );
        }

        form.setCost( 302.05f );
        try {
            code = new CPTCode( form );
            assertNotNull( code );
        }
        catch ( final IllegalArgumentException e ) {
            fail( "This is a valid CPTCode." );
        }
    }

    @Test
    @Transactional
    public void testDatabaseEntry () {
        final CPTCodeForm form = new CPTCodeForm();
        form.setId( 1L );
        form.setCode( "29481" );
        form.setDescription( "Testing" );
        form.setCost( 250.50f );
        form.setHasDuration( true );
        final CPTCode code = new CPTCode( form );

        // Save to the database and assert that there is only 1 entry in the
        // database.
        service.save( code );
        final CPTCode entry = service.findByCode( "29481" );
        assertEquals( code, entry );

    }
}
