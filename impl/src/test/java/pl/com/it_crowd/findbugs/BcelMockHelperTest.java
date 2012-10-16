package pl.com.it_crowd.findbugs;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.Type;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class BcelMockHelperTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void mockAnnotationEntry()
    {
        //        Given
        final AnnotationEntry entryA = BcelMockHelper.mockAnnotationEntry("javax.persistence.Column", true, "nullable=true");
        final AnnotationEntry entryB = BcelMockHelper.mockAnnotationEntry("javax.persistence.Column", true, "name=EMAIL", "nullable=true");

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

    @Test
    public void mockField()
    {
//        Given
        final Field fieldA = BcelMockHelper.mockField("Ljava/lang/String;");
        final String expectedAnnotaionType = "org.junit.Test";
        final String expectedAnnotaionType2 = "javax.persistence.Column";
        final Field fieldB = BcelMockHelper.mockField("Ljava/util/Collection;",
            BcelMockHelper.mockAnnotationEntry(expectedAnnotaionType, "timeout=23", "wtf=ftw"),
            BcelMockHelper.mockAnnotationEntry(expectedAnnotaionType2, "name=ID"));

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
