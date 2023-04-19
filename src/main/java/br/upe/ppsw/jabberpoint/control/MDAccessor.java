package br.upe.ppsw.jabberpoint.control;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import br.upe.ppsw.jabberpoint.model.*;
import org.apache.commons.io.FilenameUtils;

public class MDAccessor implements FileAccessor {

	private final String SHOWTITLE = "showtitle";
	private final String SLIDE = "slide";

	@Override
	public void loadFile(Presentation presentation, String fileName) throws IOException {
		try {
			FileReader file = new FileReader(fileName);
			Scanner scanner = new Scanner(file);
			presentation.setTitle(scanner.nextLine().substring(2));
			String linha = scanner.nextLine();
			while (scanner.hasNextLine()) {
				Slide slide = new Slide();
				slide.setTitle(linha.substring(3));
				linha = scanner.nextLine();
				while (!linha.startsWith("## ")) {
					if (linha.startsWith(" - [")) {
						String[] item = linha.split("]");
						TextItem textItem = new TextItem(Integer.parseInt(item[1].substring(1).strip().replace(")", "")), item[0].substring(4));
						slide.append(textItem);
					}
					if (linha.startsWith(" - ![")) {
						String[] item = linha.split("]");
						BitmapItem bitmapItem = new BitmapItem(Integer.parseInt(item[1].substring(1).replace(")", "")), item[0].substring(5));
						slide.append(bitmapItem);
					}
					if(!scanner.hasNextLine()) {
						break;
					}
					linha = scanner.nextLine();
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
			file.write("# " + presentation.getTitle());
			List<Slide> slides = presentation.getSlides();
			for (Slide slide: slides) {
				file.write("\n## " + slide.getTitle());
				for (SlideItem item: slide.getItems()) {
					file.write(convertSlideItem(item));
				}
			}

			file.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private String convertSlideItem(SlideItem item) {
		String items = "\n - ";
		if (item instanceof TextItem) {
			String text = "[" + FilenameUtils.getName(((TextItem) item).getText()) + "]("+ item.getLevel() + ")";
			return items + text;
		}
		if (item instanceof BitmapItem) {
			String bitMap = "![" + FilenameUtils.getName(((BitmapItem) item).getName()) + "]("+ item.getLevel() + ")";
			return items + bitMap;
		}

		return items + item;
	}
}
