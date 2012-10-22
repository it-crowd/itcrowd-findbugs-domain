package pl.com.it_crowd.findbugs;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
@Table(name = "NotNullTestingEntity")
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
    @ManyToOne
    @JoinTable(name = "BLA", joinColumns = @JoinColumn(name = "ManyToOneEntity"), inverseJoinColumns = @JoinColumn(name = "joinTableA"))
    private Group joinTableA;

    @ManyToOne(optional = true)
    @JoinTable(name = "BLA", joinColumns = @JoinColumn(name = "ManyToOneEntity"), inverseJoinColumns = @JoinColumn(name = "joinTableB"))
    private Group joinTableB;

    @ManyToOne(optional = false)
    @JoinTable(name = "BLA", joinColumns = @JoinColumn(name = "ManyToOneEntity"), inverseJoinColumns = @JoinColumn(name = "joinTableC"))
    private Group joinTableC;

    @NotNull
    @ManyToOne(optional = false)
    @JoinTable(name = "BLA", joinColumns = @JoinColumn(name = "ManyToOneEntity"), inverseJoinColumns = @JoinColumn(name = "joinTableD"))
    private Group joinTableD;

    @NotNull
    @ManyToOne(optional = true)
    @JoinTable(name = "BLA", joinColumns = @JoinColumn(name = "ManyToOneEntity"), inverseJoinColumns = @JoinColumn(name = "joinTableE"))
    private Group joinTableE;

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

    @Size(max = 255)
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

    @Size(max = 255)
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

    @Size(max = 255)
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

    @Size(max = 255)
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

