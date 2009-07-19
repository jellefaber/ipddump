package org.quaternions.ipddump.data;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * The InteractivePagerBackup represents a single IPD file and provides an easy
 * way to generate records based on the initial record data.
 * </p>
 * <p>
 * In general, in the ipddump project IPD refers to the <em>file</em> and
 * <code>InteractivePagerBackup </code> refers to the <em>datastructure</em>
 * representing the file.
 * </p>
 *
 * @author borkholder
 * @date Jan 1, 2008
 */
public class InteractivePagerBackup {

    /**
     * The character used as the line feed.
     */
    protected char lineFeed;

    /**
     * The version of the IPD.
     */
    protected final int version;

    /**
     * The list of databases, or rather the names of the databases.
     */
    protected final List<String> databases;

    /**
     * The set of SMS messages.
     */
    protected final List<SMSMessage> smsRecords;

    /**
     * The set of contacts.
     */
    protected final List<Contact> contacts;

    /**
     * The set of Tasks Entries.
     */
    protected final List<Task> tasks;

    /**
     * The set of Memos.
     */
    protected final List<Memo> memos;

    /**
     * The set of TimeZones.
     */
    protected final List<BBTimeZone> timeZones;

    /**
     * The set of Phone Call Logs.
     */
    protected final List<CallLog> callLogs;

    /**
     * Reports If there were Errors while parsing
     */
    private boolean errorFlag=false;

    //~--- constructors -------------------------------------------------------

    /**
     * Creates a new database.
     *
     * @param version The IPD version
     * @param lineFeed The line feed character
     */
    public InteractivePagerBackup(int version, char lineFeed) {
        this.version =version;
        this.lineFeed=lineFeed;
        databases    =new ArrayList<String>();
        smsRecords   =new ArrayList<SMSMessage>();
        contacts     =new ArrayList<Contact>();
        tasks        =new ArrayList<Task>();
        memos        =new ArrayList<Memo>();
        timeZones    =new ArrayList<BBTimeZone>();
        callLogs    =new ArrayList<CallLog>();
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Adds a new database to the list of contained databases.
     *
     * @param name The name of the database to add
     */
    public void addDatabase(String name) {
        databases.add(name);
    }

    /**
     * Gets the collection of contacts.
     *
     * @return An unmodifiable collection of contacts
     */
    public Collection<Contact> contacts() {
        return Collections.<Contact>unmodifiableCollection(contacts);
    }

    /**
     * Creates a new {@link Record} to represent the type of data for the database
     * given by the dbIndex value.
     *
     * @param dbIndex The index of the database that this record will be in
     * @param version The version of the database to which this record belongs
     * @param uid The unique identifier of the Record
     * @param length The length of the Record in the data
     * @return A new Record
     */
    public Record createRecord(int dbIndex, int version, int uid, int length) {

        /*
         * Fix for bug #2, there might be an error in parsing, but for now, this seems to fix it.
         */
        if ((dbIndex>=databases.size()) || (dbIndex<0)) {
            return new DummyRecord(dbIndex, version, uid, length);
        } else if ("SMS Messages".equals(databases.get(dbIndex))) {
            SMSMessage record=new SMSMessage(dbIndex, version, uid, length);

            smsRecords.add(record);

            return record;
        } else if ("Address Book".equals(databases.get(dbIndex)) || "Quick Contacts".equals(databases.get(dbIndex))) {
            Contact record=new Contact(dbIndex, version, uid, length);

            contacts.add(record);

            return record;
        } else if ("Memos".equals(databases.get(dbIndex))) {
            Memo record=new Memo(dbIndex, version, uid, length);

            memos.add(record);

            return record;
        } else if ("Tasks".equals(databases.get(dbIndex))) {
            Task record=new Task(dbIndex, version, uid, length);

            tasks.add(record);

            return record;
        } else if ("Time Zones".equals(databases.get(dbIndex))) {
            BBTimeZone record=new BBTimeZone(dbIndex, version, uid, length);
            timeZones.add(record);

            return record;
        } else if ("Phone Call Logs".equals(databases.get(dbIndex))) {
            CallLog record=new CallLog(dbIndex, version, uid, length);
            callLogs.add(record);

            return record;
        } else {
            return new DummyRecord(dbIndex, version, uid, length);
        }
    }

    /**
     * Gets the list of database names that have been added so far.
     *
     * @return An unmodifiable list of database names
     */
    public List<String> databaseNames() {
        return Collections.<String>unmodifiableList(databases);
    }

    /**
     * Gets the collection of memos.
     *
     * @return An unmodifiable collection of memos
     */
    public Collection<Memo> memos() {
        return Collections.<Memo>unmodifiableCollection(memos);
    }

    public void organize() {
        Collections.sort(memos);
        Collections.sort(smsRecords);
        Collections.sort(tasks);
        Collections.sort(contacts);
        Collections.sort(timeZones);
        Collections.sort(callLogs);

        Finder finder=new Finder(this);

        for (Task recordt : this.tasks) { 
                String name=finder.findTimeZoneByID(recordt.getTimeZone());
                recordt.setTimeZoneName(name);
        }
    }

    //~--- set methods --------------------------------------------------------

    public void setErrorFlag() {
        errorFlag=true;
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Gets the collection of SMS records.
     *
     * @return An unmodifiable collection of SMS records
     */
    public Collection<SMSMessage> smsRecords() {
        return Collections.<SMSMessage>unmodifiableCollection(smsRecords);
    }

    /**
     * Gets the collection of task records.
     *
     * @return An unmodifiable collection of task records
     */
    public Collection<Task> tasks() {
        return Collections.unmodifiableCollection(tasks);
    }

    /**
     * Gets the collection of the Time Zones records.
     *
     * @return An unmodifiable collection of Time Zones records
     */
    public Collection<BBTimeZone> timeZones() {
        return Collections.unmodifiableCollection(timeZones);
    }

    /**
     * Gets the collection of the Phone Call Logs.
     *
     * @return An unmodifiable collection of Phone Call Logs records
     */
    public Collection<CallLog> callLogs() {
        return Collections.unmodifiableCollection(callLogs);
    }

    public boolean wereErrors() {
        return errorFlag;
    }
}
