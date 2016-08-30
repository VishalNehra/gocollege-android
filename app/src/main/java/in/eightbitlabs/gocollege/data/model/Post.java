package in.eightbitlabs.gocollege.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.squareup.sqldelight.ColumnAdapter;
import com.squareup.sqldelight.RowMapper;

/**
 * @author shalzz
 */
@AutoValue
public abstract class Post implements PostModel, Parcelable {
    private static final ColumnAdapter<Profile> USER_COLUMN_ADAPTER = new ColumnAdapter<Profile>() {

        @Override
        public Profile map(Cursor cursor, int columnIndex) {
            return null;
        }

        @Override
        public void marshal(ContentValues values, String key, Profile value) {
            values.put(key, value.id());
        }
    };

    public static final Factory<Post> FACTORY = new Factory<>(AutoValue_Post::new, USER_COLUMN_ADAPTER);

    public static final RowMapper<Post> MAPPER = FACTORY.select_allMapper();

    public static Builder builder() {
        return new AutoValue_Post.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder timestamp(long value);
        public abstract Builder license(String value);
        public abstract Builder type(String value);
        public abstract Builder model(String value);
        public abstract Builder pickup(String value);
        public abstract Builder contact(long value);
        public abstract Builder seats(int value);
        public abstract Builder rate(Integer value);
        public abstract Builder profile(Profile value);
        public abstract Post build();
    }

    public static TypeAdapter<Post> typeAdapter(Gson gson) {
        return new AutoValue_Post.GsonTypeAdapter(gson);
    }
}
