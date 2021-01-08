package at.dimoco.tcmc.client.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModelClassesTest {

    private static final String TIMEOUT = "timeout";
    private static final String STATUS = "status";
    private static final String SSH_PORT = "sshPort";
    private static final String CONTACT_PHONE = "contactPhone";
    private static final String CONTACT_NAME = "contactName";
    private static final String CONTACT_COMPANY = "contactCompany";
    private static final String CONTACT_ADDRESS = "contactAddress";
    private static final String RECEIVED = "received";
    private static final String MOBILE_RECEIVED = "mobileReceived";
    private static final String MESSAGE = "message";
    private static final String DIRECTION = "direction";
    private static final String BALANCE = "balance";
    private static final boolean BALANCE_CHECK = true;
    private static final String TYPE = "type";
    private static final String INTERNAL_NAME = "internalName";
    private static final String APPLICATION = "application";
    private static final String OPERATOR = "operator";
    private static final String SESSION_ID = "sessionId";
    private static final String USER = "user";
    private static final String SIM_STATE = "simState";
    private static final String SIGNAL_STRENGTH = "signalStrength";
    private static final String ONLINE = "online";
    private static final String NETWORK_TYPE = "networkType";
    private static final String BATTERY_LEVEL = "batteryLevel";
    private static final String APP_VERSION_STATUS = "appVersionStatus";
    private static final String APP_VERSION = "appVersion";
    private static final String AGE = "age";
    private static final String ANDROID_VERSION = "androidVersion";
    private static final String CLAZ = "claz";
    private static final String MODEL = "model";
    private static final String MANUFACTORER = "manufactorer";
    private static final String ID = "id";
    private static final String ISO = "iso";
    private static final String NAME = "name";
    private static final String ERROR = "error";
    private static final String MSISDN = "msisdn";
    private static final String DATE = "01.01.2000";
    private static final String PORT = "port";
    private static final String SERIAL = "serial";
    private static final String PASSWORD = "password";
    private static final String HOSTNAME = "hostname";

    @Test
    public void testCountry() {
        Country country = new Country();
        Handset handset = new Handset();
        country.setId(ID);
        country.setIso(ISO);
        country.setName(NAME);
        country.setHandset(handset);
        assertFalse(country.isError());
        country.setError("");
        assertFalse(country.isError());
        country.setError(ERROR);
        assertTrue(country.isError());
        assertEquals(country.getId(), ID);
        assertEquals(country.getIso(), ISO);
        assertEquals(country.getName(), NAME);
        assertEquals(country.getError(), ERROR);
        assertEquals(country.getHandset(), handset);
    }

    @Test
    public void testFilterCriteria() {
        FilterCriteria criteriaConstuct =
                new FilterCriteria(Application.TCUI, DATE, DATE, MSISDN, MSISDN, SmsDirection.INCOMING, MSISDN);
        FilterCriteria criteria = new FilterCriteria();
        criteria.setApplication(Application.TCUI);
        criteria.setDateFrom(DATE);
        criteria.setDateTo(DATE);
        criteria.setMsisdn(MSISDN);
        criteria.setMsisdnFrom(MSISDN);
        criteria.setMsisdnTo(MSISDN);
        criteria.setSmsDirection(SmsDirection.INCOMING);

        assertNotNull(criteriaConstuct);
        assertEquals(criteria.getApplication(), Application.TCUI);
        assertEquals(criteria.getDateTo(), DATE);
        assertEquals(criteria.getDateFrom(), DATE);
        assertEquals(criteria.getMsisdn(), MSISDN);
        assertEquals(criteria.getMsisdnFrom(), MSISDN);
        assertEquals(criteria.getMsisdnTo(), MSISDN);
        assertEquals(criteria.getSmsDirection(), SmsDirection.INCOMING);
    }

    @Test
    public void testHandset() {
        Handset handset = new Handset();
        HandsetModel handsetModel = new HandsetModel();
        HandsetStatus handsetStatus = new HandsetStatus();
        SimCard simcard = new SimCard();
        Lock lock = new Lock();
        handset.setId(ID);
        handset.setError(ERROR);
        handset.setHostName(HOSTNAME);
        handset.setPassword(PASSWORD);
        handset.setSerialNumber(SERIAL);
        handset.setVncPort(PORT);
        handset.setSimCard(simcard);
        handset.setLock(lock);
        handset.setHandsetModel(handsetModel);
        handset.setHandsetStatus(handsetStatus);

        assertNotNull(handset.toString());
        assertEquals(handset.getId(), ID);
        assertEquals(handset.getError(), ERROR);
        assertEquals(handset.getHostName(), HOSTNAME);
        assertEquals(handset.getPassword(), PASSWORD);
        assertEquals(handset.getSerialNumber(), SERIAL);
        assertEquals(handset.getVncPort(), PORT);
        assertEquals(handset.getSimCard(), simcard);
        assertEquals(handset.getLock(), lock);
        assertEquals(handset.getHandsetModel(), handsetModel);
        assertEquals(handset.getHandsetStatus(), handsetStatus);
    }

    @Test
    public void testHandsetModel() {
        HandsetModel handsetModel = new HandsetModel();
        handsetModel.setId(ID);
        handsetModel.setClaz(CLAZ);
        handsetModel.setError(ERROR);
        handsetModel.setManufactorer(MANUFACTORER);
        handsetModel.setModel(MODEL);

        assertEquals(handsetModel.getId(), ID);
        assertEquals(handsetModel.getError(), ERROR);
        assertEquals(handsetModel.getClaz(), CLAZ);
        assertEquals(handsetModel.getModel(), MODEL);
        assertEquals(handsetModel.getManufactorer(), MANUFACTORER);
    }

    @Test
    public void testHandsetStatus() {
        HandsetStatus handsetStatus = new HandsetStatus();
        handsetStatus.setId(ID);
        handsetStatus.setAndroidVersion(ANDROID_VERSION);
        handsetStatus.setAge(AGE);
        handsetStatus.setAppVersion(APP_VERSION);
        handsetStatus.setAppVersionStatus(APP_VERSION_STATUS);
        handsetStatus.setBatteryLevel(BATTERY_LEVEL);
        handsetStatus.setCharging(true);
        handsetStatus.setError(ERROR);
        handsetStatus.setLastUpdated(DATE);
        handsetStatus.setNetworkType(NETWORK_TYPE);
        handsetStatus.setOnline(ONLINE);
        handsetStatus.setSignalStrength(SIGNAL_STRENGTH);
        handsetStatus.setSimState(SIM_STATE);

        assertEquals(handsetStatus.getId(), ID);
        assertEquals(handsetStatus.getError(), ERROR);
        assertEquals(handsetStatus.getAge(), AGE);
        assertEquals(handsetStatus.getAndroidVersion(), ANDROID_VERSION);
        assertEquals(handsetStatus.getAppVersion(), APP_VERSION);
        assertEquals(handsetStatus.getAppVersionStatus(), APP_VERSION_STATUS);
        assertEquals(handsetStatus.getBatteryLevel(), BATTERY_LEVEL);
        assertTrue(handsetStatus.getIsCharging());
        assertEquals(handsetStatus.getLastUpdated(), DATE);
        assertEquals(handsetStatus.getNetworkType(), NETWORK_TYPE);
        assertEquals(handsetStatus.getOnline(), ONLINE);
        assertEquals(handsetStatus.getSignalStrength(), SIGNAL_STRENGTH);
        assertEquals(handsetStatus.getSimState(), SIM_STATE);
    }

    @Test
    public void testLock() {
        Lock lock = new Lock();
        Lock lockCons = new Lock(Application.TCUI, OPERATOR);
        LockStatus lockStatus = new LockStatus();
        lock.setId(ID);
        lock.setError(ERROR);
        lock.setApplication(APPLICATION);
        lock.setLocked(true);
        lock.setMsisdn(MSISDN);
        lock.setOperator(OPERATOR);
        lock.setSessionId(SESSION_ID);
        lock.setUser(USER);
        lock.setStatus(lockStatus);

        assertNotNull(lockCons);
        assertEquals(lock.getId(), ID);
        assertEquals(lock.getError(), ERROR);
        assertEquals(lock.getApplication(), APPLICATION);
        assertTrue(lock.isLocked());
        assertEquals(lock.getMsisdn(), MSISDN);
        assertEquals(lock.getOperator(), OPERATOR);
        assertEquals(lock.getSessionId(), SESSION_ID);
        assertEquals(lock.getUser(), USER);
        assertEquals(lock.getStatus(), lockStatus);
    }

    @Test
    public void testLockStatus() {
        LockStatus lockStatus = new LockStatus();
        LockStatus lockStatusConst = new LockStatus(MSISDN, true, USER, OPERATOR, APPLICATION);
        lockStatus.setId(ID);
        lockStatus.setError(ERROR);
        lockStatus.setApplication(APPLICATION);
        lockStatus.setLocked(true);
        lockStatus.setMsisdn(MSISDN);
        lockStatus.setOperator(OPERATOR);
        lockStatus.setSessionId(SESSION_ID);
        lockStatus.setUser(USER);

        assertNotNull(lockStatusConst);
        assertTrue(lockStatus.isLocked());
        assertEquals(lockStatus.getId(), ID);
        assertEquals(lockStatus.getError(), ERROR);
        assertEquals(lockStatus.getApplication(), APPLICATION);
        assertEquals(lockStatus.getMsisdn(), MSISDN);
        assertEquals(lockStatus.getOperator(), OPERATOR);
        assertEquals(lockStatus.getSessionId(), SESSION_ID);
        assertEquals(lockStatus.getUser(), USER);
    }

    @Test
    public void testLockResponse() {
        LockStatusResponse response = new LockStatusResponse();
        LockStatus status = new LockStatus();
        response.setId(ID);
        response.setError(ERROR);
        response.setStatus(status);

        assertEquals(response.getId(), ID);
        assertEquals(response.getError(), ERROR);
        assertEquals(response.getStatus(), status);
    }

    @Test
    public void testOperator() {
        Operator operator = new Operator();
        operator.setId(ID);
        operator.setError(ERROR);
        operator.setInternalName(INTERNAL_NAME);

        assertNotNull(operator.toString());
        assertEquals(operator.getId(), ID);
        assertEquals(operator.getError(), ERROR);
        assertEquals(operator.getInternalName(), INTERNAL_NAME);
    }

    @Test
    public void testPaymentType() {
        PaymentType type = new PaymentType();
        type.setId(ID);
        type.setType(TYPE);
        type.setError(ERROR);
        assertEquals(type.getId(), ID);
        assertEquals(type.getError(), ERROR);
        assertEquals(type.getType(), TYPE);
    }

    @Test
    public void testSimCard() {
        SimCard card = new SimCard();
        Operator operator = new Operator();
        PaymentType paymentType = new PaymentType();
        card.setId(ID);
        card.setError(ERROR);
        card.setMsisdn(MSISDN);
        card.setOperator(operator);
        card.setPaymentType(paymentType);
        card.setBalance(BALANCE);
        card.setBalanceCheck(BALANCE_CHECK);

        assertEquals(card.getId(), ID);
        assertEquals(card.getError(), ERROR);
        assertEquals(card.getMsisdn(), MSISDN);
        assertEquals(card.getBalance(), BALANCE);
        assertEquals(card.isBalanceCheck(), BALANCE_CHECK);
        assertEquals(card.getOperator(), operator);
        assertEquals(card.getPaymentType(), paymentType);
    }

    @Test
    public void testSmsMessage() {
        SmsMessage message = new SmsMessage();
        message.setId(ID);
        message.setError(ERROR);
        message.setFrom(MSISDN);
        message.setTo(MSISDN);
        message.setApplication(APPLICATION);
        message.setDirection(DIRECTION);
        message.setHandled(true);
        message.setMessage(MESSAGE);
        message.setReceived(RECEIVED);
        message.setMobileReceived(MOBILE_RECEIVED);

        assertEquals(message.getId(), ID);
        assertEquals(message.getError(), ERROR);
        assertEquals(message.getFrom(), MSISDN);
        assertEquals(message.getTo(), MSISDN);
        assertEquals(message.getApplication(), APPLICATION);
        assertEquals(message.getDirection(), DIRECTION);
        assertTrue(message.isHandled());
        assertEquals(message.getMessage(), MESSAGE);
        assertEquals(message.getReceived(), RECEIVED);
        assertEquals(message.getMobileReceived(), MOBILE_RECEIVED);
    }

    @Test
    public void testTestcenter() {
        Testcenter center = new Testcenter();
        TestcenterModel model = new TestcenterModel();
        center.setId(ID);
        center.setName(NAME);
        center.setError(ERROR);
        center.setSshPort(SSH_PORT);
        center.setHostname(HOSTNAME);
        center.setContactAddress(CONTACT_ADDRESS);
        center.setContactCompany(CONTACT_COMPANY);
        center.setContactName(CONTACT_NAME);
        center.setContactPhone(CONTACT_PHONE);
        center.setModel(model);

        assertEquals(center.getId(), ID);
        assertEquals(center.getName(), NAME);
        assertEquals(center.getError(), ERROR);
        assertEquals(center.getSshPort(), SSH_PORT);
        assertEquals(center.getHostname(), HOSTNAME);
        assertEquals(center.getContactAddress(), CONTACT_ADDRESS);
        assertEquals(center.getContactCompany(), CONTACT_COMPANY);
        assertEquals(center.getContactPhone(), CONTACT_PHONE);
        assertEquals(center.getContactName(), CONTACT_NAME);
        assertEquals(center.getModel(), model);
    }

    @Test
    public void testTestcenterModel() {
        TestcenterModel model = new TestcenterModel();
        model.setId(ID);
        model.setError(ERROR);
        model.setModel(MODEL);
        model.setManufactorer(MANUFACTORER);

        assertEquals(model.getId(), ID);
        assertEquals(model.getError(), ERROR);
        assertEquals(model.getModel(), MODEL);
        assertEquals(model.getManufactorer(), MANUFACTORER);
    }

    @Test
    public void testVncRestartCallback() {
        VncRestartCallback restart = new VncRestartCallback();
        restart.setId(ID);
        restart.setError(ERROR);
        restart.setStatus(STATUS);
        restart.setTimeout(TIMEOUT);

        assertEquals(restart.getId(), ID);
        assertEquals(restart.getError(), ERROR);
        assertEquals(restart.getStatus(), STATUS);
        assertEquals(restart.getTimeout(), TIMEOUT);
    }

}
