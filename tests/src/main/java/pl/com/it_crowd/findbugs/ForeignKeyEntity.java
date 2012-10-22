package pl.com.it_crowd.findbugs;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("UnusedDeclaration")
@Table(name = "ForeignKeyEntity")
@Entity
public class ForeignKeyEntity {
// ------------------------------ FIELDS ------------------------------

    @ForeignKey(name = "FK___ForeignKeyEntity")
    @ManyToOne
    @JoinColumn(name = "THE_GROUP")
    private Group invalidGroupA;

    @ManyToOne
    @JoinColumn(name = "THE_GROUP")
    private Group invalidGroupB;

    @ManyToOne
    @JoinTable(name = "ForeignKeyEntity_GROUP", joinColumns = @JoinColumn(name = "THE_GROUP"))
    private Group invalidGroupC;

    @ForeignKey(name = "FK___ForeignKeyEntity___THE_GROUP")
    @ManyToOne
    @JoinColumn(name = "THE_GROUP")
    private Group validGroupA;

    @ForeignKey(name = "FK___ForeignKeyEntity_GROUP___ForeignKeyEntity", inverseName = "FK___ForeignKeyEntity_GROUP___GROUPS")
    @ManyToOne
    @JoinTable(name = "ForeignKeyEntity_GROUP", joinColumns = @JoinColumn(name = "ForeignKeyEntity"), inverseJoinColumns = @JoinColumn(name = "GROUPS"))
    private Group validGroupB;
}
