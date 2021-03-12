package petras.bukelis.balticamadeusandroidtask.enities;

import android.os.Parcel;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static com.google.common.truth.Truth.assertThat;

import petras.bukelis.balticamadeusandroidtask.entities.Post;

@RunWith(AndroidJUnit4ClassRunner.class)
@SmallTest
public class PostParcelableInstrumentedTest {


    @Test
    public void Post_ParcelableWriteRead()
    {
        Post mPost = new Post(1,"Test","Test");
        Parcel parcel = Parcel.obtain();
        mPost.writeToParcel(parcel,0);
        parcel.setDataPosition(0);

        Post postFromParcel = Post.CREATOR.createFromParcel(parcel);
        assertThat(postFromParcel).isEqualTo(mPost);

        parcel.recycle();
    }
}
