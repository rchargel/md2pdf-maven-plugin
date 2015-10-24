package net.zcarioca.maven.plugins.md2pdf.pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.ObjectUtils;

import net.zcarioca.maven.plugins.md2pdf.doc.DocumentModel;
import net.zcarioca.maven.plugins.md2pdf.doc.DocumentTOC;
import net.zcarioca.maven.plugins.md2pdf.doc.DocumentTOCItem;
import net.zcarioca.maven.plugins.md2pdf.doc.ObjectFactory;

public class PDFDocumentFactory {

    @SuppressWarnings("unchecked")
    public static final PDFDocument createFromXML(final File xmlFile, final File markdownFileDirectory, final File outputFileDirectory)
            throws IOException {
        try {
            final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class, DocumentModel.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final JAXBElement<DocumentModel> doc = (JAXBElement<DocumentModel>) unmarshaller.unmarshal(xmlFile);
            final DocumentModel model = doc.getValue();

            final PDFDocumentBuilder builder = new PDFDocumentBuilder()
                    .setOutputFile(outputFile(outputFileDirectory, model.getOutputName()))
                    .setAuthor(model.getCover().getAuthor())
                    .setCompany(model.getCover().getCompanyName())
                    .setCompanyLogo(model.getCover().getCompanyLogo())
                    .setConfidential(ObjectUtils.defaultIfNull(model.getMeta().getConfidential(), Boolean.FALSE))
                    .setCreationDate(model.getMeta().getCreationdate())
                    .setDescription(model.getMeta().getDescription())
                    .setMetaAuthor(ObjectUtils.defaultIfNull(model.getMeta().getAuthor(), model.getCover().getAuthor()))
                    .setMetaCompany(ObjectUtils.defaultIfNull(model.getMeta().getCreator(), model.getCover().getCompanyName()))
                    .setMetaTitle(ObjectUtils.defaultIfNull(model.getMeta().getTitle(), model.getCover().getCoverTitle()))
                    .setProject(model.getCover().getProjectName())
                    .setProjectLogo(model.getCover().getProjectLogo())
                    .setSubTitle(model.getCover().getCoverSubTitle())
                    .setTitle(model.getCover().getCoverTitle())
                    .setVersion(model.getCover().getCoverVersion());

            final List<File> tocItems = markdownFiles(markdownFileDirectory, model.getToc());

            return new PDFDocument(builder, tocItems);
        } catch (final JAXBException exc) {
            throw new IOException(exc.getMessage(), exc);
        }
    }

    private static File outputFile(final File outputDirectory, final String outputFileName) {
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        File outputFile = null;
        if (!outputFileName.endsWith(".pdf")) {
            outputFile = new File(outputDirectory, outputFileName + ".pdf");
        } else {
            outputFile = new File(outputDirectory, outputFileName);
        }
        return outputFile;
    }

    private static List<File> markdownFiles(final File markdownDirectory, final DocumentTOC toc) throws IOException {
        if (!markdownDirectory.exists()) {
            throw new IOException("No directory at " + markdownDirectory.getPath());
        }

        if (toc == null || toc.getItem() == null) {
            throw new IOException("No markdown files to convert");
        }
        final List<File> files = new ArrayList<File>(toc.getItem().size());
        for (final DocumentTOCItem item : toc.getItem()) {
            File file = null;
            if (!item.getRef().endsWith(".md")) {
                file = new File(markdownDirectory, item.getRef() + ".md");
            } else {
                file = new File(markdownDirectory, item.getRef());
            }
            if (!file.exists()) {
                throw new IllegalArgumentException("Could not find file " + file.getPath());
            }
            files.add(file);
        }
        return files;
    }
}
