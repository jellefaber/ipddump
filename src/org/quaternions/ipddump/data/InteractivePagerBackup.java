package org.quaternions.ipddump.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
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
  protected char                   lineFeed;

  /**
   * The version of the IPD.
   */
  protected final int              version;

  /**
   * The list of databases, or rather the names of the databases.
   */
  protected final List<String>     databases;

  /**
   * The set of SMS messages.
   */
  protected final List<SMSMessage> smsRecords;

  /**
   * The set of contacts.
   */
  protected final List<Contact>    contacts;

  /**
   * The set of Tasks Entries.
   */
  protected final List<Tasks>    tasks;
  /**
   * The set of Memos.
   */
  protected final List<Memos>    memos;

  /**
   * Creates a new database.
   *
   * @param version The IPD version
   * @param lineFeed The line feed character
   */
  public InteractivePagerBackup( int version, char lineFeed ) {
    this.version = version;
    this.lineFeed = lineFeed;
    databases = new ArrayList<String>();
    smsRecords = new LinkedList<SMSMessage>();
    contacts = new LinkedList<Contact>();
    tasks = new LinkedList<Tasks>();
    memos = new LinkedList<Memos>();
  }

  /**
   * Adds a new database to the list of contained databases.
   *
   * @param name The name of the database to add
   */
  public void addDatabase( String name ) {
    databases.add( name );
  }

  /**
   * Gets the list of database names that have been added so far.
   *
   * @return An unmodifiable list of database names
   */
  public List<String> databaseNames() {
    return Collections.<String> unmodifiableList( databases );
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
  public Record createRecord( int dbIndex, int version, int uid, int length ) {
    //System.out.println(databases.get( dbIndex ));
    if ( dbIndex >= databases.size() ) {
      return new DummyRecord( dbIndex, version, uid, length );
    } else if ( "SMS Messages".equals( databases.get( dbIndex ) ) ) {
      SMSMessage record = new SMSMessage( dbIndex, version, uid, length );
      smsRecords.add( record );
      return record;
    } else if ( "Address Book".equals( databases.get( dbIndex ) ) ) {
      Contact record = new Contact( dbIndex, version, uid, length );
      contacts.add( record );
      return record;
//    } else if ( "Tasks".equals( databases.get( dbIndex ) ) ) {
//      Tasks record = new Tasks( dbIndex, version, uid, length );
//      tasks.add( record );
//      return record;
    }else if ( "Memos".equals( databases.get( dbIndex ) ) ) {
      Memos record = new Memos( dbIndex, version, uid, length );
      memos.add( record );
      return record;
    } else {
      return new DummyRecord( dbIndex, version, uid, length );
    }
  }

  /**
   * Gets the collection of SMS records.
   *
   * @return An unmodifiable collection of SMS records
   */
  public Collection<SMSMessage> smsRecords() {
    return Collections.<SMSMessage> unmodifiableCollection( smsRecords );
  }

  /**
   * Gets the collection of contacts.
   *
   * @return An unmodifiable collection of contacts
   */
  public Collection<Contact> contacts() {
    return Collections.<Contact> unmodifiableCollection( contacts );
  }

  /**
   * Gets the collection of memos.
   *
   * @return An unmodifiable collection of memos
   */
   public Collection<Memos> memos() {
    return Collections.<Memos> unmodifiableCollection( memos );
  }
}
