package cn.net.bhe.mutil;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class FileServerUtils {

    public static FileServer build(int port) throws IOException {
        return build(port, "ROOT");
    }

    public static FileServer build(int port, String root) throws IOException {
        return build("localhost", port, root);
    }

    public static FileServer build(String host, int port, String root) throws IOException {
        return build(host, port, root, StrUtils.EMPTY, StrUtils.EMPTY);
    }

    public static FileServer build(String host, int port, String root, String username, String password) throws IOException {
        return build(host, port, root, username, password, new FileHandler());
    }

    public static FileServer build(String host, int port, String root, String username, String password, FileHandler fileHandler) throws IOException {
        return new FileServer(host, port, root, username, password, fileHandler);
    }

    public static class FileServer {

        private final String host;
        private final int port;
        private final FileHandler fileHandler;

        private FileServer(String host, int port, String root, String username, String password, FileHandler fileHandler) {
            this.host = host;
            this.port = port;
            this.fileHandler = fileHandler.init(root, username, password);
        }

        public void start() throws IOException {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(host, port), NumUtils.ZERO);
            httpServer.createContext("/", fileHandler);
            httpServer.setExecutor(null);
            httpServer.start();
        }

    }

    public static class FileHandler implements HttpHandler {

        private String root;
        private String username;
        private String password;

        private FileHandler init(String root, String username, String password) {
            this.root = root;
            this.username = username;
            this.password = password;
            return this;
        }

        public void handle(HttpExchange httpExchange) throws IOException {
            if (!authenticateUser(httpExchange)) {
                process401(httpExchange);
                return;
            }

            String path = httpExchange.getRequestURI().toString();
            path = UrlUtils.decode(path);
            path = StrUtils.trim(path, "/", true, false);
            path = FlUtils.combine(root, path);

            File file = new File(path);
            if (!file.exists()) {
                process404(httpExchange);
                return;
            }

            if (file.isDirectory()) {
                processDirectory(httpExchange, path);
                return;
            }

            processFile(httpExchange, path);
        }

        private boolean authenticateUser(HttpExchange httpExchange) {
            if (StrUtils.isEmpty(username) && StrUtils.isEmpty(password)) {
                return true;
            }

            String authorization = httpExchange.getRequestHeaders().getFirst("Authorization");
            if (StrUtils.isEmpty(authorization)) {
                return false;
            }

            String prefix = "Basic ";
            if (!authorization.startsWith(prefix)) {
                return false;
            }

            String encodedCredentials = authorization.substring(prefix.length()).trim();
            String credentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);
            String[] parts = credentials.split(StrUtils.COLON);

            return parts.length == 2 && parts[0].equals(username) && parts[1].equals(password);
        }

        protected void process401(HttpExchange httpExchange) throws IOException {
            /* 注意顺序 */
            // 1、设置响应头
            // 2、发送状态码
            // 3、写入响应体
            // 4、关闭响应体

            httpExchange.getResponseHeaders().set("WWW-Authenticate", "Basic realm=\"MyRealm\"");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
            httpExchange.getResponseBody().close();
        }

        protected void process404(HttpExchange httpExchange) throws IOException {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
            httpExchange.getResponseBody().close();
        }

        protected void processDirectory(HttpExchange httpExchange, String directoryPath) throws IOException {
            String indexPath = FlUtils.combine(directoryPath, "index.html");
            File indexFile = new File(indexPath);
            if (indexFile.exists() && indexFile.isFile()) {
                processFile(httpExchange, indexPath);
                return;
            }

            File directoryFile = new File(directoryPath);
            String[] subDirectories = directoryFile.list((current, name) -> new File(current, name).isDirectory());
            String[] subFiles = directoryFile.list((current, name) -> new File(current, name).isFile());

            httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            try (OutputStream os = httpExchange.getResponseBody();
                 PrintWriter writer = new PrintWriter(os)) {

                writer.println("<html><head><meta charset=\"UTF-8\"></head><body><ul>");

                if (ArrUtils.isNotEmpty(subDirectories)) {
                    Arrays.sort(subDirectories);
                    for (String subDir : subDirectories) {
                        writer.printf("<li><a href=\"%s/\">%s/</a></li>", UrlUtils.encode(subDir), subDir);
                        writer.println();
                    }
                }

                if (ArrUtils.isNotEmpty(subFiles)) {
                    Arrays.sort(subFiles);
                    for (String subFile : subFiles) {
                        writer.printf("<li><a href=\"%s\">%s</a></li>", UrlUtils.encode(subFile), subFile);
                        writer.println();
                    }
                }

                writer.println("</ul></body></html>");
            } finally {
                httpExchange.getResponseBody().close();
            }
        }

        protected void processFile(HttpExchange httpExchange, String filePath) throws IOException {
            File file = new File(filePath);

            httpExchange.getResponseHeaders().set("Content-Type", getContentType(filePath));
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, file.length());

            try (InputStream is = new FileInputStream(file);
                 OutputStream os = httpExchange.getResponseBody()) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
            } finally {
                httpExchange.getResponseBody().close();
            }
        }

    }

    public static String getContentType(String path) {
        String extension;
        int idx = path.lastIndexOf(".");
        if (idx == -1) {
            extension = StrUtils.EMPTY;
        } else {
            extension = path.substring(idx);
        }
        switch (extension.toLowerCase()) {
            case ".aac":
                return "audio/aac";
            case ".abw":
                return "application/x-abiword";
            case ".apng":
                return "image/apng";
            case ".arc":
                return "application/x-freearc";
            case ".avif":
                return "image/avif";
            case ".avi":
                return "video/x-msvideo";
            case ".azw":
                return "application/vnd.amazon.ebook";
            case ".bin":
                return "application/octet-stream";
            case ".bmp":
                return "image/bmp";
            case ".bz":
                return "application/x-bzip";
            case ".bz2":
                return "application/x-bzip2";
            case ".cda":
                return "application/x-cdf";
            case ".csh":
                return "application/x-csh";
            case ".css":
                return "text/css";
            case ".csv":
                return "text/csv";
            case ".doc":
                return "application/msword";
            case ".docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case ".eot":
                return "application/vnd.ms-fontobject";
            case ".epub":
                return "application/epub+zip";
            case ".gz":
                return "application/gzip";
            case ".gif":
                return "image/gif";
            case ".htm":
                return "text/html; charset=UTF-8";
            case ".html":
                return "text/html; charset=UTF-8";
            case ".ico":
                return "image/vnd.microsoft.icon";
            case ".ics":
                return "text/calendar";
            case ".jar":
                return "application/java-archive";
            case ".jpeg":
                return "image/jpeg";
            case ".jpg":
                return "image/jpeg";
            case ".js":
                return "text/javascript";
            case ".json":
                return "application/json";
            case ".jsonld":
                return "application/ld+json";
            case ".mid":
                return "audio/midi";
            case ".midi":
                return "audio/midi";
            case ".mjs":
                return "text/javascript";
            case ".mp3":
                return "audio/mpeg";
            case ".mp4":
                return "video/mp4";
            case ".mpeg":
                return "video/mpeg";
            case ".mpkg":
                return "application/vnd.apple.installer+xml";
            case ".odp":
                return "application/vnd.oasis.opendocument.presentation";
            case ".ods":
                return "application/vnd.oasis.opendocument.spreadsheet";
            case ".odt":
                return "application/vnd.oasis.opendocument.text";
            case ".oga":
                return "audio/ogg";
            case ".ogv":
                return "video/ogg";
            case ".ogx":
                return "application/ogg";
            case ".opus":
                return "audio/ogg";
            case ".otf":
                return "font/otf";
            case ".png":
                return "image/png";
            case ".pdf":
                return "application/pdf";
            case ".php":
                return "application/x-httpd-php";
            case ".ppt":
                return "application/vnd.ms-powerpoint";
            case ".pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case ".rar":
                return "application/vnd.rar";
            case ".rtf":
                return "application/rtf";
            case ".sh":
                return "application/x-sh";
            case ".svg":
                return "image/svg+xml";
            case ".tar":
                return "application/x-tar";
            case ".tif":
                return "image/tiff";
            case ".tiff":
                return "image/tiff";
            case ".ts":
                return "video/mp2t";
            case ".ttf":
                return "font/ttf";
            case ".txt":
                return "text/plain";
            case ".vsd":
                return "application/vnd.visio";
            case ".wav":
                return "audio/wav";
            case ".weba":
                return "audio/webm";
            case ".webm":
                return "video/webm";
            case ".webp":
                return "image/webp";
            case ".woff":
                return "font/woff";
            case ".woff2":
                return "font/woff2";
            case ".xhtml":
                return "application/xhtml+xml";
            case ".xls":
                return "application/vnd.ms-excel";
            case ".xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case ".xml":
                return "application/xml";
            case ".xul":
                return "application/vnd.mozilla.xul+xml";
            case ".zip":
                return "application/zip";
            case ".3gp":
                return "video/3gpp";
            case ".3g2":
                return "video/3gpp2";
            case ".7z":
                return "application/x-7z-compressed";
            default:
                return "application/octet-stream";
        }
    }

}
