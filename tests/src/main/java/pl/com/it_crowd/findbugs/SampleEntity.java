package pl.com.it_crowd.findbugs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class SampleEntity {
// ------------------------------ FIELDS ------------------------------

    @Column(nullable = false)
    private Boolean booleanAttributeA;

    @NotNull
    @Column(nullable = true)
    private Boolean booleanAttributeB;

// --------------------------- CONSTRUCTORS ---------------------------

    public SampleEntity()
    {

    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Boolean getBooleanAttributeA()
    {
        return booleanAttributeA;
    }

    public void setBooleanAttributeA(Boolean booleanAttributeA)
    {
        this.booleanAttributeA = booleanAttributeA;
    }

    public Boolean getBooleanAttributeB()
    {
        return booleanAttributeB;
    }

    public void setBooleanAttributeB(Boolean booleanAttributeB)
    {
        this.booleanAttributeB = booleanAttributeB;
    }
}

