package at.dimoco.tcmc.client.service;


import at.dimoco.tcmc.client.exception.ErrorCodes;
import at.dimoco.tcmc.client.exception.RestClientException;
import at.dimoco.tcmc.client.model.Balance;
import at.dimoco.tcmc.client.model.BalanceCheck;
import at.dimoco.tcmc.client.model.BaseEntity;
import at.dimoco.tcmc.client.model.FilterCriteria;
import at.dimoco.tcmc.client.model.Handset;
import at.dimoco.tcmc.client.model.HandsetStatus;
import at.dimoco.tcmc.client.model.Lock;
import at.dimoco.tcmc.client.model.LockStatus;
import at.dimoco.tcmc.client.model.LockStatusResponse;
import at.dimoco.tcmc.client.model.SimCard;
import at.dimoco.tcmc.client.model.SmsMessage;
import at.dimoco.tcmc.client.model.Testcenter;
import at.dimoco.tcmc.client.model.VncRestartCallback;
import at.dimoco.tcmc.client.util.HttpCaller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of TCMC REST client
 */
public class TcmcClientServiceImpl implements TcmcClientService {

    public static final String SIM_CARDS_EXT = "simcards/";
    public static final String TESTCENTER_EXT = "testcenters/";
    public static final String HANDSET_EXT = "handsets/";
    public static final String SMS_EXT = "sendsms/";
    public static final String SMS_BOX_EXT = "smsbox/";
    public static final String LOCK_EXT = "locks/";
    public static final String HANDSET_STATUS_EXT = "handsetstatus/";
    public static final String RESTART_VNC_EXT = "vncrestart/";
    public static final String GET_BALANCE_EXT = "getBalance/";
    public static final String CHECK_BALANCE_EXT = "balanceCheck/";
    private static final Logger log = LoggerFactory.getLogger(TcmcClientServiceImpl.class);
    private static final String ERROR_NO_DATA = "No data";
    private static final String ERROR_WHILE_PARSING_JSON = "Error while parsing JSON: ";
    private static final String PROBLEM_DELETING_RECORD = "Problem when deleting Testcenter record: ";
    private static final String UPDATE = "update";
    private static final String ERROR = "error";
    private String baseUrl = "http://qa01.dimoco.at:8087/tcmc/api/v1/";

    public TcmcClientServiceImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    /**
     * Empty constructor
     */
    public TcmcClientServiceImpl() {
        // default constructor
    }

    /**
     * TESTCENTER SERVICES
     */

    @Override
    public List<Testcenter> getAllTestcenters() {
        List<Testcenter> testCenters;
        try {
            String url = baseUrl + TESTCENTER_EXT;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            testCenters = deserializeList(response, new TypeToken<List<Testcenter>>() {
            }.getType());
            if (testCenters == null || testCenters.isEmpty()) {
                log.error("Empty data set when trying to get all testcenters");
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
            }
            return testCenters;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public Testcenter getTestCenterById(String testCenterId, boolean updateMode) {
        Testcenter testCenter;
        try {
            String url = baseUrl + TESTCENTER_EXT + testCenterId;
            Map<String, String> queryParams = new HashMap<>();
            if (updateMode) {
                queryParams.put(UPDATE, "true");
            }
            String response = makeHttpRequest(HttpCaller.GET, url, null, queryParams);
            testCenter = deserializeEntity(response, Testcenter.class);
            if (testCenter == null || testCenter.isError()) {
                String error = testCenter == null ? "" : testCenter.getError();
                log.error("Empty data set when trying to get testcenter by id: " + error);
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, error, null);
            }
            return testCenter;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public Testcenter saveOrUpdateTestcenter(Testcenter testcenter) {
        boolean isUpdate = true;
        try {
            if (!hasValue(testcenter.getId())) {
                isUpdate = false;
            }
            String url = baseUrl + TESTCENTER_EXT + (isUpdate ? testcenter.getId() : "");
            String testcenterJson = serializeEntity(testcenter, new Testcenter.TestcenterSerializer());
            String response = makeHttpRequest(isUpdate ? HttpCaller.PUT : HttpCaller.POST, url, testcenterJson, null);
            Testcenter savedTestcenter = deserializeEntity(response, Testcenter.class);
            if (isError(savedTestcenter)) {
                String error = savedTestcenter == null ? "" : savedTestcenter.getError();
                log.error("Problem when saving or updating Testcenter: " + error);
                throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, error, null);
            }
            return savedTestcenter;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public boolean deleteTestcenter(String testcenterId) {
        boolean isDeleted;
        String url = baseUrl + TESTCENTER_EXT + testcenterId;
        try {
            makeHttpRequest(HttpCaller.DELETE, url, null, null);
            isDeleted = true;
        } catch (RestClientException e) {
            log.error(PROBLEM_DELETING_RECORD, e);
            isDeleted = false;
        }
        return isDeleted;
    }

    /**
     * HANDSET SERVICES
     */

    @Override
    public List<Handset> getAllHandsets() {
        List<Handset> handsets;
        try {
            String url = baseUrl + HANDSET_EXT;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            handsets = deserializeList(response, new TypeToken<List<Handset>>() {
            }.getType());
            if (handsets == null || handsets.isEmpty()) {
                log.error("Empty data set when trying to get all handsets");
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
            }
            return handsets;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public Handset getHandsetById(String handsetId, boolean updateMode) {
        Handset handset;
        try {
            String url = baseUrl + HANDSET_EXT + handsetId;
            Map<String, String> queryParams = new HashMap<>();
            if (updateMode) {
                queryParams.put(UPDATE, "true");
            }
            String response = makeHttpRequest(HttpCaller.GET, url, null, queryParams);
            handset = deserializeEntity(response, Handset.class);
            if (handset == null || handset.isError()) {
                String error = handset == null ? "" : handset.getError();
                log.error("Empty data set when trying to get handset by id: " + error);
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, error, null);
            }
            return handset;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public HandsetStatus getHandsetStatusByHandsetId(String handsetId) {
        HandsetStatus handsetStatus;
        try {
            String url = baseUrl + HANDSET_STATUS_EXT + handsetId;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            handsetStatus = deserializeEntity(response, HandsetStatus.class);
            if (handsetStatus == null || handsetStatus.isError()) {
                String error = handsetStatus == null ? "" : handsetStatus.getError();
                log.error("Empty data set when trying to get handset status b handset id: " + error);
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, error, null);
            }

            return handsetStatus;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    /**
     * SIMDARD SERVICES
     */

    @Override
    public List<SimCard> getAllSimCards() {
        List<SimCard> simCards;
        try {
            String url = baseUrl + SIM_CARDS_EXT;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            simCards = deserializeList(response, new TypeToken<List<SimCard>>() {
            }.getType());
            if (simCards == null || simCards.isEmpty()) {
                log.error("Empty data set when trying to get all sim cards");
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
            }
            return simCards;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public SimCard getSimCardById(String simCardId) {
        SimCard simCard;
        try {
            String url = baseUrl + SIM_CARDS_EXT + simCardId;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            simCard = deserializeEntity(response, SimCard.class);
            if (simCard == null || simCard.isError()) {
                String error = simCard == null ? "" : simCard.getError();
                log.error("Empty data set when trying to get sim card by id: " + error);
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, error, null);
            }
            return simCard;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public SmsMessage sendSms(SmsMessage smsMessage) {
        try {
            String url = baseUrl + SMS_EXT;
            String smsJson = serializeEntity(smsMessage, new SmsMessage.SmsMessageSerializer());
            String response = makeHttpRequest(HttpCaller.POST, url, smsJson, null);
            SmsMessage sms = deserializeEntity(response, SmsMessage.class);
            if (sms == null || sms.isError()) {
                String error = sms == null ? "" : sms.getError();
                log.error("Problem when sending sms message: " + error);
                throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, error, null);
            }
            return sms;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public List<SmsMessage> getSmsHistory(FilterCriteria criteria) {
        List<SmsMessage> smsMessages;
        try {
            String url = baseUrl + SMS_BOX_EXT;
            Map<String, String> params = criteria.makeSmsBoxRequestParams();
            String response = makeHttpRequest(HttpCaller.GET, url, null, params);
            smsMessages = deserializeList(response, new TypeToken<List<SmsMessage>>() {
            }.getType());
            if (smsMessages == null || smsMessages.isEmpty()) {
                log.error("Empty data set when trying to get sms history");
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
            }
            return smsMessages;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public List<SmsMessage> getSmsHistoryForTestcenter(FilterCriteria criteria, String testcenterId) {
        List<SmsMessage> smsMessages;
        try {
            String url = baseUrl + SMS_BOX_EXT + "testcenter/" + testcenterId;
            Map<String, String> params = criteria.makeSmsBoxRequestParams();
            String response = makeHttpRequest(HttpCaller.GET, url, null, params);
            smsMessages = deserializeList(response, new TypeToken<List<SmsMessage>>() {
            }.getType());
            if (smsMessages == null || smsMessages.isEmpty()) {
                log.debug("Empty data set when trying to get sms history");
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
            }
            return smsMessages;

        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public List<Lock> getAllLocks(String sessionId) {
        List<Lock> locks;
        try {
            String url = baseUrl + LOCK_EXT;
            Map<String, String> queryParams = new HashMap<>();
            if (sessionId != null && !sessionId.isEmpty()) {
                queryParams.put("sessionId", sessionId);
            }
            String response = makeHttpRequest(HttpCaller.GET, url, null, queryParams);
            // success response
            if (!response.contains(ERROR)) {
                locks = deserializeList(response, new TypeToken<List<Lock>>() {
                }.getType());
                if (locks == null || locks.isEmpty()) {
                    log.error("Empty data set when trying to get all locks");
                    throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
                }
                return locks;
            }
            // error response
            Lock lock = deserializeEntity(response, Lock.class);
            throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, lock.getError(), null);
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public Lock getLockByIdOrOperatorName(String param) {
        List<Lock> locks;
        try {
            String url = baseUrl + LOCK_EXT + param;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            if (!response.contains(ERROR)) {
                locks = deserializeList(response, new TypeToken<List<Lock>>() {
                }.getType());
                if (locks == null || locks.isEmpty()) {
                    log.error("Empty data set when trying to get Lock by id or operator name");
                    throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
                }
                return locks.get(0);
            }
            Lock lock = deserializeEntity(response, Lock.class);
            throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, lock.getError(), null);
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public Lock getLockByMsisdn(String msisdn) {
        List<Lock> locks;
        try {
            String url = baseUrl + LOCK_EXT + "msisdn/" + msisdn;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            if (!response.contains(ERROR)) {
                locks = deserializeList(response, new TypeToken<List<Lock>>() {
                }.getType());
                if (locks == null || locks.isEmpty()) {
                    log.error("Empty data set when trying to get Lock by id or operator name");
                    throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, ERROR_NO_DATA, null);
                }
                return locks.get(0);
            }
            Lock lock = deserializeEntity(response, Lock.class);
            throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, lock.getError(), null);
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public LockStatus lockOperator(Lock lock) {
        try {
            String url = baseUrl + LOCK_EXT;
            String lockJson = serializeEntity(lock, new Lock.LockSerializer());
            String response = makeHttpRequest(HttpCaller.POST, url, lockJson, null);
            LockStatusResponse lockResponse = deserializeEntity(response, LockStatusResponse.class);
            if (lockResponse == null || lockResponse.isError()) {
                String error = lockResponse == null ? "" : lockResponse.getError();
                log.error("Problem locking an operator: " + error);
                throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, error, null);
            }
            return lockResponse.getStatus();
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public boolean deleteLockByOperatorNameOrOperatorId(String param) {
        boolean isDeleted;
        String url = baseUrl + LOCK_EXT + param;
        try {
            makeHttpRequest(HttpCaller.DELETE, url, null, null);
            isDeleted = true;
        } catch (RestClientException e) {
            log.error(PROBLEM_DELETING_RECORD, e);
            isDeleted = false;
        }
        return isDeleted;
    }

    @Override
    public boolean deleteLockByMsisdn(String msisdn) {
        boolean isDeleted;
        String url = baseUrl + LOCK_EXT + "msisdn/" + msisdn;
        try {
            makeHttpRequest(HttpCaller.DELETE, url, null, null);
            isDeleted = true;
        } catch (RestClientException e) {
            log.error(PROBLEM_DELETING_RECORD, e);
            isDeleted = false;
        }
        return isDeleted;
    }

    @Override
    public boolean deleteLocksBySessionId(String sessionId) {
        boolean isDeleted;
        String url = baseUrl + LOCK_EXT + "sessionId/" + sessionId;
        try {
            makeHttpRequest(HttpCaller.DELETE, url, null, null);
            isDeleted = true;
        } catch (RestClientException e) {
            log.error(PROBLEM_DELETING_RECORD, e);
            isDeleted = false;
        }
        return isDeleted;
    }

    @Override
    public boolean deleteLocksByUsername(String username) {
        boolean isDeleted;
        String url = baseUrl + LOCK_EXT + "user/" + username;
        try {
            makeHttpRequest(HttpCaller.DELETE, url, null, null);
            isDeleted = true;
        } catch (RestClientException e) {
            log.debug(PROBLEM_DELETING_RECORD, e);
            isDeleted = false;
        }
        return isDeleted;
    }

    @Override
    public VncRestartCallback restartVnc(String handsetId) {
        VncRestartCallback vncRestartCallback;
        try {
            String url = baseUrl + RESTART_VNC_EXT + handsetId;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            vncRestartCallback = deserializeEntity(response, VncRestartCallback.class);
            if (vncRestartCallback == null || vncRestartCallback.isError()) {
                String error = vncRestartCallback == null ? "" : vncRestartCallback.getError();
                log.error("Empty data set when trying to restart vnc server");
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, error, null);
            }
            return vncRestartCallback;
        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    /**
     * Get last known balance
     *
     * @param msisdn Msisdn to check balance for
     * @return Balance record
     */
    @Override public Balance getBalance(String msisdn) {
        Balance balance;
        try {
            String url = baseUrl + GET_BALANCE_EXT + msisdn;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            balance = deserializeEntity(response, Balance.class);
            if (balance == null || balance.isError()) {
                String error = balance == null ? "" : balance.getError();
                log.error("Empty data set when trying to get balance: " + error);
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, error, null);
            }
            return balance;

        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }
    }

    /**
     * Triggers balance check on handset
     *
     * @param msisdn Msisdn to trigger balance check for
     * @return BalanceCheck record
     */
    @Override public BalanceCheck checkBalance(String msisdn) {
        BalanceCheck balanceCheck;
        try {
            String url = baseUrl + CHECK_BALANCE_EXT + msisdn;
            String response = makeHttpRequest(HttpCaller.GET, url, null, null);
            balanceCheck = deserializeEntity(response, BalanceCheck.class);
            if (balanceCheck == null || balanceCheck.isError()) {
                String error = balanceCheck == null ? "" : balanceCheck.getError();
                log.error("Emoty data set when trying to check for balance: " + error);
                throw new RestClientException(ErrorCodes.ENTITY_NOT_FOUND_ERROR, error, null);
            }
            return balanceCheck;

        } catch (JsonSyntaxException e) {
            log.error(ERROR_WHILE_PARSING_JSON, e);
            throw new RestClientException(ErrorCodes.JSON_PARSE_ERROR, e.getMessage(), null);
        }

    }

    /**
     * Wrapper for making http calls
     *
     * @param requestType   GET or POST
     * @param url           Url to request
     * @param payloadParams Payload params
     * @param params        Body params
     * @return Response string
     */
    public String makeHttpRequest(int requestType, String url, String payloadParams, Map<String, String> params) {
        HttpCaller caller = new HttpCaller();
        return caller.request(requestType, url, payloadParams, params);
    }

    public <T> String serializeEntity(T entity, JsonSerializer<T> serializer) {
        Gson gson = new GsonBuilder().registerTypeAdapter(entity.getClass(), serializer).create();
        return gson.toJson(entity);
    }

    public <T> List<T> deserializeList(String response, Type type) {
        List<T> result;
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        result = gson.fromJson(response, type);

        return result;
    }

    public <T> T deserializeEntity(String response, Class<T> clazz) {
        T result;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        result = gson.fromJson(response, clazz);

        return result;
    }

    public boolean hasValue(String val) {
        return val != null && !val.isEmpty();
    }

    public boolean isError(BaseEntity entity) {
        return entity == null || entity.isError();
    }

}
