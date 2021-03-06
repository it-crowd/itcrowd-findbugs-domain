package pl.itcrowd.findbugs.domain;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import static pl.itcrowd.findbugs.domain.Annotations.JAVAX_PERSISTENCE_ENTITY;

public abstract class EntityAnnotationDetector extends BytecodeScanningDetector {
// ------------------------------ FIELDS ------------------------------

    protected BugReporter bugReporter;

    protected boolean entity;

// --------------------------- CONSTRUCTORS ---------------------------

    public EntityAnnotationDetector(BugReporter bugReporter)
    {
        this.bugReporter = bugReporter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void visit(JavaClass obj)
    {
        for (AnnotationEntry entry : obj.getAnnotationEntries()) {
            entity |= JAVAX_PERSISTENCE_ENTITY.equals(entry.getAnnotationType());
        }
        super.visit(obj);
    }

    @Override
    public void visit(Field obj)
    {
        if (!entity) {
            return;
        }
        if (isInvalid(obj)) {
            bugReporter.reportBug(new BugInstance(this, getBugType(), HIGH_PRIORITY).addClass(this).addField(this));
        }
    }

    @Override
    public void visit(Method obj)
    {
        if (!entity) {
            return;
        }
        if (isInvalid(obj)) {
            bugReporter.reportBug(new BugInstance(this, getBugType(), HIGH_PRIORITY).addClass(this).addMethod(this));
        }
    }

    protected abstract String getBugType();

    protected abstract boolean isInvalid(FieldOrMethod member);
}
