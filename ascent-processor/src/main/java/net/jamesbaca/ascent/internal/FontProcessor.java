package net.jamesbaca.ascent.internal;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;

import net.jamesbaca.ascent.Font;
import net.jamesbaca.ascent.InjectedAscent;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by jamesbaca on 9/27/14.
 */
@AutoService(Processor.class)
public final class FontProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        long startTime = System.currentTimeMillis();

        for (TypeElement annotation : annotations) {
            write(classesWithFieldsAnnotatedWith(environment.getElementsAnnotatedWith(annotation)));
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        messager().printMessage(Diagnostic.Kind.NOTE,
                "AscentProcessor took " + elapsedTime + " milliseconds");
        return true;
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(Font.class.getName());
    }

    private void write(Map<EnclosingClass, Collection<AnnotatedField>> fieldsByEnclosingClass) {
        WriterFactory
                writerFactory = new WriterFactory(elementUtils(), typeUtils(), filer(), InjectedAscent.SUFFIX);

        for (EnclosingClass enclosingClass : fieldsByEnclosingClass.keySet()) {
            try {
                writerFactory.writeClass(enclosingClass)
                        .withFields(fieldsByEnclosingClass.get(enclosingClass));
            } catch (IOException e) {
                messager().printMessage(Diagnostic.Kind.ERROR,
                        "Error generating helper for class " + enclosingClass.getClassName()
                                + ". Reason: " + e.getMessage());
            }
        }
    }

    private Map<EnclosingClass, Collection<AnnotatedField>> classesWithFieldsAnnotatedWith(
            Set<? extends Element> annotatedElements) {
        return new AnnotationsConverter(messager(), elementUtils(), typeUtils())
                .convert(annotatedElements);
    }

    private Messager messager() {
        return processingEnv.getMessager();
    }

    private Elements elementUtils() {
        return processingEnv.getElementUtils();
    }

    private Types typeUtils() {
        return processingEnv.getTypeUtils();
    }

    private Filer filer() {
        return processingEnv.getFiler();
    }
}
