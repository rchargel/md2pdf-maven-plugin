package net.zcarioca.maven.plugins.md2pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import net.zcarioca.maven.plugins.md2pdf.doc.DocumentModel;
import net.zcarioca.maven.plugins.md2pdf.doc.ObjectFactory;

public class DocumentModelUnmarshalTest extends AbstractTestHarness {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class, DocumentModel.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final JAXBElement<DocumentModel> doc = (JAXBElement<DocumentModel>) unmarshaller.unmarshal(markdownXmlFile);
        assertNotNull(doc);

        final DocumentModel model = doc.getValue();
        assertNotNull(model);
        assertNotNull(model.getMeta());
        assertEquals("outputfile", model.getOutputName());
        assertEquals("ZCarioca", model.getMeta().getAuthor());
        assertEquals("2015-10-25", model.getMeta().getCreationdate());
        assertEquals("This is a test document", model.getMeta().getDescription());
        assertEquals("My Test Document", model.getMeta().getTitle());
        assertNotNull(model.getCover());
        assertEquals("My Name", model.getCover().getAuthor());
        assertEquals("My Company Name", model.getCover().getCompanyName());
        assertEquals("https://maven.apache.org/images/maven-logo-black-on-white.png", model.getCover().getCompanyLogo());
        assertEquals("My Test Document", model.getCover().getCoverTitle());
        assertEquals("A cool document", model.getCover().getCoverSubTitle());
        assertEquals("1.0", model.getCover().getCoverVersion());
        assertEquals("Maven Test Project", model.getCover().getProjectName());
        assertEquals("http://www.rutzenholzer.eu/static/pic/git.png", model.getCover().getProjectLogo());
        assertNotNull(model.getToc());
        assertEquals(5, model.getToc().getItem().size());
    }

}
