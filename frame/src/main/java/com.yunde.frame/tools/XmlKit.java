package com.yunde.frame.tools;

public class XmlKit {

    //XML格式处理
//    private void parse(List list, OMElement result, String nodeName) {
//        Iterator iterator = result.getChildElements();
//        QName qName = new QName(nodeName);
//        while (iterator.hasNext()) {
//            OMElement element = (OMElement) iterator.next();
//            if (!element.getQName().equals(qName)) {
//                parse(list, element, nodeName);
//            } else {
//                Map map = new HashMap();
//                Iterator<OMElement> iter = element.getChildElements();
//                while (iter.hasNext()) {
//                    OMElement node = iter.next();
//                    map.put(node.getLocalName(), node.getText());
//                    list.add(map);
//                }
//            }
//        }
//    }
}
