package com.example.java82.modules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class AudioModule extends Module {
    @Value(value = "#{${audioModuleTitle}}")
    private String title;

    @Value(value = "#{${audioModuleExtensions}}")
    private List<String> allowableExtensions;

    @Value(value = "#{${audioModuleOptions}}")
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

    public void executeAudioOperation(String path, String command, String title) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe",
                            "/c",
                            String.format(command, path))
                    .redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            bufferedReader.lines().forEach(sb::append);
            System.out.printf(title, sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void audioTrackName(String path) {
        executeAudioOperation(path,
                "ffprobe -i %s -show_entries format_tags=title -of compact=p=0:nk=1 -v 0",
                "Название трека: %s\n");
    }

    public void audioDuration(String path) {
        executeAudioOperation(path,
                "ffprobe -i %s -show_entries format=duration -of compact=p=0:nk=1 -v 0",
                "Длительность трека в секундах: %s\n");
    }

    public void audioBitrate(String path) {
        executeAudioOperation(path,
                "ffprobe -i %s -show_entries stream=bit_rate -of compact=p=0:nk=1 -v 0\n",
                "Битрейт: %s\n");
    }
}