package tests;

import gui.panels.AbstractPanel;
import gui.panels.CashierPanel;
import gui.panels.ChefPanel;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassDescriptionExtractor {

    private static final StringBuilder content = new StringBuilder();

    public static void main(String[] args) {
        String[] packages = new String[]{"gui.buttons", "gui.dialogs", "gui.panels", "gui", "restaurant"};
        for (String pkg : packages) {
            Set<Class<?>> classesInPackage = getClassesInPackage(pkg);
            if (classesInPackage == null) {
                continue;
            }
            for (Class<?> c : classesInPackage) {
                printClass(c);
            }
        }
        System.out.println(content);
    }

    public static void printClass(Class<?> c) {
        content.append("\\subsection{").append(c.getSimpleName()).append("}\n\n");
        //Append Parameters
        Field[] fields = c.getDeclaredFields();
        if (fields.length > 0) {
            content.append("\\textbf{Parametri}:\n");
            content.append("\\begin{itemize}\n");
            for (Field field : fields) {
                content.append("\t\\item \\textbf{").append(field.getName()).append(":}\n\t\n");
            }
            content.append("\\end{itemize}\n");
        }
        //Append Methods
        Method[] methods = c.getDeclaredMethods();
        if (methods.length > 0) {
            content.append("\\textbf{Metodi}:\n");
            content.append("\\begin{itemize}\n");
            for (Method method : methods) {
                content.append("\t\\item \\textbf{").append(method.getName()).append("():}\n\t\n");
            }
            content.append("\\end{itemize}\n");
        }
    }

    public static Set<Class<?>> getClassesInPackage(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().filter(line -> line.endsWith(".class")).map(line -> getClass(line, packageName)).collect(Collectors.toSet());
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

}
