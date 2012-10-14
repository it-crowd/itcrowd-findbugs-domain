package pl.com.it_crowd.findbugs;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class SampleEntity2 {
// ------------------------------ FIELDS ------------------------------

    @NotNull
    @Column(nullable = false)
    private String stringAttributeA;

    @NotEmpty
    @NotNull
    @Column(nullable = true)
    private String stringAttributeB;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getStringAttributeA()
    {
        return stringAttributeA;
    }

    public void setStringAttributeA(String stringAttributeA)
    {
        this.stringAttributeA = stringAttributeA;
    }

    public String getStringAttributeB()
    {
        return stringAttributeB;
    }

    public void setStringAttributeB(String stringAttributeB)
    {
        this.stringAttributeB = stringAttributeB;
    }
}

