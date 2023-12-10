package com.example.java82.modules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class DirectoryModule extends Module {
    @Value(value = "#{${directoryModuleTitle}}")
    private String title;

    @Value(value = "#{${directoryModuleExtensions}}")
    private List<String> allowableExtensions;

    @Value(value = "#{${directoryModuleOptions}}")
    private List<String> options;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSuitableExtension(String extension) {
        return allowableExtensions.contains(extension);
    }

    @Override
    public List<String> getAllowableExtensions() {
        return allowableExtensions;
    }

    @Override
    public List<String> getOptions() {
        return options;
    }

    @Override
    public void run(String path) {
        for (String o : options) {
            System.out.println(o);
        }
        System.out.println("Print help");
        System.out.println("Выберите метод");
        Scanner sc= new Scanner(System.in);
        int num = sc.nextInt();
        switch (num) {
            case 1: directoryList(path);
                break;
            case 2: directorySize(path);
                break;
            case 3: directoryLastModified(path);
                break;
            case 4: super.printHelp();
                break;
        }
    }

    public void directoryList(String path) {
        File[] files = (new File(path)).listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    private long directorySize(File directory) {
        long length = 0;
        File[] files = directory.listFiles();
        if(files != null){
            for (File file : files) {
                if (file.isFile())
                    length += file.length();
                else
                    length += directorySize(file);
            }
        }

        return length;
    }

    public void directorySize(String path) {
        System.out.printf("Размер всех файлов и папок в директории: %s", directorySize(new File(path)));
    }

    public void directoryLastModified(String path) {
        File file = new File(path);
        String date = (new SimpleDateFormat("MM.dd.yyyy HH:mm:ss")).format(file.lastModified());
        System.out.printf("Дата последней модификации: %s\n", date);
    }
}