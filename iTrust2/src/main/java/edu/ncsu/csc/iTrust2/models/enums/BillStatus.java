package edu.ncsu.csc.iTrust2.models.enums;

public enum BillStatus {

    BILL_PAID ( "Paid" ),

    BILL_UNPAID ( "Unpaid" ),

    BILL_DELINQUENT ( "Delinquent" );

    private String status;

    private BillStatus ( final String status ) {
        this.status = status;
    }

    public String getStatus () {
        return this.status;
    }
}
