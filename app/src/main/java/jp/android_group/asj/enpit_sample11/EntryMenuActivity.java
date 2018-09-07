package jp.android_group.asj.enpit_sample11;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.android_group.asj.enpit_sample11.model.EntryModel;

public class EntryMenuActivity extends Activity {
    /**
     * ログ出力用
     */
    private static final String TAG = EntryMenuActivity.class.getName().toString();

    private ListView listView;

    private static int mAutherUserID = 0;
    private static String mAutherUserName = "";
    private static String mServerIP = "";
    private static ArrayList<EntryModel> mEntryModels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_menu);

        listView = findViewById(R.id.lv_entry_title);

        Intent intent = getIntent();
        mAutherUserID = intent.getIntExtra(Constants.EXTRA_USER_ID, 0);
        mAutherUserName = intent.getStringExtra(Constants.EXTRA_USER_NAME);
        mServerIP = intent.getStringExtra(Constants.EXTRA_SERVER_IP);
        if (mAutherUserID == 0) {
            Toast.makeText(this, R.string.no_login_error, Toast.LENGTH_LONG);
        }
        GetEntryModels();
    }

    private void GetEntryModels() {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "GetEntryModels() start");
        }

        Connecting2Server connecting2Server = new Connecting2Server(EntryMenuActivity.this);
        connecting2Server.setOnCallBack(new Connecting2Server.CallBackTask() {
            @Override
            public void CallBack(int result) {
                super.CallBack(result);
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "GetEntryModels()  Call Back Success.");
                    Log.i(TAG, "GetEntryModels()  mAutherUserID:" + String.valueOf(mAutherUserID)
                            + ", mAutherUserName:" + mAutherUserName);
                }
                // ListView設定
                EntryAdapter entryAdapter = new EntryAdapter(
                        EntryMenuActivity.this,
                        mEntryModels, // 使用するデータ
                        R.layout.entry_list_layout // 自作したレイアウト
                );
                listView.setAdapter(entryAdapter);
            }
        });
        connecting2Server.execute(Constants.ACCESS_TYPE_GET_ENTRY, mServerIP, String.valueOf(mAutherUserID));
        return;
    }

    /**
     * サーバへの問い合わせ非同期クラス
     */
    private static class Connecting2Server extends AsyncTask<String, Integer, Boolean> {
        private Context context;
        private ProgressDialog mProgressDialog;
        private Boolean isShowProgress;
        private Connecting2Server.CallBackTask callbacktask;
        private int id = 0;

        /**
         * コンストラクタ
         *
         * @param context
         */
        public Connecting2Server(Context context) {
            this.context = context;
        }

        /**
         * getIsShowProgress
         * プログレスが表示中かどうかを返却
         *
         * @return
         */
        public Boolean getIsShowProgress() {
            return isShowProgress;
        }

        /**
         * onPreExecute
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isShowProgress = true;
            showDialog();
        }

        /**
         * doInBackground
         *
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(String... params) {
            ArrayList<EntryModel> entryModels = ServerAccessUtil.AccessServerEntry(context, params[0], params[1], params[2]);
            if ((entryModels == null) || entryModels.size() == 0)
                return false;
            mEntryModels = entryModels;
            return true;
        }

        /**
         * onPostExecute
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            dismissDialog();
            isShowProgress = false;
            callbacktask.CallBack(-1);
        }

        /**
         * showDialog
         * ダイアログ表示
         */
        public void showDialog() {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getResources().getString(R.string.connect_Server));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        /**
         * dismissDialog
         */
        public void dismissDialog() {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        /**
         * @param callBackTask
         */
        public void setOnCallBack(Connecting2Server.CallBackTask callBackTask) {
            callbacktask = callBackTask;
        }

        /**
         * コールバック用のstaticなclass
         */
        public static class CallBackTask {
            public void CallBack(int result) {
            }
        }
    }

    private class EntryAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<EntryModel> mEntryModels;
        private int resource = 0;

        public EntryAdapter(Context context, ArrayList<EntryModel> entryModels, int resource) {
            mContext = context;
            mEntryModels = entryModels;
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return mEntryModels.size();
        }

        @Override
        public EntryModel getItem(int position) {
            return mEntryModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mEntryModels.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Activity activity = (Activity) mContext;
            EntryModel entryModel = getItem(position);
            if (convertView == null) {
                convertView = activity.getLayoutInflater().inflate(resource, null);
            }

            ((TextView) convertView.findViewById(R.id.mainText)).setText(entryModel.getTitle());

            return convertView;
        }
    }

}
