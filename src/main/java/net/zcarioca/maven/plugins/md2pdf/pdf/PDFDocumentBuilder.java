package net.zcarioca.maven.plugins.md2pdf.pdf;

import java.io.File;

class PDFDocumentBuilder {
    private File outputFile;

    private String metaAuthor;
    private String metaCompany;
    private boolean confidential;
    private String creationDate;
    private String description;
    private String metaTitle;

    private String author;
    private String company;
    private String companyLogo;
    private String project;
    private String projectLogo;
    private String title;
    private String subTitle;
    private String version;

    public File getOutputFile() {
        return outputFile;
    }

    public PDFDocumentBuilder setOutputFile(final File outputFile) {
        this.outputFile = outputFile;
        return this;
    }

    public String getMetaAuthor() {
        return metaAuthor;
    }

    public PDFDocumentBuilder setMetaAuthor(final String metaAuthor) {
        this.metaAuthor = metaAuthor;
        return this;
    }

    public String getMetaCompany() {
        return metaCompany;
    }

    public PDFDocumentBuilder setMetaCompany(final String metaCompany) {
        this.metaCompany = metaCompany;
        return this;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public PDFDocumentBuilder setConfidential(final boolean confidential) {
        this.confidential = confidential;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public PDFDocumentBuilder setCreationDate(final String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PDFDocumentBuilder setDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public PDFDocumentBuilder setMetaTitle(final String metaTitle) {
        this.metaTitle = metaTitle;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public PDFDocumentBuilder setAuthor(final String author) {
        this.author = author;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public PDFDocumentBuilder setCompany(final String company) {
        this.company = company;
        return this;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public PDFDocumentBuilder setCompanyLogo(final String companyLogo) {
        this.companyLogo = companyLogo;
        return this;
    }

    public String getProject() {
        return project;
    }

    public PDFDocumentBuilder setProject(final String project) {
        this.project = project;
        return this;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public PDFDocumentBuilder setProjectLogo(final String projectLogo) {
        this.projectLogo = projectLogo;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PDFDocumentBuilder setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public PDFDocumentBuilder setSubTitle(final String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public PDFDocumentBuilder setVersion(final String version) {
        this.version = version;
        return this;
    }

}
