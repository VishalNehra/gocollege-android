package in.eightbitlabs.carpool.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.squareup.sqldelight.RowMapper;

/**
 * @author shalzz
 */
@AutoValue
public abstract class Profile implements ProfileModel, Parcelable {
    public static final Factory<Profile> FACTORY = new Factory<>(AutoValue_Profile::new);

    public static final RowMapper<Profile> MAPPER = FACTORY.select_by_idMapper();

    public static TypeAdapter<Profile> typeAdapter(Gson gson) {
        return new AutoValue_Profile.GsonTypeAdapter(gson);
    }
}
