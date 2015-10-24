package net.zcarioca.maven.plugins.md2pdf.pdf;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class PDFDocument {
    private final File outputFile;
    private final PDFMetaData meta;
    private final PDFCover cover;
    private final List<File> markdownFiles;

    PDFDocument(final PDFDocumentBuilder builder, final List<File> markdownFiles) {
        this.outputFile = builder.getOutputFile();
        this.meta = new PDFMetaData(builder);
        this.cover = new PDFCover(builder);
        this.markdownFiles = Collections.unmodifiableList(markdownFiles);
    }

    public File getOutputFile() {
        return outputFile;
    }

    public PDFCover getCover() {
        return cover;
    }

    public PDFMetaData getMetaData() {
        return meta;
    }

    public List<File> getMarkdownFiles() {
        return markdownFiles;
    }

    // static File markdownFile(final TocItem item, final File markdownDirectory) throws IOException {
    // if (!markdownDirectory.exists()) {
    // throw new IOException("Markdown directory not found: " + markdownDirectory.getPath());
    // }
    //
    // String fileName = item.getFileName();
    // if (!fileName.endsWith(".md")) {
    // fileName = fileName + ".md";
    // }
    // final File mdFile = new File(markdownDirectory, fileName);
    // if (!mdFile.exists()) {
    // throw new IOException("Markdown file not found: " + mdFile.getPath());
    // }
    // return mdFile;
    // }

}
