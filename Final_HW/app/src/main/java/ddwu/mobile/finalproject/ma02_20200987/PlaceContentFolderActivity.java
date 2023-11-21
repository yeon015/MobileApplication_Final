package ddwu.mobile.finalproject.ma02_20200987;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlaceContentFolderActivity extends AppCompatActivity {
    final static String TAG = "PlaceContentFolderActivity";

    Place place;

    PlaceDB placeDB;
    PlaceDao placeDao;

    TextView nameTv;
    TextView phoneNum;
    TextView addressTv;
    TextView placeType;
    TextView URL;

    EditText memo;

    Flowable<Place> resultPlace;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        nameTv = findViewById(R.id.nameTv);
        phoneNum = findViewById(R.id.phoneNum);
        addressTv = findViewById(R.id.addressTv);
        placeType = findViewById(R.id.placeType);
        URL = findViewById(R.id.URLTv);
        memo = findViewById(R.id.memo);

        Intent intent = getIntent();
        place = (Place) intent.getSerializableExtra("place");

        placeDB = PlaceDB.getDatabase(this);
        placeDao = placeDB.placeDao();

        resultPlace = placeDao.getPlaceById(place.get_Id());

        mDisposable.add( resultPlace
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(place -> {
                            nameTv.setText(place.name);
                            phoneNum.setText(place.phoneNumber);
                            addressTv.setText(place.address);
                            placeType.setText(place.type);
                            URL.setText(place.uri);
                            memo.setText(place.memo);
                        },
                        throwable -> Log.d(TAG, "error", throwable)) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                memo = findViewById(R.id.memo);
                Log.d("memo: ", String.valueOf(memo));
                Completable updateResult = placeDao.updateMemo(place.get_Id(), memo.getText().toString());

                Disposable disposable = updateResult
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> Log.d(TAG, "Update success: "),
                                throwable -> Log.d(TAG, "error"));

                disposable.dispose();

                mDisposable.add(updateResult
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> Log.d(TAG, "Update success: "),
                                throwable -> Log.d(TAG, "error")) );
                finish();
                break;
            case R.id.btnDelete:
                Completable deleteResult = placeDao.delete(place.get_Id());
                mDisposable.add(deleteResult
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() ->
                                        Log.d(TAG, "Delete success: "),
                                throwable -> Log.d(TAG, "error")) );
                finish();
                break;
        }
    }
}
