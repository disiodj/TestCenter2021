package at.dimoco.tcmc.client.util;

import at.dimoco.tcmc.client.exception.ErrorCodes;
import at.dimoco.tcmc.client.exception.RestClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Util class that makes HTTP calls
 */
public class HttpCaller {

    public static final int GET = 1;
    public static final int POST = 2;
    public static final int PUT = 3;
    public static final int DELETE = 4;
    private static final Logger log = LoggerFactory.getLogger(HttpCaller.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CHARSET_ENCODING = "application/json; charset=UTF-8";
    private static final String UTF_ENCODING = "UTF-8";
    private static final String HTTP_RESPONSE_PROBLEM = "Problem while getting HTTP response";
    private static final String STATUS = "Status != 200 and Status != 404 and Status != 423. Status is: ";

    /**
     * Factory method that decides about request type based on the input params
     *
     * @param requestType   Request type (GET,POST,PUT,DELETE)
     * @param url           Url to request
     * @param payloadParams Payload params (used for PUT and POST)
     * @param params        Query string params
     * @return String HTTP response body
     */
    public String request(int requestType, String url, String payloadParams, Map<String, String> params) {

        String response;
        switch (requestType) {
            case GET:
                response = doGet(url, params);
                break;

            case POST:
                response = doPost(url, payloadParams, params);
                break;

            case PUT:
                response = doPut(url, payloadParams, params);
                break;

            case DELETE:
                response = doDelete(url);
                break;

            default:
                response = null;
        }
        return response;
    }

    /**
     * Executes GET request
     *
     * @param url Url to request
     * @return String response
     */
    private String doGet(String url, Map<String, String> params) {
        try {
            String finalUrl = url + (hasValue(params) ? createQueryStringFromMap(params) : "");
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(finalUrl);
            httpGet.setHeader(CONTENT_TYPE, CHARSET_ENCODING);
            // Custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (isStatusOkForGet(status)) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, UTF_ENCODING) : null;
                    } else {
                        log.debug("Exception while making HTTP GET request" +
                                STATUS + status);
                        throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
                    }
                }
            };
            // execute
            String responseBody = httpClient.execute(httpGet, responseHandler);
            // close client
            httpClient.close();

            return responseBody;
        } catch (IOException ex) {
            log.error("Exception while making HTTP GET request: ", ex);
            throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
        }
    }

    /**
     * Execute POST requests
     *
     * @param url          URL to request
     * @param payloadParam Payload param string
     * @param params       Name value params
     * @return Response(as String)
     */
    private String doPost(String url, String payloadParam, Map<String, String> params) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);

            // headers
            post.setHeader(CONTENT_TYPE, CHARSET_ENCODING);

            // name value params
            if (hasValue(params)) {
                List<NameValuePair> urlParameters = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(urlParameters));
            }

            // body params
            if (hasValue(payloadParam)) {
                HttpEntity entity = new ByteArrayEntity(payloadParam.getBytes(UTF_ENCODING));
                post.setEntity(entity);
            }

            HttpResponse response = client.execute(post);

            int status = response.getStatusLine().getStatusCode();
            if (isStatusOkForPostAndPut(status)) {
                return EntityUtils.toString(response.getEntity(), UTF_ENCODING);
            } else {
                log.debug("Exception while making HTTP POST request" +
                        STATUS + status);
                throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
            }
        } catch (IOException ex) {
            log.error("Exception while making HTTP POST request: ", ex);
            throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
        }
    }

    /**
     * Execute PUT requests
     *
     * @param url          URL to request
     * @param payloadParam Payload param string
     * @param params       Name value params
     * @return Response(as String)
     */
    private String doPut(String url, String payloadParam, Map<String, String> params) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPut put = new HttpPut(url);

            // headers
            put.setHeader(CONTENT_TYPE, CHARSET_ENCODING);

            // name value params
            if (hasValue(params)) {
                List<NameValuePair> urlParameters = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                put.setEntity(new UrlEncodedFormEntity(urlParameters));
            }

            // body params
            if (hasValue(payloadParam)) {
                HttpEntity entity = new ByteArrayEntity(payloadParam.getBytes(UTF_ENCODING));
                put.setEntity(entity);
            }

            HttpResponse response = client.execute(put);

            int status = response.getStatusLine().getStatusCode();
            if (isStatusOkForPostAndPut(status)) {
                return EntityUtils.toString(response.getEntity(), UTF_ENCODING);
            } else {
                log.debug("Exception while making HTTP PUT request" +
                        "Status != 200 and Status != 404 and Status != 423. Status is: " + status);
                throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
            }
        } catch (IOException ex) {
            log.error("Exception while making HTTP PUT request: ", ex);
            throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
        }
    }

    /**
     * Delete method
     *
     * @param url Url to request
     * @return Response
     */
    private String doDelete(String url) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpDelete delete = new HttpDelete(url);
            delete.setHeader(CONTENT_TYPE, CHARSET_ENCODING);
            HttpResponse response = client.execute(delete);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_NO_CONTENT || status == HttpStatus.SC_OK) {
                return String.valueOf(response.getStatusLine().getStatusCode());
            } else {
                log.debug("Exception while making HTTP DELETE request" + "Status != 200 Status is: " + status);
                throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
            }
        } catch (IOException ex) {
            log.error("Exception while making HTTP DELETE request: ", ex);
            throw new RestClientException(ErrorCodes.HTTP_CALL_ERROR, HTTP_RESPONSE_PROBLEM, null);
        }
    }

    private String createQueryStringFromMap(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder build = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (i == 0) {
                build.append("?").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), UTF_ENCODING));
            } else {
                build.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            i++;
        }
        return build.toString();
    }

    private boolean isStatusOkForGet(int status) {
        boolean isOk = status == HttpStatus.SC_OK || status == HttpStatus.SC_NOT_FOUND;
        return isOk || status == HttpStatus.SC_LOCKED;
    }

    private boolean isStatusOkForPostAndPut(int status) {
        boolean isOk = status == HttpStatus.SC_OK || status == HttpStatus.SC_LOCKED;
        isOk = isOk || status == HttpStatus.SC_NOT_FOUND || status == HttpStatus.SC_CREATED;
        return isOk || status == HttpStatus.SC_CONFLICT;
    }

    private boolean hasValue(String val) {
        return val != null && !val.isEmpty();
    }

    private boolean hasValue(Map<String, String> params) {
        return params != null && !params.isEmpty();
    }
}
