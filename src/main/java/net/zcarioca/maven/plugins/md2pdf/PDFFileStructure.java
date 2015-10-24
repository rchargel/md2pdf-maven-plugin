package net.zcarioca.maven.plugins.md2pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sun.xml.txw2.annotation.XmlElement;

class PDFFileStructure {
    private final String title;
    private final String subtitle;
    private final String titleImage;
    private final String leftHeader;
    private final String rightHeader;
    private final String leftFooter;
    private final String rightFooter;

    private final List<File> tocItems;

    private PDFFileStructure(final String title, final String subtitle, final String titleImage, final String leftHeader, final String rightHeader,
            final String leftFooter, final String rightFooter, final List<File> tocItems) {
        this.title = title;
        this.subtitle = subtitle;
        this.titleImage = titleImage;
        this.leftHeader = leftHeader;
        this.rightHeader = rightHeader;
        this.leftFooter = leftFooter;
        this.rightFooter = rightFooter;

        this.tocItems = Collections.unmodifiableList(tocItems);
    }

    static PDFFileStructure fromXML(final File xmlFile, final File markdownDirectory) throws IOException {
        try {
            final JAXBContext context = JAXBContext.newInstance(Document.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final Document doc = (Document) unmarshaller.unmarshal(xmlFile);

            final List<File> tocItems = new ArrayList<File>(doc.getToc().size());
            for (final TocItem item : doc.getToc()) {
                tocItems.add(markdownFile(item, markdownDirectory));
            }

            return new PDFFileStructure(doc.getTitle(), doc.getSubtitle(), doc.getTitleImage(), doc.getLeftHeader(), doc.getRightHeader(),
                    doc.getLeftFooter(), doc.getRightFooter(), tocItems);
        } catch (final JAXBException exc) {
            throw new IOException(exc.getMessage(), exc);
        }
    }

    static File markdownFile(final TocItem item, final File markdownDirectory) throws IOException {
        if (!markdownDirectory.exists()) {
            throw new IOException("Markdown directory not found: " + markdownDirectory.getPath());
        }

        String fileName = item.getFileName();
        if (!fileName.endsWith(".md")) {
            fileName = fileName + ".md";
        }
        final File mdFile = new File(markdownDirectory, fileName);
        if (!mdFile.exists()) {
            throw new IOException("Markdown file not found: " + mdFile.getPath());
        }
        return mdFile;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public String getLeftHeader() {
        return leftHeader;
    }

    public String getRightHeader() {
        return rightHeader;
    }

    public String getLeftFooter() {
        return leftFooter;
    }

    public String getRightFooter() {
        return rightFooter;
    }

    public List<File> getTocItems() {
        return tocItems;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @XmlRootElement
    private static class Document {
        String title;
        String subtitle;
        String titleImage;
        String leftHeader;
        String rightHeader;
        String leftFooter;
        String rightFooter;

        List<TocItem> tocItems;

        public String getTitle() {
            return title;
        }

        @XmlElement
        public void setTitle(final String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        @XmlElement
        public void setSubtitle(final String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTitleImage() {
            return titleImage;
        }

        @XmlElement
        public void setTitleImage(final String titleImage) {
            this.titleImage = titleImage;
        }

        public String getLeftHeader() {
            return leftHeader;
        }

        @XmlElement
        public void setLeftHeader(final String leftHeader) {
            this.leftHeader = leftHeader;
        }

        public String getRightHeader() {
            return rightHeader;
        }

        @XmlElement
        public void setRightHeader(final String rightHeader) {
            this.rightHeader = rightHeader;
        }

        public String getLeftFooter() {
            return leftFooter;
        }

        @XmlElement
        public void setLeftFooter(final String leftFooter) {
            this.leftFooter = leftFooter;
        }

        public String getRightFooter() {
            return rightFooter;
        }

        @XmlElement
        public void setRightFooter(final String rightFooter) {
            this.rightFooter = rightFooter;
        }

        public List<TocItem> getToc() {
            return tocItems;
        }

        @XmlElementWrapper
        @XmlElementRef
        public void setToc(final List<TocItem> tocItems) {
            this.tocItems = tocItems;
        }

    }

    @XmlRootElement(name = "item")
    private static class TocItem {
        private String fileName;

        public String getFileName() {
            return fileName;
        }

        @XmlAttribute(name = "file")
        public void setFileName(final String fileName) {
            this.fileName = fileName;
        }
    }
}
