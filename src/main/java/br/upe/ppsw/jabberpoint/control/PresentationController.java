package br.upe.ppsw.jabberpoint.control;

import java.io.IOException;

import br.upe.ppsw.jabberpoint.model.Presentation;
import br.upe.ppsw.jabberpoint.view.SlideViewerFrame;

public class PresentationController {
	  protected static final String JABVERSION = "Jabberpoint 1.6 -";

	public Presentation loadPresentation(String... args)throws IOException {
		
		Presentation presentation = new Presentation();
		new SlideViewerFrame(JABVERSION, presentation);

		if (args.length == 0) {
			Accessor.getDemoAccessor().loadFile(presentation, "");
		} else {
			new XMLAccessor().loadFile(presentation, args[0]);
		}

		presentation.setSlideNumber(0);
		
		return presentation;

	}
}
