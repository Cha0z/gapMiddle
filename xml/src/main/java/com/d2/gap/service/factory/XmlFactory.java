package com.d2.gap.service.factory;

import com.d2.gap.service.ParserType;
import com.d2.gap.service.XmlService;
import com.d2.gap.service.parser.DomParser;
import com.d2.gap.service.parser.JaxbParser;
import com.d2.gap.service.parser.SaxParser;

public class XmlFactory {

    public static XmlService getXmlParser(ParserType type) {
        XmlService service = null;

        switch (type) {
            case DOM: {
                service = new DomParser();
                break;
            }
            case SAX: {
                service = new SaxParser();
                break;
            }
            case JAXB: {
                service = new JaxbParser();
                break;
            }
        }
        return service;
    }

}
