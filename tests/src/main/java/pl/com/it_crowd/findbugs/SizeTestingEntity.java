package pl.com.it_crowd.findbugs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

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
}
