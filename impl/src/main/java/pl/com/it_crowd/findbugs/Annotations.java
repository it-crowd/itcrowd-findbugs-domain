package pl.com.it_crowd.findbugs;

public interface Annotations {
// ------------------------------ FIELDS ------------------------------

    static final String JAVAX_PERSISTENCE_COLUMN = "Ljavax/persistence/Column;";
    static final String JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH = "length";
    static final String JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE = "nullable";
    static final int JAVAX_PERSISTENCE_COLUMN_DEFAULT_LENGTH = 255;
    static final String JAVAX_PERSISTENCE_JOIN_COLUMN = "Ljavax/persistence/JoinColumn;";
    static final String JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY = "Lorg/hibernate/validator/constraints/NotEmpty;";
    static final String JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL = "Lorg/hibernate/validator/constraints/NotNull;";
    static final String JAVAX_VALIDATION_CONSTRAINTS_SIZE = "Ljavax/validation/constraints/Size;";
    static final String JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX = "max";
    static final int JAVAX_VALIDATION_CONSTRAINTS_SIZE_DEFAULT_MAX = 2147483647;
}
