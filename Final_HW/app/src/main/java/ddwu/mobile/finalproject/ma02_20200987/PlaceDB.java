package ddwu.mobile.finalproject.ma02_20200987;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Place.class}, version = 1)
public abstract class PlaceDB extends RoomDatabase {
    public abstract PlaceDao placeDao();

    //Singleton 하나의 객체만 생성 유지.
    private static volatile PlaceDB INSTANCE;

    static PlaceDB getDatabase(final Context context) {
        if (INSTANCE == null) {   //만들어진 객체 있는지 검사. 없으면
            synchronized (PlaceDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlaceDB.class, "place_db.db").build();
                }
            }
        }
        return INSTANCE;  //있으면 있는거 반환
    }
}
