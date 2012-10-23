package pl.itcrowd.findbugs.domain;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;

import static pl.itcrowd.findbugs.domain.Annotations.JAVAX_PERSISTENCE_TABLE;
import static pl.itcrowd.findbugs.domain.Annotations.JAVAX_PERSISTENCE_TABLE_ATTRIBUTE_NAME;
import static pl.itcrowd.findbugs.domain.BcelHelper.getAnnotationPropertyValue;

public class ForeignKeyInconsistency extends EntityAnnotationDetector {
// ------------------------------ FIELDS ------------------------------

    private String tableName;

// --------------------------- CONSTRUCTORS ---------------------------

    public ForeignKeyInconsistency(BugReporter bugReporter)
    {
        super(bugReporter);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void visit(JavaClass obj)
    {
        tableName = "";
        super.visit(obj);
        for (AnnotationEntry entry : obj.getAnnotationEntries()) {
            if (JAVAX_PERSISTENCE_TABLE.equals(entry.getAnnotationType())) {
                tableName = getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_TABLE_ATTRIBUTE_NAME);
            }
        }
    }

    @Override
    protected String getBugType()
    {
        return "FOREIGN_KEY_INCONSISTENCY";
    }

    @Override
    protected boolean isInvalid(FieldOrMethod member)
    {
        return !Validator.validateForeignKey(member, tableName);
    }
}
