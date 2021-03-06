package pl.itcrowd.findbugs.domain;

public interface Annotations {

    // ------------------------------ FIELDS ------------------------------
    static final String JAVAX_PERSISTENCE_COLUMN = "Ljavax/persistence/Column;";
    static final String JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_LENGTH = "length";
    static final String JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NAME = "name";
    static final String JAVAX_PERSISTENCE_COLUMN_ATTRIBUTE_NULLABLE = "nullable";
    static final int JAVAX_PERSISTENCE_COLUMN_DEFAULT_LENGTH = 255;
    static final boolean JAVAX_PERSISTENCE_COLUMN_DEFAULT_NULLABLE = true;
    static final String JAVAX_PERSISTENCE_ENTITY = "Ljavax/persistence/Entity;";
    static final String JAVAX_PERSISTENCE_GENERATED_VALUE = "Ljavax/persistence/GeneratedValue";
    static final String JAVAX_PERSISTENCE_ID = "Ljavax/persistence/Id;";
    static final String JAVAX_PERSISTENCE_JOIN_COLUMN = "Ljavax/persistence/JoinColumn;";
    static final String JAVAX_PERSISTENCE_JOIN_TABLE = "Ljavax/persistence/JoinTable;";
    static final String JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_INVERSE_JOIN_COLUMNS = "inverseJoinColumns";
    static final String JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_JOIN_COLUMNS = "joinColumns";
    static final String JAVAX_PERSISTENCE_JOIN_TABLE_ATTRIBUTE_NAME = "name";
    static final String JAVAX_PERSISTENCE_MANY_TO_ONE = "Ljavax/persistence/ManyToOne;";
    static final String JAVAX_PERSISTENCE_MANY_TO_ONE_ATTRIBUTE_OPTIONAL = "optional";
    static final boolean JAVAX_PERSISTENCE_MANY_TO_ONE_DEFAULT_OPTIONAL = true;
    static final String JAVAX_PERSISTENCE_TABLE = "Ljavax/persistence/Table;";
    static final String JAVAX_PERSISTENCE_TABLE_ATTRIBUTE_NAME = "name";
    static final String JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY = "Lorg/hibernate/validator/constraints/NotEmpty;";
    static final String JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL = "Ljavax/validation/constraints/NotNull;";
    static final String JAVAX_VALIDATION_CONSTRAINTS_SIZE = "Ljavax/validation/constraints/Size;";
    static final String JAVAX_VALIDATION_CONSTRAINTS_SIZE_ATTRIBUTE_MAX = "max";
    static final int JAVAX_VALIDATION_CONSTRAINTS_SIZE_DEFAULT_MAX = 2147483647;
    static final String ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY = "Lorg/hibernate/annotations/ForeignKey;";
    static final String ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY_ATTRIBUTE_INVERSE_NAME = "inverseName";
    static final String ORG_HIBERNATE_ANNOTATIONS_FOREIGNKEY_ATTRIBUTE_NAME = "name";
    static final String ORG_HIBERNATE_ANNOTATIONS_TYPE = "Lorg/hibernate/annotations/Type;";
    static final String ORG_HIBERNATE_ANNOTATIONS_TYPE_ATTRIBUTE_TYPE = "type";
}
