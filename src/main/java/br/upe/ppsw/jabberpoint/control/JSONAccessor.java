package br.upe.ppsw.jabberpoint.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;

import br.upe.ppsw.jabberpoint.model.BitmapItem;
import br.upe.ppsw.jabberpoint.model.Presentation;
import br.upe.ppsw.jabberpoint.model.SlideItem;
import br.upe.ppsw.jabberpoint.model.TextItem;

public class JSONAccessor implements FileAccessor {
	
	private final String SHOWTITLE = "showtitle";
	private final String SLIDE = "slide";

	@Override
	public void loadFile(Presentation presentation, String fileName) throws IOException {
		Gson gson = new Gson();
		
//		try {
//			FileReader file = new FileReader(fileName);
//			Scanner scanner = new Scanner(file);
//			scanner.
//			gson.toJson, null);
//			JsonWriter json = gson.newJsonWriter(new FileWriter("file.json"));
////			json.jsonValue(fileName)
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		try {
//			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			Document document = builder.parse(new File(fileName));
//			Element doc = document.getDocumentElement();
//			
//			presentation.setTitle(getTitle(doc, SHOWTITLE));
//			
//			NodeList slides = doc.getElementsByTagName(SLIDE);
//			
//		} catch (IOException iox) {
//		      System.err.println(iox.toString());
//	    } catch (SAXException sax) {
//	      System.err.println(sax.getMessage());
//	    } catch (ParserConfigurationException pcx) {
//	      System.err.println("123");
//	    }
//		
	}
//	
//	  private String getTitle(Element element, String tagName) {
//		    NodeList titles = element.getElementsByTagName(tagName);
//		    return titles.item(0).getTextContent();
//
//		  }

	@Override
	public void saveFile(Presentation presentation, String fileName) throws IOException {
		
		String json = "{\r\n"
				+ "		\"id\": 6,\r\n"
				+ "		\"titulo\": \"wnh5efhjsgfha\",\r\n"
				+ "		\"descricao\": \"ajajanncjaja\",\r\n"
				+ "		\"requisitos\": \"skgnsajh\",\r\n"
				+ "		\"prazo\": \"2002-01-02T02:02:00.000+00:00\",\r\n"
				+ "		\"tipo\": \"EXTENSAO\",\r\n"
				+ "		\"coordenador\": {\r\n"
				+ "			\"id\": 4,\r\n"
				+ "			\"nome\": \"amadeu\",\r\n"
				+ "			\"email\": \"amadeu.santos@upe.br\",\r\n"
				+ "			\"perfis\": [\r\n"
				+ "				{\r\n"
				+ "					\"nome\": \"ADMINISTRADOR\"\r\n"
				+ "				},\r\n"
				+ "				{\r\n"
				+ "					\"nome\": \"COORDENADOR_PESQUISA\"\r\n"
				+ "				},\r\n"
				+ "				{\r\n"
				+ "					\"nome\": \"COORDENADOR_EXTENSAO\"\r\n"
				+ "				}\r\n"
				+ "			]\r\n"
				+ "		}\r\n"
				+ "	} ";
		
//		file.write("\"presentation\":{");
//		file.write("\"showtitle\":" + presentation.getTitle()+ ",");
//		
//		
//		for(int numberSlide = 0; numberSlide < presentation.getSize(); numberSlide++) {
//			file.write("\"slide\":{");
//			file.write("\"title\":\"" + presentation.getSlide(numberSlide).getTitle() + "\",");
//			file.write("\"items\":\"");
//			file.write(presentation.getSlide(numberSlide).getSlideItems().stream().map(this::convertSlideItem).toList().toString());
//			file.write("},");
//		}
		
//		file.close();
		
//		try {
//			FileWriter file = new FileWriter(fileName);
//			file.write("{");
//			file.write("\"showtitle\": \""+ presentation.getTitle() +"\",");
//			for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
//				file.write("\"title\":\"" + presentation.getSlide(slideNumber) + "\",");
//				presentation.getSlide(slideNumber).getItems().stream().
//			}
//			file.write(null);
//			JsonElement presentationJson =  gson.toJsonTree(presentation);
//			file.write(gson.toJson(presentationJson));
//			file.close();
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		try (FileWriter writer = new FileWriter(fileName)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonElement tree = gson.toJsonTree(presentation);

			gson.toJson(tree, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String convertSlideItem(SlideItem item) {
		Gson json = new Gson();
		if (item instanceof TextItem) {
			System.out.println(json.toJson(item));
			return json.toJson(item);
		}
		if (item instanceof BitmapItem) {
			String bitMap = "{\"image\":\"classpath:" + FilenameUtils.getName(((BitmapItem) item).getName()) + "\",\"level\":"+ item.getLevel() + "}"; 
			return bitMap;
		}
		
		return "Ignoring " + item;
	}
}
