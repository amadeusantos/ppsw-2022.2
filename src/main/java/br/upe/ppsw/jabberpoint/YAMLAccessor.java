package br.upe.ppsw.jabberpoint;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import br.upe.ppsw.jabberpoint.control.FileAccessor;
import br.upe.ppsw.jabberpoint.model.Presentation;

public class YAMLAccessor implements FileAccessor {

	@Override
	public void loadFile(Presentation presentation, String fileName) throws IOException {
		try {
			FileReader file = new FileReader(fileName);
			Yaml yaml = new Yaml(new Constructor(Presentation.class));
			presentation = yaml.load(file);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void saveFile(Presentation presentation, String fileName) throws IOException {
		try {
			FileWriter file = new FileWriter(fileName);
			Yaml yaml = new Yaml(new Constructor(Presentation.class));
			file.write(yaml.dump(presentation));
			System.out.println(yaml.dump(presentation));
			file.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
