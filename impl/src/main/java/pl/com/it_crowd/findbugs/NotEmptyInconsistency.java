package pl.com.it_crowd.findbugs;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import static pl.com.it_crowd.findbugs.BcelHelper.isArray;
import static pl.com.it_crowd.findbugs.BcelHelper.isCollection;
import static pl.com.it_crowd.findbugs.BcelHelper.isJavaxPersistenceColumn;
import static pl.com.it_crowd.findbugs.BcelHelper.isMap;
import static pl.com.it_crowd.findbugs.BcelHelper.isString;

public class NotEmptyInconsistency extends EntityAnnotationDetector {
// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Construct a ClassNodeDetector. The bugReporter is passed to the
     * constructor and stored in a protected final field.
     *
     * @param bugReporter the BugReporter that bug should be reporter to.
     */
    public NotEmptyInconsistency(BugReporter bugReporter)
    {
        super(bugReporter);
    }

    @Override
    protected String getBugType()
    {
        return "NOT_EMPTY_INCONSISTENCY";
    }

    private Type getType(FieldOrMethod obj)
    {
        if (obj instanceof Field) {
            return ((Field) obj).getType();
        } else {
            return ((Method) obj).getReturnType();
        }
    }

    protected boolean isInvalid(FieldOrMethod obj)
    {
        boolean columnAnnotationPresent = false;
        boolean notEmptyAnnotationPresent = false;
        boolean notNullColumn = false;
        for (AnnotationEntry entry : obj.getAnnotationEntries()) {
            if ("Lorg/hibernate/validator/constraints/NotEmpty;".equals(entry.getAnnotationType())) {
                notEmptyAnnotationPresent = true;
            } else if (isJavaxPersistenceColumn(entry)) {
                columnAnnotationPresent = true;
                notNullColumn = "false".equals(BcelHelper.getAnnotationPropertyValue(entry, "nullable"));
            }
        }
        Type type = getType(obj);
        boolean notEmptyCandidate = isString(type) || isArray(type) || isCollection(type) || isMap(type);
        return columnAnnotationPresent && (notNullColumn && notEmptyCandidate && !notEmptyAnnotationPresent || !notNullColumn && notEmptyAnnotationPresent);
    }
}
