package pl.itcrowd.findbugs.domain;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("UnusedDeclaration")
@Table(name = "ManyToOneEntity")
@Entity
public class ManyToOneEntity {
// ------------------------------ FIELDS ------------------------------

    @ForeignKey(name = "FK___ManyToOneEntity___invalidGroups")
    @NotNull
    @ManyToOne()
    @JoinColumn(name = "invalidGroups", nullable = false)
    private Group invalidGroups;

    @ForeignKey(name = "FK___ManyToOneEntity___invalidGroupsB")
    @ManyToOne(optional = false)
    @JoinColumn(name = "invalidGroupsB", nullable = true)
    private Group invalidGroupsB;

    @ManyToOne(optional = false)
    private Group missingJoinColumn;

    @ForeignKey(name = "FK___ManyToOneEntity___validGroups")
    @ManyToOne()
    @JoinColumn(name = "validGroups")
    private Group validGroups;

    @ForeignKey(name = "FK___ManyToOneEntity___validGroupsB")
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "validGroupsB", nullable = false)
    private Group validGroupsB;

    @ForeignKey(name = "FK___BLA___ManyToOneEntity", inverseName = "FK___BLA___validGroupsC")
    @NotNull
    @ManyToOne(optional = false)
    @JoinTable(name = "BLA", joinColumns = @JoinColumn(name = "ManyToOneEntity"), inverseJoinColumns = @JoinColumn(name = "validGroupsC"))
    private Group validGroupsC;

    @ForeignKey(name = "FK___ManyToOneEntity___validLeftOutForHibernateValidation")
    @JoinColumn(name = "validLeftOutForHibernateValidation")
    private Group validLeftOutForHibernateValidation;
}
