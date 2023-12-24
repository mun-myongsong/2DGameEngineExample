package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MapUtil {
    public static final int    NUMBER_OF_TILE_SIZE  = 2;
    public static final String MAP_KEY_DATA         = "data";
    public static final String MAP_KEY_HEIGHT       = "height";
    public static final String MAP_KEY_ID           = "id";
    public static final String MAP_KEY_WIDTH        = "width";
    public static final String MAP_KEY_SOURCE       = "source";
    public static final String MAP_KEY_TILE_HEIGHT  = "tileheight";
    public static final String MAP_KEY_TILE_WIDTH   = "tilewidth";
    public static final String TILE_SET_PATH        = "/tileset/";

    private static void loadMapInfo(Map<String, String> mapData, String fileName)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(MapUtil.class.getResourceAsStream(TILE_SET_PATH + fileName));
        document.getDocumentElement().normalize();
        Element elementList = document.getDocumentElement();
        NodeList nodeList = elementList.getElementsByTagName("tileset");
        Element element = (Element) nodeList.item(0);
        mapData.put(MAP_KEY_SOURCE, element.getAttribute(MAP_KEY_SOURCE).replace("tsx", "png"));
        nodeList = elementList.getElementsByTagName("layer");
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            int id = Integer.parseInt(element.getAttribute(MAP_KEY_ID));
            mapData.put(MAP_KEY_ID + id, element.getAttribute(MAP_KEY_ID));
            mapData.put(MAP_KEY_WIDTH + id, element.getAttribute(MAP_KEY_WIDTH));
            mapData.put(MAP_KEY_HEIGHT + id, element.getAttribute(MAP_KEY_HEIGHT));
            mapData.put(MAP_KEY_DATA + id, element.getTextContent());
        }
    }

    // FIXME TileSizeの値を取得する方法を考える必要がある。
    private static void loadTileSizeInfo(Map<String, String> mapData, String fileName) throws IOException, URISyntaxException {
        try (BufferedReader br = new BufferedReader(
                    new FileReader(new File(MapUtil.class.getResource(TILE_SET_PATH + fileName).toURI())))) {
            String line = "";
            int idx = 0;
            while ((line = br.readLine()) != null) {
                idx++;
                if (idx == NUMBER_OF_TILE_SIZE) {
                    String beginStr = "tilewidth=";
                    String endStr = "tileheight=";
                    int beginIndex = line.indexOf(beginStr);
                    int endIndex = line.indexOf(endStr);
                    String tileWidth = line.substring(beginIndex, endIndex).replace(beginStr, "").replace("\"", "").replace(" ", "");
                    beginStr = "tileheight=";
                    endStr = "infinite=";
                    beginIndex = line.indexOf(beginStr);
                    endIndex = line.indexOf(endStr);
                    String tileHeight = line.substring(beginIndex, endIndex).replace(beginStr, "").replace("\"", "").replace(" ", "");
                    mapData.put(MAP_KEY_TILE_WIDTH, tileWidth);
                    mapData.put(MAP_KEY_TILE_HEIGHT, tileHeight);
                }
            }
        } catch (IOException e) {
            throw new IOException("タイルサイズの読み込みに失敗しました。 fileName: " + fileName, e);
        } catch (URISyntaxException e) {
            System.err.println("不正なURIが設定されたため例外が発生しました。 fileName: " + fileName);
            throw e;
        }
    }

    public static Map<String, String> loadMapFromFileName(String fileName)
            throws ParserConfigurationException, SAXException, IOException, URISyntaxException {
        try {
            Map<String, String> mapData = new HashMap<>();
            loadMapInfo(mapData, fileName);
            loadTileSizeInfo(mapData, fileName);
            return mapData;
        } catch (ParserConfigurationException e) {
            System.err.println("XMLの構成に致命的なミスがあります。 fileName: " + fileName);
            throw e;
        } catch (SAXException e) {
            throw new SAXException("SAXでの処理で例外が発生しました。fileName: " + fileName, e);
        } catch (IOException e) {
            throw new IOException("入出力処理で例外が発生しました。 fileName: " + fileName, e);
        }
    }
}
