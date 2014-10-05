package net.jamesbaca.ascent.internal;

import javax.lang.model.element.TypeElement;

class AnnotatedField {

    private final String name;
    private final String typeFaceName;
    private final TypeElement enclosingClassType;


    AnnotatedField(String name, String typeFaceName, TypeElement enclosingClassType) {
        this.name = name;
        this.typeFaceName = typeFaceName;
        this.enclosingClassType = enclosingClassType;

    }

    public String getName() {
        return name;
    }

    public String getTypefaceName() {
        return typeFaceName;
    }

    public TypeElement getEnclosingClassType() {
        return enclosingClassType;
    }

}
