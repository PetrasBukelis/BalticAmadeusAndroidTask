package petras.bukelis.balticamadeusandroidtask.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "post_table")
public class Post implements Parcelable {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("userId")
    private int userId;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public Post(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel parcel) {
            return new Post(parcel);
        }

        @Override
        public Post[] newArray(int i) {
            return new Post[0];
        }
    };

    private Post(Parcel parcel)
    {
        id = parcel.readInt();
        userId = parcel.readInt();
        title = parcel.readString();
        body = parcel.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeString(title);
        parcel.writeString(body);

    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Post){
            Post p = (Post) o;
            return this.title.equals(p.getTitle());
        } else
            return false;
    }
}
