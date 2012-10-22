package pl.com.it_crowd.findbugs;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ArrayElementValue;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.SimpleElementValue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class BcelMockHelper {
// -------------------------- STATIC METHODS --------------------------

    public static ElementValuePair makeAnnotationProperty(String name, Object value)
    {
        return new ElementValuePair(0, toElementValue(value), mockConstantPool(0, Constants.CONSTANT_Utf8, new ConstantUtf8(name)));
    }

    public static ElementValuePair makeAnnotationProperty(String name, Object... value)
    {
        return new ElementValuePair(0, toElementValue(value), mockConstantPool(0, Constants.CONSTANT_Utf8, new ConstantUtf8(name)));
    }

    public static AnnotationEntry mockAnnotationEntry(String annotationType, ElementValuePair... valuePairs)
    {
        return mockAnnotationEntry(annotationType, true, valuePairs);
    }

    public static AnnotationEntry mockAnnotationEntry(String annotationType, boolean runtimeVisible, ElementValuePair... valuePairs)
    {
        final AnnotationEntry entry = mock(AnnotationEntry.class);
        when(entry.getAnnotationType()).thenReturn(annotationType);
        when(entry.isRuntimeVisible()).thenReturn(runtimeVisible);
        when(entry.getElementValuePairs()).thenReturn(valuePairs);
        return entry;
    }

    public static ConstantPool mockConstantPool(ConstantPoolEntry... entries)
    {
        final ConstantPool pool = mock(ConstantPool.class);
        for (ConstantPoolEntry entry : entries) {
            if (entry.tag == Constants.CONSTANT_Class) {
                when(pool.getConstantString(entry.index, entry.tag)).thenCallRealMethod();
            }
            when(pool.getConstant(entry.index, entry.tag)).thenReturn(entry.value);
            when(pool.getConstant(entry.index)).thenReturn(entry.value);
        }
        return pool;
    }

    public static ConstantPool mockConstantPool(int index, byte tag, final Constant value)
    {
        return mockConstantPool(new ConstantPoolEntry(index, tag, value));
    }

    public static Field mockField(String type, AnnotationEntry... annotationEntries)
    {
        final org.apache.bcel.classfile.Annotations annotations = mock(org.apache.bcel.classfile.Annotations.class);
        when(annotations.getAnnotationEntries()).thenReturn(annotationEntries);
        return new Field(0, 0, 1, new Attribute[]{annotations}, mockConstantPool(1, Constants.CONSTANT_Utf8, new ConstantUtf8(type)));
    }

    public static JavaClass mockJavaClass(String className, AnnotationEntry... annotationEntries)
    {
        final org.apache.bcel.classfile.Annotations annotations = mock(org.apache.bcel.classfile.Annotations.class);
        when(annotations.getAnnotationEntries()).thenReturn(annotationEntries);
        final ConstantPool constantPool = mockConstantPool(new ConstantPoolEntry(0, Constants.CONSTANT_Class, new ConstantClass(1)),
            new ConstantPoolEntry(1, Constants.CONSTANT_Utf8, new ConstantUtf8(className)));
        return new JavaClass(0, 0, "", 0, 0, 0, constantPool, new int[0], new Field[0], new Method[0], new Attribute[]{annotations});
    }

    public static ElementValue toElementValue(Object value)
    {
        if (value instanceof AnnotationEntry) {
            return new AnnotationElementValue(ElementValue.ANNOTATION, (AnnotationEntry) value, null);
        } else if (value instanceof Object[]) {
            ElementValue[] elementValues = new ElementValue[((Object[]) value).length];
            Object[] valueArray = (Object[]) value;
            for (int i = 0, value1Length = (valueArray).length; i < value1Length; i++) {
                elementValues[i] = toElementValue(valueArray[i]);
            }
            return new ArrayElementValue(ElementValue.ARRAY, elementValues, null);
        } else if (value instanceof Integer) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_INT, 0, mockConstantPool(0, Constants.CONSTANT_Integer, new ConstantInteger((Integer) value)));
        } else if (value instanceof Long) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_LONG, 0, mockConstantPool(0, Constants.CONSTANT_Long, new ConstantLong((Long) value)));
        } else if (value instanceof Double) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_DOUBLE, 0, mockConstantPool(0, Constants.CONSTANT_Double, new ConstantDouble((Double) value)));
        } else if (value instanceof Float) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_FLOAT, 0, mockConstantPool(0, Constants.CONSTANT_Float, new ConstantFloat((Float) value)));
        } else if (value instanceof Short) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_SHORT, 0, mockConstantPool(0, Constants.CONSTANT_Integer, new ConstantInteger((Short) value)));
        } else if (value instanceof Byte) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_SHORT, 0, mockConstantPool(0, Constants.CONSTANT_Integer, new ConstantInteger((Byte) value)));
        } else if (value instanceof Character) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_CHAR, 0,
                mockConstantPool(0, Constants.CONSTANT_Integer, new ConstantInteger((Character) value)));
        } else if (value instanceof Boolean) {
            return new SimpleElementValue(ElementValue.PRIMITIVE_BOOLEAN, 0,
                mockConstantPool(0, Constants.CONSTANT_Integer, new ConstantInteger(((Boolean) value) ? 1 : 0)));
        } else if (value instanceof String) {
            return new SimpleElementValue(ElementValue.STRING, 0, mockConstantPool(0, Constants.CONSTANT_Utf8, new ConstantUtf8((String) value)));
        } else {
            throw new IllegalArgumentException("Unsupported type " + (value == null ? null : value.getClass().getCanonicalName()));
        }
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private BcelMockHelper()
    {
    }

// -------------------------- INNER CLASSES --------------------------

    public static class ConstantPoolEntry {
// ------------------------------ FIELDS ------------------------------

        private int index;

        private byte tag;

        private Constant value;

// --------------------------- CONSTRUCTORS ---------------------------

        public ConstantPoolEntry(int index, byte tag, Constant value)
        {
            this.index = index;
            this.tag = tag;
            this.value = value;
        }
    }
}
