package com.foodaclic.livraison.utils.event;

public class NetworkOperationEvent {

  final public static int HAS_FAILED = -1;
  final public static int COST_UPDATED = 100;
  final public static int HAS_FINISHED_ALL = 10;
  final public static int HAS_FINISHED_ONE = 1;
  final public static int HAS_STARTED = 0;
  final public static int HAS_NOT_SYNCED = 3;
  final public static int PARAMS = 2;
  final public static int HAS_CONTACT_SYNC_STARTED = 200;
  final public static int HAS_NOTHING = 99;
  final public static int HAS_ROUTE = 90;
  final public static int HAS_CHANGED = 9000;
  final public static int HAS_BT_OK = 444;
  final public static int HAS_BT_NO = 555;


  private String mMessage;
  private int mStatus;
  private long mId;
  public int mPosition;
  private boolean isDataFetching;
  private boolean route;

  public NetworkOperationEvent(int status) {
    this.mStatus = status;
  }

  //public NetworkOperationEvent(int status, boolean isDataFetching) {
  //  this(status);
  //  this.isDataFetching = isDataFetching;
  //}
    
    /*public NetworkOperationEvent(int status, int position){
      this.mStatus=status;
    	this.mPosition=position;
    }*/

  public NetworkOperationEvent(int status, String message) {
    this(status, true);
    this.mMessage = message;
  }

  public NetworkOperationEvent(int status, long id) {
    this(status);
    this.mId = id;
  }

  public NetworkOperationEvent(int status, boolean route) {
    this(status);
    this.route = route;
  }

  public NetworkOperationEvent(int status, String message, boolean isDataFetching) {
    this(status);
    this.mMessage = message;
  }

  public long getId() {
    return mId;
  }

  public void setId(long mId) {
    this.mId = mId;
  }

  public String getMessage() {
    return mMessage;
  }

  public boolean hasFailed() {
    return (mStatus == HAS_FAILED);
  }
  public boolean hasNothing() {
    return (mStatus == HAS_NOTHING);
  }

  public boolean hasParameters() {
    return (mStatus == PARAMS);
  }

  public boolean isDataFetching() {
    return isDataFetching;
  }

  public void setDataFetching(boolean dataFetching) {
    isDataFetching = dataFetching;
  }

  public boolean hasFinishedAll() {
    return (mStatus == HAS_FINISHED_ALL);
  }

  public boolean hasFinishedOne() {
    return (mStatus == HAS_FINISHED_ONE);
  }

  public boolean hasStarted() {
    return (mStatus == HAS_STARTED);
  }
  public boolean hasContactSyncStarted() {
    return (mStatus == HAS_CONTACT_SYNC_STARTED);
  }

  public boolean hasCostUpdated() {
    return (mStatus == COST_UPDATED);
  }

  public boolean hasNotSynced() {
    return (mStatus == HAS_NOT_SYNCED);
  }

  public void setMessage(String message) {
    this.mMessage = message;
  }

  public boolean isRoute() {
    return route;
  }
  public boolean hasRoute() {
    return (mStatus == HAS_ROUTE);
  }

  public boolean hasChanged() {
    return (mStatus == HAS_CHANGED);
  }
  public boolean hasBTOK() {
    return (mStatus == HAS_BT_OK);
  }
  public boolean hasBTNO() {
    return (mStatus == HAS_BT_NO);
  }


}
