package pl.com.it_crowd.findbugs;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import org.apache.bcel.classfile.JavaClass;

public class TableInconsistency extends BytecodeScanningDetector {
// ------------------------------ FIELDS ------------------------------

    public static final String BUG_TYPE = "TABLE_INCONSISTENCY";

    protected BugReporter bugReporter;

// --------------------------- CONSTRUCTORS ---------------------------

    public TableInconsistency(BugReporter bugReporter)
    {
        this.bugReporter = bugReporter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void visit(JavaClass obj)
    {
        if (!Validator.validateTable(obj)) {
            bugReporter.reportBug(new BugInstance(this, BUG_TYPE, HIGH_PRIORITY).addClass(this));
        }
    }
}
