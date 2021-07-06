package com.fajar.employeedata.config;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class LogParser {
    public static Collection<Long> getTimestampsByDescription(String xml, String description) throws Exception {
    	Collection<Long> result = new ArrayList<>();
    	
    	Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		.parse(new InputSource(new StringReader(xml)));
    	Node logNode = doc.getElementsByTagName("log").item(0);
    	List<Node> events = getNodeByTag(logNode, "event");
    	for (Node node : events) {
			List<Node> descList = getNodeByTag(node, "description");
			String desc = descList.get(0).getTextContent();
			if (description.equals(desc)) {
				String timestamp = (node.getAttributes().getNamedItem("timestamp").getNodeValue());
				result.add(Long.valueOf(timestamp));
			}
		}
    	
		return result ;
    }
    
    private static List<Node> getNodeByTag(Node node, String tag) {
    	NodeList events = node.getChildNodes();
    	List<Node> nodes = new ArrayList<>();
    	for (int i = 0; i < events.getLength(); i++) {
			Node item = (events.item(i));
			if (tag.equals(item.getNodeName())) {
				nodes.add(item);
				
			}
		}
    	return nodes;
    }
    
    public static void main(String[] args) throws Exception {
        String xml = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<log>\n" +
                "    <event timestamp=\"1614285589\">\n" +
                "        <description>Intrusion detected</description>\n" +
                "    </event>\n" +
                "    <event timestamp=\"1614286432\">\n" +
                "        <description>Intrusion ended</description>\n" +
                "    </event>\n" +
                "</log>";
        
        Collection<Long> timestamps = getTimestampsByDescription(xml, "Intrusion ended");
        for(long timestamp: timestamps)
            System.out.println(timestamp); 
    }
}