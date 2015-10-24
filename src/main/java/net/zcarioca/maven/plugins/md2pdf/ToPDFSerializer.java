package net.zcarioca.maven.plugins.md2pdf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.pegdown.ast.AbbreviationNode;
import org.pegdown.ast.AnchorLinkNode;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.CodeNode;
import org.pegdown.ast.DefinitionListNode;
import org.pegdown.ast.DefinitionNode;
import org.pegdown.ast.DefinitionTermNode;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.HtmlBlockNode;
import org.pegdown.ast.InlineHtmlNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.OrderedListNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.QuotedNode;
import org.pegdown.ast.RefImageNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.ReferenceNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SimpleNode;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrikeNode;
import org.pegdown.ast.StrongEmphSuperNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TableBodyNode;
import org.pegdown.ast.TableCaptionNode;
import org.pegdown.ast.TableCellNode;
import org.pegdown.ast.TableColumnNode;
import org.pegdown.ast.TableHeaderNode;
import org.pegdown.ast.TableNode;
import org.pegdown.ast.TableRowNode;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.VerbatimNode;
import org.pegdown.ast.Visitor;
import org.pegdown.ast.WikiLinkNode;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

class ToPDFSerializer implements Visitor {
    private final PDFFileStructure pdfData;
    private final RootNode rootNode;
    private final Font textFont;
    private final Font codeFont;
    private final Font titleFont;
    private final Font subTitleFont;
    private final Font smallFont;

    private Document document;
    private PdfWriter writer;
    private OutputStream outputStream;

    ToPDFSerializer(final PDFFileStructure pdfFileStructure, final RootNode rootNode) {
        this.pdfData = pdfFileStructure;
        this.rootNode = rootNode;

        this.textFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        this.codeFont = FontFactory.getFont(FontFactory.COURIER, 10);
        this.titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        this.subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        this.smallFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8);
    }

    public void printPDF(final File outputFile) throws IOException {
        this.document = new Document(PageSize.LETTER, 35, 35, 50, 50);
        this.outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            writer = PdfWriter.getInstance(this.document, this.outputStream);
            writer.setLinearPageMode();

            this.document.open();

            writeFrontPage();

            closeDocument();
        } catch (final DocumentException exc) {
            throw new IOException(exc.getMessage(), exc);
        }
    }

    private void writeFrontPage() throws DocumentException {
        this.document.addCreationDate();
        this.document.addTitle(pdfData.getTitle());

        final PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.getDefaultCell().setBorderWidth(0);
        final PdfPCell c = new PdfPCell();
        c.setBorder(PdfPCell.NO_BORDER);
        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
        c.setMinimumHeight(document.getPageSize().getHeight() - (document.bottomMargin() + document.topMargin()));

        final Paragraph title = new Paragraph(this.pdfData.getTitle(), this.titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        c.addElement(title);
        c.addElement(new Paragraph(" ", this.titleFont));
        if (StringUtils.isNotBlank(pdfData.getSubtitle())) {
            final Paragraph subtitle = new Paragraph(pdfData.getSubtitle(), this.subTitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            c.addElement(subtitle);
        }
        table.addCell(c);
        this.document.add(table);
    }

    private void closeDocument() {
        this.document.close();
        IOUtils.closeQuietly(this.outputStream);
    }

    @Override
    public void visit(final AbbreviationNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final AnchorLinkNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final AutoLinkNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final BlockQuoteNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final BulletListNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final CodeNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final DefinitionListNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final DefinitionNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final DefinitionTermNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final ExpImageNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final ExpLinkNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final HeaderNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final HtmlBlockNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final InlineHtmlNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final ListItemNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final MailLinkNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final OrderedListNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final ParaNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final QuotedNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final ReferenceNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final RefImageNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final RefLinkNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final RootNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final SimpleNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final SpecialTextNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final StrikeNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final StrongEmphSuperNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TableBodyNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TableCaptionNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TableCellNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TableColumnNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TableHeaderNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TableNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TableRowNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final VerbatimNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final WikiLinkNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final TextNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final SuperNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final Node node) {
        // TODO Auto-generated method stub

    }

}
