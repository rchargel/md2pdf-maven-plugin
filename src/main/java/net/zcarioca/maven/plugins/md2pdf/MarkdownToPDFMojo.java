package net.zcarioca.maven.plugins.md2pdf;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal which touches a timestamp file.
 *
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class MarkdownToPDFMojo extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = false) private File outputDirectory;

    @Parameter(defaultValue = "${basedir}/src/site/markdown", property = "markdownFilesDir", required = false) private File markdownFilesDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        if (!markdownFilesDirectory.exists()) {
            throw new MojoExecutionException("Could not find directory " + markdownFilesDirectory);
        }

        final File[] markdownFiles = markdownFilesDirectory.listFiles(new MarkdownFileFilter());
        for (final File markdownFile : markdownFiles) {
            getLog().info("Found file: " + markdownFile.getName());
            try {
                new MarkdownToPDFConverter(markdownFile, null, outputDirectory, getLog()).execute();
            } catch (final IOException exc) {
                getLog().error(exc.getMessage(), exc);
            }
        }
    }

    private static class MarkdownFileFilter implements FileFilter {
        @Override
        public boolean accept(final File pathname) {
            return pathname.getName().toLowerCase().endsWith(".md");
        }
    }

}
