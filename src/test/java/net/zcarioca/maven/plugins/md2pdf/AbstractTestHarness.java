package net.zcarioca.maven.plugins.md2pdf;

import java.io.File;
import java.net.URL;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Before;

public class AbstractTestHarness {

    protected File markdownXmlFile;
    protected File markdownDirectory;
    protected File outputDir;
    protected Log log;

    @Before
    public void setup() {
        markdownXmlFile = getTestFile("/testfile.xml");
        markdownDirectory = getTestFile("/markdownfiles/introduction.md").getParentFile();
        outputDir = new File(System.getProperty("java.io.tmpdir"));

        this.log = new SystemStreamLog();
    }

    public static final File getTestFile(final String classpath) {
        try {
            final URL resourceURL = AbstractTestHarness.class.getResource(classpath);
            return new File(resourceURL.toURI().getPath());
        } catch (final Exception exc) {
            return null;
        }
    }

}
