package edu.ncsu.csc.iTrust2.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.forms.CPTCodeForm;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.Personnel;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.services.CPTCodeService;
import edu.ncsu.csc.iTrust2.services.UserService;

/**
 * Tests APICPTCodeController and associated methods.
 *
 * @author Gabe Weaver
 *
 */
@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APICPTCodeTest {

    private MockMvc               mvc;

    @Autowired
    private CPTCodeService        service;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService<User>     userService;

    /**
     * Set up test.
     */
    @Before
    @WithMockUser ( username = "admin", roles = { "USER", "ADMIN" } )
    public void setup () throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
        service.deleteAll();
        userService.deleteAll();

        // Add a BSM to the system for testing.
        userService.save( new Personnel( new UserForm( "bsm", "bsm", Role.ROLE_BSM, 1 ) ) );

        // Add a patient to the system for testing.
        userService.save( new Patient( new UserForm( "patient", "patient", Role.ROLE_PATIENT, 1 ) ) );
    }

    @Test
    @Transactional
    @WithMockUser ( username = "bsm", roles = { "ADMIN", "BSM" } )
    public void testCPTCodeAPIWithBSM () throws Exception {
        final CPTCodeForm form = new CPTCodeForm();
        form.setCode( "00000" );
        form.setCost( 10.0f );
        form.setDescription( "Office Visit" );
        form.setHasDuration( false );

        // Test Post
        final String postContent = mvc.perform( post( "/api/v1/cptcodes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( form ) ) ).andReturn().getResponse().getContentAsString();

        final CPTCodeForm postResult = TestUtils.gson().fromJson( postContent, CPTCodeForm.class );
        form.setId( postResult.getId() );
        assertEquals( form, postResult );

        // Test Get
        final String getContent = mvc.perform( get( "/api/v1/cptcodes" ) ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();
        assertNotNull( getContent );

        // Test Edit
        form.setCost( 20.0f );
        final String putContent = mvc.perform( put( "/api/v1/cptcodes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( form ) ) ).andReturn().getResponse().getContentAsString();
        final CPTCodeForm putResult = TestUtils.gson().fromJson( putContent, CPTCodeForm.class );
        assertEquals( form, putResult );

        // Test Delete
        mvc.perform( delete( "/api/v1/cptcodes/" + form.getCode() ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );

        assertTrue( service.findByCode( form.getCode() ).getDisabled() );

    }

}
