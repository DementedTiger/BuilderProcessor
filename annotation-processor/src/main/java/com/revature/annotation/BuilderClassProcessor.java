package com.revature.annotation;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public class BuilderClassProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            writeBuilderFile(roundEnv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void writeBuilderFile(RoundEnvironment roundEnv) throws IOException {
        for(Element element: roundEnv.getElementsAnnotatedWith(Builder.class)){

            PackageElement packageElement = (PackageElement)element.getEnclosingElement();
            String simpleClassName = element.getSimpleName().toString();
            String builderName = simpleClassName + "Builder";



            JavaFileObject builderFile = processingEnv.getFiler()
                    .createSourceFile(builderName);

            try(PrintWriter out = new PrintWriter(builderFile.openWriter())){
                if (packageElement.getQualifiedName().toString() != null){
                    out.print("package ");
                    out.print(packageElement.getQualifiedName().toString());
                    out.println(";");
                    out.println();
                }

                out.print("public class ");
                out.print(builderName);
                out.println(" {");
                out.println();

                out.print("   private ");
                out.print(simpleClassName);
                out.print(" object = new ");
                out.print(simpleClassName);
                out.println("();");
                out.println();

                out.print("   public ");
                out.print(simpleClassName);
                out.println(" build() {");
                out.println("    return object;");
                out.println("    }");
                out.println();

                if (element.getKind().isClass()){
                    for (Element enclosed: element.getEnclosedElements()){
//                        if (enclosed.getKind().isField() && (enclosed.getModifiers().contains(Modifier.PUBLIC)
//                        | enclosed.getModifiers().contains(Modifier.PROTECTED))){
                        if(enclosed.getKind().isField()){
                            String variable = enclosed.getSimpleName().toString();
                            String methodName = "set" + variable.substring(0, 1).toUpperCase() + variable.substring(1);
                            String argumentType = enclosed.asType().toString();
                            out.print("   public ");
                            out.print(builderName);
                            out.print(" ");
                            out.print(methodName);

                            out.print("(");

                            out.print(argumentType);
                            out.println(" value) {");
                            out.print("    object.");
                            out.print(methodName);
                            out.println("(value);");
                            out.println("    return this;");
                            out.println("   }");
                            out.println();
                        }
                    }
                }
                out.println("}");
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
            }
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList("com.revature.annotation.Builder"));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}

