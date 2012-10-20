package pl.com.it_crowd.findbugs;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_ENTITY;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_TABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_VALIDATION_CONSTRAINTS_SIZE;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockAnnotationEntry;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockField;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockJavaClass;

public class ValidatorTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void validateEntityVsTable()
    {
//        Given
        final JavaClass classA = mockJavaClass("SomeEntity", mockAnnotationEntry(JAVAX_PERSISTENCE_ENTITY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_TABLE, "name=ENT"));
        final JavaClass classB = mockJavaClass("SomeEntity", mockAnnotationEntry(JAVAX_PERSISTENCE_ENTITY), mockAnnotationEntry(JAVAX_PERSISTENCE_TABLE));
        final JavaClass classC = mockJavaClass("SomeEntity", mockAnnotationEntry(JAVAX_PERSISTENCE_TABLE));

//        When
        final boolean resultA = Validator.validateEntityVsTable(classA);
        final boolean resultB = Validator.validateEntityVsTable(classB);
        final boolean resultC = Validator.validateEntityVsTable(classC);

//        Then
        assertTrue(resultA);
        assertFalse(resultB);
        assertTrue(resultC);
    }

    @Test
    public void validateNotEmpty()
    {
//        Given
        final Field fieldA = mockField("Ljava/lang/String;");
        final Field fieldB = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY));
        final Field fieldC = mockField("Ljava/lang/Integer;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY));
        final Field fieldD = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldE = mockField("Ljava/util/ArrayList;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldF = mockField("Ljava/util/HashMap;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldG = mockField("[I", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY));
        final Field fieldH = mockField("Ljava/lang/Integer;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldI = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldJ = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY),
            mockAnnotationEntry("Lorg/junit/Test;"));


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
        final Field fieldA = mockField("Ljava/lang/String;");
        final Field fieldB = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL));
        final Field fieldC = mockField("Ljava/lang/Integer;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL));
        final Field fieldD = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldE = mockField("Ljava/util/ArrayList;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldF = mockField("Ljava/util/HashMap;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, "nullable=false"));
        final Field fieldG = mockField("[I", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL));
        final Field fieldH = mockField("Ljava/lang/Integer;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldI = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldJ = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry("Lorg/junit/Test;"));
        final Field fieldK = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, "nullable=false"));


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
    }

    @Test
    public void validateSize()
    {
//        Given
        final Field fieldA = mockField("Ljava/lang/String;");
        final Field fieldB = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE));
        final Field fieldC = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldD = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, "max=255"),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldE = mockField("Ljava/util/ArrayList;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, "max=30"),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, "length=30"));
        final Field fieldF = mockField("Ljava/util/HashMap;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, "max=30"),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, "length=30"));
        final Field fieldG = mockField("[I", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, "max=30"));
        final Field fieldH = mockField("Ljava/lang/Integer;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, "max=30"),
            mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, "length=30"));
        final Field fieldI = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN));
        final Field fieldJ = mockField("Ljava/lang/String;", mockAnnotationEntry(JAVAX_VALIDATION_CONSTRAINTS_SIZE, "max=30"),
            mockAnnotationEntry("Lorg/junit/Test;", "timeout=30"));
        final Field fieldK = mockField("I", mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, "nullable=true"));


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
}
