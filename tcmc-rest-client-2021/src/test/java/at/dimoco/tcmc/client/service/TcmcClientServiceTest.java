package at.dimoco.tcmc.client.service;

import at.dimoco.tcmc.client.exception.RestClientException;
import at.dimoco.tcmc.client.model.Application;
import at.dimoco.tcmc.client.model.Balance;
import at.dimoco.tcmc.client.model.BalanceCheck;
import at.dimoco.tcmc.client.model.FilterCriteria;
import at.dimoco.tcmc.client.model.Handset;
import at.dimoco.tcmc.client.model.HandsetStatus;
import at.dimoco.tcmc.client.model.Lock;
import at.dimoco.tcmc.client.model.LockStatus;
import at.dimoco.tcmc.client.model.SimCard;
import at.dimoco.tcmc.client.model.SmsDirection;
import at.dimoco.tcmc.client.model.SmsMessage;
import at.dimoco.tcmc.client.model.Testcenter;
import at.dimoco.tcmc.client.model.TestcenterModel;
import at.dimoco.tcmc.client.model.VncRestartCallback;
import at.dimoco.tcmc.client.util.HttpCaller;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class TcmcClientServiceTest {

    private static final String ID_1 = "1";

    private static final String JSON_ENCODING = "UTF8";

    private static final String JSON_FIXTURES_FOLDER = "/data/json/";

    private static final String TCMC_REST_URL = "http://www.test.qa/";

    private final TcmcClientServiceImpl service = spy(new TcmcClientServiceImpl(TCMC_REST_URL));

    private static final Logger log = LoggerFactory.getLogger(TcmcClientServiceImpl.class);

    @Test
    public void testGetAllTestcenters() {
        String jsonData = readFileContent("testcenters.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        List<Testcenter> allTestcenters = service.getAllTestcenters();
        assertNotNull(allTestcenters);
        assertFalse(allTestcenters.isEmpty());
    }

    @Test(expected = RestClientException.class)
    public void testGetAllTestcentersFailEmpty() {
        String jsonData = readFileContent("fail-empty.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllTestcenters();
    }

    @Test(expected = RestClientException.class)
    public void testGetAllTestcentersFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllTestcenters();
    }

    @Test(expected = RestClientException.class)
    public void testGetAllTestcentersFailNull() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllTestcenters();
    }

    @Test
    public void testGetTestCenterById() {
        String jsonData = readFileContent("testcenter-1.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        Testcenter testcenter = service.getTestCenterById(ID_1, false);
        assertNotNull(testcenter);
    }

    @Test
    public void testGetTestCenterByIdUpdate() {
        String jsonData = readFileContent("testcenter-1.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("update", "true");
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        Testcenter testcenter = service.getTestCenterById(ID_1, true);
        assertNotNull(testcenter);
    }

    @Test(expected = RestClientException.class)
    public void testGetTestCenterByIdFailEmpty() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        service.getTestCenterById(ID_1, false);
    }

    @Test(expected = RestClientException.class)
    public void testGetTestCenterByIdFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        service.getTestCenterById(ID_1, false);
    }

    @Test(expected = RestClientException.class)
    public void testGetTestCenterByIdFailNull() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        service.getTestCenterById(ID_1, false);
    }

    @Test
    public void testSaveTestcenter() {
        Handset handset = new Handset();
        handset.setId(ID_1);
        List<Handset> handsets = new ArrayList<Handset>();
        handsets.add(handset);
        Testcenter expected = new Testcenter();
        TestcenterModel tcModel = new TestcenterModel();
        tcModel.setId(ID_1);
        expected.setModel(tcModel);
        expected.setHandsets(handsets);
        expected.setName("Testcenter AT");
        String jsonData = readFileContent("testcenter-1.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        String tcJson = service.serializeEntity(expected, new Testcenter.TestcenterSerializer());
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, tcJson, null);
        Testcenter testcenter = service.saveOrUpdateTestcenter(expected);
        assertNotNull(testcenter);
        assertEquals(ID_1, testcenter.getId());
        assertEquals(expected.getName(), testcenter.getName());
    }

    @Test(expected = RestClientException.class)
    public void testSaveTestcenterFailEmpty() {
        Testcenter expected = new Testcenter();
        expected.setName("Testcenter AT");
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        String tcJson = service.serializeEntity(expected, new Testcenter.TestcenterSerializer());
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, tcJson, null);
        service.saveOrUpdateTestcenter(expected);
    }

    @Test(expected = RestClientException.class)
    public void testSaveTestcenterFailJson() {
        Testcenter expected = new Testcenter();
        expected.setName("Testcenter AT");
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        String tcJson = service.serializeEntity(expected, new Testcenter.TestcenterSerializer());
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, tcJson, null);
        service.saveOrUpdateTestcenter(expected);
    }

    @Test
    public void testUpdateTestcenter() {
        Testcenter expected = new Testcenter();
        expected.setId(ID_1);
        expected.setName("Testcenter AT");
        String jsonData = readFileContent("testcenter-1.json");
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        String tcJson = service.serializeEntity(expected, new Testcenter.TestcenterSerializer());
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.PUT, TCMC_REST_URL + urlSuffix + ID_1, tcJson, null);
        Testcenter testcenter = service.saveOrUpdateTestcenter(expected);
        assertNotNull(testcenter);
        assertEquals(ID_1, testcenter.getId());
        assertEquals(expected.getName(), testcenter.getName());
    }

    @Test
    public void testDeleteTestcenter() {
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        doReturn(null).when(service).makeHttpRequest(HttpCaller.DELETE, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        boolean deleted = service.deleteTestcenter(ID_1);
        assertTrue(deleted);
    }

    @Test
    public void testDeleteTestcenterFail() {
        String id = "9x9x9";
        String urlSuffix = TcmcClientServiceImpl.TESTCENTER_EXT;
        doThrow(RestClientException.class).when(service).makeHttpRequest(HttpCaller.DELETE,
                TCMC_REST_URL + urlSuffix + id, null, null);
        boolean deleted = service.deleteTestcenter(id);
        assertFalse(deleted);
    }

    @Test
    public void testGetAllHandsets() {
        String jsonData = readFileContent("handsets.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        List<Handset> allHandsets = service.getAllHandsets();
        assertNotNull(allHandsets);
        assertFalse(allHandsets.isEmpty());
    }

    @Test(expected = RestClientException.class)
    public void testGetAllHandsetsFailEmpty() {
        String jsonData = readFileContent("fail-empty.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllHandsets();
    }

    @Test(expected = RestClientException.class)
    public void testGetAllHandsetsFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllHandsets();
    }

    @Test(expected = RestClientException.class)
    public void testGetAllHandsetsFailNull() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllHandsets();
    }

    @Test
    public void testGetHandsetById() {
        String jsonData = readFileContent("handset-1.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        Handset handset = service.getHandsetById(ID_1, false);
        assertNotNull(handset);
    }

    @Test
    public void testGetHandsetByIdUpdate() {
        String jsonData = readFileContent("handset-1.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("update", "true");
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        Handset handset = service.getHandsetById(ID_1, true);
        assertNotNull(handset);
    }

    @Test(expected = RestClientException.class)
    public void testGetHandsetByIdFailEmpty() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        service.getHandsetById(ID_1, false);
    }

    @Test(expected = RestClientException.class)
    public void testGetHandsetByIdFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_EXT;
        HashMap<String, String> params = new HashMap<String, String>();
        doReturn(jsonData).when(service)
                .makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, params);
        service.getHandsetById(ID_1, false);
    }

    @Test
    public void testGetHandsetStatusByHandsetId() {
        String jsonData = readFileContent("handset-status-1.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_STATUS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        HandsetStatus handsetStatus = service.getHandsetStatusByHandsetId(ID_1);
        assertNotNull(handsetStatus);
    }

    @Test(expected = RestClientException.class)
    public void testGetHandsetStatusByHandsetIdFailEmpty() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_STATUS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        service.getHandsetStatusByHandsetId(ID_1);
    }

    @Test(expected = RestClientException.class)
    public void testGetHandsetStatusByHandsetIdFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.HANDSET_STATUS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        service.getHandsetStatusByHandsetId(ID_1);
    }

    @Test
    public void testGetAllSimCards() {
        String jsonData = readFileContent("simcards.json");
        String urlSuffix = TcmcClientServiceImpl.SIM_CARDS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        List<SimCard> allSimCards = service.getAllSimCards();
        assertNotNull(allSimCards);
        assertFalse(allSimCards.isEmpty());
    }

    @Test(expected = RestClientException.class)
    public void testGetAllSimCardsFailEmpty() {
        String jsonData = readFileContent("fail-empty.json");
        String urlSuffix = TcmcClientServiceImpl.SIM_CARDS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllSimCards();
    }

    @Test(expected = RestClientException.class)
    public void testGetAllSimCardsFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.SIM_CARDS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllSimCards();
    }

    @Test
    public void testGetSimCardById() {
        String jsonData = readFileContent("simcard-1.json");
        String urlSuffix = TcmcClientServiceImpl.SIM_CARDS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        SimCard simCard = service.getSimCardById(ID_1);
        assertNotNull(simCard);
    }

    @Test(expected = RestClientException.class)
    public void testGetSimCardByIdFailEmpty() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.SIM_CARDS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        service.getSimCardById(ID_1);
    }

    @Test(expected = RestClientException.class)
    public void testGetSimCardByIdFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.SIM_CARDS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        service.getSimCardById(ID_1);
    }

    @Test
    public void testSendSms() {
        SmsMessage sms = new SmsMessage();
        sms.setFrom("+436648166583");
        sms.setTo("+381645983417");
        sms.setApplication("tcui");
        sms.setMessage("Test OUT");
        String smsJson = service.serializeEntity(sms, new SmsMessage.SmsMessageSerializer());
        String jsonData = readFileContent("sms-1.json");
        String urlSuffix = TcmcClientServiceImpl.SMS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, smsJson, null);
        SmsMessage response = service.sendSms(sms);
        assertNotNull(response);
        assertTrue(response.isHandled());
        assertEquals(sms.getApplication(), response.getApplication());
    }

    @Test(expected = RestClientException.class)
    public void testSendSmsFailEmpty() {
        SmsMessage sms = new SmsMessage();
        sms.setFrom("+436648166583");
        sms.setTo("+381645983417");
        sms.setApplication("tcui");
        sms.setMessage("Test OUT");
        String smsJson = service.serializeEntity(sms, new SmsMessage.SmsMessageSerializer());
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.SMS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, smsJson, null);
        service.sendSms(sms);
    }

    @Test(expected = RestClientException.class)
    public void testSendSmsFailJson() {
        SmsMessage sms = new SmsMessage();
        sms.setFrom("+436648166583");
        sms.setTo("+381645983417");
        sms.setApplication("tcui");
        sms.setMessage("Test OUT");
        String smsJson = service.serializeEntity(sms, new SmsMessage.SmsMessageSerializer());
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.SMS_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, smsJson, null);
        service.sendSms(sms);
    }

    @Test
    public void testGetSmsHistory() {
        FilterCriteria criteria = new FilterCriteria();
        String jsonData = readFileContent("sms-box.json");
        String urlSuffix = TcmcClientServiceImpl.SMS_BOX_EXT;
        Map<String, String> params = criteria.makeSmsBoxRequestParams();
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, params);
        List<SmsMessage> smsHistory = service.getSmsHistory(criteria);
        assertNotNull(smsHistory);
        assertFalse(smsHistory.isEmpty());
    }

    @Test(expected = RestClientException.class)
    public void testGetSmsHistoryFailEmpty() {
        FilterCriteria criteria = new FilterCriteria();
        String jsonData = readFileContent("fail-empty.json");
        String urlSuffix = TcmcClientServiceImpl.SMS_BOX_EXT;
        Map<String, String> params = criteria.makeSmsBoxRequestParams();
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, params);
        service.getSmsHistory(criteria);
    }

    @Test(expected = RestClientException.class)
    public void testGetSmsHistoryFailJson() {
        FilterCriteria criteria = new FilterCriteria();
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.SMS_BOX_EXT;
        Map<String, String> params = criteria.makeSmsBoxRequestParams();
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, params);
        service.getSmsHistory(criteria);
    }

    @Test(expected = RestClientException.class)
    public void testGetAllLocksFail() {
        String jsonData = readFileContent("locks.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        List<Lock> allLocks = service.getAllLocks(null);
        assertNotNull(allLocks);
        assertFalse(allLocks.isEmpty());
    }

    @Test
    public void testGetAllLocksForSessionId() {
        String jsonData = readFileContent("locks.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        String sessionId = "1234567890";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("sessionId", sessionId);
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, queryParams);
        List<Lock> allLocks = service.getAllLocks(sessionId);
        assertNotNull(allLocks);
        assertFalse(allLocks.isEmpty());
    }


    @Test(expected = RestClientException.class)
    public void testGetAllLocksFailEmpty() {
        String jsonData = readFileContent("fail-empty.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllLocks(null);
    }

    @Test(expected = RestClientException.class)
    public void testGetAllLocksFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix, null, null);
        service.getAllLocks(null);
    }

    @Test
    public void testGetLockByIdOrOperatorName() {
        String operator = "AT_MOBILKOM";
        String jsonData = readFileContent("locks.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + operator, null,
                null);
        Lock lock = service.getLockByIdOrOperatorName(operator);
        assertNotNull(lock);
    }

    @Test(expected = RestClientException.class)
    public void testGetLockByIdOrOperatorNameFailEmpty() {
        String operator = "AT_MOBILKOM";
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + operator, null,
                null);
        service.getLockByIdOrOperatorName(operator);
    }

    @Test(expected = RestClientException.class)
    public void testGetLockByIdOrOperatorNameFailJson() {
        String operator = "AT_MOBILKOM";
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + operator, null,
                null);
        service.getLockByIdOrOperatorName(operator);
    }

    @Test
    public void testLockOperator() {
        String operator = "AT_MOBILKOM";
        Lock lock = new Lock();
        lock.setApplication("tcui");
        lock.setOperator(operator);
        lock.setUser("admin");
        String lockJson = service.serializeEntity(lock, new Lock.LockSerializer());
        String jsonData = readFileContent("lock-status-response.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, lockJson, null);
        LockStatus lockStatus = service.lockOperator(lock);
        assertNotNull(lockStatus);
    }

    @Test(expected = RestClientException.class)
    public void testLockOperatorFailEmpty() {
        String operator = "AT_MOBILKOM";
        Lock lock = new Lock();
        lock.setApplication("tcui");
        lock.setOperator(operator);
        lock.setUser("admin");
        String lockJson = service.serializeEntity(lock, new Lock.LockSerializer());
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, lockJson, null);
        service.lockOperator(lock);
    }

    @Test(expected = RestClientException.class)
    public void testLockOperatorFailJson() {
        String operator = "AT_MOBILKOM";
        Lock lock = new Lock();
        lock.setApplication("tcui");
        lock.setOperator(operator);
        lock.setUser("admin");
        String lockJson = service.serializeEntity(lock, new Lock.LockSerializer());
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.POST, TCMC_REST_URL + urlSuffix, lockJson, null);
        service.lockOperator(lock);
    }

    @Test
    public void testDeleteLockByOperatorNameOrOperatorId() {
        String operator = "AT_MOBILKOM";
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doReturn(null).when(service).makeHttpRequest(HttpCaller.DELETE, TCMC_REST_URL + urlSuffix + operator, null,
                null);
        boolean deleted = service.deleteLockByOperatorNameOrOperatorId(operator);
        assertTrue(deleted);
    }

    @Test
    public void testDeleteLockByOperatorNameOrOperatorIdFail() {
        String operator = "AT_MOBILKOM";
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doThrow(RestClientException.class).when(service).makeHttpRequest(HttpCaller.DELETE,
                TCMC_REST_URL + urlSuffix + operator, null, null);
        boolean deleted = service.deleteLockByOperatorNameOrOperatorId(operator);
        assertFalse(deleted);
    }

    @Test
    public void testDeleteLocksBySessionId() {
        String sessionId = "1234";
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT + "sessionId/";
        doReturn(null).when(service).makeHttpRequest(HttpCaller.DELETE, TCMC_REST_URL + urlSuffix + sessionId, null,
                null);
        boolean deleted = service.deleteLocksBySessionId(sessionId);
        assertTrue(deleted);
    }

    @Test
    public void testDeleteLocksBySessionIdFail() {
        String sessionId = "1234";
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doThrow(RestClientException.class).when(service).makeHttpRequest(HttpCaller.DELETE,
                TCMC_REST_URL + urlSuffix + sessionId, null, null);
        boolean deleted = service.deleteLocksBySessionId(sessionId);
        assertFalse(deleted);
    }

    @Test
    public void testDeleteLocksByUsername() {
        String username = "someuser";
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT + "user/";
        doReturn(null).when(service).makeHttpRequest(HttpCaller.DELETE,
                TCMC_REST_URL + urlSuffix + username, null, null);
        boolean deleted = service.deleteLocksByUsername(username);
        assertTrue(deleted);
    }

    @Test
    public void testShouldFailDeleteLockByUsername() {
        String username = "someuser";
        String urlSuffix = TcmcClientServiceImpl.LOCK_EXT;
        doThrow(RestClientException.class).when(service).makeHttpRequest(HttpCaller.DELETE,
                TCMC_REST_URL + urlSuffix + username, null, null);
        boolean deleted = service.deleteLocksByUsername(username);
        assertFalse(deleted);
    }

    @Test
    public void testRestartVnc() {
        String jsonData = readFileContent("vnc-restart-callback.json");
        String urlSuffix = TcmcClientServiceImpl.RESTART_VNC_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        VncRestartCallback restartCallback = service.restartVnc(ID_1);
        assertNotNull(restartCallback);
    }

    @Test(expected = RestClientException.class)
    public void testRestartVncFailEmpty() {
        String jsonData = readFileContent("fail-null.json");
        String urlSuffix = TcmcClientServiceImpl.RESTART_VNC_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        service.restartVnc(ID_1);
    }

    @Test(expected = RestClientException.class)
    public void testRestartVncFailJson() {
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.RESTART_VNC_EXT;
        doReturn(jsonData).when(service).makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + ID_1, null, null);
        service.restartVnc(ID_1);
    }

    @Test
    public void testMakeSmsBoxRequestParameters() {
        String msisdn = "123456789";
        String date = "01.01.2010";
        FilterCriteria criteria = new FilterCriteria();
        criteria.setApplication(Application.TCUI);
        criteria.setDateFrom(date);
        criteria.setDateTo(date);
        criteria.setMsisdn(msisdn);
        criteria.setMsisdnFrom(msisdn);
        criteria.setMsisdnTo(msisdn);
        criteria.setSmsDirection(SmsDirection.OUTGOING);
        Map<String, String> params = criteria.makeSmsBoxRequestParams();
        assertNotNull(params);
    }

    @Test
    public void shouldGetBalanceForExistingMsisdn() {
        String existingMsisdn = "+4917673267355";
        String jsonData = readFileContent("balance.json");
        String urlSuffix = TcmcClientServiceImpl.GET_BALANCE_EXT;
        doReturn(jsonData).when(service).
                makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + existingMsisdn, null, null);
        Balance balance = service.getBalance(existingMsisdn);
        assertNotNull(balance);

    }

    @Test(expected = RestClientException.class)
    public void shouldFailGetBalanceForNonExistingMsisdn() {
        String nonExistingMsisdn = "+321";
        String jsonData = readFileContent("balance-non-existing.json");
        String urlSuffix = TcmcClientServiceImpl.GET_BALANCE_EXT;
        doReturn(jsonData).when(service).
                makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + nonExistingMsisdn, null, null);
        service.getBalance(nonExistingMsisdn);
    }

    @Test(expected = RestClientException.class)
    public void shouldFailGetBalanceForWrongJsonFormat() {
        String validMsisdn = "+4917673267355";
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.GET_BALANCE_EXT;
        doReturn(jsonData).when(service).
                makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + validMsisdn, null, null);
        service.getBalance(validMsisdn);
    }

    @Test
    public void shouldCheckBalanceForMsisdn() {
        String existingMsisdn = "+4917673267355";
        String jsonData = readFileContent("check-balance.json");
        String urlSuffix = TcmcClientServiceImpl.CHECK_BALANCE_EXT;
        doReturn(jsonData).when(service).
                makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + existingMsisdn, null, null);
        BalanceCheck balanceCheck = service.checkBalance(existingMsisdn);
        assertNotNull(balanceCheck);
    }

    @Test(expected = RestClientException.class)
    public void shouldFailCheckBalanceForNonExistingMsisdn() {
        String nonExistingMsisdn = "+321";
        String jsonData = readFileContent("check-balance-non-existing.json");
        String urlSuffix = TcmcClientServiceImpl.CHECK_BALANCE_EXT;
        doReturn(jsonData).when(service).
                makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + nonExistingMsisdn, null, null);
        service.checkBalance(nonExistingMsisdn);
    }

    @Test(expected = RestClientException.class)
    public void shouldFailCheckBalanceForWrongJsonFormat() {
        String validMsisdn = "+4917673267355";
        String jsonData = readFileContent("fail-format.json");
        String urlSuffix = TcmcClientServiceImpl.CHECK_BALANCE_EXT;
        doReturn(jsonData).when(service).
                makeHttpRequest(HttpCaller.GET, TCMC_REST_URL + urlSuffix + validMsisdn, null, null);
        service.checkBalance(validMsisdn);
    }

    /**
     * Gets the content of the file in src/test/resources + JSON_FIXTURES_FOLDER
     *
     * @param fileName
     * @return
     */
    private String readFileContent(String fileName) {
        try {
            URL url = getClass().getResource(JSON_FIXTURES_FOLDER + fileName);
            Path resPath = Paths.get(url.toURI());
            String content = new String(Files.readAllBytes(resPath), JSON_ENCODING);
            return content;
        } catch (Exception ex) {
            log.error("Error reading file on path: [" + JSON_FIXTURES_FOLDER + fileName + "]", ex);
        }
        return null;
    }

}
