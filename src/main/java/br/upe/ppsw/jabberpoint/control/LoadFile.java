package br.upe.ppsw.jabberpoint.control;

import java.io.IOException;

import br.upe.ppsw.jabberpoint.model.Presentation;

public interface LoadFile {
	  public void loadFile(Presentation presentation, String fileName) throws IOException;
}
