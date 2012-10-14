package pl.com.it_crowd.findbugs;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class SampleEntity3 {
// ------------------------------ FIELDS ------------------------------

    private String stringAttributeA;

    private String stringAttributeB;

    @NotEmpty
    @NotNull
    private String stringAttributeC;

// --------------------- GETTER / SETTER METHODS ---------------------

    @NotNull
    @Column(nullable = false)
    public String getStringAttributeA()
    {
        return stringAttributeA;
    }

    public void setStringAttributeA(String stringAttributeA)
    {
        this.stringAttributeA = stringAttributeA;
    }

    @NotEmpty
    @NotNull
    @Column(nullable = true)
    public String getStringAttributeB()
    {
        return stringAttributeB;
    }

    public void setStringAttributeB(String stringAttributeB)
    {
        this.stringAttributeB = stringAttributeB;
    }
}

