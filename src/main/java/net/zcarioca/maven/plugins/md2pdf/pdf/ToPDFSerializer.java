package net.zcarioca.maven.plugins.md2pdf.pdf;

import static java.lang.String.format;
import static net.zcarioca.maven.plugins.md2pdf.pdf.PDFUtils.createCell;
import static net.zcarioca.maven.plugins.md2pdf.pdf.PDFUtils.createParagraph;
import static net.zcarioca.maven.plugins.md2pdf.pdf.PDFUtils.createSpacerParagraph;
import static net.zcarioca.maven.plugins.md2pdf.pdf.PDFUtils.getModifiedFont;
import static net.zcarioca.maven.plugins.md2pdf.pdf.PDFUtils.loadImageByURL;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.pegdown.ast.AnchorLinkNode;
import org.pegdown.ast.CodeNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SimpleNode;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrongEmphSuperNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TextNode;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class ToPDFSerializer extends AbstractVisitor {
    private final Section[] sectionsByLevel = new Section[6];

    private final PDFDocument pdfData;
    private final RootNode rootNode;
    private final Font textFont;
    private final Font codeFont;
    private final Font titleFont;
    private final Font subTitleFont;
    private final Font smallFont;

    private Document document;
    private PdfWriter writer;
    private OutputStream outputStream;
    private Chapter chapter;
    private Section section;
    private Paragraph paragraph;
    private Anchor anchor;
    private Font currentFont;
    private float pageHeight;
    private float pageWidth;
    private int chapterNumber = 1;

    ToPDFSerializer(final PDFDocument pdfFileStructure, final RootNode rootNode) {
        this.pdfData = pdfFileStructure;
        this.rootNode = rootNode;

        this.textFont = new Font(FontFamily.HELVETICA, 10);
        this.codeFont = new Font(FontFamily.COURIER, 10);
        this.titleFont = new Font(FontFamily.HELVETICA, 24, Font.BOLD);
        this.subTitleFont = new Font(FontFamily.HELVETICA, 16, Font.BOLD);
        this.smallFont = new Font(FontFamily.HELVETICA, 8, Font.ITALIC);
    }

    public void printPDF(final File outputFile) throws IOException {
        this.document = new Document(PageSize.LETTER, 35, 35, 50, 50);
        this.outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

        this.pageHeight = this.document.getPageSize().getHeight() - (this.document.bottomMargin() + this.document.topMargin());
        this.pageWidth = this.document.getPageSize().getWidth() - (this.document.leftMargin() + this.document.rightMargin());

        try {
            writer = PdfWriter.getInstance(this.document, this.outputStream);
            writer.setLinearPageMode();

            this.document.open();

            writeFrontPage();

            rootNode.accept(this);

            closeDocument();
        } catch (final RuntimeException exc) {
            throw new IOException(exc.getMessage(), exc);
        } catch (final DocumentException exc) {
            throw new IOException(exc.getMessage(), exc);
        }
    }

    private void writeFrontPage() throws IOException, DocumentException {
        this.document.addTitle(pdfData.getMetaData().getTitle());
        this.document.addAuthor(pdfData.getMetaData().getAuthor());
        this.document.addCreator(pdfData.getMetaData().getCompany());
        this.document.addSubject(pdfData.getMetaData().getDescription());

        final PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.getDefaultCell().setBorderWidth(0);
        final PdfPCell t = createCell(pageHeight / 3f, PdfPCell.NO_BORDER, Element.ALIGN_TOP, Element.ALIGN_CENTER);
        final PdfPCell c = createCell(pageHeight / 3f, PdfPCell.NO_BORDER, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
        final PdfPCell b = createCell(pageHeight / 3f, PdfPCell.NO_BORDER, Element.ALIGN_BOTTOM, Element.ALIGN_CENTER);

        // top title
        if (isNotBlank(pdfData.getCover().getCompany())) {
            t.addElement(createParagraph(pdfData.getCover().getCompany(), subTitleFont, Element.ALIGN_CENTER));
            t.addElement(createSpacerParagraph(subTitleFont));
        }
        if (isNotBlank(pdfData.getCover().getCompanyLogo())) {
            final Paragraph p = new Paragraph(new Chunk(loadImageByURL(pdfData.getCover().getCompanyLogo(), 50f, pageWidth), 0, 0, true));
            p.setAlignment(Element.ALIGN_CENTER);
            t.addElement(p);
        }

        // center title
        c.addElement(createParagraph(this.pdfData.getCover().getTitle(), titleFont, Element.ALIGN_CENTER));
        c.addElement(createSpacerParagraph(textFont));
        if (isNotBlank(pdfData.getCover().getSubTitle())) {
            c.addElement(createParagraph(pdfData.getCover().getSubTitle(), subTitleFont, Element.ALIGN_CENTER));
            c.addElement(createSpacerParagraph(titleFont));
        }
        if (isNotBlank(pdfData.getCover().getVersion())) {
            c.addElement(createParagraph(pdfData.getCover().getVersion(), textFont, Element.ALIGN_CENTER));
        }
        if (isNotBlank(pdfData.getCover().getAuthor())) {
            c.addElement(createParagraph(pdfData.getCover().getAuthor(), textFont, Element.ALIGN_CENTER));
        }

        // bottom title
        if (isNotBlank(pdfData.getCover().getProjectLogo())) {
            final Paragraph p = new Paragraph(new Chunk(loadImageByURL(pdfData.getCover().getProjectLogo(), 50f, pageWidth), 0, 0, true));
            p.setAlignment(Element.ALIGN_CENTER);
            b.addElement(p);
        }
        if (isNotBlank(pdfData.getCover().getProject())) {
            b.addElement(createSpacerParagraph(textFont));
            b.addElement(createParagraph(pdfData.getCover().getProject(), subTitleFont, Element.ALIGN_CENTER));
        }

        table.addCell(t);
        table.addCell(c);
        table.addCell(b);
        this.document.add(table);
        this.document.newPage();
        writer.setPageEmpty(false);
    }

    private void closeDocument() throws DocumentException {
        if (this.chapter != null) {
            this.document.add(chapter);
        }
        this.document.close();
        IOUtils.closeQuietly(this.outputStream);
    }

    @Override
    public void visit(final TextNode node) {
        this.printer.print(node.getText());
    }

    @Override
    public void visit(final AnchorLinkNode node) {
        if (StringUtils.isNotBlank(node.getName())) {
            this.anchor = new Anchor();
            if (this.paragraph != null) {
                this.anchor.setFont(this.currentFont);
            }
            anchor.setName(node.getName());
        }
    }

    @Override
    public void visit(final StrongEmphSuperNode node) {
        if (node.isClosed()) {
            if (this.inPara()) {
                append(getAndClearPrinter());
                Font font = null;
                if (node.isStrong()) {
                    font = getModifiedFont(currentFont, Font.BOLD, null);
                } else {
                    font = getModifiedFont(currentFont, Font.ITALIC, null);
                }
                final Font oldFont = this.currentFont;
                this.currentFont = font;
                visitChildren(node);
                append(new Chunk(getAndClearPrinter(), font));
                this.currentFont = oldFont;
            } else {
                log.warn("No paragraph available");
            }
        } else {
            visitChildren(node);
        }
    }

    @Override
    public void visit(final ExpLinkNode node) {
        final String url = node.url;
        if (StringUtils.isNotBlank(url)) {
            if (this.inPara()) {
                append(getAndClearPrinter());
                final Font hyperlinkFont = getModifiedFont(this.paragraph.getFont(), Font.UNDERLINE, BaseColor.BLUE);
                if (url.startsWith("#")) {
                    final Anchor link = new Anchor();
                    link.setFont(hyperlinkFont);
                    link.setReference(url);
                    visitChildren(node);
                    link.add(getAndClearPrinter());
                    append(link);
                }
            }
        } else {
            visitChildren(node);
        }
    }

    @Override
    public void visit(final ParaNode node) {
        this.paragraph = new Paragraph();
        this.paragraph.setFont(textFont);
        this.currentFont = textFont;
        this.paragraph.setLeading(20f);

        visitChildren(node);
        this.paragraph.add(getAndClearPrinter());

        this.section.add(this.paragraph);
    }

    @Override
    public void visit(final SimpleNode node) {
        switch (node.getType()) {
            case HRule:
                final LineSeparator line = new LineSeparator(1, 100, BaseColor.BLACK, Element.ALIGN_CENTER, 25);
                final Paragraph p = new Paragraph(40);
                p.add(line);
                p.setSpacingAfter(15);
                this.section.add(p);
                break;
            default:
                log.debug("Simple Node: " + node.getType());
                break;
        }
    }

    @Override
    public void visit(final SpecialTextNode node) {
        printer.print(node.getText());
    }

    @Override
    public void visit(final CodeNode node) {
        append(getAndClearPrinter());
        this.currentFont = codeFont;
        append(new Chunk(node.getText(), codeFont));
    }

    @Override
    public void visit(final HeaderNode node) {
        final int level = node.getLevel();
        final int fontSize = 18 - ((level - 1) * 2);
        final String title = getTextContent(node);
        final Font font = new Font(FontFamily.HELVETICA, fontSize, Font.BOLD);

        this.paragraph = new Paragraph();
        this.paragraph.setFont(font);
        this.paragraph.setLeading(30f - ((level - 1) * 2));
        this.currentFont = font;

        visitChildren(node);
        if (this.anchor != null) {
            this.paragraph.add(anchor);
        }
        append(getAndClearPrinter());

        if (level == 1) {
            createChapter();
            this.section.setTitle(this.paragraph);
            this.section.setBookmarkTitle(normalizeLink(title));
            this.sectionsByLevel[level - 1] = this.section;
        } else {
            if (this.sectionsByLevel[level - 2] == null) {
                throw new RuntimeException(format("Could not add section title '%s', there is no parent section.", title));
            }
            final Section section = this.sectionsByLevel[level - 2].addSection(this.paragraph);
            section.setBookmarkTitle(normalizeLink(title));
            this.sectionsByLevel[level - 1] = section;
            this.section = section;
        }
        this.anchor = null;
        this.paragraph = null;
    }

    protected String getTextContent(final SuperNode node) {
        final StringBuilder sb = new StringBuilder();
        for (final Node child : node.getChildren()) {
            if (child instanceof TextNode) {
                sb.append(' ').append(((TextNode) child).getText()).append(' ');
            } else if (child instanceof SuperNode) {
                sb.append(getTextContent((SuperNode) child));
            }
        }
        return sb.toString().trim();
    }

    protected void createChapter() {
        final Chapter chapter = new Chapter(chapterNumber++);
        try {
            if (this.chapter != null) {
                this.document.add(this.chapter);
            }
        } catch (final DocumentException exc) {
            throw new RuntimeException("Could not add chapter", exc);
        }
        this.section = chapter;
        this.chapter = chapter;
    }

    protected String getAndClearPrinter() {
        try {
            return printer.getString();
        } finally {
            printer.clear();
        }
    }

    private boolean inPara() {
        return this.anchor != null || this.paragraph != null;
    }

    private void append(final Element element) {
        if (this.anchor != null) {
            this.anchor.add(element);
        } else {
            this.paragraph.add(element);
        }
    }

    private void append(final String text) {
        if (this.anchor != null) {
            this.anchor.add(text);
        } else {
            this.paragraph.add(text);
        }
    }

}
