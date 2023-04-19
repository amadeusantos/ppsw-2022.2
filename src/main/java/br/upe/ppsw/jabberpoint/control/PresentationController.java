package br.upe.ppsw.jabberpoint.control;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import br.upe.ppsw.jabberpoint.YAMLAccessor;

public class PresentationController {
	  protected static final String JABVERSION = "Jabberpoint 1.6 -";

	public static LoadFile setLoadAccessor(String file)throws IOException {
		
		String fileExtension = FilenameUtils.getExtension(file);
		
		if (fileExtension.equals("xml")) {
			return new XMLAccessor();
		}
		
		if (fileExtension.equals("json")) {
			return new JSONAccessor();
		}
		
		if (fileExtension.equals("yml")) {
			return new YAMLAccessor();
		}
		
		return new DemoPresentation();
	}
	
	
}
