package io.realm;


import Model.RealmAlarm;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RealmAlarmRealmProxy extends RealmAlarm
    implements RealmObjectProxy {

    private static long INDEX_WEEKDAY;
    private static long INDEX_ACTIVATED;
    private static long INDEX_HOUR;
    private static long INDEX_MINUTES;
    private static long INDEX_NAME;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("weekday");
        fieldNames.add("activated");
        fieldNames.add("hour");
        fieldNames.add("minutes");
        fieldNames.add("name");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    @Override
    public int getWeekday() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_WEEKDAY);
    }

    @Override
    public void setWeekday(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_WEEKDAY, (long) value);
    }

    @Override
    public boolean getActivated() {
        realm.checkIfValid();
        return (boolean) row.getBoolean(INDEX_ACTIVATED);
    }

    @Override
    public void setActivated(boolean value) {
        realm.checkIfValid();
        row.setBoolean(INDEX_ACTIVATED, (boolean) value);
    }

    @Override
    public int getHour() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_HOUR);
    }

    @Override
    public void setHour(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_HOUR, (long) value);
    }

    @Override
    public int getMinutes() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_MINUTES);
    }

    @Override
    public void setMinutes(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_MINUTES, (long) value);
    }

    @Override
    public String getName() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_NAME);
    }

    @Override
    public void setName(String value) {
        realm.checkIfValid();
        row.setString(INDEX_NAME, (String) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmAlarm")) {
            Table table = transaction.getTable("class_RealmAlarm");
            table.addColumn(ColumnType.INTEGER, "weekday");
            table.addColumn(ColumnType.BOOLEAN, "activated");
            table.addColumn(ColumnType.INTEGER, "hour");
            table.addColumn(ColumnType.INTEGER, "minutes");
            table.addColumn(ColumnType.STRING, "name");
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_RealmAlarm");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmAlarm")) {
            Table table = transaction.getTable("class_RealmAlarm");
            if (table.getColumnCount() != 5) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 5 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 5; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type RealmAlarm");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_WEEKDAY = table.getColumnIndex("weekday");
            INDEX_ACTIVATED = table.getColumnIndex("activated");
            INDEX_HOUR = table.getColumnIndex("hour");
            INDEX_MINUTES = table.getColumnIndex("minutes");
            INDEX_NAME = table.getColumnIndex("name");

            if (!columnTypes.containsKey("weekday")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'weekday'");
            }
            if (columnTypes.get("weekday") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'weekday'");
            }
            if (!columnTypes.containsKey("activated")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'activated'");
            }
            if (columnTypes.get("activated") != ColumnType.BOOLEAN) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'boolean' for field 'activated'");
            }
            if (!columnTypes.containsKey("hour")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'hour'");
            }
            if (columnTypes.get("hour") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'hour'");
            }
            if (!columnTypes.containsKey("minutes")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'minutes'");
            }
            if (columnTypes.get("minutes") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'minutes'");
            }
            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'name'");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'name'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmAlarm class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmAlarm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static RealmAlarm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmAlarm obj = realm.createObject(RealmAlarm.class);
        if (!json.isNull("weekday")) {
            obj.setWeekday((int) json.getInt("weekday"));
        }
        if (!json.isNull("activated")) {
            obj.setActivated((boolean) json.getBoolean("activated"));
        }
        if (!json.isNull("hour")) {
            obj.setHour((int) json.getInt("hour"));
        }
        if (!json.isNull("minutes")) {
            obj.setMinutes((int) json.getInt("minutes"));
        }
        if (!json.isNull("name")) {
            obj.setName((String) json.getString("name"));
        }
        return obj;
    }

    public static RealmAlarm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmAlarm obj = realm.createObject(RealmAlarm.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("weekday") && reader.peek() != JsonToken.NULL) {
                obj.setWeekday((int) reader.nextInt());
            } else if (name.equals("activated")  && reader.peek() != JsonToken.NULL) {
                obj.setActivated((boolean) reader.nextBoolean());
            } else if (name.equals("hour")  && reader.peek() != JsonToken.NULL) {
                obj.setHour((int) reader.nextInt());
            } else if (name.equals("minutes")  && reader.peek() != JsonToken.NULL) {
                obj.setMinutes((int) reader.nextInt());
            } else if (name.equals("name")  && reader.peek() != JsonToken.NULL) {
                obj.setName((String) reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmAlarm copyOrUpdate(Realm realm, RealmAlarm object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static RealmAlarm copy(Realm realm, RealmAlarm newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmAlarm realmObject = realm.createObject(RealmAlarm.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setWeekday(newObject.getWeekday());
        realmObject.setActivated(newObject.getActivated());
        realmObject.setHour(newObject.getHour());
        realmObject.setMinutes(newObject.getMinutes());
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        return realmObject;
    }

    static RealmAlarm update(Realm realm, RealmAlarm realmObject, RealmAlarm newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setWeekday(newObject.getWeekday());
        realmObject.setActivated(newObject.getActivated());
        realmObject.setHour(newObject.getHour());
        realmObject.setMinutes(newObject.getMinutes());
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmAlarm = [");
        stringBuilder.append("{weekday:");
        stringBuilder.append(getWeekday());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{activated:");
        stringBuilder.append(getActivated());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{hour:");
        stringBuilder.append(getHour());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{minutes:");
        stringBuilder.append(getMinutes());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealmAlarmRealmProxy aRealmAlarm = (RealmAlarmRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmAlarm.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmAlarm.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmAlarm.row.getIndex()) return false;

        return true;
    }

}
