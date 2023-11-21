package ddwu.mobile.finalproject.ma02_20200987;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment searchFragment;
    Fragment folderFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        folderFragment = new FolderFragment();

        // 하단 바
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, homeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, homeFragment).commit();
                                return true;
                            case R.id.menu_search:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, searchFragment).commit();
                                return true;
                            case R.id.menu_folder:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, folderFragment).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );

    }
    public void onFragmentChange() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, folderFragment).commit();
    }
}