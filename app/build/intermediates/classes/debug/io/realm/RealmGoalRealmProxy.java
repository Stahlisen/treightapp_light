package io.realm;


import Model.RealmGoal;
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

public class RealmGoalRealmProxy extends RealmGoal
    implements RealmObjectProxy {

    private static long INDEX_GOALWEIGHT;
    private static long INDEX_GOALDATE;
    private static long INDEX_ISLOOSE;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("goalweight");
        fieldNames.add("goaldate");
        fieldNames.add("isLoose");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    @Override
    public float getGoalweight() {
        realm.checkIfValid();
        return (float) row.getFloat(INDEX_GOALWEIGHT);
    }

    @Override
    public void setGoalweight(float value) {
        realm.checkIfValid();
        row.setFloat(INDEX_GOALWEIGHT, (float) value);
    }

    @Override
    public Date getGoaldate() {
        realm.checkIfValid();
        return (java.util.Date) row.getDate(INDEX_GOALDATE);
    }

    @Override
    public void setGoaldate(Date value) {
        realm.checkIfValid();
        row.setDate(INDEX_GOALDATE, (Date) value);
    }

    @Override
    public boolean isLoose() {
        realm.checkIfValid();
        return (boolean) row.getBoolean(INDEX_ISLOOSE);
    }

    @Override
    public void setIsLoose(boolean value) {
        realm.checkIfValid();
        row.setBoolean(INDEX_ISLOOSE, (boolean) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmGoal")) {
            Table table = transaction.getTable("class_RealmGoal");
            table.addColumn(ColumnType.FLOAT, "goalweight");
            table.addColumn(ColumnType.DATE, "goaldate");
            table.addColumn(ColumnType.BOOLEAN, "isLoose");
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_RealmGoal");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmGoal")) {
            Table table = transaction.getTable("class_RealmGoal");
            if (table.getColumnCount() != 3) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 3 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 3; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type RealmGoal");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_GOALWEIGHT = table.getColumnIndex("goalweight");
            INDEX_GOALDATE = table.getColumnIndex("goaldate");
            INDEX_ISLOOSE = table.getColumnIndex("isLoose");

            if (!columnTypes.containsKey("goalweight")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'goalweight'");
            }
            if (columnTypes.get("goalweight") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'goalweight'");
            }
            if (!columnTypes.containsKey("goaldate")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'goaldate'");
            }
            if (columnTypes.get("goaldate") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'goaldate'");
            }
            if (!columnTypes.containsKey("isLoose")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'isLoose'");
            }
            if (columnTypes.get("isLoose") != ColumnType.BOOLEAN) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'boolean' for field 'isLoose'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmGoal class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmGoal";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static RealmGoal createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmGoal obj = realm.createObject(RealmGoal.class);
        if (!json.isNull("goalweight")) {
            obj.setGoalweight((float) json.getDouble("goalweight"));
        }
        if (!json.isNull("goaldate")) {
            Object timestamp = json.get("goaldate");
            if (timestamp instanceof String) {
                obj.setGoaldate(JsonUtils.stringToDate((String) timestamp));
            } else {
                obj.setGoaldate(new Date(json.getLong("goaldate")));
            }
        }
        if (!json.isNull("isLoose")) {
            obj.setIsLoose((boolean) json.getBoolean("isLoose"));
        }
        return obj;
    }

    public static RealmGoal createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmGoal obj = realm.createObject(RealmGoal.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("goalweight") && reader.peek() != JsonToken.NULL) {
                obj.setGoalweight((float) reader.nextDouble());
            } else if (name.equals("goaldate")  && reader.peek() != JsonToken.NULL) {
                if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setGoaldate(new Date(timestamp));
                    }
                } else {
                    obj.setGoaldate(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("isLoose")  && reader.peek() != JsonToken.NULL) {
                obj.setIsLoose((boolean) reader.nextBoolean());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmGoal copyOrUpdate(Realm realm, RealmGoal object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static RealmGoal copy(Realm realm, RealmGoal newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmGoal realmObject = realm.createObject(RealmGoal.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setGoalweight(newObject.getGoalweight());
        realmObject.setGoaldate(newObject.getGoaldate() != null ? newObject.getGoaldate() : new Date(0));
        realmObject.setIsLoose(newObject.isLoose());
        return realmObject;
    }

    static RealmGoal update(Realm realm, RealmGoal realmObject, RealmGoal newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setGoalweight(newObject.getGoalweight());
        realmObject.setGoaldate(newObject.getGoaldate() != null ? newObject.getGoaldate() : new Date(0));
        realmObject.setIsLoose(newObject.isLoose());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmGoal = [");
        stringBuilder.append("{goalweight:");
        stringBuilder.append(getGoalweight());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{goaldate:");
        stringBuilder.append(getGoaldate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isLoose:");
        stringBuilder.append(isLoose());
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
        RealmGoalRealmProxy aRealmGoal = (RealmGoalRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmGoal.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmGoal.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmGoal.row.getIndex()) return false;

        return true;
    }

}
