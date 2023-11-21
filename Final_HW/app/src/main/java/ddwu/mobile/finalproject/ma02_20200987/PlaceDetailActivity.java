package ddwu.mobile.finalproject.ma02_20200987;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PlaceDetailActivity extends AppCompatActivity {
    final static String TAG = "PlaceDetailActivity";

    String id;
    String name;
    String phone;
    String address;
    String types;
    String url;

    String type;

    TextView nameTv;
    TextView phoneNum;
    TextView addressTv;
    TextView placeType;
    TextView URL;

    PlaceDB placeDB;
    PlaceDao placeDao;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");

        if(intent.getStringExtra("phone") != null) {
            phone = intent.getStringExtra("phone");
        }

        address = intent.getStringExtra("address");
        types = intent.getStringExtra("type");

        if(intent.getStringExtra("URL") != null) {
            url = intent.getStringExtra("URL");
        }

        Log.d("id: ", id);
        Log.d("name: ", name);
        //Log.d("phone: ", phone);
        Log.d("address: ", address);
        Log.d("types: ", types);
        Log.d("url: ", url);


        nameTv = findViewById(R.id.nameTv);
        phoneNum = findViewById(R.id.phoneNum);
        addressTv = findViewById(R.id.addressTv);
        placeType = findViewById(R.id.placeType);
        URL = findViewById(R.id.URLTv);

        nameTv.setText(name);

        if(phone != null) {
            phoneNum.setText(phone);
        } else {
            phoneNum.setText("");
        }

        addressTv.setText(address);

        String[] array = types.split(",");
        type = array[0].substring(1) + ", " + array[1];
        placeType.setText(type);

        if(!url.equals("NULL")) {
            URL.setText(url);
        } else {
            URL.setText("");
        }

        placeDB = PlaceDB.getDatabase(this);
        placeDao = placeDB.placeDao();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                String memo = "";
                nameTv = findViewById(R.id.nameTv);
                phoneNum = findViewById(R.id.phoneNum);
                addressTv = findViewById(R.id.addressTv);
                placeType = findViewById(R.id.placeType);
                URL = findViewById(R.id.URLTv);
                Single<Long> insertResult = placeDao.insertPlace(new Place(id, nameTv.getText().toString(), phoneNum.getText().toString(), addressTv.getText().toString(), placeType.getText().toString(), URL.getText().toString(), memo));

                mDisposable.add(
                        insertResult.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(result -> Log.d(TAG, "Insertion success: " + result),
                                        throwable -> Log.d(TAG, "error")
                                ));

                Log.d("id: ", String.valueOf(insertResult));
                Toast.makeText(this, "Save Place information", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btnDelete:
                finish();
                break;
        }
    }
}
