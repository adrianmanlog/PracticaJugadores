package com.mycompany.jugadorarchivosadriangalilea;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase `GestionXML` es una implementación de la clase abstracta `GestionFichero`
 * que permite gestionar la información de jugadores utilizando un archivo XML.
 * Esta clase ofrece métodos para agregar, eliminar, modificar, obtener y listar jugadores
 * almacenados en un archivo XML, garantizando la persistencia de los datos.
 * Utiliza la API de DOM (Document Object Model) para la manipulación del archivo XML.
 */
public class GestionXML extends GestionFichero {
    private File archivoXML;

    /**
     * Constructor de la clase `GestionXML`.
     * Inicializa el archivo XML con la estructura básica si no existe.
     *
     * @param nombreArchivo Nombre del archivo XML donde se almacenarán los datos de los jugadores.
     * @throws IOException Si ocurre un error al crear o acceder al archivo.
     */
    public GestionXML(String nombreArchivo) throws IOException {
        archivoXML = new File(nombreArchivo);
        if (!archivoXML.exists()) {
            inicializarArchivoXML(); // Crear archivo XML vacío.
        }
    }

    /**
     * Agrega un nuevo jugador al archivo XML.
     *
     * @param jugador Objeto de la clase `Jugador`.
     * @return `true` si el jugador se agrega correctamente, `false` si ya existe un jugador con el mismo ID.
     * @throws IOException Si ocurre un error al leer o modificar el archivo XML.
     */
    @Override
    public Boolean agregarJugador(Jugador jugador) throws IOException {
        try {
            Document doc = cargarDocumento();
            Element raiz = doc.getDocumentElement();

            NodeList jugadores = raiz.getElementsByTagName("jugador");


            for (int i = 0; i < jugadores.getLength(); i++) {
                Element jugadorElement = (Element) jugadores.item(i);
                int idJugador = Integer.parseInt(jugadorElement.getElementsByTagName("id").item(0).getTextContent());
                if (idJugador == jugador.getId()) {
                    return false;
                }
            }


            Element nuevoJugadorElement = crearElementoJugador(doc, jugador);
            raiz.appendChild(nuevoJugadorElement);
            guardarDocumento(doc);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un jugador del archivo XML según su ID.
     *
     * @param id ID del jugador que se desea eliminar.
     * @return `true` si el jugador fue eliminado correctamente, `false` si no se encontró el jugador con el ID especificado.
     * @throws IOException Si ocurre un error al leer o modificar el archivo XML.
     */
    @Override
    public Boolean eliminarJugador(int id) throws IOException {
        try {
            Document doc = cargarDocumento();
            Element raiz = doc.getDocumentElement();

            NodeList jugadores = raiz.getElementsByTagName("jugador");
            boolean eliminado = false;

            for (int i = 0; i < jugadores.getLength(); i++) {
                Element jugadorElement = (Element) jugadores.item(i);
                int idJugador = Integer.parseInt(jugadorElement.getElementsByTagName("id").item(0).getTextContent());

                if (idJugador == id) {
                    raiz.removeChild(jugadorElement);
                    eliminado = true;
                    break;
                }
            }

            if (eliminado) {
                guardarDocumento(doc);
            }

            return eliminado;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modifica la información de un jugador en el archivo XML según su ID.
     *
     * @param id del jugador a modificar.
     * @param jugadorModificado ugador con los nuevos datos del jugador.
     * @throws IOException Si ocurre un error al leer o modificar el archivo XML.
     */
    @Override
    public void modificarJugador(int id, Jugador jugadorModificado) throws IOException {
        try {
            Document doc = cargarDocumento();
            Element raiz = doc.getDocumentElement();

            NodeList jugadores = raiz.getElementsByTagName("jugador");
            for (int i = 0; i < jugadores.getLength(); i++) {
                Element jugadorElement = (Element) jugadores.item(i);
                int idJugador = Integer.parseInt(jugadorElement.getElementsByTagName("id").item(0).getTextContent());
                if (idJugador == id) {

                    Element nuevoJugadorElement = crearElementoJugador(doc, jugadorModificado);


                    raiz.replaceChild(nuevoJugadorElement, jugadorElement);
                    break;
                }
            }
            guardarDocumento(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la información de un jugador según su ID.
     *
     * @param id del jugador que se desea obtener.
     * @return Jugador con la información del jugador encontrado, o `null` si no se encontró.
     * @throws IOException Si ocurre un error al leer el archivo XML.
     */
    @Override
    public Jugador obtenerJugador(int id) throws IOException {
        try {
            Document doc = cargarDocumento();
            Element raiz = doc.getDocumentElement();

            NodeList jugadores = raiz.getElementsByTagName("jugador");
            for (int i = 0; i < jugadores.getLength(); i++) {
                Element jugadorElement = (Element) jugadores.item(i);
                int idJugador = Integer.parseInt(jugadorElement.getElementsByTagName("id").item(0).getTextContent());
                if (idJugador == id) {
                    return extraerJugadorDeElemento(jugadorElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todos los jugadores almacenados en el archivo XML.
     *
     * @return Una lista de objetos Jugador con todos los jugadores almacenados.
     * @throws IOException Si ocurre un error al leer el archivo XML.
     */
    @Override
    public List<Jugador> listarJugadores() throws IOException {
        List<Jugador> jugadores = new ArrayList<>();
        try {
            Document doc = cargarDocumento();
            Element raiz = doc.getDocumentElement();

            NodeList jugadoresNodes = raiz.getElementsByTagName("jugador");
            for (int i = 0; i < jugadoresNodes.getLength(); i++) {
                Element jugadorElement = (Element) jugadoresNodes.item(i);
                Jugador jugador = extraerJugadorDeElemento(jugadorElement);
                jugadores.add(jugador);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    /**
     * Cierra la conexión con el archivo XML (si es necesario).
     *
     *
     * @throws IOException No se utiliza en este caso.
     */
    @Override
    public void cerrar() throws IOException {

    }

    /**
     * Carga el documento XML desde el archivo.
     *
     * @return Un Document que representa el documento XML cargado.
     * @throws Exception Si ocurre un error al cargar el documento.
     */
    private Document cargarDocumento() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(archivoXML);
    }

    /**
     * Inicializa el archivo XML con la estructura básica, creando un elemento raíz.
     *
     * @throws IOException Si ocurre un error durante la inicialización.
     */
    private void inicializarArchivoXML() throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();


            Element raiz = doc.createElement("jugadores");
            doc.appendChild(raiz);


            guardarDocumento(doc);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un elemento XML jugador basado en los datos del objeto Jugador.
     *
     * @param doc Documento XML donde se creará el elemento.
     * @param jugador Objeto Jugador con los datos a almacenar en el elemento.
     * @return Un objeto Element que representa el jugador en formato XML.
     */
    private Element crearElementoJugador(Document doc, Jugador jugador) {
        Element jugadorElement = doc.createElement("jugador");

        Element idElement = doc.createElement("id");
        idElement.appendChild(doc.createTextNode(String.valueOf(jugador.getId())));
        jugadorElement.appendChild(idElement);

        Element nickElement = doc.createElement("nick");
        nickElement.appendChild(doc.createTextNode(jugador.getNick()));
        jugadorElement.appendChild(nickElement);

        Element experienceElement = doc.createElement("experience");
        experienceElement.appendChild(doc.createTextNode(String.valueOf(jugador.getExperience())));
        jugadorElement.appendChild(experienceElement);

        Element lifeLevelElement = doc.createElement("lifeLevel");
        lifeLevelElement.appendChild(doc.createTextNode(String.valueOf(jugador.getLifeLevel())));
        jugadorElement.appendChild(lifeLevelElement);

        Element coinsElement = doc.createElement("coins");
        coinsElement.appendChild(doc.createTextNode(String.valueOf(jugador.getCoins())));
        jugadorElement.appendChild(coinsElement);

        return jugadorElement;
    }

    /**
     * Guarda el documento XML en el archivo.
     *
     * @param doc Documento XML a guardar.
     * @throws TransformerException Si ocurre un error durante la transformación del documento.
     */
    private void guardarDocumento(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivoXML);
        transformer.transform(source, result);
    }

    /**
     * Extrae los datos de un jugador desde un elemento XML.
     *
     * @param jugadorElement Elemento XML que representa al jugador.
     * @return Un objeto `Jugador` con los datos extraídos del elemento XML.
     */
    private Jugador extraerJugadorDeElemento(Element jugadorElement) {
        int id = Integer.parseInt(jugadorElement.getElementsByTagName("id").item(0).getTextContent());
        String nick = jugadorElement.getElementsByTagName("nick").item(0).getTextContent();
        int experience = Integer.parseInt(jugadorElement.getElementsByTagName("experience").item(0).getTextContent());
        int lifeLevel = Integer.parseInt(jugadorElement.getElementsByTagName("lifeLevel").item(0).getTextContent());
        int coins = Integer.parseInt(jugadorElement.getElementsByTagName("coins").item(0).getTextContent());
        return new Jugador(id, nick, experience, lifeLevel, coins);
    }
}