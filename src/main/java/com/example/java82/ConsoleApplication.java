package com.example.java82;

import com.example.java82.modules.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.*;

@SpringBootApplication
public class ConsoleApplication implements ApplicationRunner {
	private final Collection<Module> applicationModules;

	@Autowired
	public ConsoleApplication(Collection<Module> applicationModules) {
		this.applicationModules = applicationModules;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsoleApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<String> Hi = args.getNonOptionArgs();
		String path = Hi.get(0);
		Optional<String> extOpt = getExtensionByStringHandling(path);
		String ext = extOpt.get();
		List<Module> goodModules = new ArrayList<>();
		for (Module module : applicationModules) {
			if (module.isSuitableExtension(ext)) {
				goodModules.add(module);
			}
		}
		for (Module module : goodModules) {
			System.out.println(module.getTitle());
		}
		System.out.println("Выберите модуль");
		Scanner sc= new Scanner(System.in);
		int str= sc.nextInt();
		goodModules.get(str - 1).run(path);
	}


	public static Optional<String> getExtensionByStringHandling(String filename) {
		File file = new File(filename);
		if (file.isFile()) {
			return Optional.ofNullable(filename)
					.filter(f -> f.contains("."))
					.map(f -> f.substring(filename.lastIndexOf(".") + 1));
		} else {
			return Optional.of("DIRECTORY");
		}
	}
}