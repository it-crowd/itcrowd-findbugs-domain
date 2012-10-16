import org.apache.bcel.Constants;
import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.generic.Type;
import org.junit.Test;
import pl.com.it_crowd.findbugs.BcelHelper;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BcelHelperTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void getAnnotationPropertyValue() throws Exception
    {
//        Given
        final AnnotationEntry entryA = mockAnnotationEntry("javax.persistence.Column", true, "nullable=true");
        final AnnotationEntry entryB = mockAnnotationEntry("javax.persistence.Column", true, "name=PASSWORD_DIGEST", "nullable=false", "length=35");

//        When
        final String valueAnullable = BcelHelper.getAnnotationPropertyValue(entryA, "nullable");
        final String valueBname = BcelHelper.getAnnotationPropertyValue(entryB, "name");
        final String valueBnullable = BcelHelper.getAnnotationPropertyValue(entryB, "nullable");
        final String valueBlength = BcelHelper.getAnnotationPropertyValue(entryB, "length");
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

    @Test
    public void mockAnnotationEntry()
    {
//        Given
        final AnnotationEntry entryA = mockAnnotationEntry("javax.persistence.Column", true, "nullable=true");
        final AnnotationEntry entryB = mockAnnotationEntry("javax.persistence.Column", true, "name=EMAIL", "nullable=true");

//        When
        final ElementValuePair[] elementValuePairsA = entryA.getElementValuePairs();
        final ElementValuePair[] elementValuePairsB = entryB.getElementValuePairs();

//        Then
        assertNotNull(elementValuePairsA);
        assertEquals(1, elementValuePairsA.length);
        assertNotNull(elementValuePairsA[0]);
        assertEquals("nullable", elementValuePairsA[0].getNameString());
        assertNotNull(elementValuePairsA[0].getValue());
        assertEquals("true", elementValuePairsA[0].getValue().stringifyValue());
        //--
        assertNotNull(elementValuePairsB);
        assertEquals(2, elementValuePairsB.length);
        assertNotNull(elementValuePairsB[0]);
        assertEquals("name", elementValuePairsB[0].getNameString());
        assertNotNull(elementValuePairsB[0].getValue());
        assertEquals("EMAIL", elementValuePairsB[0].getValue().stringifyValue());
        assertNotNull(elementValuePairsB[1]);
        assertEquals("nullable", elementValuePairsB[1].getNameString());
        assertNotNull(elementValuePairsB[1].getValue());
        assertEquals("true", elementValuePairsB[1].getValue().stringifyValue());
    }

    private AnnotationEntry mockAnnotationEntry(String annotationType, boolean runtimeVisible, String... properties)
    {
        final AnnotationEntry entry = mock(AnnotationEntry.class);
        when(entry.getAnnotationType()).thenReturn(annotationType);
        when(entry.isRuntimeVisible()).thenReturn(runtimeVisible);
        final ArrayList<ElementValuePair> elementValuePairs = new ArrayList<ElementValuePair>();
        for (String property : properties) {
            final StringTokenizer tokenizer = new StringTokenizer(property, "=");
            final String key = tokenizer.nextToken();
            final String value = tokenizer.nextToken();
            final AnnotationElementValue elementValue = mock(AnnotationElementValue.class);
            when(elementValue.stringifyValue()).thenReturn(value);
            final ElementValuePair pair = new ElementValuePair(0, elementValue, mockConstantPool(0, Constants.CONSTANT_Utf8, new ConstantUtf8(key)));
            elementValuePairs.add(pair);
        }
        when(entry.getElementValuePairs()).thenReturn(elementValuePairs.toArray(new ElementValuePair[elementValuePairs.size()]));
        return entry;
    }

    private ConstantPool mockConstantPool(int index, byte tag, final Constant value)
    {
        final ConstantPool pool = mock(ConstantPool.class);
        when(pool.getConstant(index, tag)).thenReturn(value);
        return pool;
    }
}
