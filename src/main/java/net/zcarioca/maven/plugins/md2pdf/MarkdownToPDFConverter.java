package net.zcarioca.maven.plugins.md2pdf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

import net.zcarioca.maven.plugins.md2pdf.pdf.PDFDocument;
import net.zcarioca.maven.plugins.md2pdf.pdf.PDFDocumentFactory;

public class MarkdownToPDFConverter {

    private final PDFDocument markdownContent;
    private final File pdfFile;
    private final Log log;

    public MarkdownToPDFConverter(final File markdownXmlFile, final File markdownDir, final File outputDir, final Log log) throws IOException {
        this.markdownContent = PDFDocumentFactory.createFromXML(markdownXmlFile, markdownDir, outputDir);
        this.pdfFile = new File(outputDir, markdownXmlFile.getName().substring(0, markdownXmlFile.getName().lastIndexOf(".")) + ".pdf");
        this.log = log;
    }

    public void execute() throws MojoExecutionException {
        log.info("Generating PDF file: " + this.pdfFile);

        final String html = generateHTML();
        log.info(html);
    }

    String generateMarkdown() {
        final StringWriter writer = new StringWriter();

        for (final File markdownFile : this.markdownContent.getMarkdownFiles()) {
            InputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream(new FileInputStream(markdownFile));

                writer.write(IOUtils.toString(inputStream));
                writer.write("\n\n");
            } catch (final IOException exc) {
                this.log.error("Error parsing markdown file: " + markdownFile.getPath(), exc);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return writer.toString();
    }

    RootNode parseMarkdown() {
        final PegDownProcessor processor = new PegDownProcessor(Extensions.ABBREVIATIONS | Extensions.AUTOLINKS |
                Extensions.TABLES | Extensions.DEFINITIONS | Extensions.FENCED_CODE_BLOCKS | Extensions.STRIKETHROUGH |
                Extensions.ANCHORLINKS | Extensions.ALL_OPTIONALS | Extensions.FORCELISTITEMPARA);

        return processor.parseMarkdown(generateMarkdown().toCharArray());
    }

    String generateHTML() {
        final StringWriter writer = new StringWriter();
        writer.write("<html>\n<body>\n");

        // final Configuration config = Configuration.builder()
        // .setDecorator(new MarkdownHeaderLinkDecorator())
        // .forceExtentedProfile()
        // .setSafeMode(true)
        // .setEnablePanicMode(true)
        // .build();

        final PegDownProcessor processor = new PegDownProcessor(Extensions.ABBREVIATIONS | Extensions.AUTOLINKS |
                Extensions.TABLES | Extensions.DEFINITIONS | Extensions.FENCED_CODE_BLOCKS | Extensions.STRIKETHROUGH |
                Extensions.ANCHORLINKS | Extensions.ALL_OPTIONALS | Extensions.FORCELISTITEMPARA);

        for (final File markdownFile : this.markdownContent.getMarkdownFiles()) {
            InputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream(new FileInputStream(markdownFile));

                writer.write(processor.markdownToHtml(IOUtils.toString(inputStream)));
                // writer.write(Processor.process(markdownFile, config));
            } catch (final IOException exc) {
                this.log.error("Error parsing markdown file: " + markdownFile.getPath(), exc);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }

        writer.write("</body>\n</html>");

        return writer.toString();
    }

}
