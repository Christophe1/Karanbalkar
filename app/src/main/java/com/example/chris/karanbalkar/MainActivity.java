package com.example.chris.karanbalkar;

import java.util.Date;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.TextView;

public class MainActivity extends Activity
{

    private TextView tv;
    private String callType;
    private String phoneNumber;
    private String callDate;
    private String callDuration;
    private Date callDateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tv= (TextView)findViewById(R.id.calllog);

        getCallDetails();

    }


    public void getCallDetails()
    {

//        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI, null, null, null,CallLog.Calls.DATE + " DESC");
//        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
//                null, null, "COLUMN_FOR_SORT DESC");

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);

        StringBuilder sb = new StringBuilder();
        while (managedCursor.moveToNext()) {

            phoneNumber = managedCursor.getString(number);
            callType = managedCursor.getString(type);
            callDate = managedCursor.getString(date);

            callDateTime = new Date(Long.valueOf(callDate));

            callDuration = managedCursor.getString(duration);

            String cType = null;

            int cTypeCode = Integer.parseInt(callType);

            switch (cTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    cType = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    cType = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    cType = "MISSED";
                    break;
            }

            //if the number has a name in the person's phone then use the name instead of the number
            String numberString = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.NUMBER));

            String nameString = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME));

            if (nameString == null) {
                phoneNumber = numberString;
            } else {
                phoneNumber = nameString;
            }

            sb.append("Phone number" + phoneNumber);
            sb.append("Call Type" + cType);
            sb.append("Date" + callDateTime);
            sb.append("Call duration" + callDuration);
            sb.append("--------------");
            sb.append(System.getProperty("line.separator"));

        }

        TextView callDetails = (TextView) findViewById(R.id.calllog);
        callDetails.setText(sb.toString());

//            tv.setText("\nPhone Number:--- " + phoneNumber +
//                    "\nCall Type:--- " + cType + " " +
//                    "\nCall Date:--- " + callDateTime + " " +
//                    "\nCall duration in sec :--- " + callDuration);


        }

//       managedCursor.close();
    }

