package pl.com.it_crowd.findbugs;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.Type;
import org.apache.commons.lang.StringUtils;

import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_DEFAULT_LENGTH;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_DEFAULT_NULLABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_ENTITY;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_ID;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_COLUMN;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_TABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_INVERSE_JOIN_COLUMNS;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_JOIN_COLUMNS;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_NAME;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_MANY_TO_ONE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_MANY_TO_ONE_ATTRIBUTE_OPTIONAL;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_MANY_TO_ONE_DEFAULT_OPTIONAL;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_TABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE_DEFAULT_MAX;
import static pl.com.it_crowd.findbugs.Annotations.ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY;
import static pl.com.it_crowd.findbugs.Annotations.ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY_ATTRIBUTE_INVERSE_NAME;
import static pl.com.it_crowd.findbugs.Annotations.ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY_ATTRIBUTE_NAME;
import static pl.com.it_crowd.findbugs.BcelHelper.extractAttributeStringValue;
import static pl.com.it_crowd.findbugs.BcelHelper.getAnnotationPropertyValue;
import static pl.com.it_crowd.findbugs.BcelHelper.getAnnotationPropertyValue2;
import static pl.com.it_crowd.findbugs.BcelHelper.getType;
import static pl.com.it_crowd.findbugs.BcelHelper.isArray;
import static pl.com.it_crowd.findbugs.BcelHelper.isCollection;
import static pl.com.it_crowd.findbugs.BcelHelper.isJavaxPersistenceColumnOrJoinColumn;
import static pl.com.it_crowd.findbugs.BcelHelper.isMap;
import static pl.com.it_crowd.findbugs.BcelHelper.isSingleValue;
import static pl.com.it_crowd.findbugs.BcelHelper.isString;

public final class Validator {
// -------------------------- STATIC METHODS --------------------------

    public static boolean validateForeignKey(FieldOrMethod member, String tableName)
    {
        boolean foreignKeyAnnotationPresent = false;
        boolean joinColumnAnnotationPresent = false;
        boolean joinTableAnnotationPresent = false;
        boolean fkNameOK;
        boolean fkInverseNameOK;
        boolean fkInverseNameAttributeSet;
        boolean singleJoinColumn = false;
        String expectedFkName = "";
        String expectedFkInverseName = "";
        String fkName = "";
        String fkInverseName = "";
        String joinColumnName;
        String inverseJoinColumnName;
        for (AnnotationEntry entry : member.getAnnotationEntries()) {
            if (JAVAX_PERSISTENCE_JOIN_COLUMN.equals(entry.getAnnotationType())) {
                joinColumnAnnotationPresent = true;
                joinColumnName = getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME);
                expectedFkName = "FK___" + tableName + "___" + joinColumnName;
            } else if (JAVAX_PERSISTENCE_JOIN_TABLE.equals(entry.getAnnotationType())) {
                joinTableAnnotationPresent = true;
                final ElementValue joinColumns = getAnnotationPropertyValue2(entry, JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_JOIN_COLUMNS);
                final ElementValue inverseJoinColumns = getAnnotationPropertyValue2(entry, JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_INVERSE_JOIN_COLUMNS);
                singleJoinColumn = isSingleValue(joinColumns);
                if (singleJoinColumn) {
                    String joinTableName = getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_NAME);
                    joinColumnName = extractAttributeStringValue(joinColumns, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME);
                    inverseJoinColumnName = extractAttributeStringValue(inverseJoinColumns, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME);
                    expectedFkName = "FK___" + joinTableName + "___" + joinColumnName;
                    expectedFkInverseName = "FK___" + joinTableName + "___" + inverseJoinColumnName;
                }
            } else if (ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY.equals(entry.getAnnotationType())) {
                foreignKeyAnnotationPresent = true;
                fkName = getAnnotationPropertyValue(entry, ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY_ATTRIBUTE_NAME);
                fkInverseName = getAnnotationPropertyValue(entry, ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY_ATTRIBUTE_INVERSE_NAME);
            }
        }
        fkNameOK = expectedFkName.equals(fkName);
        fkInverseNameOK = expectedFkInverseName.equals(fkInverseName);
        fkInverseNameAttributeSet = fkInverseName != null;
        if (joinColumnAnnotationPresent) {
            return foreignKeyAnnotationPresent && fkNameOK && !fkInverseNameAttributeSet;
        } else {
            return !joinTableAnnotationPresent || !singleJoinColumn || foreignKeyAnnotationPresent && fkNameOK && fkInverseNameOK;
        }
    }

    public static boolean validateManyToOne(FieldOrMethod member)
    {
        boolean oneToManyAnnotationPresent = false;
        boolean joinColumnAnnotationPresent = false;
        boolean joinTableAnnotationPresent = false;
        boolean nullableColumn = false;
        boolean optionalManyToOne = false;
        for (AnnotationEntry entry : member.getAnnotationEntries()) {
            if (JAVAX_PERSISTENCE_MANY_TO_ONE.equals(entry.getAnnotationType())) {
                oneToManyAnnotationPresent = true;
                optionalManyToOne = Boolean.parseBoolean(BcelHelper.getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_MANY_TO_ONE_ATTRIBUTE_OPTIONAL,
                    JAVAX_PERSISTENCE_MANY_TO_ONE_DEFAULT_OPTIONAL));
            } else if (JAVAX_PERSISTENCE_JOIN_COLUMN.equals(entry.getAnnotationType())) {
                joinColumnAnnotationPresent = true;
                nullableColumn = Boolean.parseBoolean(
                    BcelHelper.getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, JAVAX_PERSISTENCE_COLUMN_DEFAULT_NULLABLE));
            } else if (JAVAX_PERSISTENCE_JOIN_TABLE.equals(entry.getAnnotationType())) {
                joinTableAnnotationPresent = true;
            }
        }
        return joinTableAnnotationPresent || (joinColumnAnnotationPresent || !oneToManyAnnotationPresent) && (!oneToManyAnnotationPresent
            || nullableColumn == optionalManyToOne);
    }

    public static boolean validateNotEmpty(FieldOrMethod member)
    {
        boolean notEmptyAnnotationPresent = false;
        for (AnnotationEntry entry : member.getAnnotationEntries()) {
            if (JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY.equals(entry.getAnnotationType())) {
                notEmptyAnnotationPresent = true;
            }
        }
        Type type = getType(member);
        boolean notEmptyCandidate = isString(type) || isArray(type) || isCollection(type) || isMap(type);
        return !notEmptyAnnotationPresent || notEmptyCandidate;
    }

    public static boolean validateNotNull(FieldOrMethod member)
    {
        boolean joinTableAnnotationPresent = false;
        boolean columnAnnotationPresent = false;
        boolean idAnnotationPresent = false;
        boolean notNullAnnotationPresent = false;
        boolean notNullColumn = false;
        boolean optionalManyToOne = true;
        for (AnnotationEntry entry : member.getAnnotationEntries()) {
            if (JAVAX_PERSISTENCE_ID.equals(entry.getAnnotationType())) {
                idAnnotationPresent = true;
            } else if (JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL.equals(entry.getAnnotationType())) {
                notNullAnnotationPresent = true;
            } else if (isJavaxPersistenceColumnOrJoinColumn(entry)) {
                columnAnnotationPresent = true;
                notNullColumn = !Boolean.parseBoolean(
                    BcelHelper.getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, JAVAX_PERSISTENCE_COLUMN_DEFAULT_NULLABLE));
            } else if (JAVAX_PERSISTENCE_JOIN_TABLE.equals(entry.getAnnotationType())) {
                joinTableAnnotationPresent = true;
            } else if (JAVAX_PERSISTENCE_MANY_TO_ONE.equals(entry.getAnnotationType())) {
                optionalManyToOne = Boolean.parseBoolean(BcelHelper.getAnnotationPropertyValue(entry, JAVAX_PERSISTENCE_MANY_TO_ONE_ATTRIBUTE_OPTIONAL,
                    JAVAX_PERSISTENCE_MANY_TO_ONE_DEFAULT_OPTIONAL));
            }
        }
        boolean notNullRequired = columnAnnotationPresent && notNullColumn || joinTableAnnotationPresent && !optionalManyToOne;
        return idAnnotationPresent || !(columnAnnotationPresent || joinTableAnnotationPresent) || !(notNullRequired && !notNullAnnotationPresent
            || !notNullRequired && notNullAnnotationPresent);
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

    public static boolean validateTable(JavaClass obj)
    {
        boolean entity = false;
        for (AnnotationEntry entry : obj.getAnnotationEntries()) {
            entity |= JAVAX_PERSISTENCE_ENTITY.equals(entry.getAnnotationType());
            if (JAVAX_PERSISTENCE_TABLE.equals(entry.getAnnotationType())) {
                if (StringUtils.isEmpty(BcelHelper.getAnnotationPropertyValue(entry, "name"))) {
                    continue;
                }
                return true;
            }
        }
        return !entity;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private Validator()
    {
    }
}
