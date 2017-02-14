package com.firefly.server.http2.router;

import com.firefly.codec.http2.model.Cookie;
import com.firefly.codec.http2.model.HttpHeader;
import com.firefly.codec.http2.model.HttpVersion;
import com.firefly.server.http2.SimpleRequest;
import com.firefly.server.http2.SimpleResponse;
import com.firefly.server.http2.router.spi.HTTPBodyHandlerSPI;
import com.firefly.server.http2.router.spi.HTTPSessionHandlerSPI;
import com.firefly.utils.function.Action1;
import com.firefly.utils.json.JsonArray;
import com.firefly.utils.json.JsonObject;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pengtao Qiu
 */
public interface RoutingContext extends Closeable {

    Object get(String key);

    Object put(String key, Object value);

    Object remove(String key);

    ConcurrentHashMap<String, Object> getAttributes();

    SimpleResponse getResponse();

    SimpleResponse getAsyncResponse();

    SimpleRequest getRequest();

    String getRouterParameter(String name);

    RoutingContext content(Action1<ByteBuffer> content);

    RoutingContext contentComplete(Action1<SimpleRequest> contentComplete);

    RoutingContext messageComplete(Action1<SimpleRequest> messageComplete);

    boolean isAsynchronousRead();

    boolean next();

    // response wrap
    default SimpleResponse setStatus(int status) {
        return getResponse().setStatus(status);
    }

    default SimpleResponse setReason(String reason) {
        return getResponse().setReason(reason);
    }

    default SimpleResponse setHttpVersion(HttpVersion httpVersion) {
        return getResponse().setHttpVersion(httpVersion);
    }

    default SimpleResponse put(HttpHeader header, String value) {
        return getResponse().put(header, value);
    }

    default SimpleResponse put(String header, String value) {
        return getResponse().put(header, value);
    }

    default SimpleResponse add(HttpHeader header, String value) {
        return getResponse().add(header, value);
    }

    default SimpleResponse add(String name, String value) {
        return getResponse().add(name, value);
    }

    default SimpleResponse addCookie(Cookie cookie) {
        return getResponse().addCookie(cookie);
    }

    default SimpleResponse write(String value) {
        return getResponse().write(value);
    }

    default SimpleResponse end(String value) {
        return getResponse().end(value);
    }

    default SimpleResponse end() {
        return getResponse().end();
    }


    // HTTP body API
    String getParameter(String name);

    List<String> getParameterValues(String name);

    Map<String, List<String>> getParameterMap();

    Collection<Part> getParts();

    Part getPart(String name);

    InputStream getInputStream();

    BufferedReader getBufferedReader();

    String getStringBody(String charset);

    String getStringBody();

    <T> T getJsonBody(Class<T> clazz);

    JsonObject getJsonObjectBody();

    JsonArray getJsonArrayBody();

    void setHTTPBodyHandlerSPI(HTTPBodyHandlerSPI httpBodyHandlerSPI);


    // HTTP session API
    HttpSession getHttpSession();

    HttpSession getSession(boolean create);

    boolean isRequestedSessionIdFromURL();

    boolean isRequestedSessionIdFromCookie();

    boolean isRequestedSessionIdValid();

    String getRequestedSessionId();

    void setHTTPSessionHandlerSPI(HTTPSessionHandlerSPI httpSessionHandlerSPI);
}