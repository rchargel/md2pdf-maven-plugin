package net.zcarioca.maven.plugins.md2pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

public class PDFFileStructureTest extends AbstractTestHarness {

    @Test
    public void testFromXML() throws IOException, URISyntaxException {
        final PDFFileStructure pdfFile = PDFFileStructure.fromXML(markdownXmlFile, markdownDirectory);

        assertNotNull(pdfFile);
        assertEquals(5, pdfFile.getTocItems().size());
    }

}
