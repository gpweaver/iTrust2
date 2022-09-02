package edu.ncsu.csc.iTrust2.controllers.routing;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ncsu.csc.iTrust2.models.enums.Role;

@Controller
public class BSMController {

    /**
     * Returns the Landing screen for the BSM
     *
     * @param model
     *            Data from the front end
     * @return The page to display
     */
    @RequestMapping ( value = "/bsm/index" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public String index ( final Model model ) {
        return Role.ROLE_BSM.getLanding();
    }

    /**
     * Method responsible for BSM's requested bills functionality. This prepares
     * the page.
     *
     * @param model
     *            Data for the front end
     * @return The page to display to the user
     */
    @GetMapping ( "/bsm/managebills" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public String requestBillingForm ( final Model model ) {
        return "/bsm/managebills";
    }

    /**
     * Returns the form page for an Admin or BSM to maintain a CPT code
     *
     * @param model
     *            The data for the front end
     * @return Page to display to the user
     */
    @GetMapping ( "/bsm/manageCPTCodes" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public String documentCPTcodeForm ( final Model model ) {
        return "/bsm/manageCPTCodes";
    }
}
