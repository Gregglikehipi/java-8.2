package com.example.java82.modules;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

public abstract class Module {
    public abstract String getTitle();
    public abstract boolean isSuitableExtension(String extension);
    public abstract List<String> getAllowableExtensions();
    public abstract List<String> getOptions();
    public abstract void run(String path);

    public void printHelp() {
        System.out.printf("%s:\n------------------%n", getTitle());
        System.out.print("Поддерживаемы форматы: ");
        for (String ext : getAllowableExtensions()) System.out.printf("%s ", ext);
        System.out.print("\nОперации: ");
        for (String ext : getOptions()) System.out.printf("%s ", ext);
        System.out.println();
    }
}
