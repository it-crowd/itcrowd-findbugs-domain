package pl.com.it_crowd.findbugs;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "ManyToOneEntity")
@Entity
public class ManyToOneEntity {
// ------------------------------ FIELDS ------------------------------

    @NotNull
    @ManyToOne()
    @JoinColumn(nullable = false)
    private Group invalidGroups;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = true)
    private Group invalidGroupsB;

    @ManyToOne(optional = false)
    private Group missingJoinColumn;

    @ManyToOne()
    @JoinColumn()
    private Group validGroups;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Group validGroupsB;

    @JoinColumn()
    private Group validLeftOutForHibernateValidation;
}
