package net.zcarioca.maven.plugins.md2pdf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.pegdown.ast.RootNode;

public class MarkdownToPDFConverterTest extends AbstractTestHarness {

    private MarkdownToPDFConverter converter;

    @Override
    @Before
    public void setup() {
        super.setup();
        try {
            this.converter = new MarkdownToPDFConverter(markdownXmlFile, markdownDirectory, outputDir, log);
        } catch (final IOException exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    public void testGenerateHTML() throws Exception {
        final String html = this.converter.generateMarkdown();
        assertNotNull(html);
    }

    @Test
    public void testParseMarkdown() throws Exception {
        final RootNode node = this.converter.parseMarkdown();

        assertNotNull(node);

        System.out.println(node.getChildren().get(0));

    }
}
