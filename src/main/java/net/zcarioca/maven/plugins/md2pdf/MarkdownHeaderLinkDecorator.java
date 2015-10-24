package net.zcarioca.maven.plugins.md2pdf;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.rjeschke.txtmark.DefaultDecorator;

public class MarkdownHeaderLinkDecorator extends DefaultDecorator {
    private final AtomicInteger[] counters = new AtomicInteger[] {
            new AtomicInteger(0),
            new AtomicInteger(0),
            new AtomicInteger(0),
            new AtomicInteger(0),
            new AtomicInteger(0),
            new AtomicInteger(0),
            new AtomicInteger(0)
    };

    @Override
    public void closeHeadline(final StringBuilder out, final int level) {
        incrCounterAtLevel(level);
        final String tag = "<h" + level;
        final int indexOf = out.lastIndexOf(tag);
        final String content = out.substring(indexOf + 4);
        final String tagName = content.toLowerCase().replaceAll("[^a-z0-9]", "-");

        out.insert(indexOf + 4, getMenuNumber(level));
        out.insert(indexOf + 3, " id=\"" + tagName + "\"");
        out.append("</h" + level + ">");
    }

    private String getMenuNumber(final int level) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < level; i++) {
            sb.append(counters[i].get()).append('.');
        }
        sb.append(' ');
        return sb.toString();
    }

    private void incrCounterAtLevel(final int level) {
        synchronized (counters) {
            counters[level - 1].incrementAndGet();
            for (int i = level + 1; i <= counters.length; i++) {
                counters[i - 1].set(0);
            }
        }
    }
}
