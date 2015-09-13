package io.realm;


import Model.RealmWeighIn;
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

public class RealmWeighInRealmProxy extends RealmWeighIn
    implements RealmObjectProxy {

    private static long INDEX_ID;
    private static long INDEX_WEIGHT;
    private static long INDEX_PICTUREFILEPATH;
    private static long INDEX_DATE;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("weight");
        fieldNames.add("picturefilepath");
        fieldNames.add("date");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    @Override
    public int getId() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_ID);
    }

    @Override
    public void setId(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_ID, (long) value);
    }

    @Override
    public float getWeight() {
        realm.checkIfValid();
        return (float) row.getFloat(INDEX_WEIGHT);
    }

    @Override
    public void setWeight(float value) {
        realm.checkIfValid();
        row.setFloat(INDEX_WEIGHT, (float) value);
    }

    @Override
    public String getPicturefilepath() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_PICTUREFILEPATH);
    }

    @Override
    public void setPicturefilepath(String value) {
        realm.checkIfValid();
        row.setString(INDEX_PICTUREFILEPATH, (String) value);
    }

    @Override
    public Date getDate() {
        realm.checkIfValid();
        return (java.util.Date) row.getDate(INDEX_DATE);
    }

    @Override
    public void setDate(Date value) {
        realm.checkIfValid();
        row.setDate(INDEX_DATE, (Date) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmWeighIn")) {
            Table table = transaction.getTable("class_RealmWeighIn");
            table.addColumn(ColumnType.INTEGER, "id");
            table.addColumn(ColumnType.FLOAT, "weight");
            table.addColumn(ColumnType.STRING, "picturefilepath");
            table.addColumn(ColumnType.DATE, "date");
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_RealmWeighIn");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmWeighIn")) {
            Table table = transaction.getTable("class_RealmWeighIn");
            if (table.getColumnCount() != 4) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 4 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 4; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type RealmWeighIn");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_ID = table.getColumnIndex("id");
            INDEX_WEIGHT = table.getColumnIndex("weight");
            INDEX_PICTUREFILEPATH = table.getColumnIndex("picturefilepath");
            INDEX_DATE = table.getColumnIndex("date");

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'id'");
            }
            if (columnTypes.get("id") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'id'");
            }
            if (!columnTypes.containsKey("weight")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'weight'");
            }
            if (columnTypes.get("weight") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'weight'");
            }
            if (!columnTypes.containsKey("picturefilepath")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'picturefilepath'");
            }
            if (columnTypes.get("picturefilepath") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'picturefilepath'");
            }
            if (!columnTypes.containsKey("date")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'date'");
            }
            if (columnTypes.get("date") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'date'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmWeighIn class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmWeighIn";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static RealmWeighIn createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmWeighIn obj = realm.createObject(RealmWeighIn.class);
        if (!json.isNull("id")) {
            obj.setId((int) json.getInt("id"));
        }
        if (!json.isNull("weight")) {
            obj.setWeight((float) json.getDouble("weight"));
        }
        if (!json.isNull("picturefilepath")) {
            obj.setPicturefilepath((String) json.getString("picturefilepath"));
        }
        if (!json.isNull("date")) {
            Object timestamp = json.get("date");
            if (timestamp instanceof String) {
                obj.setDate(JsonUtils.stringToDate((String) timestamp));
            } else {
                obj.setDate(new Date(json.getLong("date")));
            }
        }
        return obj;
    }

    public static RealmWeighIn createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmWeighIn obj = realm.createObject(RealmWeighIn.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id") && reader.peek() != JsonToken.NULL) {
                obj.setId((int) reader.nextInt());
            } else if (name.equals("weight")  && reader.peek() != JsonToken.NULL) {
                obj.setWeight((float) reader.nextDouble());
            } else if (name.equals("picturefilepath")  && reader.peek() != JsonToken.NULL) {
                obj.setPicturefilepath((String) reader.nextString());
            } else if (name.equals("date")  && reader.peek() != JsonToken.NULL) {
                if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setDate(new Date(timestamp));
                    }
                } else {
                    obj.setDate(JsonUtils.stringToDate(reader.nextString()));
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmWeighIn copyOrUpdate(Realm realm, RealmWeighIn object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static RealmWeighIn copy(Realm realm, RealmWeighIn newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmWeighIn realmObject = realm.createObject(RealmWeighIn.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId());
        realmObject.setWeight(newObject.getWeight());
        realmObject.setPicturefilepath(newObject.getPicturefilepath() != null ? newObject.getPicturefilepath() : "");
        realmObject.setDate(newObject.getDate() != null ? newObject.getDate() : new Date(0));
        return realmObject;
    }

    static RealmWeighIn update(Realm realm, RealmWeighIn realmObject, RealmWeighIn newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setId(newObject.getId());
        realmObject.setWeight(newObject.getWeight());
        realmObject.setPicturefilepath(newObject.getPicturefilepath() != null ? newObject.getPicturefilepath() : "");
        realmObject.setDate(newObject.getDate() != null ? newObject.getDate() : new Date(0));
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmWeighIn = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{weight:");
        stringBuilder.append(getWeight());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{picturefilepath:");
        stringBuilder.append(getPicturefilepath());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(getDate());
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
        RealmWeighInRealmProxy aRealmWeighIn = (RealmWeighInRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmWeighIn.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmWeighIn.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmWeighIn.row.getIndex()) return false;

        return true;
    }

}
