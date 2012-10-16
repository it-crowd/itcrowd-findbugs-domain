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
public class NotEmptyTestingEntity {
// ------------------------------ FIELDS ------------------------------

    @NotEmpty
    @Column(nullable = true)
    private int[] arrayAttributeA;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private int[] arrayAttributeB;

    @NotEmpty
    @Column
    private int[] arrayAttributeC;

    @NotEmpty
    private int[] arrayAttributeD;

    @NotNull
    @Column(nullable = false)
    private int[] arrayAttributeE;

    @NotEmpty
    @Column(nullable = true)
    private Collection collectionAttributeA;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private Collection collectionAttributeB;

    @NotEmpty
    @Column
    private Set collectionAttributeC;

    @NotEmpty
    private Set collectionAttributeD;

    @NotNull
    @Column(nullable = false)
    private List collectionAttributeE;

    @NotEmpty
    @Column(nullable = true)
    private Map mapAttributeA;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private Map mapAttributeB;

    @NotEmpty
    @Column
    private HashMap mapAttributeC;

    @NotEmpty
    private Map mapAttributeD;

    @NotNull
    @Column(nullable = false)
    private AbstractMap mapAttributeE;

    private String stringAttributeA;

    private String stringAttributeB;

    private String stringAttributeC;

    private String stringAttributeD;

    private String stringAttributeE;

// --------------------- GETTER / SETTER METHODS ---------------------

    @NotEmpty
    @Column(nullable = true)
    public String getStringAttributeA()
    {
        return stringAttributeA;
    }

    public void setStringAttributeA(String stringAttributeA)
    {
        this.stringAttributeA = stringAttributeA;
    }

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    public String getStringAttributeB()
    {
        return stringAttributeB;
    }

    public void setStringAttributeB(String stringAttributeB)
    {
        this.stringAttributeB = stringAttributeB;
    }

    @NotEmpty
    @Column
    public String getStringAttributeC()
    {
        return stringAttributeC;
    }

    public void setStringAttributeC(String stringAttributeC)
    {
        this.stringAttributeC = stringAttributeC;
    }

    @NotEmpty
    public String getStringAttributeD()
    {
        return stringAttributeD;
    }

    public void setStringAttributeD(String stringAttributeD)
    {
        this.stringAttributeD = stringAttributeD;
    }

    @NotNull
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

