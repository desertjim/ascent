package net.jamesbaca.ascent.internal;

import com.google.common.base.Joiner;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import javax.tools.JavaFileObject;

public class FontClassWriter {

    private final JavaFileObject javaFileObject;
    private final String suffix;
    private final EnclosingClass enclosingClass;

    public FontClassWriter(JavaFileObject jfo, String suffix, EnclosingClass enclosingClass) {
        this.javaFileObject = jfo;
        this.suffix = suffix;
        this.enclosingClass = enclosingClass;
    }

    public void withFields(Collection<AnnotatedField> annotatedFields) throws IOException {
        Writer writer = javaFileObject.openWriter();
        String lBrew = brewJava(annotatedFields);
        writer.write(lBrew);
        writer.flush();
        writer.close();
    }

    private String brewJava(Collection<AnnotatedField> annotatedFields) {
        String classPackage = enclosingClass.getClassPackage();
        String className = enclosingClass.getClassName();
        String helperClassName = enclosingClass.getClassName() + suffix;
        String enclosingClassName = enclosingClass.getEnclosingClassName();
        String enclosingClassPackage = enclosingClass.getEnclosingClassPackage();

        String helperType = "FontHelper";
        return Joiner.on("\n").join(
                    "// Generated code from Typeface. Do not modify!",
                    "package " + classPackage + ";",
                    "",
                    "import android.widget.TextView;",
                    "import net.jamesbaca.ascent.FontHelper;",
                    "import net.jamesbaca.ascent.Ascent;",
                    "import " + classPackage + "."+ className + ";",
                    emitParentImport(enclosingClassPackage, enclosingClassName),
                    "",
                    "public class " + helperClassName + " implements " + helperType + " {",
                    "",
                    "  public void applyFont(Object target, Ascent manager) {",
                    "",
                    emitParentApplyTypeface(enclosingClassName),
                    emitFields(className, annotatedFields),
                    "  }",
                    "}"
            );


    }

    private String emitParentImport(String packageName, String className){
        if(className != null){
            return "import " +packageName + "." + className + suffix + ";";
        }else{
            return "";
        }
    }

    private String emitParentApplyTypeface(String className){
        StringBuilder lBuilder = new StringBuilder();
        if(className != null){
            lBuilder.append("    "+className+suffix+ " superClass = new " + className+suffix + "();\n");
            lBuilder.append("    superClass.applyFont(target, manager);\n");
            return lBuilder.toString();
        }else{
            return "";
        }
    }

    private String emitFields(String className, Collection<AnnotatedField> annotatedFields) {
        StringBuilder builder = new StringBuilder();
        for (AnnotatedField field : annotatedFields) {
            builder.append(emitTypefaceField(className, field));
        }
        return builder.toString();
    }

    private String emitTypefaceField(String className, AnnotatedField field) {
        return "    ((" + className + ")target)." + field.getName() + ".setTypeface(manager.getTypeface(\"" + field.getTypefaceName() + "\"));\n";
    }
}
