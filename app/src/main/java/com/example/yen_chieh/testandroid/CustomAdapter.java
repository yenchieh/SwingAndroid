package com.example.yen_chieh.testandroid;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yen-chieh on 6/12/16.
 */
public class CustomAdapter extends BaseAdapter {
    List<BluetoothDevice> devices = new ArrayList<>();
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapter(BleDebuggerActivity mainActivity, List<BluetoothDevice> device) {
        // TODO Auto-generated constructor stub
        this.devices = device;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView name;
        TextView uuid;
        TextView rssi;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.device_list_view, null);
        holder.name = (TextView) rowView.findViewById(R.id.deviceName);
        holder.uuid = (TextView) rowView.findViewById(R.id.deviceUuid);
        holder.rssi = (TextView) rowView.findViewById(R.id.deviceRssi);
        holder.name.setText(devices.get(position).getName());
        holder.uuid.setText(devices.get(position).getAddress());
        holder.rssi.setText("None");

/*        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }

}
