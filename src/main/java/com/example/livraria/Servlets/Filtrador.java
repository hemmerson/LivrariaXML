package com.example.livraria.Servlets;

import com.example.livraria.XML.FiltraLivraria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Filtrador", urlPatterns = {"/filtrador"})
public class Filtrador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        request.setCharacterEncoding("UTF-8");

        String titulo = request.getParameter("titulo");

        try (PrintWriter out = response.getWriter()) {
            String caminho = getServletContext().getRealPath("/WEB-INF/livraria.xml");
            try {
                FiltraLivraria filtro = new FiltraLivraria(caminho);
                out.println(filtro.pegaPorTitulo(titulo));
            } catch (ParserConfigurationException | TransformerException | SAXException e) {
                out.println("<erro>" + e + "</erro>");
            }
        }
    }
}
