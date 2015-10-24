package net.zcarioca.maven.plugins.md2pdf.pdf;

public class PDFCover {

    private final String author;
    private final String company;
    private final String companyLogo;
    private final String project;
    private final String projectLogo;
    private final String title;
    private final String subTitle;
    private final String version;

    PDFCover(final PDFDocumentBuilder builder) {
        this.author = builder.getAuthor();
        this.company = builder.getCompany();
        this.companyLogo = builder.getCompanyLogo();
        this.project = builder.getProject();
        this.projectLogo = builder.getProjectLogo();
        this.title = builder.getTitle();
        this.subTitle = builder.getSubTitle();
        this.version = builder.getVersion();
    }

    public String getAuthor() {
        return author;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public String getProject() {
        return project;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getVersion() {
        return version;
    }

}
