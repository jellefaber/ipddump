package org.quaternions.ipddump.data;

import java.util.List;
import java.util.Map;

/**
 * @author borkholder
 * @date Jan 1, 2008
 */
public abstract class Record
{
   /**
    * The 0-based index of the database to which this field belongs.
    */
   protected final int databaseID;

   /**
    * The version of the database to which this field belongs.
    */
   protected final int databaseVersion;

   /**
    * The unique identifier of this record.
    */
   protected int       uniqueID;

   /**
    * The length of the record.
    */
   protected int       length;

   /**
    * Creates a new record with all provided data.
    *
    * @param dbID
    *           The database id
    * @param dbVersion
    *           The database version
    * @param uid
    *           The unique identifier of this record
    * @param recordLength
    *           The length of the record
    */
   protected Record( int dbID, int dbVersion, int uid, int recordLength )
   {
      databaseID = dbID;
      databaseVersion = dbVersion;
      uniqueID = uid;
      length = recordLength;
   }

   /**
    * Adds the field to the record.
    *
    * @param type
    *           The type of field
    * @param data
    *           The field data
    */
   public abstract void addField( int type, char[] data );

   /**
    * Gets the fields contained by this record.
    *
    * @return An unmodifiable map from the name of the field to the field data
    */
   public abstract Map<String, String> fields();

   /**
    * Gets the names of the fields contained by this record. The names of the
    * fields are up to the records.
    *
    * @return An unmodifiable list of field names
    */
   public abstract List<String> fieldNames();

   /**
    * Gets the 0-based index of the database to which this field belongs.
    *
    * @return The database index
    */
   public int getDatabaseID()
   {
      return databaseID;
   }

   /**
    * Gets the version of the database to which this field belongs.
    *
    * @return The database version
    */
   public int getDatabaseVersion()
   {
      return databaseVersion;
   }

   /**
    * Gets the unique identifier of this record.
    */
   public int getUID()
   {
      return uniqueID;
   }

   /**
    * Gets the length of the record.
    *
    * @return The record length
    */
   public int getLength()
   {
      return length;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals( Object obj )
   {
      if ( obj instanceof Record )
      {
         return uniqueID == ( (Record) obj ).uniqueID;
      }
      else
      {
         return false;
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return uniqueID;
   }
}
