package br.upe.ppsw.jabberpoint.control;

import java.io.IOException;

import br.upe.ppsw.jabberpoint.model.Presentation;

//TODO: AS - Hook Methods novos tipos de arquivos (JSON, HTML)
public interface Accessor {

  public static final String DEMO_NAME = "Apresentação de Demonstração";
  public static final String DEFAULT_EXTENSION = ".xml";

  public void loadFile(Presentation presentation, String fileName) throws IOException;

  //TODO: AS - Template Method
  public void saveFile(Presentation presentation, String fileName) throws IOException;

}
