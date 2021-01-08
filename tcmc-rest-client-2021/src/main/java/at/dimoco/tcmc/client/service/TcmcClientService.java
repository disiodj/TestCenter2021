package at.dimoco.tcmc.client.service;

import at.dimoco.tcmc.client.model.Balance;
import at.dimoco.tcmc.client.model.BalanceCheck;
import at.dimoco.tcmc.client.model.FilterCriteria;
import at.dimoco.tcmc.client.model.Handset;
import at.dimoco.tcmc.client.model.HandsetStatus;
import at.dimoco.tcmc.client.model.Lock;
import at.dimoco.tcmc.client.model.LockStatus;
import at.dimoco.tcmc.client.model.SimCard;
import at.dimoco.tcmc.client.model.SmsMessage;
import at.dimoco.tcmc.client.model.Testcenter;
import at.dimoco.tcmc.client.model.VncRestartCallback;

import java.util.List;

/**
 * TCMC REST client definition
 */
public interface TcmcClientService {

    /**
     * Get list of all testcenters
     *
     * @return List of Testcenter entities
     */
    List<Testcenter> getAllTestcenters();

    /**
     * Get testcenter by id
     *
     * @param testCenterId Testcenter id
     * @param updateMode   True will return only the reference ids for testcenter child objects, otherwise a full tree
     * @return Testcenter entity
     */
    Testcenter getTestCenterById(String testCenterId, boolean updateMode);

    /**
     * Save or update Testcenter entity
     *
     * @param testcenter Testcenter entity to save or update
     * @return Saved or updated Testcenter entity
     */
    Testcenter saveOrUpdateTestcenter(Testcenter testcenter);

    /**
     * Delete Testcenter records
     *
     * @param testcenterId Testcenter id to delete
     * @return True for delete; false otherwise
     */
    boolean deleteTestcenter(String testcenterId);

    /**
     * Get List of all handsets
     *
     * @return List of Handset entities
     */
    List<Handset> getAllHandsets();

    /**
     * Get Handset by id
     *
     * @param handsetId  Handset id
     * @param updateMode True will return only the reference ids for handset child objects, otherwise a full tree
     * @return Handset entity
     */
    Handset getHandsetById(String handsetId, boolean updateMode);

    /**
     * Get handset status by handset id
     *
     * @param handsetId Handset id
     * @return HandsetStatus entity
     */
    HandsetStatus getHandsetStatusByHandsetId(String handsetId);

    /**
     * Get List of all sim cards
     *
     * @return List of SimCard entities
     */
    List<SimCard> getAllSimCards();

    /**
     * Get sim card by id
     *
     * @param simCardId SimCard id
     * @return SimCard entity
     */
    SimCard getSimCardById(String simCardId);

    /**
     * Sends sms message
     *
     * @param smsMessage SmsMessage entity to serialize and send
     * @return SmsMessage entity model
     */
    SmsMessage sendSms(SmsMessage smsMessage);


    /**
     * Get sms history
     *
     * @param criteria FilterCriteria filter to include in search
     * @return List of SmsMessage entities
     * @see at.dimoco.tcmc.client.model.FilterCriteria
     */
    List<SmsMessage> getSmsHistory(FilterCriteria criteria);


    /**
     * Get sms history per testcenter
     * @param criteria filter to include in the search
     * @param  testcenterId Testcenter id
     * @return List of SmsMessage entities
     * @see at.dimoco.tcmc.client.model.FilterCriteria
     */
    List<SmsMessage> getSmsHistoryForTestcenter(FilterCriteria criteria, String testcenterId);

    /**
     * Get all locks
     *
     * @param sessionId Sessionid optional param
     * @return List of Lock entities
     */
    List<Lock> getAllLocks(String sessionId);

    /**
     * Get lock by id or operator name
     *
     * @param param Id or Operator name
     * @return Lock object
     */
    Lock getLockByIdOrOperatorName(String param);

    /**
     * Get lock by msisdn
     *
     * @param msisdn Msisdn
     * @return Lock object
     */
    Lock getLockByMsisdn(String msisdn);

    /**
     * Locks an operator (msisdn)
     *
     * @return Lock status entity
     * @ param Lock
     */
    LockStatus lockOperator(Lock lock);

    /**
     * Delete Lock by operator name or opreator id
     *
     * @param param Lock id or operator name
     * @return
     */
    boolean deleteLockByOperatorNameOrOperatorId(String param);

    /**
     * Delete lock by msisdn
     *
     * @param msisdn msisdn
     * @return True for success; false otherwise
     */
    boolean deleteLockByMsisdn(String msisdn);


    /**
     * Delete locks for session id
     *
     * @param sessionId Session id
     * @return True for success, false otherwise
     */
    boolean deleteLocksBySessionId(String sessionId);

    /**
     * Delete locks for username
     * @param username Username
     * @return True for success, false otherwise
     */
    boolean deleteLocksByUsername(String username);

    /**
     * Restart VNC server for handset
     *
     * @param handsetId Handset id
     * @return VncRestartCallback entity
     */
    VncRestartCallback restartVnc(String handsetId);

    /**
     * Get last known balance
     * @param msisdn Msisdn to check balance for
     * @return Balance record
     */
    Balance getBalance(String msisdn);

    /**
     * Triggers balance check on handset
     * @param msisdn Msisdn to trigger balance check for
     * @return BalanceCheck record
     */
    BalanceCheck checkBalance(String msisdn);


}
