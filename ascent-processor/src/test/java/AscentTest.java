import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import net.jamesbaca.ascent.Ascent;
import net.jamesbaca.ascent.internal.FontProcessor;

import org.junit.Test;

import java.util.Arrays;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

/**
 * Created by jamesbaca on 9/28/14.
 */
public class AscentTest {

    @Test public void fieldsMustNotBePrivate() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n').join(
                "package test;",
                "import net.jamesbaca.typeface.InjectAscent;",
                "import android.widget.TextView;",
                "public class Test {",
                "  @Font(\"42\") private TextView thing;",
                "}"
        ));

        ASSERT.about(javaSource()).that(source)
                .processedWith(typefaceProcessors()).failsToCompile();
    }

    @Test public void shouldBeTextView() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n').join(
                "package test;",
                "import net.jamesbaca.typeface.InjectTypeface;",
                "public class Test {",
                "  @Font(\"42\") int thing;",
                "}"
        ));

        ASSERT.about(javaSource()).that(source)
                .processedWith(typefaceProcessors()).failsToCompile();
    }

    @Test public void shouldSubclass() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n').join(
                "package test;",
                "import net.jamesbaca.ascent.Font;",
                "import net.jamesbaca.ascent.internal.AnnotationsConverter;",
                "import android.widget.TextView;",
                "",
                "public class Test {",
                "  @Font(\"one\") TextView one;",
                "}",
                "",
                "class Test2 extends Test{",
                "  @Font(\"two\") TextView two;",
                "}"
        ));

        ASSERT.about(javaSource()).that(source)
                .processedWith(typefaceProcessors()).compilesWithoutError();
    }

    /**
     * Test to make sure that if you inject into a class that it doesn't blow up
     */
    @Test public void canInjectEmptyClasses(){
        AscentTest classToTest = new AscentTest();
        Ascent ascent = new Ascent();
        assert(classToTest != null);
        ascent.inject(classToTest);
    }

    private Iterable<? extends Processor> typefaceProcessors() {
        return Arrays.asList(new FontProcessor());
    }

}
