package com.example.livraria.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FiltraLivraria {
    private Document doc;
    public FiltraLivraria(String caminho) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor = fabrica.newDocumentBuilder();
        doc = construtor.parse(caminho);
    }

    private String serializar(Node no) throws TransformerException {
        TransformerFactory fabrica = TransformerFactory.newInstance();
        Transformer transformador = fabrica.newTransformer();
        DOMSource fonte = new DOMSource(no);

        ByteArrayOutputStream fluxo = new ByteArrayOutputStream();

        StreamResult saida = new StreamResult(fluxo);
        transformador.transform(fonte,saida);
        return fluxo.toString();
    }
    private String pegaPorTag(String tag, String valor) throws TransformerException {
        Node noLivro = null;
        NodeList filhos = doc.getElementsByTagName(tag);

        for (int i = filhos.getLength()-1; i >= 0 ; i--){
            Node noFilho = filhos.item(i);
            if (noFilho != null) {
                if (!noFilho.getFirstChild().getNodeValue().equals(valor)) {
                    noLivro = noFilho.getParentNode();
                    noLivro.getParentNode().removeChild(noLivro);
                }
            }
        }
        return serializar(doc);
    }

    public String pegaPorTitulo(String valor) throws TransformerException {
        return pegaPorTag("titulo", valor);
    }

    public String pegaPorAutor(String valor) throws TransformerException {
        return pegaPorTag("autor", valor);
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        FiltraLivraria filtro = new FiltraLivraria("src/main/java/com/example/livraria/XML/livraria.xml");
//        System.out.println(filtro.pegaPorTitulo("Introdução ao XML"));
        System.out.println(filtro.pegaPorAutor("José"));
    }
}
