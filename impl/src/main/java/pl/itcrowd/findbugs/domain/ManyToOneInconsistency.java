package pl.itcrowd.findbugs.domain;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.FieldOrMethod;

public class ManyToOneInconsistency extends EntityAnnotationDetector {
// --------------------------- CONSTRUCTORS ---------------------------

    public ManyToOneInconsistency(BugReporter bugReporter)
    {
        super(bugReporter);
    }

    @Override
    protected String getBugType()
    {
        return "MANY_TO_ONE_INCONSISTENCY";
    }

    @Override
    protected boolean isInvalid(FieldOrMethod member)
    {
        return !Validator.validateManyToOne(member);
    }
}
