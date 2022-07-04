package tests;

import java.io.File;

public class CommandLineCompilerTest {

    public static void main(String[] args) {
        File file = new File("D:\\Programmazione\\Java\\RestaurantManager");
        printJars(file);
    }

    private static void printJars(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                printJars(f);
            }
        } else {
            if (file.getName().endsWith(".java")) {
                String path = file.getPath();
                path = path.substring(path.indexOf("\\src\\"));
                path = path.replace("\\", "/");
                System.out.print(path + " ");
            }
        }
    }

}
