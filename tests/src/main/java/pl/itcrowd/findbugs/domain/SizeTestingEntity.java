package pl.itcrowd.findbugs.domain;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("UnusedDeclaration")
@Table(name = "SizeTestingEntity")
@Entity
public class SizeTestingEntity {
// ------------------------------ FIELDS ------------------------------

    @Size
    @Column(length = 34)
    private String invalidD;

    @Size
    @Column
    private String invalidE;

    @Size(max = 34)
    @Column(length = 34)
    private String validA;

    @Size(max = 255)
    @Column
    private String validB;

    @Size(max = 3)
    private String validC;

    @NotNull
    @NotEmpty
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "TEXT", nullable = false)
    private String validD;
}
