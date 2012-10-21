package pl.com.it_crowd.findbugs;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_ENTITY;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_COLUMN;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_MANY_TO_ONE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_MANY_TO_ONE_ATTRIBUTE_OPTIONAL;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_TABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX;
import static pl.com.it_crowd.findbugs.BcelHelper.makeAnnotationProperty;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockAnnotationEntry;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockField;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockJavaClass;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_LANG_INTEGER;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_LANG_STRING;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_UTIL_ARRAY_LIST;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_UTIL_HASH_MAP;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_UTIL_LIST;
import static pl.com.it_crowd.findbugs.TypeStrings.ORG_JUNIT_TEST;
import static pl.com.it_crowd.findbugs.TypeStrings.ROW_INT;
import static pl.com.it_crowd.findbugs.TypeStrings.ROW_INT_ARRAY;

public class ValidatorTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void validateManyToOne()
    {
        //        Given
        final Field fieldA = mockField(JAVA_UTIL_LIST,
            mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "GROUPS")),
            mockAnnotationEntry(JAVAX_PERSISTENCE_MANY_TO_ONE));
        final Field fieldB = mockField(JAVA_UTIL_LIST,
            mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, false)),
            mockAnnotationEntry(JAVAX_PERSISTENCE_MANY_TO_ONE, makeAnnotationProperty(JAVAX_PERSISTENCE_MANY_TO_ONE_ATTRIBUTE_OPTIONAL, false)));
        final Field fieldC = mockField(JAVA_UTIL_LIST,
            mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, false)),
            mockAnnotationEntry(JAVAX_PERSISTENCE_MANY_TO_ONE));
        final Field fieldD = mockField(JAVA_UTIL_LIST,
            mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, false)),
            mockAnnotationEntry(JAVAX_PERSISTENCE_MANY_TO_ONE, makeAnnotationProperty(JAVAX_PERSISTENCE_MANY_TO_ONE_ATTRIBUTE_OPTIONAL, true)));
        final Field fieldE = mockField(JAVA_UTIL_LIST, mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN));

        //        When
        final boolean resultA = Validator.validateManyToOne(fieldA);
        final boolean resultB = Validator.validateManyToOne(fieldB);
        final boolean resultC = Validator.validateManyToOne(fieldC);
        final boolean resultD = Validator.validateManyToOne(fieldD);
        final boolean resultE = Validator.validateManyToOne(fieldE);

        //        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertFalse(resultC);
        assertFalse(resultD);
        assertTrue(resultE);
    }

    @Test
    public void validateNotEmpty()
    {
//        Given
        final Field fieldA = mockField(JAVA_LANG_STRING);
        final Field fieldB = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY));
        final Field fieldC = mockField(JAVA_LANG_INTEGER, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY));
        final Field fieldD = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldE = mockField(JAVA_UTIL_ARRAY_LIST, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldF = mockField(JAVA_UTIL_HASH_MAP, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldG = mockField(ROW_INT_ARRAY, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY));
        final Field fieldH = mockField(JAVA_LANG_INTEGER, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldI = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldJ = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY), mockAnnotationEntry(ORG_JUNIT_TEST));


//        When
        final boolean resultA = Validator.validateNotEmpty(fieldA);
        final boolean resultB = Validator.validateNotEmpty(fieldB);
        final boolean resultC = Validator.validateNotEmpty(fieldC);
        final boolean resultD = Validator.validateNotEmpty(fieldD);
        final boolean resultE = Validator.validateNotEmpty(fieldE);
        final boolean resultF = Validator.validateNotEmpty(fieldF);
        final boolean resultG = Validator.validateNotEmpty(fieldG);
        final boolean resultH = Validator.validateNotEmpty(fieldH);
        final boolean resultI = Validator.validateNotEmpty(fieldI);
        final boolean resultJ = Validator.validateNotEmpty(fieldJ);

//        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertFalse(resultC);
        assertTrue(resultD);
        assertTrue(resultE);
        assertTrue(resultF);
        assertTrue(resultG);
        assertFalse(resultH);
        assertTrue(resultI);
        assertTrue(resultJ);
    }

    @Test
    public void validateNotNull()
    {
//        Given
        final Field fieldA = mockField(JAVA_LANG_STRING);
        final Field fieldB = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL));
        final Field fieldC = mockField(JAVA_LANG_INTEGER, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL));
        final Field fieldD = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldE = mockField(JAVA_UTIL_ARRAY_LIST, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldF = mockField(JAVA_UTIL_HASH_MAP, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, false)));
        final Field fieldG = mockField(ROW_INT_ARRAY, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL));
        final Field fieldH = mockField(JAVA_LANG_INTEGER, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldI = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldJ = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL), mockAnnotationEntry(ORG_JUNIT_TEST));
        final Field fieldK = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, false)));
        final Field fieldL = mockField(ROW_INT_ARRAY, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL), mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, false)));


//        When
        final boolean resultA = Validator.validateNotNull(fieldA);
        final boolean resultB = Validator.validateNotNull(fieldB);
        final boolean resultC = Validator.validateNotNull(fieldC);
        final boolean resultD = Validator.validateNotNull(fieldD);
        final boolean resultE = Validator.validateNotNull(fieldE);
        final boolean resultF = Validator.validateNotNull(fieldF);
        final boolean resultG = Validator.validateNotNull(fieldG);
        final boolean resultH = Validator.validateNotNull(fieldH);
        final boolean resultI = Validator.validateNotNull(fieldI);
        final boolean resultJ = Validator.validateNotNull(fieldJ);
        final boolean resultK = Validator.validateNotNull(fieldK);
        final boolean resultL = Validator.validateNotNull(fieldL);

//        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertTrue(resultC);
        assertFalse(resultD);
        assertFalse(resultE);
        assertTrue(resultF);
        assertTrue(resultG);
        assertFalse(resultH);
        assertTrue(resultI);
        assertTrue(resultJ);
        assertTrue(resultK);
        assertTrue(resultL);
    }

    @Test
    public void validateSize()
    {
//        Given
        final Field fieldA = mockField(JAVA_LANG_STRING);
        final Field fieldB = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE));
        final Field fieldC = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE), mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldD = mockField(JAVA_LANG_STRING,
            mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, makeAnnotationProperty(JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX, 255)),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldE = mockField(JAVA_UTIL_ARRAY_LIST,
            mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, makeAnnotationProperty(JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX, 30)),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH, 30)));
        final Field fieldF = mockField(JAVA_UTIL_HASH_MAP,
            mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, makeAnnotationProperty(JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX, 30)),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH, 30)));
        final Field fieldG = mockField(ROW_INT_ARRAY,
            mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, makeAnnotationProperty(JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX, 30)));
        final Field fieldH = mockField(JAVA_LANG_INTEGER,
            mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, makeAnnotationProperty(JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX, 30)),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH, 30)));
        final Field fieldI = mockField(JAVA_LANG_STRING, mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldJ = mockField(JAVA_LANG_STRING,
            mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, makeAnnotationProperty(JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX, 30)),
            mockAnnotationEntry(ORG_JUNIT_TEST, "timeout=30"));
        final Field fieldK = mockField(ROW_INT,
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, true)));


//        When
        final boolean resultA = Validator.validateSize(fieldA);
        final boolean resultB = Validator.validateSize(fieldB);
        final boolean resultC = Validator.validateSize(fieldC);
        final boolean resultD = Validator.validateSize(fieldD);
        final boolean resultE = Validator.validateSize(fieldE);
        final boolean resultF = Validator.validateSize(fieldF);
        final boolean resultG = Validator.validateSize(fieldG);
        final boolean resultH = Validator.validateSize(fieldH);
        final boolean resultI = Validator.validateSize(fieldI);
        final boolean resultJ = Validator.validateSize(fieldJ);
        final boolean resultK = Validator.validateSize(fieldK);

//        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertFalse(resultC);
        assertTrue(resultD);
        assertTrue(resultE);
        assertTrue(resultF);
        assertTrue(resultG);
        assertFalse(resultH);
        assertFalse(resultI);
        assertTrue(resultJ);
        assertTrue(resultK);
    }

    @Test
    public void validateTable()
    {
//        Given
        final JavaClass classA = mockJavaClass("SomeEntity", mockAnnotationEntry(JAVAX_PERSISTENCE_ENTITY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_TABLE, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "ENT")));
        final JavaClass classB = mockJavaClass("SomeEntity", mockAnnotationEntry(JAVAX_PERSISTENCE_ENTITY), mockAnnotationEntry(JAVAX_PERSISTENCE_TABLE));
        final JavaClass classC = mockJavaClass("SomeEntity", mockAnnotationEntry(JAVAX_PERSISTENCE_TABLE));

//        When
        final boolean resultA = Validator.validateTable(classA);
        final boolean resultB = Validator.validateTable(classB);
        final boolean resultC = Validator.validateTable(classC);

//        Then
        assertTrue(resultA);
        assertFalse(resultB);
        assertTrue(resultC);
    }
}
