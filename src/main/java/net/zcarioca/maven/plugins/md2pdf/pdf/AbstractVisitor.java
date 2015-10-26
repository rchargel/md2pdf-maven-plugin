package net.zcarioca.maven.plugins.md2pdf.pdf;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.pegdown.Printer;
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

public abstract class AbstractVisitor implements Visitor {
    protected final Log log = new SystemStreamLog();

    protected final Printer printer = new Printer();
    protected final Map<String, ReferenceNode> references = new HashMap<String, ReferenceNode>();
    protected final Map<String, String> abbreviations = new HashMap<String, String>();

    @Override
    public void visit(final AbbreviationNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final AnchorLinkNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final AutoLinkNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final BlockQuoteNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final BulletListNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final CodeNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final DefinitionListNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final DefinitionNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final DefinitionTermNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final ExpImageNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final ExpLinkNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final HeaderNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final HtmlBlockNode node) {
        log.debug(node.getClass().getSimpleName());
    }

    @Override
    public void visit(final InlineHtmlNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final ListItemNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final MailLinkNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final OrderedListNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final ParaNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final QuotedNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final ReferenceNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final RefImageNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final RefLinkNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final RootNode node) {
        for (final ReferenceNode refNode : node.getReferences()) {
            visitChildren(refNode);
            references.put(normalizeLink(printer.toString()), refNode);
            printer.clear();
        }
        for (final AbbreviationNode abbrNode : node.getAbbreviations()) {
            visitChildren(abbrNode);
            final String abbr = printer.getString();
            printer.clear();
            abbrNode.getExpansion().accept(this);
            final String expansion = printer.getString();
            abbreviations.put(abbr, expansion);
            printer.clear();
        }
        visitChildren(node);
    }

    @Override
    public void visit(final SimpleNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final SpecialTextNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final StrikeNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final StrongEmphSuperNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TableBodyNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TableCaptionNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TableCellNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TableColumnNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TableHeaderNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TableNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TableRowNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final VerbatimNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final WikiLinkNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final TextNode node) {
        log.debug(node.getClass().getSimpleName());

    }

    @Override
    public void visit(final SuperNode node) {
        visitChildren(node);

    }

    @Override
    public void visit(final Node node) {
        log.debug(node.getClass().getSimpleName());

    }

    protected void visitChildren(final SuperNode node) {
        for (final Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    protected String normalizeLink(final String string) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            final char c = string.charAt(i);
            switch (c) {
                case ' ':
                case '\n':
                case '\t':
                    continue;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

}
