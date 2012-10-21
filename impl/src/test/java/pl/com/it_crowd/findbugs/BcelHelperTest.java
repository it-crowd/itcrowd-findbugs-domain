package pl.com.it_crowd.findbugs;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.Type;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_GENERATED_VALUE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_COLUMN;
import static pl.com.it_crowd.findbugs.BcelHelper.makeAnnotationProperty;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockAnnotationEntry;
import static pl.com.it_crowd.findbugs.BcelMockHelper.mockConstantPool;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_LANG_STRING;

public class BcelHelperTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void getAnnotationPropertyValue() throws Exception
    {
//        Given
        final AnnotationEntry entryA = mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, true));
        final AnnotationEntry entryB = mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN,
            makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "PASSWORD_DIGEST"),
            makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, false), makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH, 35));

//        When
        final String valueAnullable = BcelHelper.getAnnotationPropertyValue(entryA, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE);
        final String valueBname = BcelHelper.getAnnotationPropertyValue(entryB, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME);
        final String valueBnullable = BcelHelper.getAnnotationPropertyValue(entryB, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE);
        final String valueBlength = BcelHelper.getAnnotationPropertyValue(entryB, JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH);
        final String valueBnonexistent = BcelHelper.getAnnotationPropertyValue(entryB, "fafarafa");
        final String valueBnull = BcelHelper.getAnnotationPropertyValue(entryB, null);

//        Then
        assertEquals("true", valueAnullable);
        assertEquals("PASSWORD_DIGEST", valueBname);
        assertEquals("false", valueBnullable);
        assertEquals("35", valueBlength);
        assertNull(valueBnonexistent);
        assertNull(valueBnull);
    }

    @Test
    public void getType() throws Exception
    {
//        Given
        final Field field = new Field(0, 0, 1, null, mockConstantPool(1, Constants.CONSTANT_Utf8, new ConstantUtf8(JAVA_LANG_STRING)));

//        When
        final Type type = BcelHelper.getType(field);

//        Then
        assertEquals(Type.getType(String.class), type);
    }

    @Test
    public void isArray() throws Exception
    {
//        Given
        final Type typeA = Type.getType(int[].class);
        final Type typeB = Type.getType(String[].class);
        final Type typeC = Type.getType(double[].class);
        final Type typeD = Type.getType(ArrayList.class);
        final Type typeE = Type.getType(Map.class);
        final Type typeF = Type.getType(int.class);

//        When
        final boolean resultA = BcelHelper.isArray(typeA);
        final boolean resultB = BcelHelper.isArray(typeB);
        final boolean resultC = BcelHelper.isArray(typeC);
        final boolean resultD = BcelHelper.isArray(typeD);
        final boolean resultE = BcelHelper.isArray(typeE);
        final boolean resultF = BcelHelper.isArray(typeF);

//        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertTrue(resultC);
        assertFalse(resultD);
        assertFalse(resultE);
        assertFalse(resultF);
    }

    @Test
    public void isCollection() throws Exception
    {
//        Given
        final Type typeA = Type.getType(List.class);
        final Type typeB = Type.getType(ArrayList.class);
        final Type typeC = Type.getType(Collection.class);
        final Type typeD = Type.getType(Set.class);
        final Type typeE = Type.getType(Map.class);
        final Type typeF = Type.getType(int.class);

//        When
        final boolean resultA = BcelHelper.isCollection(typeA);
        final boolean resultB = BcelHelper.isCollection(typeB);
        final boolean resultC = BcelHelper.isCollection(typeC);
        final boolean resultD = BcelHelper.isCollection(typeD);
        final boolean resultE = BcelHelper.isCollection(typeE);
        final boolean resultF = BcelHelper.isCollection(typeF);

//        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertTrue(resultC);
        assertTrue(resultD);
        assertFalse(resultE);
        assertFalse(resultF);
    }

    @Test
    public void isJavaxPersistenceColumn() throws Exception
    {
//        Given
        final AnnotationEntry entryA = mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN);
        final AnnotationEntry entryB = mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN);

//        When
        final boolean valueA = BcelHelper.isJavaxPersistenceColumn(entryA);
        final boolean valueB = BcelHelper.isJavaxPersistenceColumn(entryB);

//        Then
        assertTrue(valueA);
        assertFalse(valueB);
    }

    @Test
    public void isJavaxPersistenceColumnOrJoinColumn() throws Exception
    {
//        Given
        final AnnotationEntry entryA = mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN);
        final AnnotationEntry entryB = mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN);
        final AnnotationEntry entryC = mockAnnotationEntry(JAVAX_PERSISTENCE_GENERATED_VALUE);

//        When
        final boolean valueA = BcelHelper.isJavaxPersistenceColumnOrJoinColumn(entryA);
        final boolean valueB = BcelHelper.isJavaxPersistenceColumnOrJoinColumn(entryB);
        final boolean valueC = BcelHelper.isJavaxPersistenceColumnOrJoinColumn(entryC);

//        Then
        assertTrue(valueA);
        assertTrue(valueB);
        assertFalse(valueC);
    }

    @Test
    public void isJavaxPersistenceJoinColumn() throws Exception
    {
//        Given
        final AnnotationEntry entryA = mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN);
        final AnnotationEntry entryB = mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN);

//        When
        final boolean valueA = BcelHelper.isJavaxPersistenceJoinColumn(entryA);
        final boolean valueB = BcelHelper.isJavaxPersistenceJoinColumn(entryB);

//        Then
        assertFalse(valueA);
        assertTrue(valueB);
    }

    @Test
    public void isMap() throws Exception
    {
//        Given
        final Type typeA = Type.getType(Map.class);
        final Type typeB = Type.getType(HashMap.class);
        final Type typeC = Type.getType(AbstractMap.class);
        final Type typeD = Type.getType(Set.class);
        final Type typeE = Type.getType(Collection.class);
        final Type typeF = Type.getType(int.class);

//        When
        final boolean resultA = BcelHelper.isMap(typeA);
        final boolean resultB = BcelHelper.isMap(typeB);
        final boolean resultC = BcelHelper.isMap(typeC);
        final boolean resultD = BcelHelper.isMap(typeD);
        final boolean resultE = BcelHelper.isMap(typeE);
        final boolean resultF = BcelHelper.isMap(typeF);

//        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertTrue(resultC);
        assertFalse(resultD);
        assertFalse(resultE);
        assertFalse(resultF);
    }

    @Test
    public void isString() throws Exception
    {
//        Given
        final Type typeA = Type.getType(String.class);
        final Type typeB = Type.getType("".getClass());
        final Type typeC = Type.getType(String[].class);
        final Type typeD = Type.getType(int.class);

//        When
        final boolean resultA = BcelHelper.isString(typeA);
        final boolean resultB = BcelHelper.isString(typeB);
        final boolean resultC = BcelHelper.isString(typeC);
        final boolean resultD = BcelHelper.isString(typeD);

//        Then
        assertTrue(resultA);
        assertTrue(resultB);
        assertFalse(resultC);
        assertFalse(resultD);
    }
}
