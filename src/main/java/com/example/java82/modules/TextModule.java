package com.example.java82.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TextModule extends Module{
    @Value(value = "#{${textModuleTitle}}")
    private String title;

    @Value(value = "#{${textModuleExtensions}}")
    private List<String> allowableExtensions;

    @Value(value = "#{${textModuleOptions}}")
    private List<String> options;

    public String getTitle() {
        return title;
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
    public boolean isSuitableExtension(String extension) {
        return allowableExtensions.contains(extension);
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
            case 1: countString(path);
                break;
            case 2: countChar(path);
                break;
            case 3: countWords(path);
                break;
            case 4: super.printHelp();
                break;
        }
    }

    public void countString(String path) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while (reader.readLine() != null) {
                count++;
            }

            System.out.printf("Количество строк: %d", count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countChar(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String str = reader.readLine();
            Map<Character, Integer> characters = new HashMap<>();

            while (str != null) {
                for (int i = 0; i < str.length(); i++) {
                    char ch = str.charAt(i);
                    characters.put(ch, characters.getOrDefault(ch, 0) + 1);
                }

                str = reader.readLine();
            }

            for (Map.Entry<Character, Integer> ch : characters.entrySet()) {
                System.out.printf("%s : %d\n", ch.getKey(), ch.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countWords(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String str = reader.readLine();
            int count = 0;

            while (str != null) {
                count += str.split(" ").length;

                str = reader.readLine();
            }

            System.out.printf("Количество слов в тексте: %d", count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}