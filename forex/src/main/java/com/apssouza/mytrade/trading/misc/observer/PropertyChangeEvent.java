package com.apssouza.mytrade.trading.misc.observer;

import java.util.EventObject;

/**
 * A "PropertyChange" event gets delivered whenever a bean changes a "bound"
 * or "constrained" property.  A PropertyChangeEvent object is sent as an
 * argument to the PropertyChangeListener and VetoableChangeListener methods.
 * <P>
 * Normally PropertyChangeEvents are accompanied by the name and the old
 * and new value of the changed property.  If the new value is a primitive
 * type (such as int or boolean) it must be wrapped as the
 * corresponding java.lang.* Object type (such as Integer or Boolean).
 * <P>
 * Null values may be provided for the old and the new values if their
 * true values are not known.
 * <P>
 * An event source may send a null object as the name to indicate that an
 * arbitrary set of if its properties have changed.  In this case the
 * old and new values should also be null.
 *
 * @since 1.1
 */
public class PropertyChangeEvent extends EventObject {
    private static final long serialVersionUID = 7042693688939648123L;

    /**
     * Constructs a new {@code PropertyChangeEvent}.
     *
     * @param source        the bean that fired the event
     * @param propertyName  the programmatic name of the property that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    public PropertyChangeEvent(Object source, String propertyName,
                               Object oldValue, Object newValue) {
        super(source);
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    /**
     * Gets the programmatic name of the property that was changed.
     *
     * @return  The programmatic name of the property that was changed.
     *          May be null if multiple properties have changed.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Gets the new value for the property, expressed as an Object.
     *
     * @return  The new value for the property, expressed as an Object.
     *          May be null if multiple properties have changed.
     */
    public Object getNewValue() {
        return newValue;
    }

    /**
     * Gets the old value for the property, expressed as an Object.
     *
     * @return  The old value for the property, expressed as an Object.
     *          May be null if multiple properties have changed.
     */
    public Object getOldValue() {
        return oldValue;
    }


    /**
     * name of the property that changed.  May be null, if not known.
     * @serial
     */
    private String propertyName;

    /**
     * New value for property.  May be null if not known.
     * @serial
     */
    @SuppressWarnings("serial") // Not statically typed as Serializable
    private Object newValue;

    /**
     * Previous value for property.  May be null if not known.
     * @serial
     */
    @SuppressWarnings("serial") // Not statically typed as Serializable
    private Object oldValue;

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     *
     * @since 1.7
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append("[propertyName=").append(getPropertyName());
        appendTo(sb);
        sb.append("; oldValue=").append(getOldValue());
        sb.append("; newValue=").append(getNewValue());
        sb.append("; source=").append(getSource());
        return sb.append("]").toString();
    }

    void appendTo(StringBuilder sb) {
    }
}
