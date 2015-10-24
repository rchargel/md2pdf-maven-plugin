package net.zcarioca.maven.plugins.md2pdf;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ToPDFSerializerTest extends AbstractTestHarness {

    private MarkdownToPDFConverter converter;
    private ToPDFSerializer serializer;
    private PDFFileStructure pdfData;

    @Override
    @Before
    public void setup() {
        super.setup();
        try {
            this.pdfData = PDFFileStructure.fromXML(markdownXmlFile, markdownDirectory);
            this.converter = new MarkdownToPDFConverter(markdownXmlFile, markdownDirectory, outputDir, log);
            this.serializer = new ToPDFSerializer(pdfData, converter.parseMarkdown());
        } catch (final IOException exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    public void testPrintPDF() throws Exception {
        final File tmpFile = File.createTempFile("pdfgentest", ".pdf");
        tmpFile.deleteOnExit();
        this.serializer.printPDF(tmpFile);
        System.out.println(tmpFile.getAbsolutePath());

        assertTrue(tmpFile.length() > 0);
    }

}
