package net.jamesbaca.ascent.internal;

import javax.lang.model.element.TypeElement;

class EnclosingClass {

    private final String classPackage;
    private final String className;
    private final String targetClass;
    private final String parentEnclosingClass;
    private final TypeElement element;
    private final String enclosingClassName;
    private final String enclosingClassPackage;

    EnclosingClass(String classPackage, String className, String targetClass,
                   String parentEnclosingClass, TypeElement element, String enclosingClass, String enclosingClassPackage) {
        this.classPackage = classPackage;
        this.className = className;
        this.targetClass = targetClass;
        this.parentEnclosingClass = parentEnclosingClass;
        this.element = element;
        this.enclosingClassName = enclosingClass;
        this.enclosingClassPackage = enclosingClassPackage;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public String getClassName() {
        return className;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public String getParentEnclosingClass() {
        return parentEnclosingClass;
    }

    public TypeElement getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnclosingClass that = (EnclosingClass) o;

        if (!className.equals(that.className)) return false;
        if (!classPackage.equals(that.classPackage)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classPackage.hashCode();
        result = 31 * result + className.hashCode();
        return result;
    }

    public String getEnclosingClassName(){
        return enclosingClassName;
    }

    public String getEnclosingClassPackage(){
        return  enclosingClassPackage;
    }
}
