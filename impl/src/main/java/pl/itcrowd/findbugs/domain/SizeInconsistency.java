package pl.itcrowd.findbugs.domain;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.FieldOrMethod;

public class SizeInconsistency extends EntityAnnotationDetector {
// --------------------------- CONSTRUCTORS ---------------------------

    public SizeInconsistency(BugReporter bugReporter)
    {
        super(bugReporter);
    }

    @Override
    protected String getBugType()
    {
        return "SIZE_INCONSISTENCY";
    }

    protected boolean isInvalid(FieldOrMethod member)
    {
        return !Validator.validateSize(member);
    }
}
