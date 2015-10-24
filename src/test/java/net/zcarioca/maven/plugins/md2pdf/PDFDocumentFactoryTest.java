package net.zcarioca.maven.plugins.md2pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import net.zcarioca.maven.plugins.md2pdf.pdf.PDFDocument;
import net.zcarioca.maven.plugins.md2pdf.pdf.PDFDocumentFactory;

public class PDFDocumentFactoryTest extends AbstractTestHarness {

    @Test
    public void test() throws IOException {
        final PDFDocument document = PDFDocumentFactory.createFromXML(markdownXmlFile, markdownDirectory, outputDir);

        assertNotNull(document);
        assertNotNull(document.getCover());
        assertNotNull(document.getMetaData());
        assertNotNull(document.getMarkdownFiles());
        assertNotNull(document.getOutputFile());

        assertEquals(5, document.getMarkdownFiles().size());
        assertEquals(new File(outputDir, "outputfile.pdf").getAbsolutePath(), document.getOutputFile().getAbsolutePath());
        assertEquals("ZCarioca", document.getMetaData().getAuthor());
        assertEquals("2015-10-25", document.getMetaData().getCreationDate());
        assertEquals("This is a test document", document.getMetaData().getDescription());
        assertEquals("My Test Document", document.getMetaData().getTitle());
        assertEquals("My Name", document.getCover().getAuthor());
        assertEquals("My Company Name", document.getCover().getCompany());
        assertEquals("https://maven.apache.org/images/maven-logo-black-on-white.png", document.getCover().getCompanyLogo());
        assertEquals("My Test Document", document.getCover().getTitle());
        assertEquals("A cool document", document.getCover().getSubTitle());
        assertEquals("1.0", document.getCover().getVersion());
        assertEquals("Maven Test Project", document.getCover().getProject());
        assertEquals("http://www.rutzenholzer.eu/static/pic/git.png", document.getCover().getProjectLogo());
    }

}
