package petras.bukelis.balticamadeusandroidtask.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user_table")
public class User implements Parcelable {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User p = (User) o;
            return this.username.equals(p.getUsername());
        } else
            return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(username);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[0];
        }
    };

    private User(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        username = parcel.readString();

    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}