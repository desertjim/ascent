import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import net.jamesbaca.ascent.internal.TypefaceProcessor;

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
                "import net.jamesbaca.typeface.InjectTypeface;",
                "import android.widget.TextView;",
                "public class Test {",
                "  @InjectTypeface(\"42\") private TextView thing;",
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
                "  @InjectTypeface(\"42\") int thing;",
                "}"
        ));

        ASSERT.about(javaSource()).that(source)
                .processedWith(typefaceProcessors()).failsToCompile();
    }

    @Test public void shouldSubclass() {
        //JavaFileObjects.forSourceString()
        JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n').join(
                "package test;",
                "import net.jamesbaca.ascent.Typeface;",
                "import android.widget.TextView;",
                "",
                "public class Test {",
                "  @Typeface(\"one\") TextView one;",
                "}",
                "",
                "class Test2 extends Test{",
                "  @Typeface(\"two\") TextView two;",
                "}"
        ));

        ASSERT.about(javaSource()).that(source)
                .processedWith(typefaceProcessors()).compilesWithoutError();
    }

    private Iterable<? extends Processor> typefaceProcessors() {
        return Arrays.asList(new TypefaceProcessor());
    }
}
