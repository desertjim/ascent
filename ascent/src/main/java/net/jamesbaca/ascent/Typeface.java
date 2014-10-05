package net.jamesbaca.ascent;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by jamesbaca on 9/27/14.
 */
@Retention(RUNTIME) @Target(FIELD)
public @interface Typeface {
    String value();
}
