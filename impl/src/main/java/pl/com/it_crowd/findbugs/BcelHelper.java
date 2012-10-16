package pl.com.it_crowd.findbugs;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

import java.util.Collection;
import java.util.Map;

public final class BcelHelper {
// ------------------------------ FIELDS ------------------------------

    private static final ObjectType COLLECTION_TYPE;

    private static final ObjectType MAP_TYPE;

// -------------------------- STATIC METHODS --------------------------

    static {
        COLLECTION_TYPE = (ObjectType) Type.getType(Collection.class);
        MAP_TYPE = (ObjectType) Type.getType(Map.class);
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
        return entry != null && "Ljavax/persistence/Column;".equals(entry.getAnnotationType());
    }

    public static boolean isMap(Type type)
    {
        try {
            return type instanceof ObjectType && ((ObjectType) type).isCastableTo(MAP_TYPE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
