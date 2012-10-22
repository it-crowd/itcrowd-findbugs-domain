package pl.com.it_crowd.findbugs;

import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ArrayElementValue;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.SimpleElementValue;
import org.apache.bcel.generic.Type;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_COLUMN;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_TABLE;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_JOIN_COLUMNS;
import static pl.com.it_crowd.findbugs.Annotations.JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_NAME;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_LANG_STRING;
import static pl.com.it_crowd.findbugs.TypeStrings.JAVA_UTIL_COLLECTION;
import static pl.com.it_crowd.findbugs.TypeStrings.ORG_JUNIT_TEST;

public final class BcelMockHelperTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void makeAnnotationProperty()
    {
//        Given
        final ElementValuePair pairA = BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, true);
        final ElementValuePair pairB = BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "columnA");
        final ElementValuePair pairC = BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "columnB");
        final AnnotationEntry joinColumnA = BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN, pairB);
        final AnnotationEntry joinColumnB = BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN, pairC);
        final ElementValuePair pairD = BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_JOIN_COLUMNS, joinColumnA, joinColumnB);

//        When
        final String pairAName = pairA.getNameString();
        final String pairBName = pairB.getNameString();
        final String pairCName = pairC.getNameString();
        final String pairDName = pairD.getNameString();

        final ElementValue pairAValue = pairA.getValue();
        final ElementValue pairBValue = pairB.getValue();
        final ElementValue pairCValue = pairC.getValue();
        final ElementValue pairDValue = pairD.getValue();

//        Then
        assertEquals(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, pairAName);
        assertEquals(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, pairBName);
        assertEquals(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, pairCName);
        assertEquals(JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_JOIN_COLUMNS, pairDName);

        assertTrue(pairAValue instanceof SimpleElementValue);
        assertTrue(pairBValue instanceof SimpleElementValue);
        assertTrue(pairCValue instanceof SimpleElementValue);
        assertTrue(pairDValue instanceof ArrayElementValue);

        assertEquals(true, ((SimpleElementValue) pairAValue).getValueBoolean());
        assertEquals("columnA", ((SimpleElementValue) pairBValue).getValueString());
        assertEquals("columnB", ((SimpleElementValue) pairCValue).getValueString());
        assertEquals(2, ((ArrayElementValue) pairDValue).getElementValuesArraySize());
        assertTrue(((ArrayElementValue) pairDValue).getElementValuesArray()[0] instanceof AnnotationElementValue);
        assertTrue(((ArrayElementValue) pairDValue).getElementValuesArray()[1] instanceof AnnotationElementValue);
    }

    @Test
    public void mockAnnotationEntry()
    {
        //        Given
        final AnnotationEntry entryA = BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, true,
            BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, true));
        final AnnotationEntry entryB = BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN, false,
            BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "EMAIL"),
            BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, true));
        final AnnotationEntry entryC = BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_COLUMN,
            BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "EMAIL"),
            BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, true));
        final AnnotationEntry entryD = BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_TABLE,
            BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_NAME, "EMAIL"),
            BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_JOIN_COLUMNS,
                BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN,
                    BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "A")),
                BcelMockHelper.mockAnnotationEntry(JAVAX_PERSISTENCE_JOIN_COLUMN,
                    BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "B"))));

        //        When
        final ElementValuePair[] elementValuePairsA = entryA.getElementValuePairs();
        final ElementValuePair[] elementValuePairsB = entryB.getElementValuePairs();
        final ElementValuePair[] elementValuePairsD = entryD.getElementValuePairs();
        final boolean runtimeVisibleA = entryA.isRuntimeVisible();
        final boolean runtimeVisibleB = entryB.isRuntimeVisible();
        final boolean runtimeVisibleC = entryC.isRuntimeVisible();
        final boolean runtimeVisibleD = entryD.isRuntimeVisible();

        //        Then
        assertTrue(runtimeVisibleA);
        assertNotNull(elementValuePairsA);
        assertEquals(1, elementValuePairsA.length);
        assertNotNull(elementValuePairsA[0]);
        assertEquals(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, elementValuePairsA[0].getNameString());
        assertNotNull(elementValuePairsA[0].getValue());
        assertEquals("true", elementValuePairsA[0].getValue().stringifyValue());
        //--
        assertFalse(runtimeVisibleB);
        assertNotNull(elementValuePairsB);
        assertEquals(2, elementValuePairsB.length);
        assertNotNull(elementValuePairsB[0]);
        assertEquals(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, elementValuePairsB[0].getNameString());
        assertNotNull(elementValuePairsB[0].getValue());
        assertEquals("EMAIL", elementValuePairsB[0].getValue().stringifyValue());
        assertNotNull(elementValuePairsB[1]);
        assertEquals(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE, elementValuePairsB[1].getNameString());
        assertNotNull(elementValuePairsB[1].getValue());
        assertEquals("true", elementValuePairsB[1].getValue().stringifyValue());
        //--
        assertTrue(runtimeVisibleC);
        //--
        assertTrue(runtimeVisibleD);
        assertNotNull(elementValuePairsD);
        assertEquals(2, elementValuePairsD.length);
    }

    @Test
    public void mockField()
    {
//        Given
        final Field fieldA = BcelMockHelper.mockField(JAVA_LANG_STRING);
        final String expectedAnnotaionType = ORG_JUNIT_TEST;
        final String expectedAnnotaionType2 = JAVAX_PERSISTENCE_COLUMN;
        final Field fieldB = BcelMockHelper.mockField(JAVA_UTIL_COLLECTION,
            BcelMockHelper.mockAnnotationEntry(expectedAnnotaionType, BcelMockHelper.makeAnnotationProperty("timeout", 23),
                BcelMockHelper.makeAnnotationProperty("wtf", "ftw")),
            BcelMockHelper.mockAnnotationEntry(expectedAnnotaionType2, BcelMockHelper.makeAnnotationProperty(JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME, "ID")));

//        When
        final AnnotationEntry[] annotationEntriesA = fieldA.getAnnotationEntries();
        final AnnotationEntry[] annotationEntriesB = fieldB.getAnnotationEntries();
        final Type typeA = fieldA.getType();
        final Type typeB = fieldB.getType();

//        Then
        assertEquals(Type.getType(String.class), typeA);
        assertNotNull(annotationEntriesA);
        assertEquals(0, annotationEntriesA.length);

        assertEquals(Type.getType(Collection.class), typeB);
        assertNotNull(annotationEntriesB);
        assertEquals(2, annotationEntriesB.length);

        assertEquals(expectedAnnotaionType, annotationEntriesB[0].getAnnotationType());
        assertNotNull(annotationEntriesB[0].getElementValuePairs());
        assertEquals(2, annotationEntriesB[0].getElementValuePairs().length);
        assertEquals("timeout", annotationEntriesB[0].getElementValuePairs()[0].getNameString());
        assertEquals("wtf", annotationEntriesB[0].getElementValuePairs()[1].getNameString());

        assertEquals(expectedAnnotaionType2, annotationEntriesB[1].getAnnotationType());
        assertNotNull(annotationEntriesB[1].getElementValuePairs());
        assertEquals(1, annotationEntriesB[1].getElementValuePairs().length);
        assertEquals("name", annotationEntriesB[1].getElementValuePairs()[0].getNameString());
    }
}
