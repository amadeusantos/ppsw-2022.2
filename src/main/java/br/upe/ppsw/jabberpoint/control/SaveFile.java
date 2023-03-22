package br.upe.ppsw.jabberpoint.control;

import java.io.IOException;

import br.upe.ppsw.jabberpoint.model.Presentation;

public interface SaveFile {
	  public void saveFile(Presentation presentation, String fileName) throws IOException;
}
