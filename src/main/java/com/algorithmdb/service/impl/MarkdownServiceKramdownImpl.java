package com.algorithmdb.service.impl;

import com.algorithmdb.service.MarkdownService;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MarkdownServiceKramdownImpl implements MarkdownService {

    private final Logger log = LoggerFactory.getLogger(MarkdownServiceKramdownImpl.class);

    @Override
    public String generateHtmlFromMarkdown(String markdown) {
        log.debug("Request to generate HTML for markdown (Kramdown): {}", markdown);
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.KRAMDOWN);
        options.set(Parser.EXTENSIONS, Arrays.asList(
            AbbreviationExtension.create(),
            DefinitionExtension.create(),
            FootnoteExtension.create(),
            TablesExtension.create(),
            TypographicExtension.create()
        ));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

}
