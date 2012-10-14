package pl.com.it_crowd.findbugs;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.FieldOrMethod;

public class NotNullInconsistency extends EntityAnnotationDetector {
// --------------------------- CONSTRUCTORS ---------------------------

    public NotNullInconsistency(BugReporter bugReporter)
    {
        super(bugReporter);
    }

    @Override
    protected String getBugType()
    {
        return "NOT_NULL_INCONSISTENCY";
    }

    protected boolean isInvalid(FieldOrMethod obj)
    {
        boolean columnAnnotationPresent = false;
        boolean notNullAnnotationPresent = false;
        boolean notNullColumn = false;
        for (AnnotationEntry entry : obj.getAnnotationEntries()) {
            if ("Ljavax/validation/constraints/NotNull;".equals(entry.getAnnotationType())) {
                notNullAnnotationPresent = true;
            } else if ("Ljavax/persistence/Column;".equals(entry.getAnnotationType())) {
                columnAnnotationPresent = true;
                for (ElementValuePair pair : entry.getElementValuePairs()) {
                    notNullColumn |= "nullable".equals(pair.getNameString()) && "false".equalsIgnoreCase(pair.getValue().stringifyValue());
                }
            }
        }

        return columnAnnotationPresent && (notNullColumn && !notNullAnnotationPresent || !notNullColumn && notNullAnnotationPresent);
    }
}
