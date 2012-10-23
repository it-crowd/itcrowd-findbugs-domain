package pl.itcrowd.findbugs.domain;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.FieldOrMethod;

public class NotEmptyInconsistency extends EntityAnnotationDetector {
// --------------------------- CONSTRUCTORS ---------------------------

    public NotEmptyInconsistency(BugReporter bugReporter)
    {
        super(bugReporter);
    }

    @Override
    protected String getBugType()
    {
        return "NOT_EMPTY_INCONSISTENCY";
    }

    protected boolean isInvalid(FieldOrMethod member)
    {
        return !Validator.validateNotEmpty(member);
    }
}
