package com.paintingscollectors.init;

import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.enums.StyleName;
import com.paintingscollectors.repository.StyleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FirstInit implements CommandLineRunner {

    private final Map<StyleName, String> styleDescriptions = Map.of(
            StyleName.IMPRESSIONISM, "Impressionism is a painting style most commonly associated with the 19th century where small brush strokes are used to build up a larger picture.",
            StyleName.ABSTRACT, "Abstract art does not attempt to represent recognizable subjects in a realistic manner. ",
            StyleName.EXPRESSIONISM, "Expressionism is a style of art that doesn't concern itself with realism.",
            StyleName.SURREALISM, "Surrealism is characterized by dreamlike, fantastical imagery that often defies logical explanation.",
            StyleName.REALISM, "Also known as naturalism, this style of art is considered as 'real art' and has been the dominant style of painting since the Renaissance.");

    private final StyleRepository styleRepository;

    public FirstInit(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (styleRepository.count() > 0) {
            return;
        }

        for (StyleName styleName : styleDescriptions.keySet()) {
            Style style = new Style(styleName, styleDescriptions.get(styleName));
            styleRepository.save(style);
        }
    }
}
