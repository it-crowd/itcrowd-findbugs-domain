package pl.itcrowd.findbugs.domain;

import org.apache.bcel.classfile.AnnotationElementValue;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ArrayElementValue;
import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

import java.util.Collection;
import java.util.Map;

import static pl.itcrowd.findbugs.domain.Annotations.JAVAX_PERSISTENCE_COLUMN;
import static pl.itcrowd.findbugs.domain.Annotations.JAVAX_PERSISTENCE_JOIN_COLUMN;

public final class BcelHelper {
// ------------------------------ FIELDS ------------------------------

    private static final ObjectType COLLECTION_TYPE;

    private static final ObjectType MAP_TYPE;

// -------------------------- STATIC METHODS --------------------------

    static {
        COLLECTION_TYPE = (ObjectType) Type.getType(Collection.class);
        MAP_TYPE = (ObjectType) Type.getType(Map.class);
    }

    public static String extractAttributeStringValue(ElementValue value, String attributeName)
    {
        if (value instanceof AnnotationElementValue) {
            return extractAttributeStringValue((AnnotationElementValue) value, attributeName);
        } else if (value instanceof ArrayElementValue) {
            if (((ArrayElementValue) value).getElementValuesArraySize() == 1) {
                final ElementValue elementValue = ((ArrayElementValue) value).getElementValuesArray()[0];
                if (elementValue instanceof AnnotationElementValue) {
                    return extractAttributeStringValue((AnnotationElementValue) elementValue, attributeName);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return value == null ? null : value.stringifyValue();
        }
    }

    public static String extractAttributeStringValue(AnnotationElementValue value, String attributeName)
    {
        return getAnnotationPropertyValue(value.getAnnotationEntry(), attributeName);
    }

    public static String getAnnotationPropertyValue(AnnotationEntry entry, String propertyName)
    {
        if (propertyName == null) {
            return null;
        }
        for (ElementValuePair pair : entry.getElementValuePairs()) {
            if (propertyName.equals(pair.getNameString())) {
                return pair.getValue().stringifyValue();
            }
        }
        return null;
    }

    public static String getAnnotationPropertyValue(AnnotationEntry entry, String propertyName, Object defaultValue)
    {
        final String value = getAnnotationPropertyValue(entry, propertyName);
        return value == null ? (defaultValue == null ? null : defaultValue.toString()) : value;
    }

    public static ElementValue getAnnotationPropertyValue2(AnnotationEntry entry, String propertyName)
    {
        if (propertyName == null) {
            return null;
        }
        for (ElementValuePair pair : entry.getElementValuePairs()) {
            if (propertyName.equals(pair.getNameString())) {
                return pair.getValue();
            }
        }
        return null;
    }

    public static Type getType(FieldOrMethod obj)
    {
        if (obj instanceof Field) {
            return ((Field) obj).getType();
        } else {
            return ((Method) obj).getReturnType();
        }
    }

    public static boolean isArray(Type type)
    {
        return type instanceof ArrayType;
    }

    public static boolean isCollection(Type type)
    {
        try {
            return type instanceof ObjectType && ((ObjectType) type).isCastableTo(COLLECTION_TYPE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isJavaxPersistenceColumn(AnnotationEntry entry)
    {
        return entry != null && JAVAX_PERSISTENCE_COLUMN.equals(entry.getAnnotationType());
    }

    public static boolean isJavaxPersistenceColumnOrJoinColumn(AnnotationEntry entry)
    {
        return isJavaxPersistenceColumn(entry) || isJavaxPersistenceJoinColumn(entry);
    }

    public static boolean isJavaxPersistenceJoinColumn(AnnotationEntry entry)
    {
        return entry != null && JAVAX_PERSISTENCE_JOIN_COLUMN.equals(entry.getAnnotationType());
    }

    public static boolean isMap(Type type)
    {
        try {
            return type instanceof ObjectType && ((ObjectType) type).isCastableTo(MAP_TYPE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSingleValue(ElementValue value)
    {
        return !(value instanceof ArrayElementValue) || (((ArrayElementValue) value).getElementValuesArraySize() != 1);
    }

    public static boolean isString(Type type)
    {
        return type instanceof ObjectType && String.class.getCanonicalName().equals(((ObjectType) type).getClassName());
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private BcelHelper()
    {
    }
}
