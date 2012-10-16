package pl.com.it_crowd.findbugs;

import edu.umd.cs.findbugs.BugReporter;
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

    protected boolean isInvalid(FieldOrMethod member)
    {
        return !Validator.validateNotNull(member);
    }
}
