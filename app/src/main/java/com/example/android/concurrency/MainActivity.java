package com.example.android.concurrency;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import static android.provider.ContactsContract.*;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "CodeRunner";
    private static final int REQUEST_CODE_PERMISSION = 1001;

    // View object references
    private ListView mListView;
    private boolean mPermissionGranted;

    private static final String[] PROJECTION = {
            Contacts._ID,
            Contacts.LOOKUP_KEY,
            Contacts.DISPLAY_NAME_PRIMARY
    };

    private static final String[] FROM_COLUMNS = {
            Contacts.DISPLAY_NAME_PRIMARY
    };

    private static final int[] TO_VIEWS = {
            android.R.id.text1
    };

    private CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the logging components
        mListView = (ListView) findViewById(R.id.list_view);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            mPermissionGranted = true;
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            loadContactsData();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            mPermissionGranted = true;
            loadContactsData();
        }
    }

    private void loadContactsData() {
        if (mPermissionGranted) {
            mCursorAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    null, FROM_COLUMNS, TO_VIEWS, 0);
            mListView.setAdapter(mCursorAdapter);
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this, Contacts.CONTENT_URI,
                PROJECTION, null, null, Contacts.DISPLAY_NAME_PRIMARY
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}