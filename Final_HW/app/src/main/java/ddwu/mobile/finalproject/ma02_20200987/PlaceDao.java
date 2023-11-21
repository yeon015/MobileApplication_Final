package ddwu.mobile.finalproject.ma02_20200987;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM Place")
    Flowable<List<Place>> getAllPlaces();

    @Insert
    Single<Long> insertPlace(Place place);

    @Update
    Completable updatePlace(Place place);

    @Delete
    Completable deletePlace(Place place);

    @Query("DELETE FROM place WHERE id= :id")
    Completable delete(long id);

    @Query("SELECT * FROM Place WHERE id= :id")
    Flowable<Place> getPlaceById(long id);

    @Query("UPDATE place SET memo = :memo WHERE id= :id")
    Completable updateMemo(long id, String memo);
}