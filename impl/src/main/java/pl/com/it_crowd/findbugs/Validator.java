package pl.com.it_crowd.findbugs;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.generic.Type;

import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_DEFAULT_LENGTH;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE_DEFAULT_MAX;
import static pl.com.it_crowd.findbugs.BcelHelper.getType;
import static pl.com.it_crowd.findbugs.BcelHelper.isArray;
import static pl.com.it_crowd.findbugs.BcelHelper.isCollection;
import static pl.com.it_crowd.findbugs.BcelHelper.isJavaxPersistenceColumnOrJoinColumn;
import static pl.com.it_crowd.findbugs.BcelHelper.isMap;
import static pl.com.it_crowd.findbugs.BcelHelper.isString;

public final class Validator {
// -------------------------- STATIC METHODS --------------------------

    public static boolean validateNotEmpty(FieldOrMethod member)
    {
        boolean notEmptyAnnotationPresent = false;
        for (AnnotationEntry entry : member.getAnnotationEntries()) {
            if (Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY.equals(entry.getAnnotationType())) {
                notEmptyAnnotationPresent = true;
            }
        }
        Type type = getType(member);
        boolean notEmptyCandidate = isString(type) || isArray(type) || isCollection(type) || isMap(type);
        return !notEmptyAnnotationPresent || notEmptyCandidate;
    }

    public static boolean validateNotNull(FieldOrMethod member)
    {
        boolean columnAnnotationPresent = false;
        boolean notNullAnnotationPresent = false;
        boolean notNullColumn = false;
        for (AnnotationEntry entry : member.getAnnotationEntries()) {
            if (Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL.equals(entry.getAnnotationType())) {
                notNullAnnotationPresent = true;
            } else if (isJavaxPersistenceColumnOrJoinColumn(entry)) {
                columnAnnotationPresent = true;
                notNullColumn = "false".equals(BcelHelper.getAnnotationPropertyValue(entry, "nullable"));
            }
        }
        return !columnAnnotationPresent || !(notNullColumn && !notNullAnnotationPresent || !notNullColumn && notNullAnnotationPresent);
    }

    public static boolean validateSize(FieldOrMethod member)
    {
        boolean columnAnnotationPresent = false;
        boolean sizeAnnotationPresent = false;
        String columnLength = null;
        String sizeMax = null;
        for (AnnotationEntry entry : member.getAnnotationEntries()) {
            if (JAVAX_VALIDATION_CONSTRAINTS_SIZE.equals(entry.getAnnotationType())) {
                sizeAnnotationPresent = true;
                sizeMax = BcelHelper.getAnnotationPropertyValue(entry, JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX,
                    JAVAX_VALIDATION_CONSTRAINTS_SIZE_DEFAULT_MAX);
            } else if (isJavaxPersistenceColumnOrJoinColumn(entry)) {
                columnAnnotationPresent = true;
                columnLength = BcelHelper.getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH, JAVAX_PERSISTENCE_COLUMN_DEFAULT_LENGTH);
            }
        }
        Type type = getType(member);
        final boolean string = isString(type);
        boolean sizeCandidate = string || isArray(type) || isCollection(type) || isMap(type);
        if (!columnAnnotationPresent) {
            return true;
        } else if (sizeAnnotationPresent) {
            return sizeCandidate && (!string || columnLength.equals(sizeMax));
        } else {
            return !string;
        }
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private Validator()
    {
    }
}