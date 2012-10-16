package pl.com.it_crowd.findbugs;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.Field;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class BcelMockHelper {
// -------------------------- STATIC METHODS --------------------------

    public static AnnotationEntry mockAnnotationEntry(String annotationType, String... properties)
    {
        return mockAnnotationEntry(annotationType, true, properties);
    }

    public static AnnotationEntry mockAnnotationEntry(String annotationType, boolean runtimeVisible, String... properties)
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

    public static ConstantPool mockConstantPool(int index, byte tag, final Constant value)
    {
        final ConstantPool pool = mock(ConstantPool.class);
        when(pool.getConstant(index, tag)).thenReturn(value);
        return pool;
    }

    public static Field mockField(String type, AnnotationEntry... annotationEntries)
    {
        final org.apache.bcel.classfile.Annotations annotations = mock(org.apache.bcel.classfile.Annotations.class);
        when(annotations.getAnnotationEntries()).thenReturn(annotationEntries);
        return new Field(0, 0, 1, new Attribute[]{annotations}, mockConstantPool(1, Constants.CONSTANT_Utf8, new ConstantUtf8(type)));
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private BcelMockHelper()
    {
    }
}
