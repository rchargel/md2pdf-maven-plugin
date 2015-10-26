package net.zcarioca.maven.plugins.md2pdf.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

public class PDFUtils {
    public static Paragraph createParagraph(final String text, final Font font, final Integer align) {
        final Paragraph p = new Paragraph(text, font);
        if (align != null) {
            p.setAlignment(align);
        }
        return p;
    }

    public static Paragraph createSpacerParagraph(final Font font) {
        return new Paragraph(" ", font);
    }

    public static PdfPCell createCell(final Float height, final Integer border, final Integer vAlignment, final Integer hAlignment) {
        final PdfPCell cell = new PdfPCell();
        if (border != null)
            cell.setBorder(border);
        if (vAlignment != null)
            cell.setVerticalAlignment(vAlignment);
        if (hAlignment != null)
            cell.setHorizontalAlignment(hAlignment);
        if (height != null)
            cell.setMinimumHeight(height);
        return cell;
    }

    public static Image loadImageByURL(final String url, final Float maxHeight, final Float maxWidth) throws IOException {
        try {
            final URL imageUrl = new URL(url);
            final Image image = Image.getInstance(imageUrl);
            if (maxHeight != null && maxWidth != null) {
                if (image.getScaledHeight() > maxHeight || image.getScaledWidth() > maxWidth) {
                    image.scaleToFit(maxWidth, maxHeight);
                }
            }
            return image;
        } catch (final BadElementException exc) {
            throw new IOException("Could not generate image", exc);
        } catch (final MalformedURLException exc) {
            throw new IOException("Malformed URL: " + url, exc);
        }
    }

    public static Font getModifiedFont(final Font originalFont, final Integer style, final BaseColor color) {
        final Font newFont = new Font(originalFont);
        final int currentStyle = Math.max(0, originalFont.getStyle());
        if (style != null) {
            newFont.setStyle(currentStyle | style.intValue());
        }
        if (color != null) {
            newFont.setColor(color);
        }
        return newFont;
    }

}
