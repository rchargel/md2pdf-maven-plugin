package net.zcarioca.maven.plugins.md2pdf.pdf;

public class PDFMetaData {
    private final String author;
    private final String company;
    private final boolean confidential;
    private final String creationDate;
    private final String description;
    private final String title;

    PDFMetaData(final PDFDocumentBuilder builder) {
        this.author = builder.getMetaAuthor();
        this.company = builder.getMetaCompany();
        this.confidential = builder.isConfidential();
        this.creationDate = builder.getCreationDate();
        this.description = builder.getDescription();
        this.title = builder.getMetaTitle();
    }

    public String getAuthor() {
        return author;
    }

    public String getCompany() {
        return company;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

}
