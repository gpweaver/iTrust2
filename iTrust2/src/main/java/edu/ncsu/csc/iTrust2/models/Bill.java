package edu.ncsu.csc.iTrust2.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import edu.ncsu.csc.iTrust2.models.enums.BillStatus;

/**
 * A class represneting a bill for an Office Visit.
 *
 * @author Gabe Weaver
 *
 */
@Entity
public class Bill extends DomainObject {

    /**
     * The identifier for this CPT code in the database.
     */
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long          id;

    private float         total;

    @OneToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "office_visit_id" )
    private OfficeVisit   officeVisit;

    @OneToMany ( cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();

    @Enumerated ( EnumType.STRING )
    private BillStatus    status;

    public Bill () {

    }

    public float getTotal () {
        return total;
    }

    public void setTotal ( final float total ) {
        this.total = total;
    }

    public OfficeVisit getOfficeVisit () {
        return officeVisit;
    }

    public void setOfficeVisit ( final OfficeVisit officeVisit ) {
        this.officeVisit = officeVisit;
    }

    public List<Payment> getPayments () {
        return payments;
    }

    public void setPayments ( final List<Payment> payments ) {
        this.payments = payments;
    }

    public BillStatus getStatus () {
        return status;
    }

    public void setStatus ( final BillStatus status ) {
        this.status = status;
    }

    public void setId ( final Long id ) {
        this.id = id;
    }

    @Override
    public Long getId () {
        return id;
    }

    public void addPayment ( final Payment payment ) {
        payments.add( payment );
    }

}
