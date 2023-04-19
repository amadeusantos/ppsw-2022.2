package br.upe.ppsw.jabberpoint.control;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FilenameUtils;

import br.upe.ppsw.jabberpoint.model.BitmapItem;
import br.upe.ppsw.jabberpoint.model.Presentation;
import br.upe.ppsw.jabberpoint.model.Slide;
import br.upe.ppsw.jabberpoint.model.SlideItem;
import br.upe.ppsw.jabberpoint.model.TextItem;

public class YAMLAccessor implements FileAccessor {

	@Override
	public void loadFile(Presentation presentation, String fileName) throws IOException {
		try {
			FileReader file = new FileReader(fileName);
			Scanner scanner = new Scanner(file);
			presentation.setTitle(scanner.nextLine().substring(10));
			String penultimo = scanner.nextLine();
			String ultimo = scanner.nextLine();
			while (!penultimo.equals(ultimo)) {
				Slide slide = new Slide();

				slide.setTitle(ultimo.substring(11));
				penultimo = ultimo;
				ultimo = scanner.nextLine();
				while (!penultimo.equals(ultimo) && !ultimo.startsWith("  - title: ")) {
					penultimo = ultimo;
					ultimo = scanner.nextLine();
					if (penultimo.startsWith("    - text: ")) {
						TextItem textItem = new TextItem(Integer.parseInt(ultimo.substring(13)), penultimo.substring(12));
						slide.append(textItem);
					}
					if (penultimo.startsWith("    - image: ")) {
						BitmapItem bitmapItem = new BitmapItem(Integer.parseInt(ultimo.substring(13)), penultimo.substring(13));
						slide.append(bitmapItem);
					}
					try {
						penultimo = ultimo;
						ultimo = scanner.nextLine();
					} catch (Exception e) {
						ultimo = penultimo;
					}
				}
				presentation.append(slide);
			}


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void saveFile(Presentation presentation, String fileName) throws IOException {
		try {
			FileWriter file = new FileWriter(fileName);
			file.write("showtitle: " + presentation.getTitle());
			file.write("\nslides: ");
			List<Slide> slides = presentation.getSlides();
			for (Slide slide: slides) {
				file.write("\n  - title: " + slide.getTitle());
				for (SlideItem item: slide.getItems()) {
					file.write(convertSlideItem(item));
					file.write("\n    - level: " + item.getLevel());
				}
			}

			file.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private String convertSlideItem(SlideItem item) {
		if (item instanceof TextItem) {
			return "\n    - text: " + ((TextItem) item).getText();
		}
		if (item instanceof BitmapItem) {
			return "\n    - image: " +  FilenameUtils.getName(((BitmapItem) item).getName());
		}

		return "\n    - Ignoring " + item;
	}

}
