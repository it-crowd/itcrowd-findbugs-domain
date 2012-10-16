package pl.com.it_crowd.findbugs;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class NotNullTestingEntity {
// ------------------------------ FIELDS ------------------------------

    @NotNull
    @Column(nullable = true)
    private int[] arrayAttributeA;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private int[] arrayAttributeB;

    @NotNull
    @Column
    private int[] arrayAttributeC;

    @NotNull
    private int[] arrayAttributeD;

    @NotEmpty
    @Column(nullable = false)
    private int[] arrayAttributeE;

    @NotNull
    @Column(nullable = true)
    private Collection collectionAttributeA;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private Collection collectionAttributeB;

    @NotNull
    @Column
    private Set collectionAttributeC;

    @NotNull
    private Set collectionAttributeD;

    @NotEmpty
    @Column(nullable = false)
    private List collectionAttributeE;

    @NotNull
    @Column(nullable = true)
    private Map mapAttributeA;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private Map mapAttributeB;

    @NotNull
    @Column
    private HashMap mapAttributeC;

    @NotNull
    private Map mapAttributeD;

    @NotEmpty
    @Column(nullable = false)
    private AbstractMap mapAttributeE;

    private String stringAttributeA;

    private String stringAttributeB;

    private String stringAttributeC;

    private String stringAttributeD;

    private String stringAttributeE;

// --------------------- GETTER / SETTER METHODS ---------------------

    @NotNull
    @Column(nullable = true)
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
    @Column(nullable = false)
    public String getStringAttributeB()
    {
        return stringAttributeB;
    }

    public void setStringAttributeB(String stringAttributeB)
    {
        this.stringAttributeB = stringAttributeB;
    }

    @NotNull
    @Column
    public String getStringAttributeC()
    {
        return stringAttributeC;
    }

    public void setStringAttributeC(String stringAttributeC)
    {
        this.stringAttributeC = stringAttributeC;
    }

    @NotNull
    public String getStringAttributeD()
    {
        return stringAttributeD;
    }

    public void setStringAttributeD(String stringAttributeD)
    {
        this.stringAttributeD = stringAttributeD;
    }

    @NotEmpty
    @Column(nullable = false)
    public String getStringAttributeE()
    {
        return stringAttributeE;
    }

    public void setStringAttributeE(String stringAttributeE)
    {
        this.stringAttributeE = stringAttributeE;
    }
}

