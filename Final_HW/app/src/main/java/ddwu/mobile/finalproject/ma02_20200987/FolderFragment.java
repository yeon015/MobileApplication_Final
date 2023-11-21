package ddwu.mobile.finalproject.ma02_20200987;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FolderFragment extends Fragment {
    final static String TAG = "FolderFragment";

    ArrayList<Place> placeList;

    ArrayAdapter<Place> adapter;
    //PlaceAdapter adapter;
    ListView listView;

    PlaceDB placeDB;
    PlaceDao placeDao;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_folder, container, false);

        listView = view.findViewById(R.id.listView);
        //adapter = new PlaceAdapter(getActivity().getApplicationContext(), R.layout.place_adapter, new ArrayList<Place>());
        adapter = new ArrayAdapter<Place>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<Place>()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView)view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        listView.setAdapter(adapter);

        placeDB = PlaceDB.getDatabase(getActivity().getApplicationContext());
        placeDao = placeDB.placeDao();

        Flowable<List<Place>> resultContacts = placeDao.getAllPlaces();

        mDisposable.add(
                resultContacts.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(places -> {
                                    for (Place aPlace : places){
                                        Log.d(TAG, aPlace.toString());
                                        Log.d(TAG, "id: " + listView.getAdapter());
                                    }

                                    adapter.clear();
                                    adapter.addAll(places);
                                    //adapter.notifyDataSetChanged();
                                },
                                throwable -> Log.d(TAG, "error", throwable))    );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "id: " + listView.getAdapter());
                Log.d(TAG, "id: " + listView.getAdapter().getItem(i));
                Log.d(TAG, "id: " + listView.getAdapter().getItemId(i));

                Place place = (Place) listView.getAdapter().getItem(i);

                Intent intent = new Intent(getActivity().getApplicationContext(), PlaceContentFolderActivity.class);
                intent.putExtra("place", (Serializable) place);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }



}
