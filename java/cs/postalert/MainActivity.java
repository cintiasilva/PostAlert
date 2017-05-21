package cs.postalert;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
 //   public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    //declare variables
    //array list in order to store data for generating list items
    public static ArrayList<Data> listData = new ArrayList<>();
    public static ArrayList<String> listData2 = new ArrayList<>();
    public static ArrayAdapter adapter;
    ArrayList<Integer> selectedItems = new ArrayList<>();
    ListView list;
    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
     //   String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECEIVE_SMS},1);
            }
        }
        // pull the list view and set checkbox settings
        list = (ListView) findViewById(R.id.list);


        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_checked, listData2);
        list.setChoiceMode(list.CHOICE_MODE_MULTIPLE);
        list.setAdapter(adapter);
        listviewUpdater(MainActivity.this);
    }

    //keep list view updated
    public static void listviewUpdater(Context mContext) {
        listData2.clear();
        listData.clear();
        db = new DatabaseHandler(mContext);

        //  Log.d("Reading: ", "Reading all contacts..");
        List<Data> data = db.getAllData();

        for (Data cn : data) {
            listData2.add(0,cn.getMessage());
            listData.add(0,cn);
            adapter.notifyDataSetChanged();
        }
    }

    //delete data from listview
    public void delete(View v){
        //map integers to boolean
        SparseBooleanArray checked = list.getCheckedItemPositions();

        for (int i = 0; i < list.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                selectedItems.add(listData.get(i).getId());
       //         adapter.remove(checked.indexOfKey(i));
                db.deleteData(listData.get(i).getId());
            }
        }
        selectedItems.clear();
        listviewUpdater(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                else {
                    Toast.makeText(MainActivity.this, "PERMISSION DENIED TO READ INCOMING SMS", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onClick (View v){


    }
}
