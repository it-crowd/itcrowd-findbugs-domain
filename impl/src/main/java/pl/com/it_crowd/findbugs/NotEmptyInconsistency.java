package pl.com.it_crowd.findbugs;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

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

    protected boolean isInvalid(FieldOrMethod obj)
    {
        boolean columnAnnotationPresent = false;
        boolean notEmptyAnnotationPresent = false;
        boolean notNullColumn = false;
        for (AnnotationEntry entry : obj.getAnnotationEntries()) {
            if ("Lorg/hibernate/validator/constraints/NotEmpty;".equals(entry.getAnnotationType())) {
                notEmptyAnnotationPresent = true;
            } else if ("Ljavax/persistence/Column;".equals(entry.getAnnotationType())) {
                columnAnnotationPresent = true;
                for (ElementValuePair pair : entry.getElementValuePairs()) {
                    notNullColumn |= "nullable".equals(pair.getNameString()) && "false".equalsIgnoreCase(pair.getValue().stringifyValue());
                }
            }
        }
        Type type;
        if (obj instanceof Field) {
            type = ((Field) obj).getType();
        } else {
            type = ((Method) obj).getReturnType();
        }
        boolean isString = "java.lang.String".equals(type.toString());
        return columnAnnotationPresent && (notNullColumn && isString && !notEmptyAnnotationPresent || !notNullColumn && notEmptyAnnotationPresent);
    }
}
