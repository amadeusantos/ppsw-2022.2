package br.upe.ppsw.jabberpoint;

import java.io.IOException;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import br.upe.ppsw.jabberpoint.control.LoadFile;
import br.upe.ppsw.jabberpoint.control.PresentationController;
import br.upe.ppsw.jabberpoint.model.Presentation;
import br.upe.ppsw.jabberpoint.view.SlideViewerFrame;
import br.upe.ppsw.jabberpoint.view.Style;

@SpringBootApplication
public class JabberPointApplication implements CommandLineRunner {

	protected static final String IOERR = "IO Error: ";
	protected static final String JABERR = "Jabberpoint Error ";
	protected static final String JABVERSION = "Jabberpoint 1.6 -";

	public static void main(String[] argv) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(JabberPointApplication.class);
		builder.headless(false);
		builder.web(WebApplicationType.NONE);
		builder.run(argv);
	}

	@Override
	public void run(String... args) throws Exception {
		Style.createStyles();
		try {
			Presentation presentation = new Presentation();
			String file = "";
			if(args.length != 0) {
				file = args[0];
			}
			
			LoadFile loadFile = PresentationController.setLoadAccessor(file);
			loadFile.loadFile(presentation, file);
			new SlideViewerFrame(JABVERSION, presentation);
			presentation.setSlideNumber(0);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, IOERR + ex, JABERR, JOptionPane.ERROR_MESSAGE);
		}
	}

}
